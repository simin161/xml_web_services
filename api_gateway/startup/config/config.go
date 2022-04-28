package config

//import "os"

type Config struct {
	Port          string
	UserServiceHost string
	UserServicePort string
}

func NewConfig() *Config {
	return &Config{
		Port:          "8000",
		UserServiceHost: "localhost",
		UserServicePort: "8000",
	}
}
