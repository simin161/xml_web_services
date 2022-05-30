<template>
<nav  class="navbar navbar-fixed-top navbar-expand" style="background-color: white; list-style: none; box-shadow: rgba(0, 0, 0, 0.15) 1.95px 1.95px 2.6px; ">
      <div class="container-fluid" style="background-color: white; text-align: right">
      <a class="navbar-brand"  href="/homepage" >
      <img src="../assets/dislinktLogo.jpg" alt="" width="200" height="80">
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
<div class="containter">
 <div class="card edit-profile-card" style="margin: 5%;">
        <div class="card-body">
        <h2 style="text-align: left;">Create new post</h2> 
        <hr>
        <h6 style="text-align: left;">Text</h6>  
        <textarea  class="form-control" id="skills" v-model="postDto.text"  ></textarea>
        <br>
         
        <h6 style="text-align: left;">Share link</h6>
        <input class="form-control" id="username" v-model="postDto.link"   type="text" />

        <br>
         
        <h6 style="text-align: left;">Share image</h6>
        <input  @change="imageSelected" class="form-control" id="username"  type="file" />
        <br>
        <div style="text-align: right">
        <button @click="createNewPost()" type="button" class="btn btn-outline-secondary">Submit post</button>
        </div>
    </div>
     </div>   </div>
     <br><br>
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
      loggedUser: {},
      postDto: {
          text: '',
          pathToImage: '',
          link: '',
          articleImage: ''
      }
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
   imageSelected: function(event){
     console.log(event)
			const file = document.querySelector('input[type=file]').files[0]
			const reader = new FileReader()
			if(file != null){
				this.imagePath = true;
        reader.onloadend = () => {
				this.postDto.pathToImage = reader.result
        console.log("AAAAAA"+ this.postDto.pathToImage)
				}
				reader.readAsDataURL(file);
			}
			else{
				this.imagePath = false
			}
		},
   createNewPost: function(){
       if(this.postDto.text =="" && this.postDto.link=="" && this.postDto.pathToImage == "") {
            this.$swal.fire({
                 position: 'top-end',
                  icon: 'error',
                 title: 'Post can not be empty!',
               showConfirmButton: false,
               timer: 1500
    })
        return;
       
       }
        axios.post(process.env.VUE_APP_BACK + 'newPost',
        {
            text: this.postDto.text,
            link: this.postDto.link,
            pathToImage: this.postDto.pathToImage
        })
       .then((response) => {
            this.$router.push("/homepage")
            return response;

       })
       .catch(function (error) {
       console.log(error);
       });
   }

  },
};
</script>

<style>
</style>
