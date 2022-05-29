Vue.component('jobOffersForUser', {
	data: function(){
		return{
            jobOffers : {},
            showAllComments: false,
            comments : {}
		}
	},
template: `
		<div>
            <div clas="col" style="padding:2%">
            <br>
            <h1>My job offers</h1>
            <br>
            <table class="table">
                <thead>
                    <tr>
                    <th><span>Position</span></th>
                    <th><span>Company name</span></th>
                    <th><span>Job description</span></th>
                    <th><span>Daily Activities</span></th>
                    <th><span>Candidate requirements</span></th>
                    <th>&nbsp;</th>
                    </tr>
                </thead>
                <tbody>
                    <tr   v-for="jo in jobOffers">
                       
                        <td>{{jo.position}}</td>
                        <td>{{jo.companyName}}</td>
                        <td>{{jo.jobDescription}}</td>
                        <td>{{jo.dailyActivities}}</td>
                        <td>{{jo.candidateRequirements}}</td>
                        <td>
                          <button id="buttonPurple" @click="sendRequest(jo)"  type="button" class="btn btn-outline-success">Send job offer</button>
                          <button  id="buttonPurple" @click="seeComments(jo.id)"  type="button" class="btn btn-outline-success">See comments</button>
                          </td>
                    </tr>
                    
                </tbody>
            </table>
         </div>
            <div v-if="showAllComments == true" class="col">
            <div style="padding: 2%">
                    <h6 style="text-align: left;">Comments</h6>
                    <button  id="buttonPurple" @click="closeComment"  type="button" >Close</button>
                    <hr>
                        <li  v-for="comment in comments"  class="list-group-item">
                            <div class="row">
                                <div class="col" style="text-align: left">
                                        <h5>User comment</h5>
                                    </div>
                                    <div class="col" style="text-align: left">
                                    <h5><b>{{comment.text}}</b></h5>
                                    </div>
                                </div>
                                <div class="row">
                                <div class="col" style="text-align: left">
                                    <h5>Interview process</h5>
                                </div>
                                <div class="col" style="text-align: left">
                                <h5><b>{{comment.interview_process}}</b></h5>
                                </div>
                            </div>
                            <div class="row">
                            <div class="col" style="text-align: left">
                                <h5>Salary</h5>
                            </div>
                            <div class="col" style="text-align: left">
                            <h5><b>{{comment.salary}}</b></h5>
                            </div>
                        </div>
        
                        </li>
            </div>
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
        },
        seeComments: function(id){
            this.showAllComments = true
            axios.post("/api/commentsByOfferId",{
               id: id
            })
            .then((response)=>{
              this.comments = response.data
            }); 

        },
        closeComment : function(){
            this.showAllComments = false
        }
    },
    mounted(){
         axios.defaults.headers.common["Authorization"] =
                                               localStorage.getItem("agentUser");
         axios.get("/api/getJobOffersForUser")
              .then((response) => this.jobOffers = response.data);
    }
});