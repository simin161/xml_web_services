package service;

import beans.Certificate;
import dao.CertificateDAO;

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

}
