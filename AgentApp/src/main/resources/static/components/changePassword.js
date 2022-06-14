Vue.component('changePassword', {
	data: function(){
		return{
			password: "",
            confirmPassword : "",
		}
	},
template: `
		<div>
		    <p>Please, change your password!</p>
            <input type="password" v-model="password"/>
            <input type="password" v-model="confirmPassword"/>
            <input :disabled="isComplete" type="button" value="Confirm" @click="save"/>
		</div>
		`
	,
	computed : {
	    isComplete(){
           let validPassword = /^(?=.*\d)(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z]).{8,}$/.test(this.password)
                && this.password === this.confirmPassword;
           return !validPassword;
	    }
    },
    methods : {
        save : function(){
             axios.defaults.headers.common["Authorization"] =
                            localStorage.getItem("agentUser");
             axios.post("/api/changePassword", {"password" : this.password})
                  .then((response) =>{
                    if(response.data)
                        router.push("/firstPage");
                  })
        }
    },
    mounted(){
    }
});