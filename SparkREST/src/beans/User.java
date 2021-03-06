package beans;

import beans.enums.UserType;

public class User {
    private String firstName;
    private String lastName;
    private String organizationName;
    private String organizationUnit;
    private String countryId;
    private String email;
    private UserType userType;
    private String password;

    public String getEmail(){
        return this.email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public String getOrganizationUnit() {
        return organizationUnit;
    }

    public String getCountryId() {
        return countryId;
    }

    public String getPassword(){
        return this.password;
    }

    public void setUserType(UserType userType){
        this.userType = userType;
    }

    public UserType getUserType(){ return this.userType;}
}
