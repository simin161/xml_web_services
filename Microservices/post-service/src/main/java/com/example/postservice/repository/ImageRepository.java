package com.example.postservice.repository;

import com.example.postservice.dto.ImageDto;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ImageRepository {
    private static ImageRepository instance = null;
    private MongoClient mongoClient;
    private MongoDatabase imageDatabase;
    private MongoCollection<Document> imageCollection;

    public static ImageRepository getInstance(){
        if(instance == null){
            instance = new ImageRepository();
        }
        return instance;
    }

    private ImageRepository(){
        String connectionString = "mongodb://localhost:27017";
        try {
            mongoClient = MongoClients.create(connectionString);
            List<Document> databases = mongoClient.listDatabases().into(new ArrayList<>());
            databases.forEach(db -> System.out.println(db.toJson()));
            imageDatabase = mongoClient.getDatabase("images");
            imageCollection = imageDatabase.getCollection("images");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insert(ImageDto imageDto){
        ObjectId id;
        try {
            GridFSBucket gridBucket = GridFSBuckets.create(imageDatabase);
            InputStream inStream = new FileInputStream(new File(imageDto.getFilePath()));
            GridFSUploadOptions uploadOptions = new GridFSUploadOptions().chunkSizeBytes(1024).metadata(new Document("type", "image").append("content_type", "image/png"));
            id = gridBucket.uploadFromStream(imageDto.getFileName(), inStream, uploadOptions);

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
