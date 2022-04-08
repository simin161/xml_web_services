package service;

import beans.*;
import beans.Certificate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.KeyStoreNameDAO;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import sun.security.tools.keytool.CertAndKeyGen;
import sun.security.x509.X500Name;

import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.X509Certificate;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CertificateService {
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-mm-dd").setPrettyPrinting().create();
    private UserService userService = new UserService();

    public boolean createCertificate(Certificate certificate){

        boolean returnValue = false;

        try{
            /*
            * Prvo -> pronaci keyStore za ubaciti sertifikat -> izvuci pomocu ser broja (ali samo ako je root, inace jbg) --- ODRADJENO
            * Drugo -> Kreiranje sertifikata/popunjavanje podacima (vrv manji problem)
            * Trece (vrv veci problem) -> Ubacivanje u chain
            * Cetvrto -> upis u keyStore
            */
            certificate.setRevocationStatus("OK");
            certificate.setCertificateStatus("OK");
            //CertificateDAO.getInstance().addCertificate(certificate);
            //String keyStoreName = findKeyStoreNameForCert(certificate.getPath());

            if(userService.checkExistanceOfEmail(certificate.getIssuerEmail())){
                CertAndKeyGen keyGen = new CertAndKeyGen("RSA", "SHA1WithRSA", certificate.getIssuerEmail());
                keyGen.generate(1024);

                X500NameBuilder nameBuilder = new X500NameBuilder(BCStyle.INSTANCE);
                nameBuilder.addRDN(BCStyle.UID, UUID.randomUUID().toString());
                nameBuilder.addRDN(BCStyle.CN, certificate.getCommonName());
                nameBuilder.addRDN(BCStyle.GIVENNAME, certificate.getGivenName());
                nameBuilder.addRDN(BCStyle.SURNAME, certificate.getSurname());
                nameBuilder.addRDN(BCStyle.O, certificate.getOrganization());
                nameBuilder.addRDN(BCStyle.OU, certificate.getOrganizationalUnitName());
                nameBuilder.addRDN(BCStyle.E, certificate.getOrganizationEmail());
                nameBuilder.addRDN(BCStyle.C, certificate.getCountry());
                nameBuilder.addRDN(BCStyle.PSEUDONYM, certificate.getAlias());

                String serialNumber = String.valueOf(LocalDateTime.now().hashCode());
                if(serialNumber.contains("-"))
                    serialNumber = serialNumber.replace("-","0");

                LocalDate date = LocalDate.parse(certificate.getValidTo(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                SubjectData subjectData = new SubjectData(keyGen.getPublicKey(), nameBuilder.build(), serialNumber, LocalDate.now(), date );
                subjectData.setUserEmail(certificate.getIssuerEmail());

                X509Certificate x509Certificate;
                IssuerData issuerData;

                //KeyStoreReader ksr = new KeyStoreReader();

                issuerData = getIssuerBySerialNum(certificate.getIssuerSerialNum(), certificate.getIssuerAlias());

                //fali provera za validnost certifikata issuera ukoliko nije root certifikat a mozda bi mogle i ekstemzije ovde da se srede
                x509Certificate = new CertificateGenerator().generateCertificate(subjectData, issuerData);

                //sad smisliti kako da se storuje novokreirani (hopefully) sertifikat u korespodentni keystore
                if(certificate.getType().equals("ROOT")){
                    //TODO: nesto zasebno? msm kreiranje novog keystore-a etc etc (al tu je i drugaciji postupak za generisanje sertifikata tkd BICE IZMENA
                }
                else{
                    //ako preko issuer-a dodjem do njegovog sertifikata tehnicki ja mogu da pronadjem iz toga kom keystoreu pripada?
                    KeyStore store = findStoreByIssuer(certificate);
                   // store.setKeyEntry(certificate.getAlias(), keyGen.getPrivateKey(), "password".toCharArray(), x509Certificate);
                }

            }

            //X509Certificate topLevelCert = gson.fromJson((X509Certificate) gson.fromJson(certificate.getPath(), X509Certificate.class));
            returnValue = true;

        }catch(Exception e){
            returnValue = false;
        }

        return returnValue;

    }

    //ja se najiskrenije nadam da je ovo dobro jtzm
    public KeyStore findStoreByIssuer(Certificate cert) {

        try {
            KeyStore store;
            for (KeyStore s : getAllKeyStores()) {
                Enumeration<String> certificateAliases = s.aliases();
                while (certificateAliases.hasMoreElements()) {
                    String alias = certificateAliases.nextElement();
                    if (s.isKeyEntry(alias)) {
                        if(Objects.equals(((X509Certificate) s.getCertificate(alias)).getSerialNumber(), new BigInteger(cert.getIssuerSerialNum()))){
                            return s;
                        }
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }
    public IssuerData getIssuerBySerialNum(String issuerSerialNum, String issuerAlias) {

        //KeyStoreReader ksr = new KeyStoreReader();
        BigInteger serialNum= new BigInteger(issuerSerialNum);
        X509Certificate x509Certificate = findCertBySerNum(serialNum);
        if(x509Certificate != null){
            return new IssuerData(getPrivateKey(issuerAlias), getX500Name(x509Certificate));
        }else{
            return null;
        }

    }

    public org.bouncycastle.asn1.x500.X500Name getX500Name(X509Certificate cert){
        org.bouncycastle.asn1.x500.X500Name name;
        try{
            name = new JcaX509CertificateHolder(cert).getSubject();
            return name;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public PrivateKey getPrivateKey(String alias){
        try{
                List<KeyStore> stores = getAllKeyStores();
                for(KeyStore store : stores){
                    PrivateKey pk = (PrivateKey) store.getKey(alias, "password".toCharArray());
                    if(pk != null){
                        return pk;
                    }
                }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public void createSSCertificate(User user){
        try{
            CertAndKeyGen keyGen=new CertAndKeyGen("RSA","SHA1WithRSA",user.getEmail());
            keyGen.generate(1024);

            //Generate self signed certificate
            X509Certificate[] chain=new X509Certificate[1];
            chain[0]=keyGen.getSelfCertificate(new X500Name( "CN=" + user.getEmail()
                                                              + ", OU=" + user.getOrganizationUnit()
                                                              + ", O=" + user.getOrganizationName()
                                                              + ", C=" + user.getCountryId()), (long)365*24*3600);


            System.out.println("Potpis: " + chain[0].getSignature());
            System.out.println("Certificate before storing : "+chain[0].toString());
            KeyStoreWriter storeWriter = new KeyStoreWriter();
            String pass = "password";
            char []password = new char[pass.length()];
            for(int i=0; i < pass.length(); i++)
                password[i] = pass.charAt(i);

            storeWriter.loadKeyStore(null, password);
            storeWriter.write(user.getEmail(), keyGen.getPrivateKey(), password, chain[0]);
            storeWriter.saveKeyStore(chain[0].getSerialNumber().toString(), password);
            Names name = new Names();
            name.name = chain[0].getSerialNumber().toString();
            KeyStoreNameDAO.getInstance().getAllNames().add(name);
            KeyStoreNameDAO.getInstance().save();
            KeyStoreReader storeReader = new KeyStoreReader();

            System.out.println("Certificate after storing: "+storeReader.readCertificate(chain[0].getSerialNumber().toString(), pass, user.getEmail()).toString());

        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public X509Certificate findCertBySerNum(BigInteger a){
        for(X509Certificate c : getAllCerts("aaa")){
            if(c.getSerialNumber() == a){
                return c;
            }
        }
        return null;
    }

    public List<KeyStore> getAllKeyStores(){
        List<KeyStore> keystores = new ArrayList<KeyStore>();
        List<Names> names = KeyStoreNameDAO.getInstance().getAllNames();
        String password = "password";
        try{
            for(Names n : names){
                if (new File(n.name).exists()){
                    KeyStore store;
                    store = KeyStore.getInstance("JKS");
                    store.load(new FileInputStream(n.name), password.toCharArray());
                    keystores.add(store);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return keystores;
    }

    public String findKeyStoreNameForCert(BigInteger a){
        List<KeyStore> keystores = new ArrayList<KeyStore>();
        List<Names> keystoreNames = KeyStoreNameDAO.getInstance().getAllNames();
        String password = "password";
        try {
            for (Names name : keystoreNames) {
                if (new File(name.name).exists()) {
                    KeyStore store;
                    store = KeyStore.getInstance("JKS");
                    store.load(new FileInputStream(name.name), password.toCharArray());
                    Enumeration<String> certificateAliases = store.aliases();
                    while (certificateAliases.hasMoreElements()) {
                        String alias = certificateAliases.nextElement();
                        if (store.isKeyEntry(alias)) {
                            if(Objects.equals(((X509Certificate) store.getCertificate(alias)).getSerialNumber(), a)){
                                return name.name;
                            }
                        }
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public List<X509Certificate> getAllCerts(String password) {


        List<KeyStore> keystores = new ArrayList<KeyStore>();
        List<Names> keystoreNames = KeyStoreNameDAO.getInstance().getAllNames();
        password = "password";
        try {
            for (Names name : keystoreNames) {
                if (new File(name.name).exists()) {
                    KeyStore store;
                    store = KeyStore.getInstance("JKS");
                    store.load(new FileInputStream(name.name), password.toCharArray());
                    keystores.add(store);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        List<X509Certificate> allCertificates = new ArrayList<X509Certificate>();
        try{
            for(KeyStore store : keystores){
                getCertsFromKeystore(allCertificates, store);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return allCertificates;

        /*List<String> certificates = new ArrayList<String>();

       // KeyStoreReader reader = new KeyStoreReader();
        //while(reader.readCertificate("vaultSupreme", password, ) != null){

        //}
        KeyStore ks = KeyStore.getInstance("JKS", "SUN");
        BufferedInputStream in = new BufferedInputStream(new FileInputStream("proba"));
        ks.load(in, password.toCharArray());

        String []keystoreNames = new String[ks.size()];

        System.out.println(ks.aliases().toString());

        return null;*/

    }

    //cita sertifikate iz navedenog keystore-a
    private void getCertsFromKeystore(List<X509Certificate> allCerts, KeyStore keyStore){

        try {
            Enumeration<String> certificateAliases = keyStore.aliases();
            while(certificateAliases.hasMoreElements()){
                String alias = certificateAliases.nextElement();
                if(keyStore.isKeyEntry(alias)){
                    allCerts.add((X509Certificate) keyStore.getCertificate(alias));
                    System.out.println("-------------------POTPIS: " + ((X509Certificate) keyStore.getCertificate(alias)).getSignature() + " ----------------" );
                    System.out.println("VAS SERTIFIKAT HOPEFULLY " + ((X509Certificate) keyStore.getCertificate(alias)).toString());

                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public List<String> getAllCertsSerNums() {
        List<String> retVal = new ArrayList<String>();

        //mozda da ne povlacimo serijske brojeve za front nego imena/aliase sertifikata?
        for(X509Certificate cert : getAllCerts("password")){
            retVal.add(cert.getSerialNumber().toString());
        }

        return retVal;
    }

    // List keystore
   /* public static void list(){
        String command = " -list "+
                " -v "+
                " -keystore proba.jks "+
                " -storepass password";
        execute(command);
    }
    public static void execute(String command){
        try{
            printCommand(command);
            sun.security.tools.keytool.Main.main(parse(command));
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
    // Parse command
    private static String[] parse(String command){
        String[] options = command.trim().split(" ");
        return options;
    }

    // Print the command
    private static void printCommand(String command){
        System.out.println(command);
    }
*/

}
