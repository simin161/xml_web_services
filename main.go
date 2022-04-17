package main

import (
	"fmt"
	"log"
	"net/http"
)

func main() {
	// Set the router as the default one shipped with Gin
	http.HandleFunc("/api/register", handler)

	fmt.Printf("Starting server at port 8080\n")
	if err := http.ListenAndServe(":8080", nil); err != nil {
		log.Fatal(err)
	}
}

func handler(w http.ResponseWriter, r *http.Request) {
	r.ParseForm()                // Parses the request body
	x := r.Form.Get("firstName") // x will be "" if parameter is not set
	fmt.Println(r.GetBody())
	fmt.Println(x)
}
