Vue.component('details-screen', {
	data: function(){
    		return{
    			cert:null,
    			certsAbove: []
    		}
    	},
    template: `
            <div>
                <div><input type="button" value="Show as pdf"/> </div>
                {{cert}}
                <div v-for="c in certsAbove">
                    <div>
                        {{c.alias}} {{c.serialNumber}}
                    </div>
                </div>
            </div>
        `
    ,
    methods:{
    },
    mounted(){
            axios.get("/getChoosenCert")
                 .then(response => {this.cert = response.data});
    }
});
