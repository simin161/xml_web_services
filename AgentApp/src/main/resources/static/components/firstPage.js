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
		    <button  class="btn" id="buttonPurple" v-show="!isAdmin"  @click="navigateToRC">Register company</button>
		    <button   class="btn" id="buttonPurple" v-show="!isAdmin"  @click="showMyCompanies">My companies</button>
		    <button   class="btn" id="buttonPurple" v-show="isAdmin"   @click="showComReq">See all company reg requests</button>
            <button   class="btn" id="buttonPurple" v-show="!isAdmin"  @click="showAddAPIToken">Add API token</button>
            <button   class="btn"  id="buttonPurple" v-show="!isAdmin"  @click="navigateToJO">My job offers</button>
            <button  class="btn "  id="buttonPurple" v-show="!isAdmin"  @click="navigateAllOffers">All offers</button>
		    <div v-show="showRequests == 1">
                <div clas="col" style="padding:2%">
                <br>
                <h1>All company reg requests</h1>
                <br>
                <table class="table">
                    <thead>
                        <tr>
                        <th><span>Name</span></th>
                        <th><span>Contact info</span></th>
                        <th><span>Owner first name</span></th>
                        <th><span>Owner last name</span></th>
                        <th><span>Field</span></th>
                        <th><span>Description</span></th>
                        <th>&nbsp;</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr  v-for="req in allReq"">
                           
                            <td>{{req.name}}</td>
                            <td>{{req.contactInfo}}</td>
                            <td>{{req.owner.firstName}}</td>
                            <td>{{req.owner.lastName}}</td>
                            <td>{{req.field}}</td>
                            <td>{{req.description}}</td>
                            <td>
                              <button class="btn" id="buttonPurple" @click="accept(req)" >Accept</button>
                              <button class="btn" id="buttonPurple" @click="decline(req)">Decline</button>
                              </td>
                        </tr>
                        
                    </tbody>
                 </table>
                 </div>
		    </div>
		    <div v-show="showPage == 1">
                <div clas="col" style="padding:2%">
                <br>
                <h1>All company reg requests</h1>
                <br>
                <table class="table">
                    <thead>
                        <tr>
                        <th><span>Name</span></th>
                        <th><span>Contact info</span></th>
                        <th><span>Field</span></th>
                        <th><span>Description</span></th>
                        <th>&nbsp;</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr  v-for="c in allCompanies"">
                           
                            <td> <input class="form-control" type="text" v-model="c.name"/></td>
                            <td>  <input class="form-control" type="text" v-model="c.contactInfo"/></td>
                            <td>  <input class="form-control" type="text" v-model="c.field"/></td>
                            <td> <input class="form-control" type="text" v-model="c.description"/></td>
                            <td>
                              <button class="btn" id="buttonPurple"  @click="save(c)" v-show="c.status == 'ACCEPTED'" >Save changes</button>
                              <button  class="btn" id="buttonPurple" @click="showCreate(c)" v-show="c.status == 'ACCEPTED'">Create job offer</button>
                              </td>
                        </tr>
                        
                    </tbody>
                 </table>
                 </div>
		    </div>
		    <div v-show="showPage == 2">
		        <input type="text" v-model="jobOffer.position"/>
		        <input type="text" v-model="jobOffer.companyName"/>
		        <input type="text" v-model="jobOffer.jobDescription"/>
		        <input type="text" v-model="jobOffer.dailyActivities"/>
		        <input type="text" v-model="jobOffer.candidateRequirements"/>
		        <button class="btn" id="buttonPurple"   @click="createJobOffer">Create job offer</button>
		    </div>
		    <div v-show="showPage == 3">
		        <input type="text" v-model="apiToken"/>
		        <button class="btn" id="buttonPurple" @click="saveApiToken">Save</button>
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
        navigateAllOffers: function(){
            router.push("/allOffers")
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