import React, { Component } from 'react';
import axios from 'axios';
import DatePicker from "react-datepicker";

import "react-datepicker/dist/react-datepicker.css";
class EditProfile extends Component {
 
    constructor (props) {
        super(props)
        this.state = {
          startDate: new Date()
        };
        this.handleChange = this.handleChange.bind(this);
    }
    handleChange(date) {
        this.setState({
          startDate: date
        })
      }
     update(e){
        e.stopPropagation();
       
        var firstName = document.getElementById("firstName").value;
        var lastName = document.getElementById("lastName").value;
        var email = document.getElementById("email").value;
        var username = document.getElementById("username").value;
        var birthday = document.getElementById("birthday").value;
        var gender = document.getElementById("gender").value;
        var phone = document.getElementById("phone").value;
        var privateProfile = document.getElementById("private").value;
        var biography = document.getElementById("biography").value;
        var interests = document.getElementById("interests").value;
        var skills = document.getElementById("skills").value;
        var profile

        if(privateProfile=="YES")
            profile=true
        else 
            profile=false
        axios.post('/api/personalInfo', {
                                        'firstName' : firstName,
                                        'lastName' : lastName,
                                        'email' : email,
                                        'username' : username,
                                        'birthday' : birthday,
                                        'gender': gender,
                                        'phone' : phone,
                                        'birogrpahy': biography,
                                        'interests': interests,
                                        'skills': skills,
                                        'private': profile
                                      })
          .then(function (response) {
            console.log(response);
          })
          .catch(function (error) {
            console.log(error);
          });

          
      }
    render(){
  return (
      
    <div className="card edit-profile-card">
                <div className="card-body">
              
                    <form>
                    <div className="inputFieldsDiv">
                    <h3>Personal infromation</h3>
                    <hr/>
                        <div className="row">
                        <div className="col-lg-3">
                        <p>First name</p>
                        </div>
                        <div className="col-lg-9">
                        <input id="firstName" type="text" required/>
                        </div>
                        </div>

                        <div className="row">
                        <div className="col-lg-3">
                        <p>Last name</p>
                        </div>
                        <div className="col-lg-9">
                        <input id="lastName" type="text" required/>
                        </div>
                        </div>

                        <div className="row">
                        <div className="col-lg-3">
                        <p>Birthday</p>
                        </div>
                        <div className="col-lg-9">
                        <DatePicker id="birthday" dateFormat={"yyyy-MM-dd"} selected={ this.state.startDate } onChange={this.handleChange} ></DatePicker>
                        </div>
                        </div>

                        <div className="row">
                        <div className="col-lg-3">
                        <p>Gender</p>
                        </div>
                        <div id="molimTe" className="col-lg-9">
                                <select id="gender">
                                    <option>
                                        MALE
                                    </option>
                                    <option>
                                        FEMALE
                                    </option>
                                </select>
                        </div>
                        </div>

                        <div className="row">
                        <div className="col-lg-3">
                        <p>Phone num</p>
                        </div>
                        <div className="col-lg-9">
                        <input id="phone" type="text" required/>
                        </div>
                        </div>

                        <div className="row">
                        <div className="col-lg-3">
                        <p>Email</p>
                        </div>
                        <div className="col-lg-9">
                        <input id="email" type="text" required/>
                        </div>
                        </div>

                        <div className="row">
                        <div className="col-lg-3">
                        <p>Username</p>
                        </div>
                        <div className="col-lg-9">
                        <input id="username" type="text" required/>
                        </div>
                        </div>
                        
                        <div className="row">
                        <div className="col-lg-3">
                        <p>Private</p>
                        </div>
                        <div id="molimTe" className="col-lg-9">
                                <select id="private">
                                    <option>
                                        YES
                                    </option>
                                    <option>
                                        NO
                                    </option>
                                </select>
                        </div>
                        </div>
                   
                        </div>


                        <h3>Biography</h3>
                        <hr/>
                        <textarea id="biography"></textarea>

                        <h3>Interests</h3>
                        <hr/>
                        <textarea id="interests"></textarea>

                        <h3>Skills</h3>
                        <hr/>
                        <textarea id="skills"></textarea>

                        <button onClick={ e => this.update(e)}>Save changes</button>
                    </form>
                    
                </div>
    </div>
  );
  }
};
  
export default EditProfile;