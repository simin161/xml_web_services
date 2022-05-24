Vue.component('homepage', {
	data: function(){
		return{	
			logUser: {
			    email: '',
			    password: ''
			},
            registerUser: null,
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
		                    <td><input type="text" placeholder="First Name"/></td>
		                </tr>
		                <tr>
		                    <td><input type="text" placeholder="Last Name"/></td>
                        </tr>
                        <tr>
		                    <td><input type="text" placeholder="Username"/></td>
                        </tr>
                        <tr>
                            <td><input type="text" placeholder="Email"/></td>
                        </tr>
                        <tr>
                            <td><input type="date" placeholder="Birthday"/></td>
                        </tr>
                        <tr>
                            <td>
                                <select>
                                    <option value="FEMALE">FEMALE</option>
                                    <option value="MALE">MALE</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td><input type="password" placeholder="Password"/></td>
                        </tr>
                        <tr>
                            <td><input type="password" placeholder="Confirm password"/></td>
                        </tr>
                        <tr>
                            <td><input type="button" value="Register"/></td>
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
               /* axios.post("/signIn", this.logUser)
                     .then(response => {
                        if(response.data == true){
                            router.push('/firstPage')
                        }else{
                            console.log("Ne")
                        }
                     })*/
            },
            register : function(){
                /*axios.post("/register", this.user)
                     .then(response => {
                        if(response.data == true){
                            router.push('/firstPage')
                        }else{
                            console.log("Ne")
                        }
                     })*/
            }

    },
    mounted(){

    }

});