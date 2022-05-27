Vue.component('registerCompany', {
	data: function(){
		return{
            company : {
                name : ""
            }
		}
	},
template: `
		<div>
            <input type="text" v-model="company.name" placeholder="Company name"/>
            <input type="button" value="Register company" @click="registerCompany"/>
		</div>
		`
	,

	computed : {

    },
    methods : {
        navigateToRC : function(){
            this.router.push("/registerCompany")
        },
        registerCompany : function(){
            axios.post("/api/registerCompany", this.company)
                 .then((response) => {console.log(response.data)});
        }
    },
    mounted(){

    }

});