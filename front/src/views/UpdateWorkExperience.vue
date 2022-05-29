<template>
<nav  class="navbar navbar-fixed-top navbar-expand" style="background-color: white; list-style: none; box-shadow: rgba(0, 0, 0, 0.15) 1.95px 1.95px 2.6px; ">
      <div class="container-fluid" style="background-color: white; text-align: right">
      <a class="navbar-brand" @click="homepage()"  >
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
    </nav>
 
    <br>
    <br>
    <div class="container">
    <div class="card edit-profile-card" style="margin: 5%;">
        <div class="card-body">
    
           <h3>Work experience</h3>
            <hr/>
               
           <div class="row">
                <div class="col form-group">
                    <p id="p">Work place</p>
                    <input id="workPlace" v-model="workExp.workPlace" type="text"  class="form-control" required />
                </div>

                <div class="col form-group">
                    <p id="p">Work title</p>   
                    <input id="workTitle" v-model="workExp.workTitle" type="text" class="form-control" required/>       
                </div> 
           
          </div>
          <div class="row">

                <div class="col form-group">
                    <p id="p">From</p>   
                    <input class="form-control" type="date" v-model="workExp.from" id="from" >      
                </div> 
                 
                <div class="col form-group">
                    <p id="p">To</p>   
                    <input class="form-control" type="date" v-model="workExp.to" id="to" >
                </div> 

               <div  class="col form-group">
                    <p id="p">&nbsp;</p>   
                     <button type="button"  :disabled="isComplete" @click="update" style="width: 50%"  class="btn btn-primary">Save</button>
                 
                </div> 
               
          </div>
        
          <hr/>
          <br/> <br/>

                    
                </div>
    </div>
     </div>
    <br><br>
     <br>
    <br>
    <br>
    <br>
      <br>
    <br>
    <br>
<div style="background-color: #e6e5e3;" class="footer" >
   <br><br>
    XWS project.
   <br><br> <br><br>
</div>
</template>

<script>
   import axios from "axios";
  export default{
  data() {
    return {
      workExp:{
        workPlace : '',
        workTitle : '',
        from : '',
        to : '',
        email : ''
      }
    };
  },
  mounted() {
     if(localStorage.getItem("loggedUser") === ''){
          this.$router.push("/signIn")
      }
  },
  methods: {
     homepage: function(){
       this.$router.push("/homepage")
    },
     redirectMyProfile: function(){
        this.$router.push("/profilePage")
    },
    signOut : function(){
        localStorage.setItem("loggedUser", '');
        this.$router.push("/signIn");
    },
  update : function(){
      axios.defaults.headers.common["Authorization"] =
                             localStorage.getItem("loggedUser");
      axios.post(process.env.VUE_APP_BACK +'workExperiences', this.workExp)
      .then((response) => {
        console.log(response);
        this.$router.push("/profilePage")
      })
      .catch(function (error) {
        console.log(error);
      });
    }
  },
  computed:{
    isComplete(){
        var validSchool = /[()[\]{}<>]/.test(this.workExp.workPlace);
        var validSkills = /[()[\]{}<>]/.test(this.workExp.workTitle);
        var validFrom = /\S/.test(this.workExp.from);
        var validTo = /\S/.test(this.workExp.to);

        if(validTo && validFrom && !validSchool && !validSkills 
          && /\S/.test(this.workExp.workPlace)  && /\S/.test(this.workExp.workTitle)){
            return false;
          }

        return true;
    }
  }

};
</script>

<style>
</style>
