<template>
<nav  class="navbar navbar-fixed-top navbar-expand" style="background-color: white; list-style: none; box-shadow: rgba(0, 0, 0, 0.15) 1.95px 1.95px 2.6px; ">
      <div class="container-fluid" style="background-color: white; text-align: right">
      <a class="navbar-brand"   >
      <img src="../assets/dislinktLogo.jpg" alt="" width="200" height="80" >
      </a>
     
       <li class="nav-item dropdown">
          <a style="color: black;" class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" fill="currentColor" class="bi bi-person-circle" viewBox="0 0 16 16">
                <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0z"/>
                <path fill-rule="evenodd" d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8zm8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1z"/>
                </svg>
          </a>
          <ul class="dropdown-menu" >
            <li><a  @click="redirectMyProfile" class="dropdown-item" href="#">My profile</a></li>
            <hr>
            <li><a @click="signOut" class="dropdown-item" href="#">Log out</a></li>
          </ul>
        </li>
     
      </div>
    </nav><br><br> 
                                
<div class="row">
    <div class="col-lg-3" >
        <button @click="homepage()" id="buttonProfile">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person-video2" viewBox="0 0 16 16">
                                        <path d="M10 9.05a2.5 2.5 0 1 0 0-5 2.5 2.5 0 0 0 0 5Z"/>
                                        <path d="M2 1a2 2 0 0 0-2 2v9a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V3a2 2 0 0 0-2-2H2ZM1 3a1 1 0 0 1 1-1h2v2H1V3Zm4 10V2h9a1 1 0 0 1 1 1v9c0 .285-.12.543-.31.725C14.15 11.494 12.822 10 10 10c-3.037 0-4.345 1.73-4.798 3H5Zm-4-2h3v2H2a1 1 0 0 1-1-1v-1Zm3-1H1V8h3v2Zm0-3H1V5h3v2Z"/>
                                        </svg> USER POSTS
        </button>

        <button @click="searchUsers()" id="buttonProfile">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-search" viewBox="0 0 16 16">
                                        <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z"/>
                                        </svg> SEARCH USERS
        </button>

        <button @click="jobOffers()" id="buttonProfile">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-pc-display-horizontal" viewBox="0 0 16 16">
                                        <path d="M1.5 0A1.5 1.5 0 0 0 0 1.5v7A1.5 1.5 0 0 0 1.5 10H6v1H1a1 1 0 0 0-1 1v3a1 1 0 0 0 1 1h14a1 1 0 0 0 1-1v-3a1 1 0 0 0-1-1h-5v-1h4.5A1.5 1.5 0 0 0 16 8.5v-7A1.5 1.5 0 0 0 14.5 0h-13Zm0 1h13a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-.5.5h-13a.5.5 0 0 1-.5-.5v-7a.5.5 0 0 1 .5-.5ZM12 12.5a.5.5 0 1 1 1 0 .5.5 0 0 1-1 0Zm2 0a.5.5 0 1 1 1 0 .5.5 0 0 1-1 0ZM1.5 12h5a.5.5 0 0 1 0 1h-5a.5.5 0 0 1 0-1ZM1 14.25a.25.25 0 0 1 .25-.25h5.5a.25.25 0 1 1 0 .5h-5.5a.25.25 0 0 1-.25-.25Z"/>
                                        </svg> JOB OFFERS
        </button>
        <button @click="createPost()" id="buttonProfile2">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-plus-lg" viewBox="0 0 16 16">
                                        <path fill-rule="evenodd" d="M8 2a.5.5 0 0 1 .5.5v5h5a.5.5 0 0 1 0 1h-5v5a.5.5 0 0 1-1 0v-5h-5a.5.5 0 0 1 0-1h5v-5A.5.5 0 0 1 8 2Z"/>
                                        </svg> CREATE POST
        </button>

    </div>
 
        
      <div class="col-lg-9" style="padding-right: 5%;">
              <h1 style="text-align: left;" >Job offers</h1>
              <br>
              <form @submit="searchJobsFunc" method="post" class="d-flex">
              <input v-model="searchParam" class="form-control me-2" type="search" placeholder="Search job offers by position" aria-label="Search">
              <button class="btn btn-outline-secondary" type="submit">Search</button>
              </form>
              <br><br>
              <div >
              <h6 style="text-align: left;">Found jobs</h6>
              <hr>
                    <li  v-for="(offer,index) in foundJobOffers" :key="index"  class="list-group-item">
                        <div class="row">
                            <div style="padding-top: 1%" class="col-lg-3">
                             
                            <svg xmlns="http://www.w3.org/2000/svg" width="48" height="48" fill="currentColor" class="bi bi-building" viewBox="0 0 16 16">
                            <path fill-rule="evenodd" d="M14.763.075A.5.5 0 0 1 15 .5v15a.5.5 0 0 1-.5.5h-3a.5.5 0 0 1-.5-.5V14h-1v1.5a.5.5 0 0 1-.5.5h-9a.5.5 0 0 1-.5-.5V10a.5.5 0 0 1 .342-.474L6 7.64V4.5a.5.5 0 0 1 .276-.447l8-4a.5.5 0 0 1 .487.022zM6 8.694 1 10.36V15h5V8.694zM7 15h2v-1.5a.5.5 0 0 1 .5-.5h2a.5.5 0 0 1 .5.5V15h2V1.309l-7 3.5V15z"/>
                             <path d="M2 11h1v1H2v-1zm2 0h1v1H4v-1zm-2 2h1v1H2v-1zm2 0h1v1H4v-1zm4-4h1v1H8V9zm2 0h1v1h-1V9zm-2 2h1v1H8v-1zm2 0h1v1h-1v-1zm2-2h1v1h-1V9zm0 2h1v1h-1v-1zM8 7h1v1H8V7zm2 0h1v1h-1V7zm2 0h1v1h-1V7zM8 5h1v1H8V5zm2 0h1v1h-1V5zm2 0h1v1h-1V5zm0-2h1v1h-1V3z"/>
                              </svg>
            
                            </div>
                            <div class="col-lg-6" style="text-align: left">
                                  <h5><b>{{offer.position}}</b></h5>
                                  <p>{{offer.companyName.toUpperCase()}} </p>
                              </div>
                              <div class="col-lg-3" style="text-align: left">
                                  <button @click="seeDetails(offer)" data-bs-toggle="modal" data-bs-target="#staticBackdrop"  id="visitProf">
                                              <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-arrow-down-left" viewBox="0 0 16 16">
                                              <path fill-rule="evenodd" d="M2 13.5a.5.5 0 0 0 .5.5h6a.5.5 0 0 0 0-1H3.707L13.854 2.854a.5.5 0 0 0-.708-.708L3 12.293V7.5a.5.5 0 0 0-1 0v6z"/>
                                              </svg> SEE DEATAILS
                                  </button>
                              </div>

                        </div>
        

                    </li>
              </div>
    
    </div>

 </div>


<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
<div style="background-color: #e6e5e3;" class="footer" >
   <br><br>
    XWS project.
   <br><br> <br><br>
</div>
<!-- Modal -->
<div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="staticBackdropLabel"></h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <h3>Job offer deatils</h3>
        <hr>
        <div class="row">
                        <div class="col-lg-3">
                        <p style="text-align: left;">Position</p>
                        </div>
                        <div class="col-lg-9">
                        <p  ><b>{{position}}</b></p>
                        </div>
                        <hr>
                        <div class="col-lg-3">
                        <p style="text-align: left;">Company</p>
                        </div>
                        <div class="col-lg-9">
                        <p  ><b>{{company}}</b></p>
                        </div>
                         <hr>
                        <div class="col-lg-3">
                        <p style="text-align: left;">Job description</p>
                        </div>
                        <div class="col-lg-9">
                        <p  ><b>{{desc}}</b></p>
                        </div>
                         <hr>
                        <div class="col-lg-3">
                        <p style="text-align: left;">Daily activities</p>
                        </div>
                        <div class="col-lg-9">
                        <p  ><b>{{dailyAct}}</b></p>
                        </div>
                         <hr>
                        <div class="col-lg-3">
                        <p style="text-align: left;">Candidate requirements</p>
                        </div>
                        <div class="col-lg-9">
                        <p  ><b>{{candidateReq}}</b></p>
                        </div>
                        
</div>
      </div>
      <div class="modal-footer">
          
      </div>
    </div>
  </div>
</div>
</template>
<script>
  import axios from "axios";
  export default{
  data() {
    return {
        loggedUser : {},
        posts: [],
        foundJobOffers: [],
        position: '',
        company: '',
        desc: '',
        dailyAct: '',
        candidateReq: '',
        searchParam: ''
    };
  },
  mounted() {
      if(localStorage.getItem("loggedUser") === ''){
          const email= window.location.pathname.split('/')[2]
          if(email === '')
            this.$router.push("/signIn")
          else
            localStorage.setItem("loggedUser", email);
      }
     axios.defaults.headers.common["Authorization"] =
                             localStorage.getItem("loggedUser");
     axios.get(process.env.VUE_APP_BACK + 'user')
       .then((response) => {
           this.loggedUser = response.data;
           console.log(response.data);
            axios.get(process.env.VUE_APP_BACK + 'getAllJobOffers')
                 .then((response) => {
                    this.foundJobOffers = response.data;
                 })
                 .catch(function (error) {
                    console.log(error);
                 });
            
       })
       .catch(function (error) {
       console.log(error);
       });
  },
  methods: {   
    redirectMyProfile: function(){
        this.$router.push("/profilePage")
    },
    signOut : function(){
        localStorage.setItem("loggedUser", '');
        this.$router.push("/signIn");
    },
    homepage : function(){
        this.$router.push("/homepage");
    },
    searchUsers : function(){
        this.$router.push("/searchUsers");
    },
    jobOffers : function(){
        this.$router.push("/jobOffers");
    },
    createPost: function(){
        this.$router.push("/createNewPost");
    },
    seeDetails: function(offer){
        console.log(offer)
         this.position=offer.position
         this.company=offer.companyName
         this.desc=offer.jobDescription
         this.dailyAct=offer.dailyActivities
         this.candidateReq=offer.candidateRequirements
    },
    searchJobsFunc: function(event){
              event.preventDefault()
              if(this.searchParam=="") return
              axios.get(process.env.VUE_APP_BACK + 'searchJobOffers/'+this.searchParam)
                  .then((response) => {
                      this.foundJobOffers = response.data;
                  })
                  .catch(function (error) {
                      console.log(error);
                  });
            
    }
  }
};
</script>
<style>

#buttonProfile{
	width: 60%;
	height: 40px;
	margin: 10px auto;
	justify-content: center;
	display: block;
	color: #fff;
	background: #342438;
	font-size: 1em;
	font-weight: bold;
	margin-top: 20px;
	outline: none;
	border: none;
	border-radius: 5px;
	transition: .2s ease-in;
	cursor: pointer;
}
#buttonProfile:hover{
	background-color: white;
    color: #69558d;
    border: 1px solid #69558d;

}
#smaillbuttonProfile{
	width: 30%;
	height: 30px;
	margin: 10px auto;
	justify-content: right;
	display: block;
	color: #fff;
	background: #69558d;
	font-size: 1em;
	font-weight: bold;
	outline: none;
	border: none;
	border-radius: 5px;
	transition: .2s ease-in;
	cursor: pointer;
}
#smaillbuttonProfile:hover{
	background-color: white;
    color: #69558d;
    border: 1px solid #69558d;

}
</style>