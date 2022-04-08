package beans;

import java.security.PublicKey;
import java.time.LocalDate;
import java.util.Date;

import org.bouncycastle.asn1.x500.X500Name;

public class SubjectData {

	private PublicKey publicKey;
	private X500Name x500name;
	private String serialNumber;
	private LocalDate startDate;
	private LocalDate endDate;
	private String userEmail;

	public String getUserEmail(){
		return userEmail;
	}

	public void setUserEmail(String email){
		this.userEmail = email;
	}

	public SubjectData() {

	}

	public SubjectData(PublicKey publicKey, X500Name x500name, String serialNumber, LocalDate startDate, LocalDate endDate) {
		this.publicKey = publicKey;
		this.x500name = x500name;
		this.serialNumber = serialNumber;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public X500Name getX500name() {
		return x500name;
	}

	public void setX500name(X500Name x500name) {
		this.x500name = x500name;
	}

	public PublicKey getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(PublicKey publicKey) {
		this.publicKey = publicKey;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
}
