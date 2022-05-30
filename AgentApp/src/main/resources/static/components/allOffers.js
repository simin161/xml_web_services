Vue.component('allOffers', {
	data: function(){
		return{
            jobOffers : {},
            showComment: false,
            idOffer: null,
            text: '',
            process: '',
            salary: null,
            showAllComments: false,
            comments: {},
		}
	},
template: `
        <div clas="row">
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

		<div clas="col" style="padding:2%">
            <br>
            <h1>All job offers</h1>
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
                          <button id="buttonPurple" @click="createComment(jo.id)"  class="btn btn-outline-success">Create comment</button>
                          <button id="buttonPurple" @click="seeComments(jo.id)"  type="button" class="btn btn-outline-success">See comments</button>
                          </td>
                    </tr>
                    
                </tbody>
            </table>
		</div>
        <div v-if="showComment == true" class="col">
        <div style="padding:2%">
        <br>
        <h1>Add comment</h1>
        <br>
        <label><b>Comment</b></label>
        <textarea v-model="text" class="form-control"></textarea>
        <label><b>Interview process</b></label>
        <textarea  v-model="process" class="form-control"></textarea>
        <label><b>Salary</b></label>
        <input v-model="salary" class="form-control"></input>
        <br>
        <button class="btn" id="buttonPurple" @click="addComment"  type="button">Save</button>
         </div>
         </div>

         
    <div v-if="showAllComments == true" class="col">
    <div style="padding:2%">
            <h1 style="text-align: left;">Comments</h1>
            <button id="buttonPurple" @click="closeComment"  class="btn btn-outline-success"  type="button" >Close</button>
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
 </div>
		`
	,

	computed : {

    },
    methods : {
        createComment : function(jo){
          this.showComment = true,
          this.idOffer = jo
           
        },
        addComment: function(){
            axios.post("/api/addComment",{
                text: this.text,
                process: this.process,
                salary: this.salary,
                id: this.idOffer
            })
            .then(()=>{
                this.seeComments(this.idOffer)
                this.showComment = false
                this.idOffer = null
                this.text = '',
                this.process = '',
                this.salary = '',
                console.log("uspeoo")
            }); 

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
        },
        logOut : function(){
            localStorage.setItem("agentUser", "");
                        router.push("/");
        }
    },
    mounted(){
         axios.defaults.headers.common["Authorization"] =
                                               localStorage.getItem("agentUser");
         axios.get("/api/getAllJobOffers")
              .then((response) => this.jobOffers = response.data);
    }
});