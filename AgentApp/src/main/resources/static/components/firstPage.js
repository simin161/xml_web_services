Vue.component('firstPage', {
	data: function(){
		return{
            isAdmin : false,
            showRequests: 0,
            showPage: 0,
            allReq : {},
            allCompanies : {},
            chosenCompany : {
                name: "",
                contactInfo: "",
                field: "",
                description: ""
            },
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
        <nav  class="navbar navbar-fixed-top navbar-expand"  style="background-color: #3e214f; list-style: none; box-shadow: rgba(0, 0, 0, 0.15) 1.95px 1.95px 2.6px; ">
        <div class="container-fluid" style="background-color: #3e214f; text-align: right">
        <a class="navbar-brand"   >
        <img src="../images/cover.png" alt="" width="200" height="80" >
        </a>
        <button class="btn" id="buttonPurple"  @click="logOut">
        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-arrow-right" viewBox="0 0 16 16">
  <path fill-rule="evenodd" d="M1 8a.5.5 0 0 1 .5-.5h11.793l-3.147-3.146a.5.5 0 0 1 .708-.708l4 4a.5.5 0 0 1 0 .708l-4 4a.5.5 0 0 1-.708-.708L13.293 8.5H1.5A.5.5 0 0 1 1 8z"/>
</svg> Log out</button>
        </div>
      </nav>
      <br>
      <br>
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
                <h1>My companies</h1>
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
                            <td> <input class="form-control" type="text" v-model="c.contactInfo"/></td>
                            <td> <input class="form-control" type="text" v-model="c.field"/></td>
                            <td> <input class="form-control" type="text" v-model="c.description"/></td>
                            <td>
                              <button class="btn" :disabled="isEditComplete(c)" id="buttonPurple"  @click="save(c)" v-show="c.status == 'ACCEPTED'" >Save changes</button>
                              <button class="btn" id="buttonPurple" @click="showCreate(c)" v-show="c.status == 'ACCEPTED'">Create job offer</button>
                              </td>
                        </tr>
                    </tbody>
                 </table>
                 </div>
		    </div>
		    <div v-show="showPage == 2">
                <div style="padding:2%">
                <br>
                <h1>Create job offer</h1>
                <br>
                <label><b>Position</b></label>
                <input class="form-control" type="text" v-model="jobOffer.position"/>
                <label><b>Company names</b></label>
		        <input disabled class="form-control" type="text" v-model="chosenCompany.name"/>
                <label><b>Job description</b></label>
		        <input class="form-control" type="text" v-model="jobOffer.jobDescription"/>
                <label><b>Daily activities</b></label>
		        <input  class="form-control" type="text" v-model="jobOffer.dailyActivities"/>
                <label><b>Candidate requirements</b></label>
		        <input  class="form-control"type="text" v-model="jobOffer.candidateRequirements"/>
                <br>
		        <button :disabled="isJobOfferComplete" class="btn" id="buttonPurple"  @click="createJobOffer">Create job offer</button>
                 </div>
		    </div>
		    <div style="padding: 2%" v-show="showPage == 3">
                <h1>Create API token</h1>
                 <br>
		        <input type="text" v-model="apiToken"/>
		        <button :disabled="isApiTokenComplete" class="btn" id="buttonPurple" @click="saveApiToken">Save</button>
		    </div>
		</div>
		`
	,

	computed : {
        isEditComplete(c){
            let validCName = /^[ a-zA-Z0-9]+$/.test(c.name);
            let validContactInfo = /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(.\w{2,3})+$/.test(c.contactInfo) || /^[0-9{8}]+$/.test(c.contactInfo);
            let validField = /^[a-zA-Z]+$/.test(c.field);
            let validDesc = /^[ a-zA-Z.,]+$/.test(c.description);
            if(validCName && validContactInfo && validField && validDesc)
                return false
            return true;
        },
        isApiTokenComplete(){
            return !/\S/.test(this.apiToken);
        },
        isJobOfferComplete(){
            let validPosition =  /^[ a-zA-Z.,]+$/.test(this.jobOffer.position);
            let validDescription = /^[ a-zA-Z.,]+$/.test(this.jobOffer.jobDescription);
            let validDActivities = /^[ a-zA-Z0-9.,]+$/.test(this.jobOffer.dailyActivities);
            let validCReq = /^[ a-zA-Z0-9.,]+$/.test(this.jobOffer.candidateRequirements);
            if(validPosition && validDescription && validDActivities && validCReq)
                return false;
            return true;
        }
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
                            .then((response) => {
                                
                             router.push("/myJobOffers")
                            console.log(response.data)})
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
        },
        logOut : function(){
            localStorage.setItem("agentUser", "");
            router.push("/");
        }
    },
    mounted(){
     axios.defaults.headers.common["Authorization"] =
                                           localStorage.getItem("agentUser");
        axios.get("/api/checkIfAdmin")
             .then((response) => (this.isAdmin = response.data));
    }

});