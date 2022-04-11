package beans;

import java.math.BigInteger;
import java.security.Security;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.sql.Date;

import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.ExtendedKeyUsage;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.cert.CertIOException;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

public class CertificateGenerator {
	public CertificateGenerator() {}
	
	public X509Certificate generateCertificate(SubjectData subjectData, IssuerData issuerData, boolean canSign, String keyUsage) {
		try {
			//Posto klasa za generisanje sertifiakta ne moze da primi direktno privatni kljuc pravi se builder za objekat
			//Ovaj objekat sadrzi privatni kljuc izdavaoca sertifikata i koristiti se za potpisivanje sertifikata
			//Parametar koji se prosledjuje je algoritam koji se koristi za potpisivanje sertifiakta
			Security.addProvider(new BouncyCastleProvider());
			JcaContentSignerBuilder builder = new JcaContentSignerBuilder("SHA256WithRSAEncryption");
			//Takodje se navodi koji provider se koristi, u ovom slucaju Bouncy Castle

			builder = builder.setProvider("BC");

			//Formira se objekat koji ce sadrzati privatni kljuc i koji ce se koristiti za potpisivanje sertifikata
			ContentSigner contentSigner = builder.build(issuerData.getPrivateKey());

			//Postavljaju se podaci za generisanje sertifiakta
			X509v3CertificateBuilder certGen = new JcaX509v3CertificateBuilder(issuerData.getX500name(),
					new BigInteger(subjectData.getSerialNumber().trim()), Date.valueOf(subjectData.getStartDate()), Date.valueOf(subjectData.getEndDate()),
					subjectData.getX500name(), subjectData.getPublicKey());
			if(canSign) { //canSign -> ne ako moze da se potpise, nego ako moze DA potpisuje
				certGen.addExtension(Extension.keyUsage, true, new KeyUsage(KeyUsage.digitalSignature));
				certGen.addExtension(Extension.basicConstraints, true, new BasicConstraints(canSign));
			}
			/*ExtendedKeyUsage keyUsage1;
			switch(keyUsage){
				case "CRL_SIGN": keyUsage1 = new ExtendedKeyUsage(KeyPurposeId.);
				break;
				case "DATA_ENCIPHERMENT": keyUsage1 = new ExtendedKeyUsage(ExtendedKeyUsage.dataEncipherment);
				break;
				case "DECIPHER_ONLY": keyUsage1 = new ExtendedKeyUsage(ExtendedKeyUsage.decipherOnly);
				break;
				case "ENCIPHER_ONLY": keyUsage1 = new ExtendedKeyUsage(ExtendedKeyUsage.encipherOnly);
				break;
				case "KEY_AGREEMENT": keyUsage1 = new ExtendedKeyUsage(ExtendedKeyUsage.keyAgreement);
				break;
				case "KEY_CERT_SIGN": keyUsage1 = new ExtendedKeyUsage(ExtendedKeyUsage.keyCertSign);
				break;
				case "NON_REPUDIATION": keyUsage1 = new ExtendedKeyUsage(ExtendedKeyUsage.nonRepudiation);
				break;
				default:
					keyUsage1 = null;
			}
			if(keyUsage1 != null)
				certGen.addExtension(Extension.keyUsage, false, keyUsage1);*/
			//Generise se sertifikat
			X509CertificateHolder certHolder = certGen.build(contentSigner);

			//Builder generise sertifikat kao objekat klase X509CertificateHolder
			//Nakon toga je potrebno certHolder konvertovati u sertifikat, za sta se koristi certConverter
			JcaX509CertificateConverter certConverter = new JcaX509CertificateConverter();
			certConverter = certConverter.setProvider("BC");

			//Konvertuje objekat u sertifikat
			return certConverter.getCertificate(certHolder);
		} catch (CertificateEncodingException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (OperatorCreationException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (CertIOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
