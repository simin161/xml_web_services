package domain

import (
	"go.mongodb.org/mongo-driver/bson/primitive"
)

type RegistrationService interface {
	Get(id primitive.ObjectID) (*User, error)
	GetAll() ([]*User, error)
	Insert(user *User) (*User, error)
	DeleteAll()
}
