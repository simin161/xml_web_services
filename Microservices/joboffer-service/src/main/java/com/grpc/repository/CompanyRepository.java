package com.grpc.repository;

import com.grpc.model.Company;
import com.mongodb.client.*;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class CompanyRepository {
    private static CompanyRepository instance = null;
    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;
    private MongoCollection<Document> companyCollection;

    public static CompanyRepository getInstance(){
        if(instance == null){
            instance = new CompanyRepository();
        }
        return instance;
    }

    private CompanyRepository(){
        String connectionString = "mongodb://localhost:27017";
        try{
            mongoClient = MongoClients.create(connectionString);
            List<Document> databases = mongoClient.listDatabases().into(new ArrayList<>());
            databases.forEach(db -> System.out.println(db.toJson()));
            mongoDatabase = mongoClient.getDatabase("company");
            companyCollection = mongoDatabase.getCollection("company");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public List<Company> getAllCompanies(){
        FindIterable<Document> allCompanies;
        List<Company> allCompaniesCompany = new ArrayList<>();
        allCompanies = companyCollection.find();
        for(Document d : allCompanies){
            Document owner = (Document) d.get("owner");
            Company company = new Company(d.getString("companyName"), owner.getString("email"));
            allCompaniesCompany.add(company);
        }
        return allCompaniesCompany;
    }

    public List<String> getUserCompanyNames(String email){
        List<String> retVal = new ArrayList<>();
        for(Company c : getAllCompanies()){
            if(email.equals(c.getOwnerEmail()))
                retVal.add(c.getCompanyName());
        }
        return retVal;
    }
}
