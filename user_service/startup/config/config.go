package config

import "os"

type Config struct {
	Port            string
	UsersDBHost string
	UsersDBPort string
}

func NewConfig() *Config {
	return &Config{
		Port:            os.Getenv("USER_SERVICE_PORT"),
		UsersDBHost: os.Getenv("USER_DB_HOST"),
		UsersDBPort: os.Getenv("USER_DB_PORT"),
	}
}
