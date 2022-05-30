<template>
<nav  class="navbar navbar-fixed-top navbar-expand" style="background-color: white; list-style: none; box-shadow: rgba(0, 0, 0, 0.15) 1.95px 1.95px 2.6px; ">
      <div class="container-fluid" style="background-color: white; text-align: right">
      <a class="navbar-brand"  @click="homepage()" >
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
            <li><a @click="redirectMyProfile" class="dropdown-item" href="#">My profile</a></li>
            <hr>
            <li><a @click="signOut" class="dropdown-item" href="#">Log out</a></li>
          </ul>
        </li>
     
      </div>
    </nav>
 
    <br>
    <br>
    <div class="container">
<div class="card" >
                <div class="card-body">
                    <div >
                    <h3 style="text-align: left;">Personal infromation</h3>
                    <hr/>
                        <div class="row">
                        <div class="col-lg-3">
                        <p style="text-align: left;">First name</p>
                        </div>
                        <div class="col-lg-9">
                        <input class="form-control"  id="firstName" v-model="user.firstName" type="text" required />
                        </div>
                        </div>
                        <div class="row">
                        <div class="col-lg-3">
                        <p style="text-align: left;">Last name</p>
                        </div>
                        <div class="col-lg-9">
                        <input class="form-control"  id="lastName" v-model="user.lastName" type="text" required/>
                        </div>
                        </div>
                        <div class="row">
                        <div class="col-lg-3">
                        <p style="text-align: left;">Birthday</p>
                        </div>
                        <div class="col-lg-9">
                        <input class="form-control"  type="date" v-model="user.birthday" id="birthday" />
                        </div>
                        </div>
                        <div class="row">
                        <div class="col-lg-3">
                        <p style="text-align: left;">Gender</p>
                        </div>
                        <div  class="col-lg-9">
                                <select class="form-control"  id="gender" v-model="user.gender">
                                    <option value="MALE">
                                        MALE
                                    </option>
                                    <option value="FEMALE">
                                        FEMALE
                                    </option>
                                </select>
                        </div>
                        </div>
                        <div class="row">
                        <div class="col-lg-3">
                        <p style="text-align: left;">Phone num</p>
                        </div>
                        <div class="col-lg-9">
                        <input class="form-control"  id="phone" v-model="user.phone" type="text" />
                        </div>
                        </div>
                        <div class="row">
                        <div class="col-lg-3">
                        <p style="text-align: left;">Email</p>
                        </div>
                        <div class="col-lg-9">
                        <input class="form-control"  id="email" v-model="user.email" type="text" disabled/>
                        </div>
                        </div>
                        <div class="row">
                        <div class="col-lg-3">
                        <p style="text-align: left;">Username</p>
                        </div>
                        <div class="col-lg-9">
                        <input class="form-control" id="username" v-model="user.username" type="text" disabled/>
                        </div>
                        </div>  
                        <div class="row">
                        <div class="col-lg-3">
                        <p style="text-align: left;">Private</p>
                        </div>
                        <div  class="col-lg-9">
                                <select class="form-control"  id="private"  v-model="selected">
                                    <option value="true">
                                        YES
                                    </option>
                                    <option value="false">
                                        NO
                                    </option>
                                </select>
                        </div>
                        </div>                   
                        </div>
                        <hr>
                        <br>
                        <h3 style="text-align: left;">Biography</h3>
                    
                        <textarea class="form-control" id="biography" v-model="user.biography"></textarea>
                        <br>
                        <h3 style="text-align: left;">Interests</h3>
                       
                        <textarea  class="form-control" id="interests" v-model="user.interests"></textarea>
                        <br>

                        <h3 style="text-align: left;">Skills</h3>
                      
                        <textarea  class="form-control" id="skills" v-model="user.skills"></textarea>
                        <br>
                         <br>
                          <br>
                        <div class="row">
                            <div class="col"> 
                                <button @click="navigateToChangePassword()"  type="button" style="width: 50%" class="btn btn-outline-danger">Change password</button>
                            </div>

                            <div class="col"> 
                               <button type="button"  :disabled="isComplete" @click="update" style="width: 50%"  class="btn btn-primary">Update profile</button>
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


</template>
<script>
 import axios from "axios";
  export default{
  data() {
    return {
        user: {},
        bio: '',
        selected: 'true'
    };
  },
  mounted() {
      if(localStorage.getItem("loggedUser") === ''){
          this.$router.push("/signIn")
      }
      axios.defaults.headers.common["Authorization"] =
                             localStorage.getItem("loggedUser");
    axios.get(process.env.VUE_APP_BACK + 'user')
            .then((response) => {
                this.user = response.data;
                this.selected=this.user.privateProfile
            })
            .catch(function (error) {
            console.log(error);
            });
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
      navigateToChangePassword: function(){
              this.$router.push("/changePassword");
      },
      update : function(){
          axios.defaults.headers.common["Authorization"] =
                             localStorage.getItem("loggedUser");
            
            this.user.isPrivate = this.selected;
            axios.post(process.env.VUE_APP_BACK + 'personalInfo', this.user)
            .then((response) => {
                this.$swal.fire({
                 position: 'top-end',
                  icon: 'success',
                 title: 'Your work has been saved',
               showConfirmButton: false,
               timer: 1500
    })
                console.log(response);
                this.$router.push("/profilePage");
          
          })
          .catch(function (error) {
            console.log(error);
          });  
        }
  },
  computed: {
      isComplete(){
        var validNames = /^[ a-zA-Z\-’]+$/.test(this.user.firstName) && /^[ a-zA-Z\-’]+$/.test(this.user.lastName);
        var validGBD = /\S/.test(this.user.birthDate) && /\S/.test(this.user.gender) && /\S/.test(this.user.isPrivate);
        var validPhone = /^[0-9]+$/.test(this.user.phone);
        var validBiography = /[()[\]{}<>]/.test(this.user.biography);
        var validSkills = /[()[\]{}<>]/.test(this.user.skills);
        var validInterests = /[()[\]{}<>]/.test(this.user.interests);
        if(!validBiography &&  !validSkills && !validInterests && validNames && validGBD && validPhone)
            return false;

        return true;
      }
  }
};
</script>
<style>
</style>