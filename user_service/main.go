package main

import (
	"fmt"
	"net/http"
	"context"
	"time"
	"encoding/json"
	"go.mongodb.org/mongo-driver/bson"
	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"
	"go.mongodb.org/mongo-driver/mongo/readpref"
	"github.com/simin161/api_gateway/startup"
	cfg "github.com/simin161/api_gateway/startup/config"
)

type user struct{
	FirstName string
	LastName string
	Email string
	Username string
	Password string
}

func close(client *mongo.Client, ctx context.Context,
	cancel context.CancelFunc){

	// CancelFunc to cancel to context
	defer cancel()

	// client provides a method to close
	// a mongoDB connection.
	defer func(){

		// client.Disconnect method also has deadline.
		// returns error if any,
		if err := client.Disconnect(ctx); err != nil{
			panic(err)
		}
	}()
}

// This is a user defined method that returns mongo.Client,
// context.Context, context.CancelFunc and error.
// mongo.Client will be used for further database operation.
// context.Context will be used set deadlines for process.
// context.CancelFunc will be used to cancel context and
// resource associated with it.

func connect(uri string)(*mongo.Client, context.Context,
	context.CancelFunc, error) {

	// ctx will be used to set deadline for process, here
	// deadline will of 30 seconds.
	ctx, cancel := context.WithTimeout(context.Background(),
		30 * time.Second)

	// mongo.Connect return mongo.Client method
	client, err := mongo.Connect(ctx, options.Client().ApplyURI(uri))
	return client, ctx, cancel, err
}

// This is a user defined method that accepts
// mongo.Client and context.Context
// This method used to ping the mongoDB, return error if any.
func ping(client *mongo.Client, ctx context.Context) error{

	// mongo.Client has Ping to ping mongoDB, deadline of
	// the Ping method will be determined by cxt
	// Ping method return error if any occurred, then
	// the error can be handled.
	if err := client.Ping(ctx, readpref.Primary()); err != nil {
		return err
	}
	fmt.Println("connected successfully")
	return nil
}

func insertOne (client *mongo.Client, ctx context.Context, dataBase, col string, doc interface{}) (*mongo.InsertOneResult, error) {

// select database and collection ith Client.Database method
// and Database.Collection method
collection := client.Database(dataBase).Collection(col)

// InsertOne accept two argument of type Context
// and of empty interface
result, err := collection.InsertOne(ctx, doc)
return result, err
}

func main() {

	config := cfg.NewConfig()
	server := startup.NewServer(config)
	server.Start()

	// Set the router as the default one shipped with Gin
	http.HandleFunc("/register", handler)

}

func handler(w http.ResponseWriter, r *http.Request) {
	//r.ParseForm()  baca error jer je sve ovo prazno
	//x := r.Form.Get("firstName")
	//fmt.Println(r.GetBody())
	client, ctx, cancel, err := connect("mongodb://localhost:27017")

	decoder := json.NewDecoder(r.Body)
	var userData user
	err1 := decoder.Decode(&userData)
	if err1 != nil {
		panic(err1)
	}

	fmt.Println(userData)
	var document interface{}

	document = bson.D{
		{"firstName", userData.FirstName},
		{"lastName", userData.LastName},
		{"username", userData.Username},
		{"email", userData.Email},
		{"password", userData.Password},
	}

	insertOneResult, err := insertOne(client, ctx, "gfg",
		"marks", document)

	// handle the error
	if err != nil {
		panic(err)
	}

	// print the insertion id of the document,
	// if it is inserted.
	fmt.Println("Result of InsertOne")
	fmt.Println(insertOneResult.InsertedID)
	defer close(client, ctx, cancel)

}
