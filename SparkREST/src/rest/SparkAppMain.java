package rest;

import beans.Certificate;
import beans.CertificateView;
import beans.User;
import beans.enums.UserType;
import com.google.gson.Gson;
import dao.CertificateStatusDAO;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import service.CertificateService;
import service.UserService;
import spark.Session;
import utility.Validation;
import java.io.File;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
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

		staticFiles.externalLocation(new File("./static").getCanonicalPath());

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

		post("/invalidateCertificate", (req, res)->{
			res.type("application/json");
			CertificateView cert = gson.fromJson(req.body(), CertificateView.class);

			return certificateService.invalidateCertificate(cert);

		});

		get("/verifyAccount", (req, res)->{
			res.type("application/json");
			String c = req.queryString();
			String []cParts = c.split("=");
			String code = cParts[1];
			return userService.verifyAccount(code);

		});

	}
}
