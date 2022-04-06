package service;

import beans.Certificate;
import beans.User;
import dao.CertificateDAO;
import sun.security.tools.keytool.CertAndKeyGen;
import sun.security.x509.X500Name;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

public class CertificateService {

    public boolean createCertificate(Certificate certificate){

        boolean returnValue = false;

        try{
            certificate.setRevocationStatus("OK");
            certificate.setCertificateStatus("OK");
            CertificateDAO.getInstance().addCertificate(certificate);
            returnValue = true;

        }catch(Exception e){
            returnValue = false;
        }

        return returnValue;

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



            System.out.println("Certificate before storing : "+chain[0].toString());

            KeyStoreWriter storeWriter = new KeyStoreWriter();
            String pass = "password";
            char []password = new char[pass.length()];
            for(int i=0; i < pass.length(); i++)
                password[i] = pass.charAt(i);

            storeWriter.loadKeyStore(null, password);
            storeWriter.write(user.getEmail(), keyGen.getPrivateKey(), password, chain[0]);
            storeWriter.saveKeyStore("proba", password);

            KeyStoreReader storeReader = new KeyStoreReader();

            System.out.println("Certificate after storing: "+storeReader.readCertificate("proba", pass, user.getEmail()).toString());


        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public List<String> getAllCerts(String password) throws KeyStoreException, NoSuchProviderException, IOException, CertificateException, NoSuchAlgorithmException {

        List<String> certificates = new ArrayList<String>();

        KeyStoreReader reader = new KeyStoreReader();
        //while(reader.readCertificate("vaultSupreme", password, ) != null){

        //}
        KeyStore ks = KeyStore.getInstance("JKS", "SUN");
        BufferedInputStream in = new BufferedInputStream(new FileInputStream("proba"));
        ks.load(in, password.toCharArray());

        String []keystoreNames = new String[ks.size()];

        System.out.println(ks.aliases().toString());

        return null;

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
