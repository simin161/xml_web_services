<template>
<div className="card edit-profile-card">
                <div className="card-body">
                    <div className="inputFieldsDiv">
                    <h3>Personal infromation</h3>
                    <hr/>
                        <div className="row">
                        <div className="col-lg-3">
                        <p>First name</p>
                        </div>
                        <div className="col-lg-9">
                        <input id="firstName" v-model="user.firstName" type="text" required/>
                        </div>
                        </div>
                        <div className="row">
                        <div className="col-lg-3">
                        <p>Last name</p>
                        </div>
                        <div className="col-lg-9">
                        <input id="lastName" v-model="user.lastName" type="text" required/>
                        </div>
                        </div>
                        <div className="row">
                        <div className="col-lg-3">
                        <p>Birthday</p>
                        </div>
                        <div className="col-lg-9">
                        <input type="date" v-model="user.birthday" id="birthday" />
                        </div>
                        </div>
                        <div className="row">
                        <div className="col-lg-3">
                        <p>Gender</p>
                        </div>
                        <div id="molimTe" className="col-lg-9">
                                <select id="gender" v-model="user.gender">
                                    <option value="MALE">
                                        MALE
                                    </option>
                                    <option value="FEMALE">
                                        FEMALE
                                    </option>
                                </select>
                        </div>
                        </div>
                        <div className="row">
                        <div className="col-lg-3">
                        <p>Phone num</p>
                        </div>
                        <div className="col-lg-9">
                        <input id="phone" v-model="user.phone" type="text" />
                        </div>
                        </div>
                        <div className="row">
                        <div className="col-lg-3">
                        <p>Email</p>
                        </div>
                        <div className="col-lg-9">
                        <input id="email" v-model="user.email" type="text" disabled/>
                        </div>
                        </div>
                        <div className="row">
                        <div className="col-lg-3">
                        <p>Username</p>
                        </div>
                        <div className="col-lg-9">
                        <input id="username" v-model="user.username" type="text" disabled/>
                        </div>
                        </div>  
                        <div className="row">
                        <div className="col-lg-3">
                        <p>Private</p>
                        </div>
                        <div id="molimTe" className="col-lg-9">
                                <select id="private"  v-model="user.private">
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
                        <h3>Biography</h3>
                        <hr/>
                        <textarea id="biography" v-model="user.biography"></textarea>
                        <h3>Interests</h3>
                        <hr/>
                        <textarea id="interests" v-model="user.interests"></textarea>

                        <h3>Skills</h3>
                        <hr/>
                        <textarea id="skills" v-model="user.skills"></textarea>

                        <button @click="update">Save changes</button>
                  
                </div>
    </div>
</template>
<script>
 import axios from "axios";
  export default{
  data() {
    return {
        user: {}
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
            })
            .catch(function (error) {
            console.log(error);
            });
  },
  methods: {  
      update : function(){
          axios.defaults.headers.common["Authorization"] =
                             localStorage.getItem("loggedUser");
            axios.post(process.env.VUE_APP_BACK + 'personalInfo', this.user)
            .then((response) => {
            console.log(response);
            this.$router.push("/profilePage");
          
          })
          .catch(function (error) {
            console.log(error);
          });  
        }
  }
};
</script>
<style>
</style>