Vue.component('jobOffersForUser', {
	data: function(){
		return{
            jobOffers : {}
		}
	},
template: `
		<div>
            <div v-for="jo in jobOffers">
                <input type="text" v-model="jo.position"/>
		        <input type="text" v-model="jo.companyName"/>
		        <input type="text" v-model="jo.jobDescription"/>
		        <input type="text" v-model="jo.dailyActivities"/>
		        <input type="text" v-model="jo.candidateRequirements"/>
                <input type="button" value="Send job offer" @click="sendRequest(jo)"/>
            </div>
		</div>
		`
	,

	computed : {

    },
    methods : {
        sendRequest : function(jo){
             axios.defaults.headers.common["Authorization"] =
                                       localStorage.getItem("agentUser");
            axios.post("/api/sendJobOfferRequest", jo)
                 .then((response) => {alert(response.data)});
        }
    },
    mounted(){
         axios.defaults.headers.common["Authorization"] =
                                               localStorage.getItem("agentUser");
         axios.get("/api/getJobOffersForUser")
              .then((response) => this.jobOffers = response.data);
    }
});