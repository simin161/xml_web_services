<template>
 <div className = "main-div-login"
      style="
        display: 'flex';
        justify-content: 'Right';
        align-items: 'Right';
        height: '100vh'
      "
    >
      <div className="main">  	
        <input type="checkbox" id="chk" aria-hidden="true"/>

          <div className="signup">
              <label htmlFor="chk" aria-hidden="true">Sign up</label>
              <table style="margin-left: 69px; margin-top: -70px">
                <tr>
                  <td>
                    <input style="width: 105px"  type="text" v-model="dtoReg.firstName" id="firstName" name="txt" placeholder="First name" required/>
                  </td>
                  <td>
                    <input style="width: 105px" type="text" v-model="dtoReg.lastName" id="lastName" name="txt" placeholder="Last name" required/>
                  </td>
                </tr>
              </table>
              <input style="margin-top: -10px" id="email" v-model="dtoReg.email" type="email" name="email" placeholder="Email" required/>
              <input style="margin-top: -10px" id="username" v-model="dtoReg.username" type="text" name="txt" placeholder="Username" required/>
              <select id="gender" v-model="dtoReg.gender" style="margin-left: 70px, margin-top : -10px, height: 30px">
                <option value="FEMALE">FEMALE</option>
                <option value="MALE">MALE</option>
              </select>
              <input type="date" id="birthDate" v-model="dtoReg.birthDate" data-date-format="yyyy-mm-dd" />
              <input  id="password" v-model="dtoReg.password" style="margin-top: -10px" type="password" name="pswd" placeholder="Password" required/>
              <input style="margin-top: -10px" v-model="confirmPassword" id="confirmPassword" type="password" name="pswd" placeholder="Confirm Password" required/>
              <button :disable="isComplete" style="margin-top: -10px" @click="register">Sign Up</button>
          </div>

          <div className="login">
              <label htmlFor="chk" aria-hidden="true">Sign In</label>
              <input type="email" v-model="logDto.email" id="emailLog" name="email" placeholder="Email" required/>
              <input type="password" v-model="logDto.password" id="passwordLog" name="pswd" placeholder="Password" required/>
              <button @click="logIn">Sign In</button>
          </div>
      </div>
    </div>
</template>

<script>
   import axios from "axios";
   import swal from 'sweetalert';  
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
      if(/^(?=.*\d)(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z]).{8,}$/.test(this.logDto.password)){
          axios.post(process.env.VUE_APP_BACK + 'logInUser', {"email" : this.logDto.email, "password" : this.logDto.password})
          .then((response) => {
              console.log(response);
              if(!response.data ){
                  console.log("err");
              }else{
                  localStorage.setItem("loggedUser", response.data.accessToken);
                  this.$router.push("/profilePage");
              }
          })
          .catch(function (error) {
            console.log(error);
          });
        }else{
          alert("Invalid password form");
        }
      }else{
        alert("Invalid email form");
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
                  if(!response.data){
                     swal({  
                          title: " Oops!",  
                          text: " Something went wrong, please try again later!",  
                          icon: "error",  
                          button: "Confirm",  
                    });    
                    console.log("err");
                  }
                  else{
                    swal({  
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
      if(validNames)
        return false;

      return true;
    }
  }
};
</script>

<style>
</style>
