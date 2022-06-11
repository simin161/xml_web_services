Vue.component('emailForm', {
	data: function(){
		return{
            email: ""
		}
	},
template: `
		<div>
            <div className="page-content page-container"  style="margin: auto" id="page-content">
                <input type="text" placeholder="Enter your email :)" v-model="email"/>
                <input :disabled="isComplete" @click="navigate" type="button" value="Confirm"/>
             </div>
		</div>
		`
	,

	computed : {
        isComplete(){
            var validEmail = /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(.\w{2,3})+$/.test(this.email)
            if(validEmail)
                return false;
            return true;
        }
    },
    methods : {
        navigate : function(){
             const path= window.location.hash;
             if(path === '#/passwordless'){
                 axios.post('/api/passwordless', {"email" : this.email})
                      .then((response) => {
                          alert(response.data);
                      });
             }else{
                 axios.post('/api/forgottenPassword', {"email" : this.email})
                       .then((response) => {
                           alert(response.data);
                 });
             }
        }
    },
    mounted(){

    }

});