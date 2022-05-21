<template>
<div className="container">
   <br/>
    <div className="row">
        <div className="col-lg-3 ">
            <div className="card left-profile-card">
                <div className="card-body">
                    <div className="text-center">
                        <img src="https://bootdey.com/img/Content/avatar/avatar2.png" alt="" className="user-profile"/>
                        <h3 id="fullName">{{loggedUser.firstName}} {{loggedUser.lastName}}</h3>
                        <p id="username"></p>
                        <div className="d-flex align-items-center justify-content-center mb-3">
                            <i className="fas fa-star text-info"></i>
                            <i className="fas fa-star text-info"></i>
                            <i className="fas fa-star text-info"></i>
                            <i className="fas fa-star text-info"></i>
                            <i className="fas fa-star text-info"></i>
                        </div>
                    </div>
                    <div className="personal-info">
                        <h3>Personal Information</h3>
                        <ul className="personal-list">
                            <li><i className="fas fa-briefcase "></i><span id="email">{{loggedUser.email}}</span></li>
                            <li><i className="fas fa-map-marker-alt "></i><span id="phone"> {{loggedUser.phone}}</span></li>
                            <li><i className="far fa-envelope "></i><span id="gender">{{loggedUser.gender}}</span></li>
                            <li><i className="fas fa-mobile "></i><span id="birthday">{{loggedUser.birthday}}</span></li>
                        </ul>
                    </div>

                    <div className="skill">
                        <h3>Biography</h3>
                        <span id="biography">{{loggedUser.biography}}</span>
                        
                    </div>
                    <div className="skill">
                        <h3>Interests</h3>
                        <span id="interests">{{loggedUser.interests}}</span>
                        
                    </div>
                    <div className="skill">
                        <h3>Skills</h3>
                        <span id="skills">{{loggedUser.skills}}</span>                    
                    </div>   
                </div>
            </div>
            <div className="card left-profile-card">
                <div className="card-body">
                    
                    <div className="personal-info">
                        <div className="row">
                        <div className='col'>
                        <h3>Education</h3>
                           </div>
                           <div className='col'>
                               <button @click="navigateToAddEducation">+</button>
                           </div>
                        </div>             
                         <hr/>
                         <div  id="pills-education" role="tabpanel" aria-labelledby="pills-education-tab">
                            <div className="work-container" v-for="ed in educations" v-bind:key="ed._idEducation">
                                <h3>{{ed.school}}</h3>
                                <h6>{{ed.from}} - {{ed.to}}</h6>
                                <p>{{ed.fieldOfStudy}}</p>
                                <p>{{ed.degree}}</p>
                            </div>
                        </div>
                    </div> 
                </div>
            </div>
            <div className="card left-profile-card">
                <div className="card-body">
                    
                    <div className="personal-info">
                       <div className="row">
                        <div className='col'>
                        <h3>Work experience</h3>
                           </div>
                           <div className='col'>
                               <button @click="navigateToAddExp">+</button>
                           </div>
                        </div>
                        <hr/>
                       <div  id="pills-education" role="tabpanel" aria-labelledby="pills-education-tab">
                            <div className="work-container" v-for="we in workExperiences" v-bind:key="we._idExperience">
                                <h3>{{we.workPlace}}</h3>
                                <h6>{{we.workTitle}}</h6>
                                <p>{{we.from}} - {{we.to}}</p>
                            </div>
                        </div>
                    </div> 
                </div>
            </div>
        </div>
        <div className="col-lg-9">
            <div className="card right-profile-card">
                <div className="card-header alert-primary">
                                <div className='row' >
                                    
                                <div className='col'>
                                <h2>Timeline</h2>
                            
                                </div>
                             
                                <div className='col' >
                                <button  id="buttonProfile">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-plus-square" viewBox="0 0 16 16">
                                        <path d="M14 1a1 1 0 0 1 1 1v12a1 1 0 0 1-1 1H2a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h12zM2 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2z"/>
                                        <path d="M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4z"/>
                                        </svg> Create post
                                </button>
                                </div>

                                <div className='col'>

                                <button @click="redirectEditProfile" id="buttonProfile">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-pencil-square" viewBox="0 0 16 16">
                                        <path d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z"/>
                                        <path fillRule="evenodd" d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5v11z"/>
                                        </svg> Edit profile
                                </button>
                                </div>
        </div>
                </div>
               
                <div className="card-body">
                    <Timeline/>
                </div>
                
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
        educations: {},
        workExperiences: {}
    };
  },
  mounted() {
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
    navigateToAddEducation: function(){
        this.$router.push("/updateEducation")
    },
    navigateToAddExp: function(){
        this.$router.push("/updateWorkExperience")
    } 
  }
};
</script>
<style>
</style>