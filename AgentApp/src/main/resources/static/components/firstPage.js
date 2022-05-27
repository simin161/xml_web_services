Vue.component('firstPage', {
	data: function(){
		return{

		}
	},
template: `
		<div>
		    <input type="button" value="Register company" @click="navigateToRC"/>
		</div>
		`
	,

	computed : {

    },
    methods : {
        navigateToRC : function(){
            router.push("/registerCompany")
        }
    },
    mounted(){

    }

});