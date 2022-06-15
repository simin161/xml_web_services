package service;

public class PasswordGenerator {

    public static String generatePassword(String name){
        String reversed = "";
        for(int i = name.length() - 1; i >= 0; --i){
            reversed += name.charAt(i);
        }

        String toAdd = "";

        for(int i = 0; i < name.length(); ++i){
            toAdd += String.valueOf(65 + Integer.parseInt(String.valueOf(name.charAt(i))));
        }

        String finalized = "";

        for(int i = 0; i < name.length()/2; ++i){
            finalized += toAdd.charAt(i);
        }

        finalized += reversed;

        for(int i = name.length()/2; i < name.length(); ++i){
            finalized += toAdd.charAt(i);
        }

        return finalized;
    }
}
