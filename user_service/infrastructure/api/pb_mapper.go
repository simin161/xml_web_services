package api

import (
	"github.com/jandric162/user_service/domain"
	pb "github.com/simin161/common/proto/user_service"
)

func mapUser(user *domain.User) *pb.User {
	userPb := &pb.user{
		Id:            user.Id.Hex(),
		FirstName:          user.FirstName,
		LastName: user.LastName,
		Email: user.Email,
		Username: user.Username,
		Password: user.Password,
	}

	return userPb
}
