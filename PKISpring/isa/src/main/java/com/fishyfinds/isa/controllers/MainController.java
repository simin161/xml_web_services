package com.fishyfinds.isa.controllers;

import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;

@RestController
public class MainController {
    @Autowired
    private service.UserService userService;
    @Autowired
    private service.CertificateService certificateService;

    @GetMapping("/checkAndInvalidate")
    public boolean checkAndInvalidate(){
        for(X509Certificate c : certificateService.getAllCerts()){
            System.out.println("KASMDOKAMSDLAKNSDALKNDSALKNSD");
            try {
                c.checkValidity();
            } catch (CertificateExpiredException e) {
                dao.CertificateStatusDAO.getInstance().invalidateCertificate(c.getSerialNumber().toString());
            } catch (CertificateNotYetValidException e) {
                dao.CertificateStatusDAO.getInstance().invalidateCertificate(c.getSerialNumber().toString());
            }

            try{
                List<beans.CertificateView> above = certificateService.getCertsAbove(c.getSerialNumber().toString());
                if(above.size() == 1){
                    System.out.println("root");
                    c.verify(c.getPublicKey());
                }
                else if(above.size() == 2){
                    System.out.println("prvi ispod root-a");
                    c.verify(certificateService.findCertBySerNum(new BigInteger(above.get(0).getSerialNumber())).getPublicKey());
                }
                else{
                    System.out.println("2 >");
                    c.verify(certificateService.findCertBySerNum(new BigInteger(above.get(above.size()-2).getSerialNumber())).getPublicKey());
                }
            }catch(CertificateException | NoSuchAlgorithmException | InvalidKeyException | NoSuchProviderException e){
                dao.CertificateStatusDAO.getInstance().invalidateCertificate(c.getSerialNumber().toString());
            } catch (SignatureException e) {
                dao.CertificateStatusDAO.getInstance().invalidateCertificate(c.getSerialNumber().toString());
            }
        }
        return true;
    }

    @PostMapping("/signIn")
    public void signIn(@RequestBody Map<String, String> message){

    }

    @PostMapping("/register")
    public void register(@RequestBody Map<String, String> message){

    }

    @PostMapping("/downloadCertificate")
    public String downloadCertificate(){
        try{
            String path = certificateService.loadCertificateToFile(gson.fromJson(req.body(), String.class));
            return path;
        }catch(Exception e){
            return "";
        }
    }

    @PostMapping("/createCertificate")
    public boolean createCertificate(@RequestBody Map<String, String> message){
        beans.Certificate cert = (beans.Certificate) gson.fromJson(req.body(), beans.Certificate.class);
        if(utility.Validation.validateText(cert.getType()) && utility.Validation.validateText(cert.getAlias())
                && utility.Validation.validateText(cert.getCommonName()) && utility.Validation.validateText(cert.getGivenName())
                && utility.Validation.validateText(cert.getOrganization()) && utility.Validation.validateText(cert.getOrganizationalUnitName())
                && utility.Validation.validateEmail(cert.getOrganizationEmail()) && utility.Validation.validateText(cert.getCountry())
                && utility.Validation.validateTextWithNumber(cert.getKeyUsage()))
            return certificateService.createCertificate(cert);
    }

    @GetMapping("/getLoggedUser")
    public beans.User getLoggedUser(){

    }

    @GetMapping("/getUsers")
    public List<beans.User> getUsers()    {
        return userService.getAllUsers();
    }

    @GetMapping("/getAllCerts")
    public List<beans.Certificate> getAllCerts(){
        String password = gson.fromJson(req.body(), String.class);
        Session session = req.session(true);
        beans.User user = session.attribute("loggedUser");
        if(user.getUserType() == beans.enums.UserType.ADMIN)
            return gson.toJson(certificateService.getAllCertsForAdmin());
        else{
            return gson.toJson(certificateService.getAllCertsForUser(user.getEmail()));
        }
    }

    @GetMapping("/getAllCertsForDropDown")
    public List<Certificate> getAllCertsForDropDown(){
        Session session = req.session(true);
        beans.User user = session.attribute("loggedUser");
        String password = gson.fromJson(req.body(), String.class);
        return gson.toJson(certificateService.getAllCertsForSubjectWithoutEE(user.getEmail()));
    }

    @PostMapping("/saveChoosenCert")
    public boolean saveChoosenCert(){
        Session session = req.session(true);
        session.attribute("certificate", gson.fromJson(req.body(), beans.CertificateView.class));
        return true;
    }

    @GetMapping("/getChoosenCert")
    public beans.Certificate getChoosenCert(){
        res.type("application/json");
        Session session = req.session(true);
        beans.CertificateView cert = session.attribute("certificate");
        return gson.toJson(cert);
    }

    @GetMapping("/checkCA")
    public boolean checkCA(){
        Session session = req.session(true);
        beans.User user = session.attribute("loggedUser");
        if(user.getUserType() == beans.enums.UserType.ADMIN)
            return true;
        return certificateService.checkIfUserHasCA(user.getEmail());
    }

    @PostMapping("/invalidateCertificate")
    public boolean invalidateCertificate(@RequestBody beans.CertificateView cert){
        return certificateService.invalidateCertificate(cert);

    }

    /*
    @PostMapping("/getPdf")
    public boolean getPdf(){
    PDDocument pdfdoc= new PDDocument();
			pdfdoc.addPage(new PDPage());
			pdfdoc.addPage(new PDPage());
			Session session = req.session(true);
			CertificateView cert = session.attribute("certificate");
			PDPage page = pdfdoc.getPage(0);
			PDPageContentStream contentStream = new PDPageContentStream(pdfdoc, page);
			contentStream.beginText();
			PDFont font = PDType1Font.TIMES_ROMAN;
			contentStream.newLineAtOffset(25, 700);
			contentStream.setLeading(14.5f);
			contentStream.setFont(font, 12);
			contentStream.showText("Version: " + cert.getVersion());
			contentStream.newLine();
			contentStream.showText("Alias: " + cert.getAlias());
			contentStream.newLine();
			contentStream.showText("IssuerDN: ");
			String[] iArr = cert.getIssuerDN().split(",");
			for(int i = 0; i < iArr.length; ++i){
				contentStream.newLine();
				contentStream.showText(iArr[i]);
			}
			contentStream.newLine();
			contentStream.showText("SubjectDN: " + cert.getSubjectDN());
			String[] sArr = cert.getSubjectDN().split(",");
			for(int i = 0; i < sArr.length; ++i){
				contentStream.newLine();
				contentStream.showText(sArr[i]);
			}
			contentStream.newLine();
			contentStream.showText("Serial Number: " + cert.getSerialNumber());
			contentStream.newLine();
			contentStream.showText("Not valid before: " + cert.getDateFrom());
			contentStream.newLine();
			contentStream.showText("Not valid after: " + cert.getDateTo());
			contentStream.newLine();
			contentStream.showText("Public key: ");
			char[] array = cert.getPublicKey().replace("\n", "").replace("\r","").toCharArray();
			for(int i = 0; i < array.length; ++i){
				if(i % 80 == 0){
					contentStream.newLine();
				}
				contentStream.showText(String.valueOf(array[i]));
			}
			contentStream.newLine();
			contentStream.showText("Signature Algorith: " + cert.getSignatureAlg());
			contentStream.newLine();
			contentStream.showText("Signature: " + cert.getSignature());
			contentStream.endText();
			contentStream.close();

			contentStream = new PDPageContentStream(pdfdoc,  pdfdoc.getPage(1));
			List<CertificateView> certs = certificateService.getCertsAbove(cert.getSerialNumber());
			contentStream.beginText();
			contentStream.newLineAtOffset(25, 700);
			contentStream.setLeading(14.5f);
			contentStream.setFont(font, 12);
			contentStream.showText("Hierarchy: ");
			contentStream.newLine();
			for(CertificateView c : certs){
				contentStream.newLine();;
				contentStream.showText(" " + c.getAlias() + " " + c.getSerialNumber());
			}
			contentStream.endText();
			contentStream.close();
			pdfdoc.save("static/" + cert.getSerialNumber() + ".pdf");
//prints the message if the PDF is created successfully
			System.out.println("PDF created");
//closes the document
			pdfdoc.close();
			return true;
    }
     */
}
