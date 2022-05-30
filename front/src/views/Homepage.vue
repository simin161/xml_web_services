<template>
<nav  class="navbar navbar-fixed-top navbar-expand" style="background-color: white; list-style: none; box-shadow: rgba(0, 0, 0, 0.15) 1.95px 1.95px 2.6px; ">
      <div class="container-fluid" style="background-color: white; text-align: right">
      <a class="navbar-brand" href="/homepage"  >
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
        <button @click="homepage()" id="buttonProfile2">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person-video2" viewBox="0 0 16 16">
                                        <path d="M10 9.05a2.5 2.5 0 1 0 0-5 2.5 2.5 0 0 0 0 5Z"/>
                                        <path d="M2 1a2 2 0 0 0-2 2v9a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V3a2 2 0 0 0-2-2H2ZM1 3a1 1 0 0 1 1-1h2v2H1V3Zm4 10V2h9a1 1 0 0 1 1 1v9c0 .285-.12.543-.31.725C14.15 11.494 12.822 10 10 10c-3.037 0-4.345 1.73-4.798 3H5Zm-4-2h3v2H2a1 1 0 0 1-1-1v-1Zm3-1H1V8h3v2Zm0-3H1V5h3v2Z"/>
                                        </svg> USER POSTS
        </button>

        <button @click="searchUsers()" id="buttonProfile2">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-search" viewBox="0 0 16 16">
                                        <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z"/>
                                        </svg> SEARCH USERS
        </button>

        <button @click="jobOffers()" id="buttonProfile2">
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
          <h1 style="text-align: left;" >User posts</h1>
          <button @click="refreshPage()" id="smaillbuttonProfile">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-plus-lg" viewBox="0 0 16 16">
                                        <path fill-rule="evenodd"/>
                                        </svg> REFRESH
        </button>
        <div   v-for="(post,index) in posts" :key="index" class="card" >
                <div class="card-body" style="text-align: left;">
                    <h5 style="text-align: left"><b>{{post.fullName}}</b></h5>
           <p style="color: gray; font-size: 13px">{{post.date}}</p>
           <p v-if="post.text !=''" style="text-align: left">{{post.text}}</p>
           
           <a v-if="post.link != ''" target="_blank"  v-bind:href="'http://'+ post.link">{{post.link}}</a> <br>
           <div v-if="post.pathToImage !=''" style="text-align: center">
            <img  :src="require('@/assets/' + post.pathToImage)" alt="" width="700" height="300" >
            </div>       
            <hr>
                 <div >
                   <a @click="loadReactions(post.idPost)" data-bs-toggle="modal" data-bs-target="#staticBackdrop" style="color: gray; text-decoration: none" target="_blank"  ><b>{{post.numOfReactions}} reactions </b></a> ,
                   <a @click="loadComments(post.idPost)" data-bs-toggle="modal" data-bs-target="#loadCommentsModal"   style="color: gray; text-decoration: none" target="_blank"  ><b>{{post.numOfComments}} comments </b></a> 
                 
                    
              
                 </div>
        <br>
            <div >

               
                  <button  @click="addLike(post.idPost,index)" style="width: 20%;" type="button" class="btn btn-outline-secondary"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-suit-heart-fill" viewBox="0 0 16 16">
                 <path d="M4 1c2.21 0 4 1.755 4 3.92C8 2.755 9.79 1 12 1s4 1.755 4 3.92c0 3.263-3.234 4.414-7.608 9.608a.513.513 0 0 1-.784 0C3.234 9.334 0 8.183 0 4.92 0 2.755 1.79 1 4 1z"/>
                 </svg>Like</button>


                <button  @click="addDislike(post.idPost,index)" style="width: 20%;" type="button" class="btn btn-outline-secondary"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-hand-thumbs-down-fill" viewBox="0 0 16 16">
                 <path d="M6.956 14.534c.065.936.952 1.659 1.908 1.42l.261-.065a1.378 1.378 0 0 0 1.012-.965c.22-.816.533-2.512.062-4.51.136.02.285.037.443.051.713.065 1.669.071 2.516-.211.518-.173.994-.68 1.2-1.272a1.896 1.896 0 0 0-.234-1.734c.058-.118.103-.242.138-.362.077-.27.113-.568.113-.856 0-.29-.036-.586-.113-.857a2.094 2.094 0 0 0-.16-.403c.169-.387.107-.82-.003-1.149a3.162 3.162 0 0 0-.488-.9c.054-.153.076-.313.076-.465a1.86 1.86 0 0 0-.253-.912C13.1.757 12.437.28 11.5.28H8c-.605 0-1.07.08-1.466.217a4.823 4.823 0 0 0-.97.485l-.048.029c-.504.308-.999.61-2.068.723C2.682 1.815 2 2.434 2 3.279v4c0 .851.685 1.433 1.357 1.616.849.232 1.574.787 2.132 1.41.56.626.914 1.28 1.039 1.638.199.575.356 1.54.428 2.591z"/>
                 </svg>Dislike</button>


                <button @click="addCom(post.idPost,index)" data-bs-toggle="modal" data-bs-target="#addComment" style="width: 20%;" type="button" class="btn btn-outline-secondary"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-chat-square-dots-fill" viewBox="0 0 16 16">
                <path d="M0 2a2 2 0 0 1 2-2h12a2 2 0 0 1 2 2v8a2 2 0 0 1-2 2h-2.5a1 1 0 0 0-.8.4l-1.9 2.533a1 1 0 0 1-1.6 0L5.3 12.4a1 1 0 0 0-.8-.4H2a2 2 0 0 1-2-2V2zm5 4a1 1 0 1 0-2 0 1 1 0 0 0 2 0zm4 0a1 1 0 1 0-2 0 1 1 0 0 0 2 0zm3 1a1 1 0 1 0 0-2 1 1 0 0 0 0 2z"/>
                </svg> Comment</button>

              
                
            </div>
                </div>

      </div>
             <button @click="loadNewer()" id="smaillbuttonProfile">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-plus-lg" viewBox="0 0 16 16">
                                        <path fill-rule="evenodd" />
                                        </svg> BACK
        </button>

             <button @click="loadOlder()" id="smaillbuttonProfile">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-plus-lg" viewBox="0 0 16 16">
                                        <path fill-rule="evenodd" />
                                        </svg> LOAD OLDER
        </button>
    </div>

 </div>


<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
<div style="background-color: #e6e5e3;" class="footer" >
   <br><br>
    XWS project.
   <br><br> <br><br>
</div>
<!-- Modal -->
<div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
  <div class="modal-dialog modal-xl">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="staticBackdropLabel"></h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div  class="modal-body">
        <h3>Reactions</h3>
        <hr>
       
           <ul class="list-group" >
            <li   v-for="(react,index) in reactions" :key="index" class="list-group-item">
            <div style="text-align:left;">
              <b>{{react.email}}</b>
              <br>
              <p>{{react.reactionType}}</p>
            </div>
         
            
            </li>
            </ul>

      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>

<!-- Modal -->
<div class="modal fade bd-example-modal-xl" id="loadCommentsModal"  data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel"  aria-hidden="true">
  <div  class="modal-dialog modal-xl">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="staticBackdropLabel"></h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <h3>Comments</h3>
        <hr>
       
           <ul class="list-group" >
            <li   v-for="(comment,index) in comments" :key="index" class="list-group-item">
            <div style="text-align:left;">
              <b>{{comment.commentatorsEmail}}</b>
              <br>
              <p>{{comment.text}}</p>
            </div>
         
            
            </li>
            </ul>

      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>

<!-- Modal -->
<div class="modal fade" id="addComment" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="staticBackdropLabel"></h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <h3>Add comment</h3>
        <hr>
         <textarea  style="height: 250px" class="form-control" id="skills" v-model="comment"  ></textarea>
         <p style="color: red; font-size: 20; text-align: left;" v-if="comment==''">Please enter comment!</p>

      </div>
      <div class="modal-footer">
          
        <button @click="addComment" type="button" class="btn btn-outline-success" data-bs-dismiss="modal">Add comment</button>
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
        comment: '',
        idPost: '',
        index: null,
        num: false,
        comments: [],
        reactions: [],
        value: 0
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

       axios.defaults.headers.common["Authorization"] =localStorage.getItem("loggedUser");
                axios.get(process.env.VUE_APP_BACK + 'user')
                .then((response) => {
                    this.loggedUser = response.data;

                    console.log(response.data);

                          axios.get(process.env.VUE_APP_BACK + 'getAllFeedPosts/'+this.value)
                          .then((response) => {
                              this.posts = response.data;
                              console.log("HOMEPAGEEEEE  "+response.data.length)
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
    refreshPage : function(){
        axios.get(process.env.VUE_APP_BACK + 'getAllFeedPosts/0')
                          .then((response) => {
                              this.posts = response.data;
                              console.log("HOMEPAGEEEEE  "+response.data.length)
                          })
                          .catch(function (error) {
                              console.log(error);
                          });     
    },
    loadOlder : function(){
      this.value = this.value+1;
      axios.get(process.env.VUE_APP_BACK + 'getAllFeedPosts/'+this.value)
                          .then((response) => {
                              this.posts = response.data;
                              console.log("HOMEPAGEEEEE  "+response.data.length)
                          })
                          .catch(function (error) {
                              console.log(error);
                          });     
    },
    loadNewer : function(){
      this.value = this.value-1;
      axios.get(process.env.VUE_APP_BACK + 'getAllFeedPosts/'+this.value)
                          .then((response) => {
                              this.posts = response.data;
                              console.log("HOMEPAGEEEEE  "+response.data.length)
                          })
                          .catch(function (error) {
                              console.log(error);
                          });     
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
    loadReactions: function(postId){

            axios.post(process.env.VUE_APP_BACK + 'findReactionsByPostId',{
              id: postId,
            })
                                .then((response) => {
                                  this.reactions = response.data
                                    return response;
                                })
    },
    addCom: function(postId,index){
            this.idPost = postId
            this.index = index
    },
    addComment: function(){
                if(this.comment == ""){
                    this.$swal.fire({
                          position: 'top-end',
                            icon: 'error',
                          title: 'Please enter text!',
                        showConfirmButton: false,
                        timer: 2500
              })
                 return;
              }

                            axios.post(process.env.VUE_APP_BACK + 'comment',{
                              postId: this.idPost,
                              text: this.comment,
                              commentatorsEmail: this.loggedUser.email
                            })
                            .then((response) => {
                            
                                this.comment = ""
                                 axios.post(process.env.VUE_APP_BACK + 'numOfCommentsByPostId',
                            {
                                id: this.idPost
                            }
                            )
                            .then((response) => {
                              this.posts[this.index].numOfComments =response.data
                              
                            })
                                return response;
                            })
    },
    loadComments: function(postId){
                        axios.post(process.env.VUE_APP_BACK + 'findCommentsByPostId',{
                            id: postId,
                          })
                                              .then((response) => {
                                                this.comments = response.data
                                                  return response;
                                              })
    },
    getUserInfo: function(email){
                          var username=""
                          axios.get(process.env.VUE_APP_BACK + 'user/'+email+"/")
                                              .then((response) => {
                                                  username = response.data;
                                              })
                          return username
    },
    addLike: function(postId,index){
        
     axios.post(process.env.VUE_APP_BACK + 'checkReaction',{postId: postId})
      .then((response) => {
        console.log("Respooonse"+response.data)

            if(  response.data == "LIKE"){
                axios.post(process.env.VUE_APP_BACK + 'deleteReaction',{postId: postId,reactionType: "LIKE"})
                .then((response)=>{
                     console.log(response.data);
                     this.$swal.fire({
                     position: 'top-end',
                     icon: 'success',
                     title: 'Reaction removed',
                     showConfirmButton: false,
                     timer: 1500})

                   axios.post(process.env.VUE_APP_BACK + 'numOfReactionsByPostId', {id: postId})
                   .then((response) => {
                    this.posts[index].numOfReactions= response.data;
                     })
                               
                })
              }else if (response.data == "NONE"){
                   axios.post(process.env.VUE_APP_BACK + 'reaction',{
                              postId: postId,
                              reactionType: "LIKE"
                             })
                              .then((response) => {
                           console.log(response)
                             axios.post(process.env.VUE_APP_BACK + 'numOfReactionsByPostId',
                            {
                                id: postId
                            }
                            )
                            .then((response) => {
                                     console.log(response.data);
                               this.posts[index].numOfReactions= response.data;
                                 
                            
                            })
                          })

              }else if (response.data == "DISLIKE"){

                  axios.post(process.env.VUE_APP_BACK + 'deleteReaction',{postId: postId,reactionType: "DISLIKE"})
                .then(()=>{  
                    axios.post(process.env.VUE_APP_BACK + 'reaction',{
                              postId: postId,
                              reactionType: "LIKE"
                             })
                              .then((response) => {
                           console.log(response)
                             axios.post(process.env.VUE_APP_BACK + 'numOfReactionsByPostId',
                            {
                                id: postId
                            }
                            )
                            .then((response) => {
                                     console.log(response.data);
                               this.posts[index].numOfReactions= response.data;
                                 
                            
                            })
                          })
                        
                          })
              }
                             
                                 
                            
           })
                          
      
   
  
  

    },
     addDislike: function(postId,index){
     axios.post(process.env.VUE_APP_BACK + 'checkReaction',{postId: postId})
      .then((response) => {
        console.log("Respooonse"+response.data)

            if(response.data == "DISLIKE" ){
                axios.post(process.env.VUE_APP_BACK + 'deleteReaction',{postId: postId,reactionType: "DISLIKE"})
                .then((response)=>{
                     console.log(response.data);
                     this.$swal.fire({
                     position: 'top-end',
                     icon: 'success',
                     title: 'Reaction removed',
                     showConfirmButton: false,
                     timer: 1500})

                   axios.post(process.env.VUE_APP_BACK + 'numOfReactionsByPostId', {id: postId})
                   .then((response) => {
                    this.posts[index].numOfReactions= response.data;
                     })
                               
                })
              }else if (response.data == "NONE"){
                   axios.post(process.env.VUE_APP_BACK + 'reaction',{
                              postId: postId,
                              reactionType: "DISLIKE"
                             })
                              .then((response) => {
                           console.log(response)
                             axios.post(process.env.VUE_APP_BACK + 'numOfReactionsByPostId',
                            {
                                id: postId
                            }
                            )
                            .then((response) => {
                                     console.log(response.data);
                               this.posts[index].numOfReactions= response.data;
                                 
                            
                            })
                          })

              }else if (response.data == "LIKE"){
                   axios.post(process.env.VUE_APP_BACK + 'deleteReaction',{postId: postId,reactionType: "LIKE"})
                .then(()=>{  
                    axios.post(process.env.VUE_APP_BACK + 'reaction',{
                              postId: postId,
                              reactionType: "DISLIKE"
                             })
                              .then((response) => {
                           console.log(response)
                             axios.post(process.env.VUE_APP_BACK + 'numOfReactionsByPostId',
                            {
                                id: postId
                            }
                            )
                            .then((response) => {
                                     console.log(response.data);
                               this.posts[index].numOfReactions= response.data;
                                 
                            
                            })
                          })
                        
                          })

              }
                             
                                 
                            
           })
  

    }
  }
};
</script>
<style>

#buttonProfile2{
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
#buttonProfile2:hover{
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