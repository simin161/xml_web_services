import React from 'react';
  
const SignIn = () => {
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
            <form>
              <label for="chk" aria-hidden="true">Sign up</label>
              <table style={{paddingLeft: "58px", marginTop: "-55px"}}>
                <tr>
                  <td>
                    <input style={{width: "94px"}} type="text" name="txt" placeholder="First name" required=""/>
                  </td>
                  <td>
                    <input style={{width: "94px"}} type="text" name="txt" placeholder="Last name" required=""/>
                  </td>
                </tr>
              </table>
              <input style={{marginTop: "-10px"}} type="email" name="email" placeholder="Email" required=""/>
              <input style={{marginTop: "-10px"}} type="text" name="txt" placeholder="Username" required=""/>
              <input style={{marginTop: "-10px"}} type="password" name="pswd" placeholder="Password" required=""/>
              <input style={{marginTop: "-10px"}} type="password" name="pswd" placeholder="Confirm Password" required=""/>
              <button>Sign Up</button>
            </form>
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