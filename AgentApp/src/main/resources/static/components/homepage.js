Vue.component('homepage', {
	data: function(){
		return{	
			logUser: {
			    email: '',
			    password: ''
			},
            registerUser: {
                firstName : "",
                lastName : "",
                password: "",
                email: ""
            },
            confirmPassword : "",
            showPage: 0,
            buttonText : "Sign up"
		}
	},
template: `	
		<div>
        <nav  class="navbar navbar-fixed-top navbar-expand"  style="background-color: #3e214f; list-style: none; box-shadow: rgba(0, 0, 0, 0.15) 1.95px 1.95px 2.6px; ">
        <div class="container-fluid" style="background-color: #3e214f; text-align: right">
        <a class="navbar-brand"   >
        <img src="../images/cover.png" alt="" width="200" height="80" >
        </a>
        </div>
      </nav>
      <br>
      <br>
		     <div style="margin-left: auto; margin-right:auto; width:30%;">
                 <button class="btn" id="buttonPurple" @click="changeForm">{{buttonText}}</button>
                 </div>
                 <br>
                 <div style="margin: 10%;  width:30%;" v-if="showPage === 0">
                    <h1 style="text-align: left; color: #3e214f">Log in</h1>
                     <input class="form-control" type="text" placeholder="E-mail" v-model="logUser.email"/>
                     <input class="form-control" type="password" placeholder="Password" v-model="logUser.password"/>
                     <input class="form-control" type="text"  placeholder="Google Auth Code" v-model="logUser.code"/>
                     <button :disabled="isLogComplete" class="btn" id="buttonPurple"  @click="signIn">Sign in</button>
                     <a href="#/passwordless">Passwordless</a>
                     <a href="#/forgottenPassword">Forgotten password?</a>
                 </div>
             <div style="margin-left: 10%;  width:30%;" v-if="showPage === 1">
             <h1 style="text-align: left; color: #3e214f">Sign up</h1>
		           <table>
		                <tr>
		                    <td><input  class="form-control" type="text" placeholder="First Name" v-model="registerUser.firstName"/></td>
		                </tr>
		                <tr>
		                    <td><input  class="form-control" type="text" placeholder="Last Name" v-model="registerUser.lastName"/></td>
                        </tr>
                        <tr>
                            <td><input  class="form-control" type="text" placeholder="Email" v-model="registerUser.email"/></td>
                        </tr>
                        <tr>
                            <td><input  class="form-control" type="password" placeholder="Password" v-model="registerUser.password"/></td>
                        </tr>
                        <tr>
                            <td><input  class="form-control" type="password" placeholder="Confirm password" v-model="confirmPassword"/></td>
                        </tr>
                        <tr>
                            <td><button :disabled="isRegComplete" class="btn" id="buttonPurple"  @click="register">Register</button></td>
                        </tr>
		           </table>
		    </div>
		</div>
		`
	,

	computed : {
        isRegComplete(){
            let validEmail = /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(.\w{2,3})+$/.test(this.registerUser.email);
            let validNames = /^[ a-zA-Z\-’]+$/.test(this.registerUser.firstName) && /^[ a-zA-Z\-’]+$/.test(this.registerUser.lastName);
            let validPassword = /^(?=.*\d)(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z]).{8,}$/.test(this.registerUser.password)
                        && /^(?=.*\d)(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z]).{8,}$/.test(this.confirmPassword);
            if(validEmail && validNames && validPassword && this.registerUser.password === this.confirmPassword)
                return false;
            return true;
        },
        isLogComplete(){
            let validEmail = /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(.\w{2,3})+$/.test(this.logUser.email);
            let isPasswordNonEmpty = /\S/.test(this.logUser.password);
            if(validEmail && isPasswordNonEmpty)
                return false;
            return true;
        }
    },
    methods : {
changeForm : function(){
                if(this.showPage == 0){
                    this.buttonText = "Log in"
                    this.showPage = 1;
                }
                else{
                    this.buttonText = "Sign up"
                    this.showPage = 0;
                }
            },
            signIn : function(){
              axios.post("/api/logIn", this.logUser)
                     .then(response => {
                        if(response.data !== ""){
                            localStorage.setItem("agentUser", response.data.accessToken);
                            if(this.logUser.password.length === 6){
                                router.push('/changePassword');
                            }
                            else
                                router.push('/firstPage');
                        }else{
                            console.log("Ne")
                        }
              })
            },
            register : function(){
                axios.post("/api/register", this.registerUser)
                     .then(response => {
                        if(response.data !== ""){
                            localStorage.setItem("agentUser", response.data.accessToken);
                            router.push('/firstPage')
                            console.log("Da")
                        }else{
                            console.log("Ne")
                        }
                     })
            }

    },
    mounted(){

    }

});