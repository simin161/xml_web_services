<template>
<div className="container">
   <br/>
    <div className="row">
        <div className="col-lg-3 ">
            <div className="card left-profile-card">
                <div className="card-body">
                    <div className="text-center">
                        <img src="https://bootdey.com/img/Content/avatar/avatar2.png" alt="" className="user-profile"/>
                        <h3 id="fullName">John Doe</h3>
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
                            <li><i className="fas fa-briefcase "></i><span id="email"></span></li>
                            <li><i className="fas fa-map-marker-alt "></i><span id="phone"> New York</span></li>
                            <li><i className="far fa-envelope "></i><span id="gender">like @example.com</span></li>
                            <li><i className="fas fa-mobile "></i><span id="birthday"></span></li>
                        </ul>
                    </div>

                    <div className="skill">
                        <h3>Biography</h3>
                        <span id="biography"></span>
                        
                    </div>
                    <div className="skill">
                        <h3>Interests</h3>
                        <span id="interests"></span>
                        
                    </div>
                    <div className="skill">
                        <h3>Skills</h3>
                        <span id="skills"></span>                    
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
                            <div className="work-container">
                                <h3>The Art Institute :- New Yourk</h3>
                                <h6><i className="far fa-calendar-alt"></i>Jan 2017 to <span className="badge badge-info">Current</span></h6>
                                <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
                            </div>
                            <div className="work-container">
                                <h3>Eitech :- New Jersy</h3>
                                <h6><i className="material-icons"></i>Jan 2017 to <span className="badge badge-info">Current</span></h6>
                                <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
                            </div>
                            <div className="work-container">
                                <h3>School of Visual Arts :- Chicago</h3>
                                <h6><i className="material-icons"></i>Jan 2017 to <span className="badge badge-info">Current</span></h6>
                                <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
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
                        <WorkExperience/>
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
        loggedUser : null
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
                 .then(function (response) {
                    console.log(response.data.length)
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