package rest;

import beans.Certificate;
import beans.CertificateView;
import beans.User;
import beans.enums.UserType;
import com.google.gson.Gson;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import service.CertificateService;
import service.UserService;
import spark.Session;

import java.io.File;
import java.security.Key;
import java.util.ArrayList;

import static spark.Spark.*;
import static spark.Spark.port;
import static spark.Spark.staticFiles;

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
		port(8080);

		//webSocket("/ws", WsHandler.class);

		staticFiles.externalLocation(new File("./static").getCanonicalPath());


		get("/getMessage", (req, res)->{
			res.type("application/json");

			return "Dunje Vrbaski";
		});

		post("/signIn", (req, res)->{
			res.type("application/json");
			User user =  userService
					.findUserByEmail(((User) gson.fromJson(req.body(), User.class)).getEmail());
			if(user != null && user.getPassword().equals(((User) gson.fromJson(req.body(), User.class)).getPassword())) {
				Session session = req.session(true);
				session.attribute("loggedUser",user);
				return true;
			}
			return false;
		});

		post("/register", (req, res)->{
			res.type("application/json");
			boolean returnValue = false;
			if (userService.register((User) gson.fromJson(req.body(), User.class))) {
				returnValue = true;

				Session session = req.session(true);
				session.attribute("loggedUser", userService
						.findUserByEmail(((User) gson.fromJson(req.body(), User.class)).getEmail()));
			}
			return returnValue;
		});

		post("/createCertificate", (req,res)->{
			res.type("application/json");
			return certificateService.createCertificate((Certificate) gson.fromJson(req.body(), Certificate.class));
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
				return gson.toJson(certificateService.getAllCerts(password));
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
	}
}
