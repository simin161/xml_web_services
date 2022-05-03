package com.example.postservice.repository;

import com.example.postservice.model.Post;
import com.mongodb.client.*;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class PostRepository {

    private static PostRepository instance = null;

    public static PostRepository getInstance(){
        if(instance == null)
            instance = new PostRepository();

        return instance;
    }

    private MongoClient mongoClient;
    private MongoDatabase postsDatabase;
    private MongoCollection<Document> postsCollection;

    private PostRepository(){
        String connectionString = "mongodb://localhost:27017";
        try {
            mongoClient = MongoClients.create(connectionString);
            List<Document> databases = mongoClient.listDatabases().into(new ArrayList<>());
            databases.forEach(db -> System.out.println(db.toJson()));
            postsDatabase = mongoClient.getDatabase("posts");
            postsCollection = postsDatabase.getCollection("posts");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insert(Post post){
        Document postToSave = new Document("_id", new ObjectId());
        postToSave.append("usersId", post.getUsersId())
                .append("text", post.getText())
                .append("pathToImage", post.getPathToImage())
                .append("link", post.getLink());

        postsCollection.insertOne(postToSave);
    }

    public List<Post> getAllPosts(){
        FindIterable<Document> iterable = postsCollection.find();
        List<Post> retVal = new ArrayList<Post>();
        for(Document d : iterable){
            Post p = new Post();
            p.setId(d.getString("id"));
            p.setLink(d.getString("link"));
            p.setPathToImage(d.getString("pathToImage"));
            p.setText(d.getString("text"));
            retVal.add(p);
        }
        return retVal;
    }


}
