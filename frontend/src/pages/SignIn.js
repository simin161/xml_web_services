import React from 'react';
import axios from 'axios';
import {useNavigate} from "react-router-dom";
const SignIn = () => {
  let navigate=useNavigate()
  function register(e){
    e.stopPropagation();
    var firstName = document.getElementById("firstName").value;
    var lastName = document.getElementById("lastName").value;
    var email = document.getElementById("email").value;
    var username = document.getElementById("username").value;
    var password = document.getElementById("password").value;
    var confirmPassword = document.getElementById("confirmPassword").value;
    if(confirmPassword === password){
      axios.post(process.env.REACT_APP_BACKEND_URL + 'register', {
                                    'firstName' : firstName,
                                    'lastName' : lastName,
                                    'email' : email,
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
      console.log("invalid password");
    }
  }

  function logIn(e){
    e.stopPropagation();
    var email = document.getElementById("emailLog").value;
    var password = document.getElementById("passwordLog").value;
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
              <input style={{marginTop: "-10px"}} id="firstName" type="text" name="txt" placeholder="First name" required={true}/>
              <input style={{marginTop: "-10px"}} id="lastName" type="text" name="txt" placeholder="Last name" required={true}/>
              <input style={{marginTop: "-10px"}} id="email" type="email" name="email" placeholder="Email" required={true}/>
              <input style={{marginTop: "-10px"}} id="username" type="text" name="txt" placeholder="Username" required={true}/>
              <input style={{marginTop: "-10px"}} id="password" type="password" name="pswd" placeholder="Password" required={true}/>
              <input style={{marginTop: "-10px"}} id="confirmPassword" type="password" name="pswd" placeholder="Confirm Password" required={true}/>
              <button onClick={ e => register(e)}>Sign Up</button>
          
          </div>

          <div className="login">
            <form>
              <label htmlFor="chk" aria-hidden="true">Sign In</label>
              <input type="" id="emailLog" name="email" placeholder="Email" required={true}/>
              <input type="password" id="passwordLog" name="pswd" placeholder="Password" required={true}/>
              <button onClick={e => logIn(e)}>Sign In</button>
            </form>
          </div>
      </div>
    </div>
  );
};
  
export default SignIn;