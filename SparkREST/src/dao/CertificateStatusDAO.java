package dao;

import beans.Certificate;
import beans.CertificateStatus;
import beans.enums.StatusType;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class CertificateStatusDAO {

    private ArrayList<CertificateStatus> allStatuses;
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-mm-dd").setPrettyPrinting().create();

    private static CertificateStatusDAO instance;
    public static CertificateStatusDAO getInstance(){
        if(instance == null){
            instance = new CertificateStatusDAO();
        }
        return instance;
    }

    public CertificateStatusDAO(){load();}

    private void load(){
        try {
            Reader reader = Files.newBufferedReader(Paths.get("data/certificateStatuses.json"));
            allStatuses = new ArrayList<CertificateStatus>(Arrays.asList(gson.fromJson(reader, CertificateStatus[].class)));
            reader.close();

        }catch(Exception e) {
            allStatuses = new ArrayList<CertificateStatus>();
        }
    }

    public void save(){
        try {
            Writer writer;
            writer = Files.newBufferedWriter(Paths.get("data/certificateStatuses.json"));
            gson.toJson(allStatuses, writer);
            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }
    }

    public ArrayList<CertificateStatus> getAllStatuses(){
        return allStatuses;
    }

    public void addStatus(CertificateStatus status){
        allStatuses.add(status);
    }

    public boolean checkCertificateStatus(BigInteger serialNumber) {

        for(CertificateStatus cs : allStatuses){
            if(Objects.equals(cs.getCertificateSerialNumber(), serialNumber)){
                if(cs.getStatus() == StatusType.VALID){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean invalidateCertificate(String serialNumber) {

        BigInteger snum = new BigInteger(serialNumber);
        for(CertificateStatus cs : allStatuses){
            if(Objects.equals(cs.getCertificateSerialNumber(), snum)){
                cs.setStatus(StatusType.INVALID);
                save();
                return true;
            }
        }
        return false;
    }
}
