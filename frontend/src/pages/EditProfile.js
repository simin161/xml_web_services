import React, { useState,useEffect} from 'react';
import axios from 'axios';
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import {useNavigate} from "react-router-dom";
const EditProfile = () => {
         const navigate=useNavigate()
         const [startDate, setStartDate] = useState();
         const email= window.location.pathname.split('/')[2]
        
          useEffect(()=>{
          
            axios.get(process.env.REACT_APP_BACKEND_URL + 'user/'+email+"/")
            .then(function (response) {
                  document.getElementById("email").value=response.data.email
                  document.getElementById("username").value=response.data.username
                  document.getElementById("firstName").value=response.data.firstName
                  document.getElementById("lastName").value=response.data.lastName 
                  document.getElementById("phone").value=response.data.phone
                  document.getElementById("gender").value=response.data.gender
                  document.getElementById("birthday").value=response.data.birthday
                  document.getElementById("biography").value=response.data.biography
                  document.getElementById("interests").value=response.data.interests
                  document.getElementById("skills").value=response.data.skills
                  if(response.data.privateProfile==true)
                  document.getElementById("private").value="YES"
                  else
                  document.getElementById("private").value="NO"
            })
            .catch(function (error) {
            console.log(error);
            });

          })

     function update(){
       
       
        var firstName = document.getElementById("firstName").value;
        console.log("ssdfsdf   "+firstName)
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
            profile="true"
        else 
            profile="false"
        axios.post(process.env.REACT_APP_BACKEND_URL + 'personalInfo',
         {
                                        'firstName' : firstName,
                                        'lastName' : lastName,
                                        'email' : email,
                                        'username' : username,
                                        'birthday' : birthday,
                                        'gender': gender,
                                        'phone' : phone,
                                        'biography': biography,
                                        'interests': interests,
                                        'skills': skills,
                                        'private': profile
          })
          .then(function (response) {
            console.log(response);
            
              navigate("/profilePage/"+email)
          
          })
          .catch(function (error) {
            console.log(error);
          });

          
      }
    
  return (
      
    <div className="card edit-profile-card">
                <div className="card-body">
              
                
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
                        <DatePicker id="birthday" dateFormat={"yyyy-MM-dd"} selected={ startDate } onChange={(date)=>setStartDate(date)} ></DatePicker>
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
                        <input id="phone" type="text" />
                        </div>
                        </div>

                        <div className="row">
                        <div className="col-lg-3">
                        <p>Email</p>
                        </div>
                        <div className="col-lg-9">
                        <input id="email" type="text" disabled/>
                        </div>
                        </div>

                        <div className="row">
                        <div className="col-lg-3">
                        <p>Username</p>
                        </div>
                        <div className="col-lg-9">
                        <input id="username" type="text" disabled/>
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

                        <button onClick={update}>Save changes</button>
                  
                </div>
    </div>
  );
  
};
  
export default EditProfile;