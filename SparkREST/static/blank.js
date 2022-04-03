Vue.component('homepage', {
	data: function(){
    		return{
    			showPage: 0,
    			user: {
    			    firstName: "",
                    lastName: "",
                    organizationName: "",
                    organizationUnit: "",
                    countryId: "",
                    email: "",
                    password: ""
    			},
    			logUser: {
    			    email: "",
    			    password: "",
    			}
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
                  <input type="text" placeholder="Password" v-model="logUser.password"/>
                  <input type="button" value="Sign in" @click="signIn"/>
              </div>
              <div style="margin-left: auto; margin-right:auto; width:30%;" v-if="showPage === 1">
                  <input type="text" placeholder="First name" v-model="user.firstName"/>
                  <input type="text" placeholder="Last name" v-model="user.lastName"/>
                  <input type="text" placeholder="Organization name" v-model="user.organizationName"/>
                  <input type="text" placeholder="Organization unit" v-model="user.organizationUnit"/>
                  <input type="text" placeholder="Country ID (for example: SRB, SLO, CRO...)" v-model="user.countryId"/>
                  <input type="text" placeholder="E-mail" v-model="user.email"/>
                  <input type="text" placeholder="Password" v-model="user.password"/>
                  <input type="button" value="Register" @click="register"/>
              </div>
         </div>

        `
        ,
        methods:{
            changeForm : function(){
                if(this.showPage == 0){
                    this.showPage = 1;
                }
                else{
                    this.showPage = 0;
                }
            },
            signIn : function(){
                axios.post("/signIn", this.logUser)
                     .then(response => {
                        if(response.data == true){
                            router.push('/firstPage')
                        }else{
                            console.log("Ne")
                        }
                     })
            },
            register : function(){
                axios.post("/register", this.user)
                     .then(response => {
                        if(response.data == true){
                            router.push('/firstPage')
                        }else{
                            console.log("Ne")
                        }
                     })
            }
        }
        ,
        mounted(){
            axios.get("/getMessage")
                 .then(response=> (this.message = response.data))
        }
});
