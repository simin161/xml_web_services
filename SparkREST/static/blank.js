Vue.component('homepage', {
	data: function(){
    		return{
    			message: ""
    		}
    	},
    template: `

    	<p>Ousi oupac {{message}}</p>

    		`
        ,

          mounted(){

                axios.get("/getMessage")
                .then(response=> (this.message = response.data))

          }
});
