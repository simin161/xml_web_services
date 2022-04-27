package main

import (
	"github.com/simin161/api_gateway/startup"
	cfg "github.com/simin161/api_gateway/startup/config"
)

func main() {

	config := cfg.NewConfig()
	server := startup.NewServer(config)
	server.Start()

	// Set the router as the default one shipped with Gin
	//http.HandleFunc("/register", handler)

}
/*
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
*/