<template>


 <div class="slikadiv2">
<nav  class="navbar navbar-fixed-top navbar-expand" style="background-color: white; list-style: none; box-shadow: rgba(0, 0, 0, 0.15) 1.95px 1.95px 2.6px; ">
      <div class="container-fluid" style="background-color: white; text-align: right">
      <a class="navbar-brand"   >
      <img src="../assets/dislinktLogo.jpg" alt="" width="200" height="80" >
      </a>
   
          <button id="buttonhomepage" class="btn" @click="sign()" style="font-size: 150%;">Sign in</button>
      </div>
    </nav>
    <div class="container">
     <div  style="padding: 4%;">
        <br>
        <form @submit="searchUsersFunc" method="post" class="d-flex">
        <input v-model="searchParam" class="form-control me-2" type="search" placeholder="Search users by first name, last name, username or email" aria-label="Search">
        <button class="btn btn-outline-secondary" type="submit">Search</button>
        </form>
        <br><br>
        <div v-if="foundUsers.length>0">
      
             <br> <br>
               <h6 style="text-align: left;">Found users</h6>
              <li  v-for="user in foundUsers" v-bind:key="user.username" class="list-group-item">
                  <div class="row">
                       <div class="col-lg-3">
                                                  <svg viewBox="0 0 36 36" fill="none" role="img" xmlns="http://www.w3.org/2000/svg" width="50" height="50"><title>Lucy Stone</title><mask id="mask__beam" maskUnits="userSpaceOnUse" x="0" y="0" width="36" height="36"><rect width="36" height="36" rx="72" fill="#FFFFFF"></rect></mask><g mask="url(#mask__beam)"><rect width="36" height="36" fill="#a65bb7"></rect><rect x="0" y="0" width="36" height="36" transform="translate(4 4) rotate(340 18 18) scale(1.1)" fill="#240c39" rx="36"></rect><g transform="translate(-4 -3) rotate(0 18 18)"><path d="M15 20c2 1 4 1 6 0" stroke="#FFFFFF" fill="none" stroke-linecap="round"></path><rect x="14" y="14" width="1.5" height="2" rx="1" stroke="none" fill="#FFFFFF"></rect><rect x="20" y="14" width="1.5" height="2" rx="1" stroke="none" fill="#FFFFFF"></rect></g></g></svg>

       
                       </div>
                       <div class="col-lg-6" style="text-align: left">
                            <h5><b>{{user.firstName}} {{user.lastName}}</b></h5>
                            <p>{{user.username}} <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-dot" viewBox="0 0 16 16">
  <path d="M8 9.5a1.5 1.5 0 1 0 0-3 1.5 1.5 0 0 0 0 3z"/>
</svg> {{user.email}}</p>
                        </div>
                         <div class="col-lg-3" style="text-align: left">
                            <button @click="visitProfile(user.email)"  id="visitProf">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-arrow-down-left" viewBox="0 0 16 16">
                                         <path fill-rule="evenodd" d="M2 13.5a.5.5 0 0 0 .5.5h6a.5.5 0 0 0 0-1H3.707L13.854 2.854a.5.5 0 0 0-.708-.708L3 12.293V7.5a.5.5 0 0 0-1 0v6z"/>
                                        </svg> VISIT PROFILE
                            </button>
                        </div>

                  </div>
   

              </li>
        </div>
    </div>
     

      
    </div>
    
  </div>
       
</template>

<script>

import axios from "axios"
export default {
  data() {
    return {
        searchParam: '',
        foundUsers: []
    };
  },
  mounted() {
    
  },
  methods: {
       sign: function(){
         this.$router.push("/signIn")
       },
       searchUsersFunc: function(event){
              event.preventDefault()
              if(this.searchParam=="") return
              axios.get(process.env.VUE_APP_BACK + 'searchUsers/'+this.searchParam)
                  .then((response) => {
                      this.foundUsers = response.data;
                  })
                  .catch(function (error) {
                      console.log(error);
                  });
            
       },
       visitProfile: function(email){
            this.$router.push("/showProfilePublic/"+email)
       }
    }
};
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
#nav {
  padding: 30px;
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
}
#buttonhomepage{
   background-color: #000000;
   color: white;
}
#buttonhomepage:hover{
   background-color: #e385fe;
   color: white;
}

#nav a.router-link-exact-active {
  color: #42b983;
}

.slikadiv2{
  background-color: #e0b1ec;
  background-repeat: no-repeat;
  background-size: 100% 80%;
  height: 300px;
  
  
     
}
.container .sp {
     font-family:   Georgia, 'Times New Roman', Times, serif;
     color: rgb(53, 52, 52);
     font-size: 40px;
     font-weight: 700;
     font-stretch: extra-expanded;
     
}
</style>
