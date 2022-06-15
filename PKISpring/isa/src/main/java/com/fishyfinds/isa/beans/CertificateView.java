package beans;

public class CertificateView {
    public CertificateView(){}

    public CertificateView(String issuerDN, String subjectDN, String serialNumber, String signatureAlg, String version, String publicKey, String dateFrom, String dateTo, String signature, String alias) {
        this.issuerDN = issuerDN;
        this.subjectDN = subjectDN;
        this.serialNumber = serialNumber;
        this.signatureAlg = signatureAlg;
        this.version = version;
        this.publicKey = publicKey;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.signature = signature;
        this.alias = alias;
    }

    private String issuerDN;

    public String getIssuerDN() {
        return issuerDN;
    }

    public String getSubjectDN() {
        return subjectDN;
    }

    public String getIsValid() {
        return isValid;
    }

    public String getSignatureAlg() {
        return signatureAlg;
    }

    public String getVersion() {
        return version;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public String getSignature() {
        return signature;
    }

    public String getAlias() {
        return alias;
    }

    private String subjectDN;

    public String getSerialNumber() {
        return serialNumber;
    }

    private String isValid;
    private String serialNumber;
    private String signatureAlg;
    private String version;
    private String publicKey;
    private String dateFrom;
    private String dateTo;
    private String signature;
    private String alias;

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setSubjectDN(String subjectDN) {
        this.subjectDN = subjectDN;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public void setSignatureAlg(String signatureAlg) {
        this.signatureAlg = signatureAlg;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public void setIssuerDN(String issuerDN) {
        this.issuerDN = issuerDN;
    }
}
