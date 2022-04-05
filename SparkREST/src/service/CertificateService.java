package service;

import beans.Certificate;
import beans.User;
import dao.CertificateDAO;
import sun.security.tools.keytool.CertAndKeyGen;
import sun.security.x509.X500Name;

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

            System.out.println("Certificate : "+chain[0].toString());
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
