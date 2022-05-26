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
    </nav>
    <br>
    <br> 
     <div class="container">
    <div class="card edit-profile-card" style="margin-left: 5%; margin-right: 5%;">
        <div class="card-body" style="text-align: left;">
        
           <h5 style="text-align: left"><b>Magdalena Reljin</b></h5>
           <p style="color: gray; font-size: 13px">2021-04-08</p>
           <p style="text-align: left">Ovo je moj prvi post. Ovo je moj prvi post. Ovo je moj prvi post. Ovo je moj prvi post. Ovo je moj prvi post. Ovo je moj prvi post. Ovo je moj prvi post. Ovo je moj prvi post.</p>
           
           <a >Ovo je moj prvi post.</a> <br>
           <div style="text-align: center">
            <img src="../assets/dislinktLogo.jpg" alt="" width="700" height="300" >
            </div>       
            <hr>
                 <div >

                   <b>5 likes, </b>
                   <b>56 dislikes, </b>
                   <b>156 comments </b>    
                    
              
                 </div>
      <br>
            <div >
                <button style="width: 11%;" type="button" class="btn btn-outline-secondary"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-suit-heart-fill" viewBox="0 0 16 16">
                 <path d="M4 1c2.21 0 4 1.755 4 3.92C8 2.755 9.79 1 12 1s4 1.755 4 3.92c0 3.263-3.234 4.414-7.608 9.608a.513.513 0 0 1-.784 0C3.234 9.334 0 8.183 0 4.92 0 2.755 1.79 1 4 1z"/>
                 </svg> Like</button>


                <button style="width: 11%;" type="button" class="btn btn-outline-secondary"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-hand-thumbs-down-fill" viewBox="0 0 16 16">
                 <path d="M6.956 14.534c.065.936.952 1.659 1.908 1.42l.261-.065a1.378 1.378 0 0 0 1.012-.965c.22-.816.533-2.512.062-4.51.136.02.285.037.443.051.713.065 1.669.071 2.516-.211.518-.173.994-.68 1.2-1.272a1.896 1.896 0 0 0-.234-1.734c.058-.118.103-.242.138-.362.077-.27.113-.568.113-.856 0-.29-.036-.586-.113-.857a2.094 2.094 0 0 0-.16-.403c.169-.387.107-.82-.003-1.149a3.162 3.162 0 0 0-.488-.9c.054-.153.076-.313.076-.465a1.86 1.86 0 0 0-.253-.912C13.1.757 12.437.28 11.5.28H8c-.605 0-1.07.08-1.466.217a4.823 4.823 0 0 0-.97.485l-.048.029c-.504.308-.999.61-2.068.723C2.682 1.815 2 2.434 2 3.279v4c0 .851.685 1.433 1.357 1.616.849.232 1.574.787 2.132 1.41.56.626.914 1.28 1.039 1.638.199.575.356 1.54.428 2.591z"/>
                 </svg> Dislike</button>


                <button style="width: 11%;" type="button" class="btn btn-outline-secondary"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-chat-square-dots-fill" viewBox="0 0 16 16">
                <path d="M0 2a2 2 0 0 1 2-2h12a2 2 0 0 1 2 2v8a2 2 0 0 1-2 2h-2.5a1 1 0 0 0-.8.4l-1.9 2.533a1 1 0 0 1-1.6 0L5.3 12.4a1 1 0 0 0-.8-.4H2a2 2 0 0 1-2-2V2zm5 4a1 1 0 1 0-2 0 1 1 0 0 0 2 0zm4 0a1 1 0 1 0-2 0 1 1 0 0 0 2 0zm3 1a1 1 0 1 0 0-2 1 1 0 0 0 0 2z"/>
                </svg> Comment</button>
                
            </div>
         </div>
    </div>
     </div>
    <br>
    <br>  
<br>
    <br>
  
    <br>
    <br>  
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
        loggedUser : {},
        posts: []
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
            axios.get(process.env.VUE_APP_BACK + 'postsForHomePage')
                 .then((response) => {
                    this.posts = response.data;
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