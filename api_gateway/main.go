package main

import (
	"github.com/simin161/api_gateway/startup"
	"github.com/simin161/api_gateway/startup/config"
	"fmt"
)

func main() {

	fmt.Printf("api_gateway main go")

	config := config.NewConfig()
	server := startup.NewServer(config)
	server.Start()
}
