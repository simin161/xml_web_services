import React from 'react';
import axios from 'axios';
const SignIn = () => {
  
  function register(e){
    e.stopPropagation();
    console.log("ikad???");
    var firstName = document.getElementById("firstName").value;
    var lastName = document.getElementById("lastName").value;
    var email = document.getElementById("email").value;
    var password = document.getElementById("password").value;
    var confirmPassword = document.getElementById("confirmPassword").value;
    if(confirmPassword === password){
      axios.post('/api/register', {
                                    'firstName' : firstName,
                                    'lastName' : lastName,
                                    'email' : email,
                                    'password' : password
                                  })
      .then(function (response) {
        console.log(response);
      })
      .catch(function (error) {
        console.log(error);
      });
    }else{
      console.log("invalid password");
    }
  }

  return (
    <div class = "main-div-login"
      style={{
        display: 'flex',
        justifyContent: 'Right',
        alignItems: 'Right',
        height: '100vh'
      }}
    >
      <div class="main">  	
        <input type="checkbox" id="chk" aria-hidden="true"/>

          <div class="signup">
              <label for="chk" aria-hidden="true">Sign up</label>
              <table style={{paddingLeft: "58px", marginTop: "-55px"}}>
                <tr>
                  <td>
                    <input style={{width: "94px"}} id="firstName" type="text" name="txt" placeholder="First name" required="true"/>
                  </td>
                  <td>
                    <input style={{width: "94px"}} id="lastName" type="text" name="txt" placeholder="Last name" required="true"/>
                  </td>
                </tr>
              </table>
              <input style={{marginTop: "-10px"}} id="email" type="email" name="email" placeholder="Email" required="true"/>
              <input style={{marginTop: "-10px"}} id="username" type="text" name="txt" placeholder="Username" required="true"/>
              <input style={{marginTop: "-10px"}} id="password" type="password" name="pswd" placeholder="Password" required="true"/>
              <input style={{marginTop: "-10px"}} id="confirmPassword" type="password" name="pswd" placeholder="Confirm Password" required="true"/>
              <button onClick={ e => register(e)}>Sign Up</button>
          </div>

          <div class="login">
            <form>
              <label for="chk" aria-hidden="true">Sign In</label>
              <input type="email" name="email" placeholder="Email" required=""/>
              <input type="password" name="pswd" placeholder="Password" required=""/>
              <button>Sign In</button>
            </form>
          </div>
      </div>
    </div>
  );
};
  
export default SignIn;