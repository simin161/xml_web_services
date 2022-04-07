package dao;

import beans.Certificate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KeyStoreNameDAO {

    private List<String> allNames;
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-mm-dd").setPrettyPrinting().create();

    private static KeyStoreNameDAO instance;
    public static KeyStoreNameDAO getInstance(){
        if(instance == null){
            instance = new KeyStoreNameDAO();
        }
        return instance;
    }

    public KeyStoreNameDAO(){
        load();
    }

    public List<String> getAllNames(){
        return allNames;
    }

    private void load(){
        try {
            Reader reader = Files.newBufferedReader(Paths.get("data/keystoreNames.json"));
            allNames = new ArrayList<String>(Arrays.asList(gson.fromJson(reader, String[].class)));
            reader.close();

        }catch(Exception e) {
            allNames = new ArrayList<String>();
        }
    }

    public void save(){
        try {
            Writer writer;
            writer = Files.newBufferedWriter(Paths.get("data/keystoreNames.json"));
            gson.toJson(allNames, writer);
            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }
    }

    //dodaje novo ime i cuva ga u fajl
    public void addName(String name){
        allNames.add(name);
        save();
    }

}
