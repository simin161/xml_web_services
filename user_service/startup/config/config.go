package config

//import "os"

type Config struct {
	Port            string
	UsersDBHost string
	UsersDBPort string
}

func NewConfig() *Config {
	return &Config{
		Port:            "8080",
		UsersDBHost: "user_db",
		UsersDBPort: "27017",
	}
}
