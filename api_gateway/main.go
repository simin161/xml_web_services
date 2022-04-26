package main

import (
	"github.com/simin161/xml_web_services/tree/connectingBackFront/api_gateway/startup"
	"github.com/simin161/xml_web_services/tree/connectingBackFront/api_gateway/startup/config"
)

func main() {
	config := config.NewConfig()
	server := startup.NewServer(config)
	server.Start()
}
