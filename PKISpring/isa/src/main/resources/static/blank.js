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
    			},
    			confirmPassword: ""
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
                  <input type="button" :disabled="isLComplete" value="Sign in" @click="signIn"/>
              </div>
              <div style="margin-left: auto; margin-right:auto; width:30%;" v-if="showPage === 1">
                  <input type="text" placeholder="First name" v-model="user.firstName"/>
                  <input type="text" placeholder="Last name" v-model="user.lastName"/>
                  <input type="text" placeholder="Organization name" v-model="user.organizationName"/>
                  <input type="text" placeholder="Organization unit" v-model="user.organizationUnit"/>
                  <input type="text" placeholder="Country ID (for example: SRB, SLO, CRO...)" v-model="user.countryId"/>
                  <input type="text" placeholder="E-mail" v-model="user.email"/>
                  <input type="password" placeholder="Password" v-model="user.password"/>
                  <input type="password" placeholder="Confirm password" v-model="confirmPassword"/>
                  <input type="button" :disabled="isRComplete" value="Register" @click="register"/>
              </div>
         </div>

        `
        ,
        computed: {
            isRComplete(){
                 var validNames = /^[ a-zA-Z\-’]+$/.test(this.user.firstName) && /^[ a-zA-Z\-’]+$/.test(this.user.lastName);
                 var validOGNames = /^[a-zA-Z0-9]+$/.test(this.user.organizationName) &&
                 /&[a-zA-Z0-9]+$/.test(this.user.organizationUnit);
                 var validCId = /^[A-Z]+$/.test(this.user.countryId);
                 var validEmail = /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(.\w{2,3})+$/.test(this.user.email);
                 var validPassword = /^(?=.*\d)(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z]).{8,}$/.test(this.user.password);
                 if(validNames && validOGNames && validCId && validEmail && validPassword && this.user.password === this.confirmPassword)
                    return false;

                 return true;
            },
            isLComplete(){
                 var validEmail = /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(.\w{2,3})+$/.test(this.logUser.email);
                 var validPassword = /\S/.test(this.logUser.password);
                 if(validEmail && validPassword)
                    return false;
                 return true;
            }
        },
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
