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
            <li><a  class="dropdown-item" href="#">My profile</a></li>
            <hr>
            <li><a @click="signOut" class="dropdown-item" href="#">Log out</a></li>
          </ul>
        </li>
     
      </div>
    </nav>
 
    <br>
    <br>
    
<div class="container">
    <div class="row">
        <div class="col-lg-4 ">
            <div class="card left-profile-card">
                <div class="card-body" style="padding: 5%;">
                    <div class="text-center" style="padding: 3%;">
                        <svg viewBox="0 0 36 36" fill="none" role="img" xmlns="http://www.w3.org/2000/svg" width="80" height="80"><title>Lucy Stone</title><mask id="mask__beam" maskUnits="userSpaceOnUse" x="0" y="0" width="36" height="36"><rect width="36" height="36" rx="72" fill="#FFFFFF"></rect></mask><g mask="url(#mask__beam)"><rect width="36" height="36" fill="#a65bb7"></rect><rect x="0" y="0" width="36" height="36" transform="translate(4 4) rotate(340 18 18) scale(1.1)" fill="#240c39" rx="36"></rect><g transform="translate(-4 -3) rotate(0 18 18)"><path d="M15 20c2 1 4 1 6 0" stroke="#FFFFFF" fill="none" stroke-linecap="round"></path><rect x="14" y="14" width="1.5" height="2" rx="1" stroke="none" fill="#FFFFFF"></rect><rect x="20" y="14" width="1.5" height="2" rx="1" stroke="none" fill="#FFFFFF"></rect></g></g></svg>
                        <h3 id="fullName">{{loggedUser.firstName}} {{loggedUser.lastName}}</h3>
                        <p id="username">{{loggedUser.username}} </p>
                       
                    </div>
                    <br>
    <br>
                    <div class="personal-info" style="text-align: left;">
                        <h5 style="text-align: left">Personal Information</h5>
                           <hr>
                            <div class="row">
                               <div class="col" style="color: gray">
                                 Email
                                </div>
                                <div class="col" style="text-align: right">
                                    <p id="email"><b>{{loggedUser.email}}</b></p>
                                </div>
                            </div>

                              <div class="row">
                               <div class="col" style="color: gray">
                                 Phone
                                </div>
                                <div class="col" style="text-align: right">
                                     <p id="phone"><b> {{loggedUser.phone}}</b></p>
                                </div>
                            </div>

                             <div class="row" >
                               <div class="col" style="color: gray">
                                 Gender
                                </div>
                                <div class="col" style="text-align: right">
                                     <p id="phone"> <b>{{loggedUser.gender}} </b></p>
                                </div>
                            </div>
                              <div class="row">
                               <div class="col" style="color: gray" >
                                 Birthday
                                </div>
                                <div class="col" style="text-align: right">
                                     <p id="phone"><b> {{loggedUser.birthday}}</b></p>
                                </div>
                            </div>
                          
                          
                  
                    </div>
                   <br>
                    <div class="skill">
                        <h5 style="text-align: left">Biography</h5>
                        <hr>
                        <p><b>{{loggedUser.biography}}</b></p>
                        
                    </div>
                    <div class="skill">
                        <h5 style="text-align: left">Interests</h5>
                        <hr>
                        <p id="interests"><b>{{loggedUser.interests}}</b></p>
                        
                    </div>
                    <div class="skill">
                        <h5 style="text-align: left">Skills</h5>
                        <hr>
                        <p id="skills"><b>{{loggedUser.skills}}</b></p>                    
                    </div>   
                </div>
            </div>
            <br>

            <div class="card left-profile-card">
                <div class="card-body">
                    
                    <div class="personal-info">
                        <div class="row">
                        <div class='col'>
                        <h4 style="text-align: left">Education</h4>
                           </div>
                           <div class='col'>
                               <button id="smaillbuttonProfile" @click="navigateToAddEducation">+</button>
                           </div>
                        </div>             
                         <hr/>
                         <div  id="pills-education" role="tabpanel" aria-labelledby="pills-education-tab">
                           
                            <ul class="list-group">
                            <li v-for="ed in educations" v-bind:key="ed._idEducation" class="list-group-item">
                                <h6><b><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-building" viewBox="0 0 16 16">
  <path fill-rule="evenodd" d="M14.763.075A.5.5 0 0 1 15 .5v15a.5.5 0 0 1-.5.5h-3a.5.5 0 0 1-.5-.5V14h-1v1.5a.5.5 0 0 1-.5.5h-9a.5.5 0 0 1-.5-.5V10a.5.5 0 0 1 .342-.474L6 7.64V4.5a.5.5 0 0 1 .276-.447l8-4a.5.5 0 0 1 .487.022zM6 8.694 1 10.36V15h5V8.694zM7 15h2v-1.5a.5.5 0 0 1 .5-.5h2a.5.5 0 0 1 .5.5V15h2V1.309l-7 3.5V15z"/>
  <path d="M2 11h1v1H2v-1zm2 0h1v1H4v-1zm-2 2h1v1H2v-1zm2 0h1v1H4v-1zm4-4h1v1H8V9zm2 0h1v1h-1V9zm-2 2h1v1H8v-1zm2 0h1v1h-1v-1zm2-2h1v1h-1V9zm0 2h1v1h-1v-1zM8 7h1v1H8V7zm2 0h1v1h-1V7zm2 0h1v1h-1V7zM8 5h1v1H8V5zm2 0h1v1h-1V5zm2 0h1v1h-1V5zm0-2h1v1h-1V3z"/>
</svg>  {{ed.school}}</b></h6>
                                <p> {{ed.from}} - {{ed.to}}</p>
                                <p>{{ed.degree}}, {{ed.fieldOfStudy}}</p>
                                <div style="text-align: right;">
                                <button @click="educationToDelete=ed" data-bs-toggle="modal" data-bs-target="#staticBackdrop"
  style="width: 13%" type="button" class="btn btn-outline-danger"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash3" viewBox="0 0 16 16">
  <path d="M6.5 1h3a.5.5 0 0 1 .5.5v1H6v-1a.5.5 0 0 1 .5-.5ZM11 2.5v-1A1.5 1.5 0 0 0 9.5 0h-3A1.5 1.5 0 0 0 5 1.5v1H2.506a.58.58 0 0 0-.01 0H1.5a.5.5 0 0 0 0 1h.538l.853 10.66A2 2 0 0 0 4.885 16h6.23a2 2 0 0 0 1.994-1.84l.853-10.66h.538a.5.5 0 0 0 0-1h-.995a.59.59 0 0 0-.01 0H11Zm1.958 1-.846 10.58a1 1 0 0 1-.997.92h-6.23a1 1 0 0 1-.997-.92L3.042 3.5h9.916Zm-7.487 1a.5.5 0 0 1 .528.47l.5 8.5a.5.5 0 0 1-.998.06L5 5.03a.5.5 0 0 1 .47-.53Zm5.058 0a.5.5 0 0 1 .47.53l-.5 8.5a.5.5 0 1 1-.998-.06l.5-8.5a.5.5 0 0 1 .528-.47ZM8 4.5a.5.5 0 0 1 .5.5v8.5a.5.5 0 0 1-1 0V5a.5.5 0 0 1 .5-.5Z"/>
</svg></button>
                                </div>
                            </li>
                            </ul>
                        </div>
                    </div> 
                </div>
            </div>
            <br>
            <div class="card left-profile-card">
                <div class="card-body">
                    
                    <div class="personal-info">
                     <div class="row">
                                <div class='col'>
                                <h4 style="text-align: left">Work experience</h4>
                                </div>
                                <div class='col'>
                                    <button id="smaillbuttonProfile" @click="navigateToAddExp">+</button>
                                </div>
                                </div>
                        <hr/>
                      

                         <div  id="pills-education" role="tabpanel" aria-labelledby="pills-education-tab">
                           
                            <ul class="list-group">
                            <li v-for="we in workExperiences" v-bind:key="we._idExperience" class="list-group-item">
                                <h6><b><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-building" viewBox="0 0 16 16">
  <path fill-rule="evenodd" d="M14.763.075A.5.5 0 0 1 15 .5v15a.5.5 0 0 1-.5.5h-3a.5.5 0 0 1-.5-.5V14h-1v1.5a.5.5 0 0 1-.5.5h-9a.5.5 0 0 1-.5-.5V10a.5.5 0 0 1 .342-.474L6 7.64V4.5a.5.5 0 0 1 .276-.447l8-4a.5.5 0 0 1 .487.022zM6 8.694 1 10.36V15h5V8.694zM7 15h2v-1.5a.5.5 0 0 1 .5-.5h2a.5.5 0 0 1 .5.5V15h2V1.309l-7 3.5V15z"/>
  <path d="M2 11h1v1H2v-1zm2 0h1v1H4v-1zm-2 2h1v1H2v-1zm2 0h1v1H4v-1zm4-4h1v1H8V9zm2 0h1v1h-1V9zm-2 2h1v1H8v-1zm2 0h1v1h-1v-1zm2-2h1v1h-1V9zm0 2h1v1h-1v-1zM8 7h1v1H8V7zm2 0h1v1h-1V7zm2 0h1v1h-1V7zM8 5h1v1H8V5zm2 0h1v1h-1V5zm2 0h1v1h-1V5zm0-2h1v1h-1V3z"/>
</svg>  {{we.workPlace}}</b></h6>
                                <p> {{we.workTitle}}</p>
                                <p>{{we.from}} - {{we.to}}</p>
                                <div style="text-align: right;">
                                <button @click="workExpToDelete=we" data-bs-toggle="modal" data-bs-target="#staticBackdrop1" style="width: 13%" type="button" class="btn btn-outline-danger"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash3" viewBox="0 0 16 16">
  <path d="M6.5 1h3a.5.5 0 0 1 .5.5v1H6v-1a.5.5 0 0 1 .5-.5ZM11 2.5v-1A1.5 1.5 0 0 0 9.5 0h-3A1.5 1.5 0 0 0 5 1.5v1H2.506a.58.58 0 0 0-.01 0H1.5a.5.5 0 0 0 0 1h.538l.853 10.66A2 2 0 0 0 4.885 16h6.23a2 2 0 0 0 1.994-1.84l.853-10.66h.538a.5.5 0 0 0 0-1h-.995a.59.59 0 0 0-.01 0H11Zm1.958 1-.846 10.58a1 1 0 0 1-.997.92h-6.23a1 1 0 0 1-.997-.92L3.042 3.5h9.916Zm-7.487 1a.5.5 0 0 1 .528.47l.5 8.5a.5.5 0 0 1-.998.06L5 5.03a.5.5 0 0 1 .47-.53Zm5.058 0a.5.5 0 0 1 .47.53l-.5 8.5a.5.5 0 1 1-.998-.06l.5-8.5a.5.5 0 0 1 .528-.47ZM8 4.5a.5.5 0 0 1 .5.5v8.5a.5.5 0 0 1-1 0V5a.5.5 0 0 1 .5-.5Z"/>
</svg></button>
                                </div>
                            </li>
                            </ul>
                        </div>
                    </div> 
                </div>
            </div>
        </div>
        <div class="col-lg-8">
            <div class="card right-profile-card">
                <div class="card-header alert-primary" style="background-color: white; color: white;" >
                                <div class='row' >
                           
                             
                                <div class='col' >
                                <button @click="redirectCreatePost" id="buttonProfile">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-plus-square" viewBox="0 0 16 16">
                                        <path d="M14 1a1 1 0 0 1 1 1v12a1 1 0 0 1-1 1H2a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h12zM2 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2z"/>
                                        <path d="M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4z"/>
                                        </svg> Create post
                                </button>
                                </div>

                                <div class='col'>

                                <button @click="redirectEditProfile" id="buttonProfile">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-pencil-square" viewBox="0 0 16 16">
                                        <path d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z"/>
                                        <path fillRule="evenodd" d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5v11z"/>
                                        </svg> Edit profile
                                </button>
                                <button @click="generateAPIToken">Generate API Token</button>
                                </div>
        </div>
                </div>
               
                <div class="card-body">
                    M
                </div>
                
            </div>
        </div>
    </div>
</div>
<br><br>
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
        <p>Are you sure you want to delete education?</p>

      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
        <button @click="deleteEducation()" type="button" data-bs-dismiss="modal" class="btn btn-danger">Delete</button>
      </div>
    </div>
  </div>
</div>

<!-- Modal -->
<div class="modal fade" id="staticBackdrop1" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel1" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="staticBackdropLabel1"></h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <p>Are you sure you want to delete experience?</p>

      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
        <button @click="deleteWorkExp()" type="button" data-bs-dismiss="modal" class="btn btn-danger">Delete</button>
      </div>
    </div>
  </div>
</div>
</template>
<script>
  import axios from "axios";
  import swal from "sweetalert";
  export default{
  data() {
    return {
        loggedUser : {},
        educations: {},
        workExperiences: {},
        educationToDelete: null,
        workExpToDelete: null,
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
            axios.get(process.env.VUE_APP_BACK + 'educations/'+this.loggedUser.email+"/")
                 .then((response) => {
                    this.educations = response.data;
                 })
                 .catch(function (error) {
                    console.log(error);
                 });
            axios.get(process.env.VUE_APP_BACK + 'experiences/'+this.loggedUser.email+"/")
                 .then((response) => {
                    this.workExperiences = response.data;
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
    redirectEditProfile: function(){
        this.$router.push("/editProfile")
    },
    redirectCreatePost: function(){
        this.$router.push("/createNewPost")
    },
    navigateToAddEducation: function(){
        this.$router.push("/updateEducation")
    },
    navigateToAddExp: function(){
        this.$router.push("/updateWorkExperience")
    } ,
    signOut : function(){
        localStorage.setItem("loggedUser", '');
        this.$router.push("/signIn");
    },
    generateAPIToken : function(){
        axios.defaults.headers.common["Authorization"] = localStorage.getItem("loggedUser");
        axios.post(process.env.VUE_APP_BACK + 'generateUserAPIToken')
        .then((response)=>{
            console.log(response);
            if(response.data == true){
                swal({  
                            title: "Token generation successful!",  
                            text: "Please check you email for a message containing your API token!",  
                            icon: "success",  
                            button: "Confirm",  
                      });  
            }else{
                swal({  
                          title: " Oops!",  
                          text: " Something went wrong, please try again later!",  
                          icon: "error",  
                          button: "Confirm",  
                    });    
            }

        })
        .catch(function (error){
            console.log(error);
        })
    },
    deleteEducation: function(){
    console.log("MENE BRISES" +this.educationToDelete.school)
                 axios.post(process.env.VUE_APP_BACK + 'deleteEducation',
                 {
                    id:  this.educationToDelete.id
                 })
                 .then((response) => {
                       this.$swal.fire({
                        position: 'top-end',
                        icon: 'success',
                        title: 'Education deleted',
                         showConfirmButton: false,
                          timer: 1500
                             })
                       axios.get(process.env.VUE_APP_BACK + 'educations/'+this.loggedUser.email+"/")
                            .then((response) => {
                                this.educations = response.data;
                            })
                            .catch(function (error) {
                                console.log(error);
                            });
                            return response;
                  })
                 .catch(function (error) {
                    console.log(error);
                 });
    },
    deleteWorkExp: function(){
    console.log("MENE BRISES" +this.workExpToDelete.workTitle)
     axios.post(process.env.VUE_APP_BACK + 'deleteExperience',
                 {
                    id:  this.workExpToDelete.id
                 })
                 .then((response) => {
                       this.$swal.fire({
                        position: 'top-end',
                        icon: 'success',
                        title: 'Work experience deleted',
                         showConfirmButton: false,
                          timer: 1500
                             })
                       axios.get(process.env.VUE_APP_BACK + 'experiences/'+this.loggedUser.email+"/")
                            .then((response) => {
                                this.workExperiences = response.data;
                            })
                            .catch(function (error) {
                                console.log(error);
                            });
                            return response;
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
	background: #e385fe;
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
	background: #e385fe;
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