import React, { useState,useEffect} from 'react';
import axios from 'axios';
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import {useNavigate} from "react-router-dom";
const CreatePost = () => {
         const navigate=useNavigate()
         const [startDate, setStartDate] = useState();
         const email= window.location.pathname.split('/')[2]
        
          useEffect(()=>{
          
            axios.get(process.env.REACT_APP_BACKEND_URL + 'user/'+email+"/")
            .then(function (response) {
                  document.getElementById("email").value=response.data.email
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
        axios.post(process.env.REACT_APP_BACKEND_URL + 'newPost',
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

                        <h3>This post is about...</h3>
                        <hr/>
                        <textarea id="about"></textarea>

                        <h3>TODO: Add image</h3>
                        <hr/>
                        <textarea id="image"></textarea>

                        <h3>Link:</h3>
                        <hr/>
                        <textarea id="link"></textarea>

                        <button onClick={update}>Create new post</button>
                  
                </div>
            </div>
    </div>
  );
  
};
  
export default CreatePost;