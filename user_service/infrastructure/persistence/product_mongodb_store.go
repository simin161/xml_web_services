package persistence

import (
	"context"
	"github.com/jandric162/user_service/domain"
	"go.mongodb.org/mongo-driver/bson"
	"go.mongodb.org/mongo-driver/bson/primitive"
	"go.mongodb.org/mongo-driver/mongo"
)

const (
	DATABASE   = "users"
	COLLECTION = "user"
)

type UserMongoDBStore struct {
	allUsers *mongo.Collection
}

func NewUserMongoDBStore(client *mongo.Client) domain.RegistrationService {
	allUsers := client.Database(DATABASE).Collection(COLLECTION)
	return &UserMongoDBStore{
		allUsers: allUsers,
	}
}

func (store *UserMongoDBStore) Get(id primitive.ObjectID) (*domain.User, error) {
	filter := bson.M{"_id": id}
	return store.filterOne(filter), nil
}

func (store *UserMongoDBStore) GetAll() ([]*domain.User, error) {
	filter := bson.D{{}}
	return store.filter(filter), nil
}

func (store *UserMongoDBStore) Insert(newUser *domain.User) error {
	result, err := store.allUsers.InsertOne(context.TODO(), newUser)
	if err != nil {
		return err
	}
	newUser.Id = result.InsertedID.(primitive.ObjectID)
	return nil
}

func (store *UserMongoDBStore) DeleteAll() {
	store.allUsers.DeleteMany(context.TODO(), bson.D{{}})
}

func (store *UserMongoDBStore) filter(filter interface{}) ([]*domain.User, error) {
	cursor, err := store.allUsers.Find(context.TODO(), filter)
	defer cursor.Close(context.TODO())

	if err != nil {
		return nil, err
	}
	return decode(cursor)
}

func (store *UserMongoDBStore) filterOne(filter interface{}) (user *domain.User, err error) {
	result := store.allUsers.FindOne(context.TODO(), filter)
	err = result.Decode(&user)
	return
}

func decode(cursor *mongo.Cursor) (allUsers []*domain.User, err error) {
	for cursor.Next(context.TODO()) {
		var user domain.User
		err = cursor.Decode(&user)
		if err != nil {
			return
		}
		allUsers = append(allUsers, &user)
	}
	err = cursor.Err()
	return
}
