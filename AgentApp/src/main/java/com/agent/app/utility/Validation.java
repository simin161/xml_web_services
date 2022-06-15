package com.agent.app.utility;

import com.agent.app.model.JobOffer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

    public static boolean validatePassword(String password){
        Pattern pattern = Pattern.compile("^(?=.*\\d)(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z]).{8,}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(password);
        return matcher.find();
    }

    public static boolean validateEmail(String email){
        Pattern pattern = Pattern.compile("^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(.\\w{2,3})+$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.find();
    }

    public static boolean validateName(String name){
        Pattern pattern = Pattern.compile("^[a-zA-Z\\-’]+$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(name);
        return matcher.find();
    }

    public static boolean validateUsername(String username){
        Pattern pattern = Pattern.compile("^[a-zA-Z]+([0-9]+)?$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(username);
        return matcher.find();
    }

    public static boolean validatePhone(String phone){
        Pattern pattern = Pattern.compile("^[0-9]+$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(phone);
        return matcher.find();
    }

    public static boolean validateNonBrackets(String text){
        Pattern pattern = Pattern.compile("[()\\[\\]{}<>]", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);
        return !matcher.find();
    }

    public static boolean validateNumbers(String text){
        Pattern pattern = Pattern.compile("([0-9+])?$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);
        return matcher.find();
    }

    public static boolean checkIfEmptyJobOffer(JobOffer jobOfferDto){
        return jobOfferDto.getJobDescription().equals("")
                || jobOfferDto.getCandidateRequirements().equals("")
                || jobOfferDto.getCompanyName().equals("")
                || jobOfferDto.getDailyActivities().equals("")
                || jobOfferDto.getPosition().equals("");
    }

}
