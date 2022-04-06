package service;

import beans.Certificate;
import beans.User;
import dao.CertificateDAO;
import sun.security.tools.keytool.CertAndKeyGen;
import sun.security.x509.X500Name;

import java.security.KeyStore;
import java.security.cert.X509Certificate;

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

            System.out.println("Certificate after storing: "+storeReader.readCertificate("proba", pass, user.getEmail()).toString());;


        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
