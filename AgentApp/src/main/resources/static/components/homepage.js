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
            showPage: 0
		}
	},
template: `	
		<div>
		     <div style="margin-left: auto; margin-right:auto; width:30%;">
                 <input type="button" value="Change form" @click="changeForm"/>
                 </div>
                 <br>
                 <div style="margin-left: auto; margin-right:auto; width:30%;" v-if="showPage === 0">
                     <input type="text" placeholder="E-mail" v-model="logUser.email"/>
                     <input type="password" placeholder="Password" v-model="logUser.password"/>
                     <input type="button" value="Sign in" @click="signIn"/>
                 </div>
             <div style="margin-left: auto; margin-right:auto; width:30%;" v-if="showPage === 1">
		           <table>
		                <tr>
		                    <td><input type="text" placeholder="First Name" v-model="registerUser.firstName"/></td>
		                </tr>
		                <tr>
		                    <td><input type="text" placeholder="Last Name" v-model="registerUser.lastName"/></td>
                        </tr>
                        <tr>
                            <td><input type="text" placeholder="Email" v-model="registerUser.email"/></td>
                        </tr>
                        <tr>
                            <td><input type="password" placeholder="Password" v-model="registerUser.password"/></td>
                        </tr>
                        <tr>
                            <td><input type="password" placeholder="Confirm password"/></td>
                        </tr>
                        <tr>
                            <td><input type="button" value="Register" @click="register"/></td>
                        </tr>
		           </table>
		    </div>
		</div>
		`
	,

	computed : {

    },
    methods : {
changeForm : function(){
                if(this.showPage == 0){
                    this.showPage = 1;
                }
                else{
                    this.showPage = 0;
                }
            },
            signIn : function(){
              axios.post("/api/logIn", this.logUser)
                     .then(response => {
                        if(response.data == true){
                            //router.push('/firstPage')
                            console.log("Da")
                        }else{
                            console.log("Ne")
                        }
              })
            },
            register : function(){
                axios.post("/api/register", this.registerUser)
                     .then(response => {
                        if(response.data == true){
                            //router.push('/firstPage')
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