package beans;

import java.time.LocalDateTime;

public class VerificationCode {
    private String code;
    private LocalDateTime dateOfCreation;

    public void setCode(String code) {
        this.code = code;
    }

    public void setDateOfCreation(LocalDateTime dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public String getCode() {
        return code;
    }

    public LocalDateTime getDateOfCreation() {
        return dateOfCreation;
    }
}
