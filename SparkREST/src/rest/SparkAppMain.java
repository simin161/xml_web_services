package rest;

import beans.Certificate;
import beans.CertificateView;
import beans.User;
import beans.enums.UserType;
import com.google.gson.Gson;
import dao.CertificateStatusDAO;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
/*import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;*/
import service.CertificateService;
import service.UserService;
import spark.Session;
import utility.Validation;

import javax.security.auth.login.Configuration;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static spark.Spark.*;

public class SparkAppMain {

	private static Gson gson = new Gson();

	/**
	 * Ključ za potpisivanje JWT tokena.
	 * Biblioteka: https://github.com/jwtk/jjwt
	 */

	static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	private static UserService userService = new UserService();
	private static CertificateService certificateService = new CertificateService();
	public static void main(String[] args) throws Exception {
		port(8081);

		//webSocket("/ws", WsHandler.class);

		staticFiles.externalLocation(new File("./static").getCanonicalPath());
		Date today = new Date();
		DateFormat dateFormatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
		//Date date = dateFormatter .parse(today.toString());

		get("/checkAndInvalidate", (req, res)->{
			res.type("application/json");
			for(X509Certificate c : certificateService.getAllCerts()){
				System.out.println("KASMDOKAMSDLAKNSDALKNDSALKNSD");
				try {
					c.checkValidity();
				} catch (CertificateExpiredException e) {
					CertificateStatusDAO.getInstance().invalidateCertificate(c.getSerialNumber().toString());
				} catch (CertificateNotYetValidException e) {
					CertificateStatusDAO.getInstance().invalidateCertificate(c.getSerialNumber().toString());
				}

				try{
					List<CertificateView> above = certificateService.getCertsAbove(c.getSerialNumber().toString());
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
					CertificateStatusDAO.getInstance().invalidateCertificate(c.getSerialNumber().toString());
				} catch (SignatureException e) {
					CertificateStatusDAO.getInstance().invalidateCertificate(c.getSerialNumber().toString());
				}
			}
			return "Zorica Markovic";
		});

		get("/getMessage", (req, res)->{
			res.type("application/json");

			return "Zorica Markovic";
		});

		post("/signIn", (req, res)->{
			res.type("application/json");
			User user =  userService
					.findUserByEmail(((User) gson.fromJson(req.body(), User.class)).getEmail());
			if(user != null && user.getPassword().equals(((User) gson.fromJson(req.body(), User.class)).getPassword())) {
				if(user.isActivated()){
					Session session = req.session(true);
					session.attribute("loggedUser",user);
					return true;
				}else{
					return false;
				}
			}
			return false;
		});

		post("/register", (req, res)->{
			res.type("application/json");
			boolean returnValue = false;
			String url = req.url();
			String path = req.uri();
			String rootUrl = url.substring(0, url.length() - path.length());
			if (userService.register((User) gson.fromJson(req.body(), User.class),rootUrl)) {
				returnValue = true;
			}
			return returnValue;
		});

		post("/downloadCertificate", (req, res) -> {
			res.type("application/json");
			try{
				String path = certificateService.loadCertificateToFile(gson.fromJson(req.body(), String.class));
				return path;
			}catch(Exception e){
				return "";
			}
		});

		post("/createCertificate", (req,res)->{
			res.type("application/json");
			Certificate cert = (Certificate) gson.fromJson(req.body(), Certificate.class);
			if(Validation.validateText(cert.getType()) && Validation.validateText(cert.getAlias())
			&& Validation.validateText(cert.getCommonName()) && Validation.validateText(cert.getGivenName())
			&& Validation.validateText(cert.getOrganization()) && Validation.validateText(cert.getOrganizationalUnitName())
			&& Validation.validateEmail(cert.getOrganizationEmail()) && Validation.validateText(cert.getCountry())
			&& Validation.validateTextWithNumber(cert.getKeyUsage()))
			return certificateService.createCertificate(cert);

			return null;
		});

		get("/signOut", (req, res) -> {

			Session session = req.session(true);
			User user = session.attribute("loggedUser");

			if (user != null) {
				session.invalidate();
			}
			return true;
		});

		get("/getLoggedUser", (req, res) -> {

			res.type("application/json");
			Session session = req.session(true);
			User user = session.attribute("loggedUser");

			if (user!=null) {

				User userLogged = session.attribute("loggedUser");
				ArrayList<User> users = new ArrayList<User>();
				users.add(userLogged);
				return gson.toJson(userLogged);

			} else {

				return "EHE?! TE NANDAYO?!";
			}
		});

		get("/getUsers", (req, res) -> {
			res.type("application/json");
			return gson.toJson(userService.getAllUsers());
		});

		post("/getAllCerts", (req, res) -> {
			res.type("application/json");
			String password = gson.fromJson(req.body(), String.class);
			Session session = req.session(true);
			User user = session.attribute("loggedUser");
			if(user.getUserType() == UserType.ADMIN)
				return gson.toJson(certificateService.getAllCertsForAdmin());
			else{
				return gson.toJson(certificateService.getAllCertsForUser(user.getEmail()));
			}
		});

		get("/getAllCertsForDropDown", (req, res) -> {
			res.type("application/json");
			Session session = req.session(true);
			User user = session.attribute("loggedUser");
			String password = gson.fromJson(req.body(), String.class);
			return gson.toJson(certificateService.getAllCertsForSubjectWithoutEE(user.getEmail()));
		});

		post("/saveChoosenCert", (req, res) -> {
			res.type("application/json");
			Session session = req.session(true);
			session.attribute("certificate", gson.fromJson(req.body(), CertificateView.class));
			return true;
		});

		get("/getChoosenCert", (req, res) ->{
			res.type("application/json");
			Session session = req.session(true);
			CertificateView cert = session.attribute("certificate");
			return gson.toJson(cert);
		});

		get("/getCertsAbove", (req, res) -> {
			res.type("application/json");
			Session session = req.session(true);
			CertificateView cert = session.attribute("certificate");
			return gson.toJson(certificateService.getCertsAbove(cert.getSerialNumber()));
		});

		get("/checkCA", (req, res) -> {
			Session session = req.session(true);
			User user = session.attribute("loggedUser");
			if(user.getUserType() == UserType.ADMIN)
				return true;
			return certificateService.checkIfUserHasCA(user.getEmail());
		});

		/*post("/getPdf", (req, res) ->{
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
		});
*/
		post("/invalidateCertificate", (req, res)->{

			res.type("application/json");
			CertificateView cert = gson.fromJson(req.body(), CertificateView.class);

			return certificateService.invalidateCertificate(cert);

		});

		get("/verifyAccount?code=:vcode", (req, res)->{

			res.type("application/json");
			return userService.verifyAccount(req.params(":vcode"));

		});

	}
}
