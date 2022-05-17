import React, { useState } from 'react';
import axios from 'axios';
import {useNavigate} from "react-router-dom";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
const SignIn = () => {
  let navigate=useNavigate()
  function register(e){
    e.stopPropagation();
    var firstName = document.getElementById("firstName").value;
    var lastName = document.getElementById("lastName").value;
    var email = document.getElementById("email").value;
    var gender = document.getElementById("gender").value;
    var birthDate = document.getElementById("birthDate").value;
    var username = document.getElementById("username").value;
    var password = document.getElementById("password").value;
    var confirmPassword = document.getElementById("confirmPassword").value;
    if(isValidPassword(password) && (confirmPassword === password)){
      if(isValidName(firstName) && isValidName(lastName) && isValidUsername(username) && isValidEmail(email)){
        axios.post(process.env.REACT_APP_BACKEND_URL + 'register', {
                                      'firstName' : firstName,
                                      'lastName' : lastName,
                                      'email' : email,
                                      'gender': gender,
                                      'birthDate': birthDate,
                                      'username' : username,
                                      'password' : password
                                    })
        .then(function (response) {
          console.log(response);
          if(!response.data){
            console.log("err");
          }else{
            localStorage.setItem("loggedUser", response.data);
            navigate("/profilePage/" + email, {replace: true});
          }
        })
        .catch(function (error) {
          console.log(error);
        });
    }else{
      alert("Invalid input");
    }
    }else{
      alert("invalid password or password should contain at least one uppercase letter, one number and one special character");
    }
  }

  function isValidEmail(name){
    var regEx =/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
    if(regEx.test(name)){
      console.log(regEx.test(name));
      return true;
    }
    return false;
  }

  function isValidName(name){
    var regEx = /^[ a-zA-Z\-\’]+$/;
    if(!regEx.test(name)){
      return false;
    }
    return true;
  }

  function isValidUsername(name){
    var regEx = /^[a-zA-Z]+([0-9]+)?$/;
    if(!regEx.test(name)){
      return false;
    }
    return true;
  }

  function isValidPassword(name){
    var regEx = /^(?=.*\d)(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z]).{8,}$/;
    if(!regEx.test(name)){
      console.log(regEx.test(name));

      return true;
    }
    return true;
  }

  function logIn(e){
    e.stopPropagation();
    var email = document.getElementById("emailLog").value;
    var password = document.getElementById("passwordLog").value;
    if(isValidEmail(email)){
      axios.post(process.env.REACT_APP_BACKEND_URL + 'logInUser', {
        'email' : email,
        'password': password
      })
      .then(function (response) {
          console.log(response);
          if(!response.data ){
              console.log("err");
          }else{
              localStorage.setItem("loggedUser", response.data);
              navigate("/profilePage/" + email, {replace: true});
          }
      })
      .catch(function (error) {
        console.log(error);
      });
    }
  }
  const [startDate, setStartDate] = useState(new Date());
  return (
    <div className = "main-div-login"
      style={{
        display: 'flex',
        justifyContent: 'Right',
        alignItems: 'Right',
        height: '100vh'
      }}
    >
      <div className="main">  	
        <input type="checkbox" id="chk" aria-hidden="true"/>

          <div className="signup">
              <label htmlFor="chk" aria-hidden="true">Sign up</label>
              <table style={{marginLeft: "69px", marginTop: "-70px"}}>
                <tr>
                  <td>
                    <input style={{width: "105px"}} type="text" id="firstName" name="txt" placeholder="First name" required={true}/>
                  </td>
                  <td>
                    <input style={{width: "105px"}} type="text" id="lastName" name="txt" placeholder="Last name" required={true}/>
                  </td>
                </tr>
              </table>
              <input style={{marginTop: "-10px"}} id="email" type="email" name="email" placeholder="Email" required={true}/>
              <input style={{marginTop: "-10px"}} id="username" type="text" name="txt" placeholder="Username" required={true}/>
              <select id="gender" style={{marginLeft: '70px', marginTop : '-10px', height: '30px'}}>
                <option>FEMALE</option>
                <option>MALE</option>
              </select>
              <DatePicker id="birthDate" selected={startDate} onChange={date => setStartDate(date)} dateFormat= "yyyy-MM-dd" />
              <input  id="password" style={{marginTop: "-10px"}} type="password" name="pswd" placeholder="Password" required={true}/>
              <input style={{marginTop: "-10px"}} id="confirmPassword" type="password" name="pswd" placeholder="Confirm Password" required={true}/>
              <button style={{marginTop: "-10px"}} onClick={ e => register(e)}>Sign Up</button>
          </div>

          <div className="login">
              <label htmlFor="chk" aria-hidden="true">Sign In</label>
              <input type="email" id="emailLog" name="email" placeholder="Email" required={true}/>
              <input type="password" id="passwordLog" name="pswd" placeholder="Password" required={true}/>
              <button onClick={e => logIn(e)}>Sign In</button>
          </div>
      </div>
    </div>
  );
};
  
export default SignIn;