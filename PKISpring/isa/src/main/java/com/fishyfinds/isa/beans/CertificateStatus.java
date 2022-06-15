package beans;

import beans.enums.StatusType;

import java.math.BigInteger;

public class CertificateStatus {

    BigInteger certificateSerialNumber;
    StatusType status;

    public BigInteger getCertificateSerialNumber() {
        return certificateSerialNumber;
    }

    public void setCertificateSerialNumber(BigInteger certificateSerialNumber) {
        this.certificateSerialNumber = certificateSerialNumber;
    }

    public StatusType getStatus() {
        return status;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }

    public CertificateStatus(BigInteger certificateSerialNumber, StatusType status) {
        this.certificateSerialNumber = certificateSerialNumber;
        this.status = status;
    }
}
