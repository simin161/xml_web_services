package dao;

import beans.Certificate;
import beans.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class CertificateDAO {

    private ArrayList<Certificate> allCertificates;
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-mm-dd").setPrettyPrinting().create();

    private static CertificateDAO instance;
    public static CertificateDAO getInstance() {
        if(instance == null) {
            instance =  new CertificateDAO();
        }
        return instance;
    }

    public CertificateDAO() {
        load();
    }

    public ArrayList<Certificate> getAllUsers(){
        return allCertificates;
    }

    private void load() {
        try {
            Reader reader = Files.newBufferedReader(Paths.get("data/certificates.json"));
            allCertificates = new ArrayList<Certificate>(Arrays.asList(gson.fromJson(reader, Certificate[].class)));
            reader.close();

        }catch(Exception e) {
            allCertificates = new ArrayList<Certificate>();
        }
    }

    public void save() {
        try {
            Writer writer;
            writer = Files.newBufferedWriter(Paths.get("data/certificates.json"));
            gson.toJson(allCertificates, writer);
            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }
    }

    public void addCertificate(Certificate newCertificate) {
        allCertificates.add(newCertificate);
    }
}
