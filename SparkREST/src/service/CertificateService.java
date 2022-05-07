package service;

import beans.*;
import beans.Certificate;
import beans.enums.StatusType;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.CertificateStatusDAO;
import dao.KeyStoreNameDAO;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.eclipse.jetty.util.ssl.X509;
import sun.security.tools.keytool.CertAndKeyGen;
import sun.security.x509.X500Name;

import javax.lang.model.element.Name;
import javax.security.auth.x500.X500Principal;
import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.X509Certificate;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
            * Drugo -> Kreiranje sertifikata/popunjavanje podacima (vrv manji problem) --- ODRADJENO
            * Trece (vrv veci problem) -> Ubacivanje u chain --- ODRADJENO --- ZAPRAVO NIJE ODRADJENO< ALI NIJE NUZNO
            * Cetvrto -> upis u keyStore --- ODRADJENO
            */
            certificate.setRevocationStatus("OK");
            certificate.setCertificateStatus("OK");
            //CertificateDAO.getInstance().addCertificate(certificate);
            //String keyStoreName = findKeyStoreNameForCert(certificate.getPath());

            if(userService.checkExistanceOfEmail(certificate.getIssuerEmail())){
                CertAndKeyGen keyGen = new CertAndKeyGen("RSA", "SHA1WithRSA", certificate.getIssuerEmail());
                keyGen.generate(1024);
                String fileName;
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
                if(!certificate.getType().equals("ROOT")) {
                    X509Certificate choosenCertificate = findCertBySerNum(certificate.getPath());
                    if (choosenCertificate.getNotAfter().before(Date.from(date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())))
                        return false;
                }
                SubjectData subjectData = new SubjectData(keyGen.getPublicKey(), nameBuilder.build(), serialNumber, LocalDate.now(), date );
                subjectData.setUserEmail(certificate.getIssuerEmail());

                X509Certificate x509Certificate;
                IssuerData issuerData;

                if(!certificate.getType().equals("ROOT")) {
                    //KeyStoreReader ksr = new KeyStoreReader();
                    X509Certificate issuerCert = findCertBySerNum(certificate.getPath());
                    KeyStore store;
                    store = KeyStore.getInstance("JKS");
                    fileName = findKeyStoreNameForCert(issuerCert.getSerialNumber());
                    store.load(new FileInputStream(fileName), "password".toCharArray());
                    issuerData = getIssuerBySerialNum(certificate.getPath().toString(), store.getCertificateAlias(issuerCert));
                }else{
                    fileName = serialNumber;
                    issuerData = new IssuerData(keyGen.getPrivateKey(), nameBuilder.build());
                }
                //fali provera za validnost certifikata issuera ukoliko nije root certifikat a mozda bi mogle i ekstemzije ovde da se srede
                x509Certificate = new CertificateGenerator().generateCertificate(subjectData, issuerData, certificate.getType().equals("ROOT") || certificate.getType().equals("INTERMEDIATE")
                , certificate.getKeyUsage());

                System.out.println("NOVI ???? : " + x509Certificate.toString());
                KeyStoreWriter storeWriter = new KeyStoreWriter();
                String pass = "password";
                char []password = new char[pass.length()];
                for(int i=0; i < pass.length(); i++)
                    password[i] = pass.charAt(i);
                if(!certificate.getType().equals("ROOT")) {
                    storeWriter.loadKeyStore(fileName, password);
                    storeWriter.write(certificate.getAlias(), keyGen.getPrivateKey(), password, x509Certificate);
                    storeWriter.saveKeyStore(fileName, password);
                    CertificateStatusDAO.getInstance().addStatus(new CertificateStatus(x509Certificate.getSerialNumber(), StatusType.VALID));
                    CertificateStatusDAO.getInstance().save();
                }else{
                    X509Certificate[] chain=new X509Certificate[1];
                    chain[0]= x509Certificate;
                    storeWriter.loadKeyStore(null, password);
                    storeWriter.write(certificate.getAlias(), keyGen.getPrivateKey(), password, chain[0]);
                    storeWriter.saveKeyStore(fileName, password);
                    Names name = new Names();
                    name.name = fileName;
                    KeyStoreNameDAO.getInstance().getAllNames().add(name);
                    KeyStoreNameDAO.getInstance().save();
                    CertificateStatusDAO.getInstance().addStatus(new CertificateStatus(chain[0].getSerialNumber(), StatusType.VALID));
                    CertificateStatusDAO.getInstance().save();
                }
            }
            returnValue = true;

        }catch(Exception e){
            returnValue = false;
        }

        return returnValue;

    }

    public String loadCertificateToFile(String serialNumber) throws Exception {

        Base64.Encoder encoder = Base64.getMimeEncoder(64, System.getProperty("line.separator").getBytes());

        byte[] bytes = findCertBySerNum(new BigInteger(serialNumber)).getEncoded();

        String certificate =  "-----BEGIN CERTIFICATE-----" + System.getProperty("line.separator") + new String(encoder.encode(bytes)) + System.getProperty("line.separator")
                + "-----END CERTIFICATE-----";
        String path = "static/certificates/"+serialNumber + ".cer";
        writeBytesToFile(path, certificate.getBytes());
        return "./" + path;

    }

    private void writeBytesToFile(String fileOutput, byte[] bytes) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(fileOutput)) {
            fos.write(bytes);
        }
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

    public X509Certificate findCertBySerNum(BigInteger a){
        for(X509Certificate c : getAllCerts("aaa")){
            if(Objects.equals(c.getSerialNumber(), a)){
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
            int i = 0;
            for(KeyStore store : keystores){
                System.out.println("KEY STORE [" + i + "]");
                getCertsFromKeystore(allCertificates, store);
                ++i;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return allCertificates;
    }

    public List<X509Certificate> getAllNonEE(){
        List<X509Certificate> retVal = new ArrayList<>();
        for(X509Certificate c : getAllCerts("")){
            if(c.getBasicConstraints() != -1){
                retVal.add(c);
            }
        }
        return retVal;
    }

    //cita sertifikate iz navedenog keystore-a
    private void getCertsFromKeystore(List<X509Certificate> allCerts, KeyStore keyStore){

        try {
            System.out.println("\tCERTIFICATES IN KEYSTORE: ");
            Enumeration<String> certificateAliases = keyStore.aliases();
            while(certificateAliases.hasMoreElements()){
                String alias = certificateAliases.nextElement();
                if(keyStore.isKeyEntry(alias)){

                    if(checkCertificateStatus(((X509Certificate)keyStore.getCertificate(alias)).getSerialNumber())){
                        allCerts.add((X509Certificate) keyStore.getCertificate(alias));
                        System.out.println(((X509Certificate) keyStore.getCertificate(alias)).toString());
                        System.out.println("BASIC CONST: " + ((X509Certificate) keyStore.getCertificate(alias)).getBasicConstraints());
                    }else{
                        System.out.println("SERT NIJE VALIDAN");
                    }
                }
            }
            System.out.println("-----------------------------------------------------------------------------------");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private boolean checkCertificateStatus(BigInteger serialNumber){

        return CertificateStatusDAO.getInstance().checkCertificateStatus(serialNumber);

    }

    public List<CertificateView> getAllCertsForSubjectWithoutEE(String email) {
        List<CertificateView> retVal = new ArrayList<>();

        //mozda da ne povlacimo serijske brojeve za front nego imena/aliase sertifikata? Dobro pitanje
        for(X509Certificate cert : getAllNonEE()){
            String emailToCheck = cert.getSubjectDN().getName().split(",")[2].split("=")[1];
            if(emailToCheck.equals(email))
                retVal.add(new CertificateView(cert.getIssuerDN().toString(), cert.getSubjectDN().toString(), cert.getSerialNumber().toString(), cert.getSigAlgName(), String.valueOf(cert.getVersion()), cert.getPublicKey().toString(),
                        cert.getNotBefore().toString(), cert.getNotAfter().toString(), cert.getSignature().toString(), getAliasForCert( cert.getSerialNumber())));
        }

        return retVal;
    }

    public List<CertificateView> getAllCertsForUser(String email) {
        List<CertificateView> retVal = new ArrayList<>();
        for(X509Certificate cert : getAllCerts("password")){
            String emailToCheck = cert.getSubjectDN().getName().split(",")[2].split("=")[1];
            String emailToCheckIssuer = cert.getIssuerDN().getName().split(",")[2].split("=")[1];
//String issuerDN, String subjectDN, String isValid, String serialNumber, String signatureAlg, String version, String publicKey, String dateFrom, String dateTo, String signature) {

            if(emailToCheck.equals(email) || emailToCheckIssuer.equals(email))
                retVal.add(new CertificateView(cert.getIssuerDN().toString(), cert.getSubjectDN().toString(), cert.getSerialNumber().toString(), cert.getSigAlgName(), String.valueOf(cert.getVersion()), cert.getPublicKey().toString(),
                cert.getNotBefore().toString(), cert.getNotAfter().toString(), cert.getSignature().toString(), getAliasForCert( cert.getSerialNumber())));
        }
        return retVal;
    }

    public  String getAliasForCert(BigInteger serialNum){
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
                            if(Objects.equals(((X509Certificate) store.getCertificate(alias)).getSerialNumber(), serialNum)){
                                return alias;
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

    public List<CertificateView> getCertsAbove(String cCert) {
        List<CertificateView> retVal = new ArrayList<>();
        BigInteger serialNumber = new BigInteger(cCert);
        X509Certificate certificateBellow = findCertBySerNum(serialNumber);
        List<X509Certificate> certs = getAllCerts("a");
       if(certificateBellow.getSubjectDN().equals(certificateBellow.getIssuerDN())) {
             retVal.add(new CertificateView(certificateBellow.getIssuerDN().toString(), certificateBellow.getSubjectDN().toString(), certificateBellow.getSerialNumber().toString(), certificateBellow.getSigAlgName(), String.valueOf(certificateBellow.getVersion()), certificateBellow.getPublicKey().toString(),
                     certificateBellow.getNotBefore().toString(), certificateBellow.getNotAfter().toString(), certificateBellow.getSignature().toString(), getAliasForCert( certificateBellow.getSerialNumber())));
           return retVal;
       }
        boolean refresh = false;
        for(int i = 0; i < certs.size(); ++i){
            if(refresh) {
                i = 0;
                refresh = false;
            }
            X509Certificate cert = certs.get(i);
            if(cert.getSubjectDN().equals(certificateBellow.getIssuerDN())){
                retVal.add(new CertificateView(cert.getIssuerDN().toString(), cert.getSubjectDN().toString(), cert.getSerialNumber().toString(), cert.getSigAlgName(), String.valueOf(cert.getVersion()), cert.getPublicKey().toString(),
                        cert.getNotBefore().toString(), cert.getNotAfter().toString(), cert.getSignature().toString(), getAliasForCert( cert.getSerialNumber())));
                certificateBellow = cert;
                if(certificateBellow.getSubjectDN().equals(certificateBellow.getIssuerDN())){
                    break;
                }
                refresh = true;
                i = 0;
            }
        }
        List<CertificateView> reverseRetVal = new ArrayList<>();
        for(int i = retVal.size() - 1; i >= 0; --i){
            reverseRetVal.add(retVal.get(i));
        }
        X509Certificate cert = findCertBySerNum(serialNumber);
        reverseRetVal.add(new CertificateView(cert.getIssuerDN().toString(), cert.getSubjectDN().toString(), cert.getSerialNumber().toString(), cert.getSigAlgName(), String.valueOf(cert.getVersion()), cert.getPublicKey().toString(),
                cert.getNotBefore().toString(), cert.getNotAfter().toString(), cert.getSignature().toString(), getAliasForCert( cert.getSerialNumber())));
        return reverseRetVal;
    }

    public List<CertificateView> getAllCertsForAdmin() {
        List<CertificateView> retVal = new ArrayList<>();
        for(X509Certificate cert : getAllCerts("password")){
            retVal.add(new CertificateView(cert.getIssuerDN().toString(), cert.getSubjectDN().toString(), cert.getSerialNumber().toString(), cert.getSigAlgName(), String.valueOf(cert.getVersion()), cert.getPublicKey().toString(),
                    cert.getNotBefore().toString(), cert.getNotAfter().toString(), cert.getSignature().toString(), getAliasForCert( cert.getSerialNumber())));
        }
        return retVal;

    }

    public boolean checkIfUserHasCA(String email) {
        List<CertificateView> retVal = new ArrayList<>();
        for(X509Certificate cert : getAllCerts("password")){
            String emailToCheck = cert.getSubjectDN().getName().split(",")[2].split("=")[1];

            if(emailToCheck.equals(email) && cert.getBasicConstraints() != -1)
                return true;
        }
        return false;
    }

    public boolean invalidateCertificate(CertificateView certificateView){

       // boolean retVal = CertificateStatusDAO.getInstance().invalidateCertificate(certificateView.getSerialNumber());
       // if(retVal){
            return invalidateBelow(certificateView.getSerialNumber());
       // }

      //  return false;

    }

    private boolean invalidateBelow(String serialNumber) {

        X509Certificate certificate = findCertBySerNum(new BigInteger(serialNumber));
        List<X509Certificate> allCertificates = getAllCerts("a");
        boolean refresh = false;
        for(int i = 0; i < allCertificates.size(); ++i){
            if(refresh) {
                i = 0;
                refresh = false;
            }
            X509Certificate c = allCertificates.get(i);
            if(certificate.getSubjectDN().equals(c.getIssuerDN()) && !CertificateStatusDAO.getInstance().isInvalid(c.getSerialNumber())){
                CertificateStatusDAO.getInstance().invalidateCertificate(c.getSerialNumber().toString());
                certificate = c;
                refresh = true;
                i = 0;

            }
        }
        CertificateStatusDAO.getInstance().invalidateCertificate(serialNumber); //to invalidate the passed one
        return true;

    }


    public CertificateView getCertBySerNumView(String fromJson) {
        X509Certificate cert = findCertBySerNum(new BigInteger(fromJson));
       return new CertificateView(cert.getIssuerDN().toString(), cert.getSubjectDN().toString(), cert.getSerialNumber().toString(), cert.getSigAlgName(), String.valueOf(cert.getVersion()), cert.getPublicKey().toString(),
                cert.getNotBefore().toString(), cert.getNotAfter().toString(), cert.getSignature().toString(), getAliasForCert( cert.getSerialNumber()));
    }
}
