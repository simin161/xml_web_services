<template>
 <div class = "main-div-login" id="slikadiv2"
      style="
        display: 'flex';
        justify-content: 'Right';
        align-items: 'Right';
        height: '200vh';
      "
    >
      <div class="main" >  	
        <input type="checkbox" id="chk" aria-hidden="true"/>

          <div class="signup" style="height: 110%">
              <label htmlFor="chk" aria-hidden="true">Sign up</label>
             

               <input style="margin-top: 10px; margin-left: 70px"  type="text" v-model="dtoReg.firstName" id="firstName" name="txt" placeholder="First name" required/>
               <input style="margin-top: 10px; margin-left: 70px" type="text" v-model="dtoReg.lastName" id="lastName" name="txt" placeholder="Last name" required/>

              <input style="margin-top: 10px; margin-left: 70px" id="email" v-model="dtoReg.email" type="email" name="email" placeholder="Email" required/>
              <input style="margin-top: 10px; margin-left: 70px" id="username" v-model="dtoReg.username" type="text" name="txt" placeholder="Username" required/>
              <select id="gender" v-model="dtoReg.gender" style=" margin-top : 10px; margin-left: 70px; height: 30px">
                <option value="FEMALE">FEMALE</option>
                <option value="MALE">MALE</option>
              </select>
              <input type="date" style="margin-top: 10px; margin-left: 70px" id="birthDate" v-model="dtoReg.birthDate" data-date-format="yyyy-mm-dd" />
              <input  id="password" v-model="dtoReg.password" style="margin-top: 10px; margin-left: 70px" type="password" name="pswd" placeholder="Password" required/>
              <input style="margin-top: 10px; margin-left: 70px" v-model="confirmPassword" id="confirmPassword" type="password" name="pswd" placeholder="Confirm Password" required/>
              <input type="button" id="buttonSignup" :disabled="isComplete" style="margin-top: 10px; margin-left: 70px" @click="register" value="Sign up"/>
          </div>

          <div class="login"  >
              <label htmlFor="chk" aria-hidden="true">Sign In</label>
              <input  style="margin-top: 10px; margin-left: 70px" type="email" v-model="logDto.email" id="emailLog" name="email" placeholder="Email" required/>
              <input style="margin-top: 10px; margin-left: 70px"   type="password" v-model="logDto.password" id="passwordLog" name="pswd" placeholder="Password" required/>
              <a href="/passwordless">Passworldess</a>
              <br>
              <a href="/forgottenPassword">Forgot password?</a>
              <br>
              <a href="/resendVerificationMail">Resend verification mail.</a>
              <input type="button" id="buttonSignup" :disabled="isLComplete" @click="logIn" value="Sign in" />
          </div>
      </div>
    </div>
</template>

<script>
   import axios from "axios";
   //import swal from 'sweetalert';  
 
  export default{
  data() {
    return {
      dtoReg:{
        firstName: '',
        lastName: '',
        email: '',
        gender: '',
        birthDate: '',
        username: '',
        password: ''
      },
      confirmPassword : '',
      logDto:{
        email: "",
        password: ""
      }
    };
  },
  mounted() {
    
  },
  methods: {
  logIn : function(){
    if(/^\w+([.-]?\w+)*@\w+([.-]?\w+)*(.\w{2,3})+$/.test(this.logDto.email)){  
      
          axios.post(process.env.VUE_APP_BACK + 'logInUser', {"email" : this.logDto.email, "password" : this.logDto.password})
          .then((response) => {
              console.log("ssssss"+response.data);
              if(!response.data ){
                  this.$swal.fire({  
                          title: " Invalid credentials!",  
                          text: " Please check your login credentials and try again.",  
                          icon: "error",  
                          button: "Confirm",  
                    }); 
                  console.log("err");
              }else{
                  localStorage.setItem("loggedUser", response.data.accessToken);
                  this.$router.push("/homepage");
              }
          })
          .catch(function (error) {
            console.log(error);
          });
        
      }else{
        this.$swal.fire({  
                          title: " Invalid email!",  
                          text: " A user with given email does not exist. Please, check your login credentials and try again.",  
                          icon: "error",  
                          button: "Confirm",  
                    }); 
      }
     
  },
  register: function(){
  if(/^(?=.*\d)(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z]).{8,}$/.test(this.dtoReg.password) && this.confirmPassword === this.dtoReg.password){
      if( /^[ a-zA-Z\-’]+$/.test(this.dtoReg.firstName) && /^[ a-zA-Z\-’]+$/.test(this.dtoReg.lastName)){
        if(/^\w+([.-]?\w+)*@\w+([.-]?\w+)*(.\w{2,3})+$/.test(this.dtoReg.email)){
          if(/^[a-zA-Z]+([0-9]+)?$/.test(this.dtoReg.username)){
            if(/\S/.test(this.dtoReg.birthDate) && /\S/.test(this.dtoReg.gender)){
                axios.post(process.env.VUE_APP_BACK + 'register', this.dtoReg)
                .then((response) => {
                  console.log(response);
                  if(response.data === 'false'){
                     this.$swal.fire({  
                          title: " Oops!",  
                          text: " Something went wrong, please try again later!",  
                          icon: "error",  
                          button: "Confirm",  
                    });    
                    console.log("err");
                  }
                  else{
                    this.$swal.fire({  
                            title: "Registration successful!",  
                            text: "Please check you email for an account verification message!",  
                            icon: "success",  
                            button: "Confirm",  
                      });  
                  }
                })
                .catch(function (error) {
                  console.log(error);
                });
              }else{
                alert("Gender and birthday  must not be empty!")
              }
            }else{
              alert("Invalid username")
            }
          }else{
            alert("Invalid email");
          }
        }else{
          alert("Invalid first or last name");
        }
      }else{
        alert("invalid password or password should contain at least one uppercase letter, one number and one special character");
      }
   }
  },
  computed:{
    isComplete(){
      var validNames = /^[ a-zA-Z\-’]+$/.test(this.dtoReg.firstName) && /^[ a-zA-Z\-’]+$/.test(this.dtoReg.lastName);
      var validPassword = /^(?=.*\d)(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z]).{8,}$/.test(this.dtoReg.password)
       && /^(?=.*\d)(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z]).{8,}$/.test(this.confirmPassword);        
      var validEmail = /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(.\w{2,3})+$/.test(this.dtoReg.email);
      var validGBD = /\S/.test(this.dtoReg.birthDate) && /\S/.test(this.dtoReg.gender);
      var validUsername = /^[a-zA-Z]+([0-9]+)?$/.test(this.dtoReg.username);
      if(validNames && validPassword && validEmail && validGBD && validUsername)
        return false;

      return true;
    },
    isLComplete(){
      var validEmail = /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(.\w{2,3})+$/.test(this.logDto.email);
      if(validEmail)
        return false;

      return true;
    }
  }
};
</script>

<style>


code {
  font-family: source-code-pro, Menlo, Monaco, Consolas, 'Courier New',
    monospace;
}

.main-div-login{
  margin: 0;
	padding: 0;
	display: flex;
	justify-content: center;
	align-items: center;
	min-height: 100vh;
	font-family: 'Jost', sans-serif;
	background: linear-gradient(to bottom, #0f0c29, #3f2b63, #e385fe);
}
.main{
	width: 350px;
	height:550px;
	background: red;
	overflow: hidden;
	background: url("https://doc-08-2c-docs.googleusercontent.com/docs/securesc/68c90smiglihng9534mvqmq1946dmis5/fo0picsp1nhiucmc0l25s29respgpr4j/1631524275000/03522360960922298374/03522360960922298374/1Sx0jhdpEpnNIydS4rnN4kHSJtU1EyWka?e=view&authuser=0&nonce=gcrocepgbb17m&user=03522360960922298374&hash=tfhgbs86ka6divo3llbvp93mg4csvb38") no-repeat center/ cover;
	border-radius: 10px;
	box-shadow: 5px 20px 50px #000;
    margin:auto;
}
#chk{
	display: none;
}
.signup{
	position: relative;
	width:100%;
	height: 100%;
}
label{
	color: #fff;
	font-size: 2.3em;
	justify-content: center;
	display: flex;
	margin: 1px;
	font-weight: bold;
	cursor: pointer;
	transition: .5s ease-in-out;
}
input, select{
	width: 60%;
	height: 35px;
	background: #e0dede;
	justify-content: center;
	display: flex;
	margin: 10px auto;
	padding: 10px;
	border: none;
	outline: none;
	border-radius: 5px;
}
#buttonSignup{
	width: 60%;
	height: 40px;
	margin: 10px auto;
	justify-content: center;
	display: block;
	color: #fff;
	background: #e385fe;
	font-size: 1em;
	font-weight: bold;
	margin-top: 20px;
	outline: none;
	border: none;
	border-radius: 5px;
	transition: .2s ease-in;
	cursor: pointer;
}
#buttonSignup{
	background: #3f2b63;
}
.login{
	height: 560px;
	background: #eee;
	border-radius: 60% / 30%;
	transform: translateY(-140px);
	transition: .8s ease-in-out;
}
.login label{
	color: #3f2b63;
	transform: scale(.6);
}

#chk:checked ~ .login{
	transform: translateY(-500px);
}
#chk:checked ~ .login label{
	transform: scale(1);	
}
#chk:checked ~ .signup label{
	transform: scale(.6);
}


</style>
