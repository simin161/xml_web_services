Vue.component('firstpage', {
	data: function(){
    		return{
    			showPage: 0,
    			user: {
    			    firstName: "",
                    lastName: "",
                    organizationName: "",
                    organizationUnit: "",
                    countryId: "",
                    email: ""
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
    	        <input type="button" value="Sign out" @click="signOut"/>
    	      </div>
         </div>
        `
    ,
    methods:{
        signOut : function(){
            axios.get("/signOut")
                 .then(response=>{
                    if(response.data == true){
                        router.push("/")
                    }
                    else{
                        console.log("ne.")
                    }
                 })
        }
    }
});
