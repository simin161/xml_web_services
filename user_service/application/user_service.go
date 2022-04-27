package application

import (
	"github.com/jandric162/user_service/domain"
	"go.mongodb.org/mongo-driver/bson/primitive"
)

type UserService struct{
	user domain.RegistrationService
}

func NewRegistrationService(user domain.RegistrationService) *RegistrationService {
	return &RegistrationService{
		user: user,
	}
}

func (service *RegistrationService) Get(id primitive.ObjectID) (*domain.User, error) {
	return service.user.Get(id), nil
}

func (service *RegistrationService) GetAll() ([]*domain.User, error) {
	return service.user.GetAll(), nil
}

func (service *RegistrationService) Insert(newUser *domain.User) (*domain.User, error){
	return service.user.Insert(newUser), nil
}
