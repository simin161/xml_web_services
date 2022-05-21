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
                    <input style="width: 105px" pattern="^[ a-zA-Z\-\’]+$"  type="text" v-model="dtoReg.firstName" id="firstName" name="txt" placeholder="First name" required/>
                  </td>
                  <td>
                    <input style="width: 105px" pattern="^[ a-zA-Z\-\’]+$" type="text" v-model="dtoReg.lastName" id="lastName" name="txt" placeholder="Last name" required/>
                  </td>
                </tr>
              </table>
              <input style="margin-top: -10px" pattern="^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$" id="email" v-model="dtoReg.email" type="email" name="email" placeholder="Email" required/>
              <input style="margin-top: -10px" pattern="^[a-zA-Z]+([0-9]+)?$" id="username" v-model="dtoReg.username" type="text" name="txt" placeholder="Username" required/>
              <select id="gender" v-model="dtoReg.gender" style="margin-left: 70px, margin-top : -10px, height: 30px">
                <option value="FEMALE">FEMALE</option>
                <option value="MALE">MALE</option>
              </select>
              <input type="date" id="birthDate" v-model="dtoReg.birthDate" data-date-format="yyyy-mm-dd" />
              <input  id="password" pattern="^(?=.*\d)(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z]).{8,}$" v-model="dtoReg.password" style="margin-top: -10px" type="password" name="pswd" placeholder="Password" required/>
              <input style="margin-top: -10px" pattern="^(?=.*\d)(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z]).{8,}$" v-model="confirmPassword" id="confirmPassword" type="password" name="pswd" placeholder="Confirm Password" required/>
              <button style="margin-top: -10px" @click="register">Sign Up</button>
          </div>

          <div className="login">
              <label htmlFor="chk" aria-hidden="true">Sign In</label>
              <input type="email" v-model="logDto.email" pattern="^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$" id="emailLog" name="email" placeholder="Email" required/>
              <input type="password" v-model="logDto.password" id="passwordLog" name="pswd" placeholder="Password" required/>
              <button @click="logIn">Sign In</button>
          </div>
      </div>
    </div>
</template>

<script>
   import axios from "axios";
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
     
  },
  register: function(){
  if(this.confirmPassword === this.dtoReg.password){
          axios.post(process.env.VUE_APP_BACK + 'register', this.dtoReg)
          .then((response) => {
            console.log(response);
            if(!response.data){
              console.log("err");
            }else{
              localStorage.setItem("loggedUser", response.data);
              this.$router.push("/profilePage");
            }
          })
          .catch(function (error) {
            console.log(error);
          });
      }else{
        alert("invalid password or password should contain at least one uppercase letter, one number and one special character");
      }
  }}
};
</script>

<style>
</style>
