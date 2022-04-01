package rest;

import com.google.gson.Gson;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.io.File;
import java.security.Key;

import static spark.Spark.*;
import static spark.Spark.port;
import static spark.Spark.staticFiles;

public class SparkAppMain {

	private static Gson g = new Gson();

	/**
	 * Ključ za potpisivanje JWT tokena.
	 * Biblioteka: https://github.com/jwtk/jjwt
	 */
	static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

	public static void main(String[] args) throws Exception {
		port(8080);

		//webSocket("/ws", WsHandler.class);

		staticFiles.externalLocation(new File("./static").getCanonicalPath());


		get("/getMessage", (req, res)->{

			res.type("application/json");
			return "Dunje Vrbaski";

		});

	}
}
