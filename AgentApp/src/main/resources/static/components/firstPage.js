Vue.component('firstPage', {
	data: function(){
		return{
            isAdmin : false,
            showRequests: 0,
            allReq : {}
		}
	},
template: `
		<div>
		    <input v-show="!isAdmin" type="button" value="Register company" @click="navigateToRC"/>
		    <input v-show="isAdmin" type="button" value="See all company reg requests" @click="showComReq"/>

		    <div v-show="showRequests == 1">
		        <div style="1px solid black" v-for="req in allReq">
		            <p>{{req.name}}</p>
		            <p>{{req.contactInfo}}</p>
		            <p>{{req.owner.firstName}} {{req.owner.lastName}}</p>
		            <p>{{req.field}}</p>
		            <p>{{req.description}}</p>
		            <input type="button" value="Accept" @click="accept(req)"/>
		            <input type="button" value="Decline" @click="decline(req)"/>
		        </div>
		    </div>
		</div>
		`
	,

	computed : {

    },
    methods : {
        accept : function(req){
            axios.post("/api/changeCompanyStatus", {id: req.id, status : "ACCEPTED"})
                 .then((response) => {
                     axios.get("/api/getAllCompanyRequests")
                           .then((response) => this.allReq = response.data);
                 })
        },
        decline : function(req){
           axios.post("/api/changeCompanyStatus", {id: req.id, status : "DECLINED"})
           .then((response) => {
               axios.get("/api/getAllCompanyRequests")
                    .then((response) => this.allReq = response.data);
           })
        },
        navigateToRC : function(){
            router.push("/registerCompany")
        },
        showComReq : function(){
            this.showRequests = 1;
            axios.get("/api/getAllCompanyRequests")
                 .then((response) => this.allReq = response.data);
        }
    },
    mounted(){
     axios.defaults.headers.common["Authorization"] =
                                           localStorage.getItem("agentUser");
        axios.get("/api/checkIfAdmin")
             .then((response) => (this.isAdmin = response.data));
    }

});