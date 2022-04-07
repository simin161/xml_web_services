package beans;

import java.math.BigInteger;
import java.util.Date;

public class Certificate {

    String type;
    String receiver;
    String issuer;
    String validFrom;
    String validTo;
    String purpose;
    BigInteger path;
    String keyUsage;
    String key;
    String signature;
    String certificateStatus;
    String revocationStatus;

    public String getCertificateStatus(){
        return certificateStatus;
    }

    public void setCertificateStatus(String certificateStatus){
        this.certificateStatus = certificateStatus;
    }

    public String getRevocationStatus(){
        return revocationStatus;
    }

    public void setRevocationStatus(String revocationStatus){
        this.revocationStatus = revocationStatus;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(String validFrom) {
        this.validFrom = validFrom;
    }

    public String getValidTo() {
        return validTo;
    }

    public void setValidTo(String validTo) {
        this.validTo = validTo;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public BigInteger getPath() {
        return path;
    }

    public void setPath(BigInteger path) {
        this.path = path;
    }

    public String getKeyUsage() {
        return keyUsage;
    }

    public void setKeyUsage(String keyUsage) {
        this.keyUsage = keyUsage;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Certificate(String type, String receiver, String issuer, String validFrom,
                       String validTo, String purpose, BigInteger path, String keyUsage,
                       String key, String signature) {
        this.type = type;
        this.receiver = receiver;
        this.issuer = issuer;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.purpose = purpose;
        this.path = path;
        this.keyUsage = keyUsage;
        this.key = key;
        this.signature = signature;
    }
}
