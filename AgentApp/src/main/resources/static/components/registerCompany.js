Vue.component('registerCompany', {
	data: function(){
		return{
            company : {
                name : "",
                field: "",
                description: "",
                contactInfo: ""
            }
		}
	},
template: `
		<div>
            <input type="text" v-model="company.name" placeholder="Company name"/>
            <input type="text" v-model="company.field" placeholder="Field"/>
            <textarea v-model="company.contactInfo" placeholder="Contact information"/>
            <textarea v-model="company.description" placeholder="Description"/>
            <input type="button" value="Register company" @click="registerCompany"/>
		</div>
		`
	,

	computed : {

    },
    methods : {
        registerCompany : function(){
             axios.defaults.headers.common["Authorization"] =
                                       localStorage.getItem("agentUser");
            axios.post("/api/registerCompany", this.company)
                 .then((response) => {console.log(response.data)});
        }
    },
    mounted(){

    }

});