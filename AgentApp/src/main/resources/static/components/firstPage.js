Vue.component('firstPage', {
	data: function(){
		return{
            isAdmin : false,
            showRequests: 0,
            showPage: 0,
            allReq : {},
            allCompanies : {},
            chosenCompany : null,
            jobOffer: {
                position : '',
                companyName : '',
                jobDescription : '',
                dailyActivities : '',
                candidateRequirements: ''
            },
            apiToken : ''
		}
	},
template: `
		<div>
		    <input v-show="!isAdmin" type="button" value="Register company" @click="navigateToRC"/>
		    <input v-show="!isAdmin" type="button" value="My companies" @click="showMyCompanies"/>
		    <input v-show="isAdmin" type="button" value="See all company reg requests" @click="showComReq"/>
            <input v-show="!isAdmin" type="button" value="Add API token" @click="showAddAPIToken"/>
            <input v-show="!isAdmin" type="button" value="My job offers" @click="navigateToJO"/>
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
		    <div v-show="showPage == 1">
		        <div v-for="c in allCompanies">
		            <input type="text" v-model="c.name"/>
		            <input type="text" v-model="c.contactInfo"/>
		            <input type="text" v-model="c.field"/>
		            <input type="text" v-model="c.description"/>
		            <input type="button" v-show="c.status == 'ACCEPTED'" value="Save changes" @click="save(c)"/>
		            <input type="button" v-show="c.status == 'ACCEPTED'" value="Create job offer" @click="showCreate(c)"/>
		        </div>
		    </div>
		    <div v-show="showPage == 2">
		        <input type="text" v-model="jobOffer.position"/>
		        <input type="text" v-model="jobOffer.companyName"/>
		        <input type="text" v-model="jobOffer.jobDescription"/>
		        <input type="text" v-model="jobOffer.dailyActivities"/>
		        <input type="text" v-model="jobOffer.candidateRequirements"/>
		        <input type="button" value="Create job offer" @click="createJobOffer"/>
		    </div>
		    <div v-show="showPage == 3">
		        <input type="text" v-model="apiToken"/>
		        <input type="button" value="Save" @click="saveApiToken"/>
		    </div>
		</div>
		`
	,

	computed : {

    },
    methods : {
        navigateToJO : function(){
            router.push("/myJobOffers");
        },
        saveApiToken : function(){
            axios.defaults.headers.common["Authorization"] =
                                  localStorage.getItem("agentUser");
            axios.post("/api/updateApiToken", this.apiToken)
                 .then((response) => console.log(response.data))
        },
        showAddAPIToken : function(){
            this.showPage = 3;
        },
        showCreate : function(c){
            this.chosenCompany = c;
            this.showPage = 2;
        },
        createJobOffer : function(){
        this.jobOffer.companyName = this.chosenCompany.name;
         axios.defaults.headers.common["Authorization"] =
                                           localStorage.getItem("agentUser");
            axios.post("/api/createJobOffer", this.jobOffer)
                            .then((response) => console.log(response.data))
        },
        save : function(c){
           axios.defaults.headers.common["Authorization"] =
                                   localStorage.getItem("agentUser");
           axios.post("/api/editCompany", {id: c.id, name: c.name, contactInfo: c.contactInfo,  field: c.field, description: c.description})
                .then((response) => console.log(response.data))
        },
        showMyCompanies : function(){
         axios.defaults.headers.common["Authorization"] =
                        localStorage.getItem("agentUser");
            axios.get("/api/getCompaniesForUser")
                 .then((response) => {
                    this.allCompanies = response.data
                    this.showPage = 1
                 })
        },
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