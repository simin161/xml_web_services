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
            <li><a  class="dropdown-item" @click="redirectMyProfile" href="#">My profile</a></li>
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
                        <h3 id="fullName">{{user.firstName}} {{user.lastName}}</h3>
                        <p id="username" >{{user.username}} </p>

                        <p id="username" v-if="userIsFollowingThisProfile==false && user.privateProfile==true"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-lock" viewBox="0 0 16 16">
                                 <path d="M8 1a2 2 0 0 1 2 2v4H6V3a2 2 0 0 1 2-2zm3 6V3a3 3 0 0 0-6 0v4a2 2 0 0 0-2 2v5a2 2 0 0 0 2 2h6a2 2 0 0 0 2-2V9a2 2 0 0 0-2-2zM5 8h6a1 1 0 0 1 1 1v5a1 1 0 0 1-1 1H5a1 1 0 0 1-1-1V9a1 1 0 0 1 1-1z"/>
                         </svg> PRIVATE PROFILE</p>
                        <hr>
                        <div class="row">
                                  <div class="col">
                                     <a data-bs-toggle="modal" data-bs-target="#staticBackdropFollowing" style="color: gray; text-decoration: none"   ><b>{{followingsNum}} Following </b></a>

                                 </div>
                                  <div class="col">
                                       <a data-bs-toggle="modal" data-bs-target="#staticBackdropFollowers" style="color: gray; text-decoration: none"   ><b>{{followersNum}} Followers </b></a> 

                                  </div>
                 
                    
                        </div>
                         
        
                        <button @mouseenter="setButtonMouseEnter()" @mouseleave="setButtonMouseLeave()"
                         @click="addFollower()" id="buttonFollow" type="button" class="btn" >{{followButton}}</button>
                      
                    </div>
                    <br>
    <br>
                    <div v-if="user.privateProfile==false  || userIsFollowingThisProfile==true" class="personal-info" style="text-align: left;">
                        <h5 style="text-align: left">Personal Information</h5>
                           <hr>
                            <div class="row">
                               <div class="col" style="color: gray">
                                 Email
                                </div>
                                <div class="col" style="text-align: right">
                                    <p id="email"><b>{{user.email}}</b></p>
                                </div>
                            </div>

                              <div class="row">
                               <div class="col" style="color: gray">
                                 Phone
                                </div>
                                <div class="col" style="text-align: right">
                                     <p id="phone"><b> {{user.phone}}</b></p>
                                </div>
                            </div>

                             <div class="row" >
                               <div class="col" style="color: gray">
                                 Gender
                                </div>
                                <div class="col" style="text-align: right">
                                     <p id="phone"> <b>{{user.gender}} </b></p>
                                </div>
                            </div>
                              <div class="row">
                               <div class="col" style="color: gray" >
                                 Birthday
                                </div>
                                <div class="col" style="text-align: right">
                                     <p id="phone"><b> {{user.birthday}}</b></p>
                                </div>
                            </div>
                          
                          
                  
                    </div>
                   <br>
                    <div class="skill" v-if="user.privateProfile==false || userIsFollowingThisProfile==true">
                        <h5 style="text-align: left">Biography</h5>
                        <hr>
                        <p><b>{{user.biography}}</b></p>
                        
                    </div>
                    <div class="skill" v-if="user.privateProfile==false">
                        <h5 style="text-align: left">Interests</h5>
                        <hr>
                        <p id="interests"><b>{{user.interests}}</b></p>
                        
                    </div>
                    <div class="skill" v-if="user.privateProfile==false">
                        <h5 style="text-align: left">Skills</h5>
                        <hr>
                        <p id="skills"><b>{{user.skills}}</b></p>                    
                    </div>   
                </div>
            </div>
            <br>

            <div class="card left-profile-card" v-if="user.privateProfile==false  || userIsFollowingThisProfile==true ">
                <div class="card-body">
                    
                    <div class="personal-info">
                        <div class="row">
                        <div class='col'>
                        <h4 style="text-align: left">Education</h4>
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
                            
                                </div>
                            </li>
                            </ul>
                        </div>
                    </div> 
                </div>
            </div>
            <br>
            <div class="card left-profile-card" v-if="user.privateProfile==false  || userIsFollowingThisProfile==true">
                <div class="card-body">
                    
                    <div class="personal-info">
                     <div class="row">
                                <div class='col'>
                                <h4 style="text-align: left">Work experience</h4>
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
                             
                                </div>
                            </li>
                            </ul>
                        </div>
                    </div> 
                </div>
            </div>
        </div>
        <div  class="col-lg-8">
      <div  v-for="post in posts" v-bind:key="post.idPost" class="card">
                <div class="card-body" style="text-align: left;">
                    <h5 style="text-align: left"><b>{{user.firstName}} {{user.lastName}}</b></h5>
           <p style="color: gray; font-size: 13px">{{post.date}}</p>
           <p style="text-align: left">{{post.text}}</p>
           
           <a  target="_blank"  v-bind:href="'http://'+ post.link">{{post.link}}</a> <br>
           <div style="text-align: center">
            <img  :src="require('@/assets/' + post.pathToImage)" alt="" width="700" height="300" >
            </div>       
            <hr>
                 <div >
                   <a @click="loadReactions()" data-bs-toggle="modal" data-bs-target="#staticBackdrop" style="color: gray; text-decoration: none" target="_blank"  ><b>{{findNumOfReactions(post.idPost)}} reactions </b></a> ,
                   <a @click="loadComments()" data-bs-toggle="modal" data-bs-target="#staticBackdrop1" style="color: gray; text-decoration: none" target="_blank"  ><b>{{findNumOfComments(post.idPost)}} comments </b></a> 
                 
                    
              
                 </div>
      <br>
            <div >

                <button style="width: 20%;" type="button" class="btn btn-outline-secondary"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-suit-heart-fill" viewBox="0 0 16 16">
                 <path d="M4 1c2.21 0 4 1.755 4 3.92C8 2.755 9.79 1 12 1s4 1.755 4 3.92c0 3.263-3.234 4.414-7.608 9.608a.513.513 0 0 1-.784 0C3.234 9.334 0 8.183 0 4.92 0 2.755 1.79 1 4 1z"/>
                 </svg> Like</button>


                <button style="width: 20%;" type="button" class="btn btn-outline-secondary"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-hand-thumbs-down-fill" viewBox="0 0 16 16">
                 <path d="M6.956 14.534c.065.936.952 1.659 1.908 1.42l.261-.065a1.378 1.378 0 0 0 1.012-.965c.22-.816.533-2.512.062-4.51.136.02.285.037.443.051.713.065 1.669.071 2.516-.211.518-.173.994-.68 1.2-1.272a1.896 1.896 0 0 0-.234-1.734c.058-.118.103-.242.138-.362.077-.27.113-.568.113-.856 0-.29-.036-.586-.113-.857a2.094 2.094 0 0 0-.16-.403c.169-.387.107-.82-.003-1.149a3.162 3.162 0 0 0-.488-.9c.054-.153.076-.313.076-.465a1.86 1.86 0 0 0-.253-.912C13.1.757 12.437.28 11.5.28H8c-.605 0-1.07.08-1.466.217a4.823 4.823 0 0 0-.97.485l-.048.029c-.504.308-.999.61-2.068.723C2.682 1.815 2 2.434 2 3.279v4c0 .851.685 1.433 1.357 1.616.849.232 1.574.787 2.132 1.41.56.626.914 1.28 1.039 1.638.199.575.356 1.54.428 2.591z"/>
                 </svg> Dislike</button>


                <button @click="idPost=post.idPost" data-bs-toggle="modal" data-bs-target="#addComment" style="width: 20%;" type="button" class="btn btn-outline-secondary"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-chat-square-dots-fill" viewBox="0 0 16 16">
                <path d="M0 2a2 2 0 0 1 2-2h12a2 2 0 0 1 2 2v8a2 2 0 0 1-2 2h-2.5a1 1 0 0 0-.8.4l-1.9 2.533a1 1 0 0 1-1.6 0L5.3 12.4a1 1 0 0 0-.8-.4H2a2 2 0 0 1-2-2V2zm5 4a1 1 0 1 0-2 0 1 1 0 0 0 2 0zm4 0a1 1 0 1 0-2 0 1 1 0 0 0 2 0zm3 1a1 1 0 1 0 0-2 1 1 0 0 0 0 2z"/>
                </svg> Comment</button>
                
            </div>
                </div>
              
            </div>
             
             
        </div>
        
    </div>
    
</div>
<br><br>
<br><br>
<br><br>
<br><br>
<br><br>
<br><br>
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
        <h3>Reactions</h3>
        <hr>

      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>

<!-- Modal -->
<div class="modal fade" id="staticBackdrop1" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="staticBackdropLabel"></h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <h3>Comments</h3>
        <hr>

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
         

      </div>
      <div class="modal-footer">
        <button @click="addComment" type="button" class="btn btn-outline-success" data-bs-dismiss="modal">Add comment</button>
      </div>
    </div>
  </div>
</div>


<!-- Modal -->
<div class="modal fade" id="staticBackdropFollowing" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="staticBackdropLabel"></h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <h3>{{user.username}}'s followings</h3>
        <hr>
        <ul class="list-group">
            <li  v-for="following in followings" v-bind:key="following.followerEmail" class="list-group-item">{{following.personEmail}}</li>
        </ul>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>

<!-- Modal -->
<div class="modal fade" id="staticBackdropFollowers" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="staticBackdropLabel"></h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <h3>{{user.username}}'s followers</h3>
        <hr>
         <li  v-for="follower in followers" v-bind:key="follower.followerEmail" class="list-group-item">{{follower.followerEmail}}</li>
        

      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
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
        user : {},
        educations: {},
        workExperiences: {},
        posts: {},
        userIsFollowingThisProfile: false,
        followings: [],
        followers: [],
        followersNum: 0,
        followingsNum: 0,
        followButton: 'Follow',
        followButtonTemp: 'Follow',
        commment: '',
        idPost: '',
        num: false,
        
        

    };
  },
  mounted() {
      
          const username= window.location.pathname.split('/')[2]
          console.log("username "+username)
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


                axios.get(process.env.VUE_APP_BACK + 'user/'+username+"/")
                .then((response) => {
                    this.user = response.data;


                    axios.post(process.env.VUE_APP_BACK + 'checkIfUserIsFollowingOtherUser/',{otherUserEmail: this.user.email})
                    .then((response) => {
                                this.userIsFollowingThisProfile = response.data;
                                console.log("da li megi prati daju  "+this.userIsFollowingThisProfile)
                                if(this.userIsFollowingThisProfile==true){

                                             this.followButton="Following"
                                             this.followButtonTemp="Following"
                                             
                                }
                                 
                          if(this.userIsFollowingThisProfile==true || this.user.privateProfile==false){
                              console.log(response.data);

                              
                                  axios.get(process.env.VUE_APP_BACK + 'educations/'+this.user.email+"/")
                                      .then((response) => {
                                          this.educations = response.data;
                                      })

                                  axios.get(process.env.VUE_APP_BACK + 'experiences/'+this.user.email+"/")
                                      .then((response) => {
                                          this.workExperiences = response.data;
                                      })

                                  axios.get(process.env.VUE_APP_BACK + 'getAllUserPosts/'+this.user.email+"/")
                                      .then((response) => {
                                        console.log("SIZEEEE"+response.data.length)
                                          this.posts = response.data;
                                          this.num = true
                                      })
                    }

                    })

                    axios.get(process.env.VUE_APP_BACK + 'followers/'+this.user.email+"/")
                    .then((response) => {
                                this.followers = response.data;
                                this.followersNum = response.data.length
                    })
                    axios.get(process.env.VUE_APP_BACK + 'followings/'+this.user.email+"/")
                    .then((response) => {
                                this.followings = response.data;
                                this.followingsNum = response.data.length
                    })
                      console.log("AAAAA"+this.userIsFollowingThisProfile)
                    /*** ako ta osoba ima javan profil ili je pratim onda mogu da gledam njene postove,edukacije itd*/
                   


                })
       
                       
        })
  },
  methods: {   
    signOut : function(){
        localStorage.setItem("loggedUser", '');
        this.$router.push("/signIn");
    },
     redirectMyProfile: function(){
        this.$router.push("/profilePage")
    },
    findNumOfReactions: function(id){
        var numOfReactions=0;
        console.log("Id od posta  "+id)
                            axios.post(process.env.VUE_APP_BACK + 'numOfReactionsByPostId',
                            {
                                id: id
                            }
                            )
                            .then((response) => {
                                     console.log("broj rekacijaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa "+response.data);
                              numOfReactions= response.data;
                                 
                            
                            })
                            .catch(function (error) {
                                console.log(error);
                            });
                       return numOfReactions
    },
     findNumOfComments: function(id){
       console.log(id)
      if(this.num==true){
    var numOfComments
        console.log("Id od posta  "+id)
                            axios.post(process.env.VUE_APP_BACK + 'numOfCommentsByPostId',
                            {
                                id: id
                            }
                            )
                            .then((response) => {
                              console.log("nummmm"+response.data)
                               numOfComments= response.data;
                              
                            })
                            .catch(function (error) {
                                console.log(error);
                            });
                               console.log("nummmm posle thena"+this.num)
                              
                           
                           console.log("AAAA"+numOfComments)
                            return numOfComments
      }
                        
    },
    loadReactions: function(){

    },
    
    addComment: function(){
         axios.post(process.env.VUE_APP_BACK + 'comment',{
           postId: this.idPost,
           text: this.comment,
           commentatorsEmail: this.user.email
         })
                            .then((response) => {
                                this.findNumOfComments(this.idPost)
                                return response;
                            })
    },
    loadComments: function(){

    },
    getUserInfo: function(email){
        var username=""
        axios.get(process.env.VUE_APP_BACK + 'user/'+email+"/")
                            .then((response) => {
                                username = response.data;
                            })
        return username
    },
    addFollower: function(){
                            if(this.followButton=="Follow"){
                                    axios.post(process.env.VUE_APP_BACK + 'newFollower',{
                                        personEmail: this.user.email
                                    })
                                    .then((response) => {
                                      if(this.user.privateProfile == false){
                                       this.userIsFollowingThisProfile = true;
                                        this.followButton = "Following"
                                        this.followButtonTemp = "Following"
                                      }else if(this.user.privateProfile == true) {
                                        this.userIsFollowingThisProfile = false;
                                        this.followButton = "Requested"
                                        this.followButtonTemp = "Requested"
                                      }
                                        axios.get(process.env.VUE_APP_BACK + 'followers/'+this.user.email+"/")
                                        .then((response) => {
                                            this.followers = response.data;
                                            this.followersNum= response.data.length;
                                            })
                                        return response.data;
                                    })
                            }else if(this.followButton=="Unfollow"){
                                    axios.post(process.env.VUE_APP_BACK + 'removeFollower',{
                                    personEmail: this.user.email
                                    })
                                    .then((response) => {
                                        this.userIsFollowingThisProfile = true;
                                        this.followButton = "Follow"
                                        this.followButtonTemp = "Follow"
                                        axios.get(process.env.VUE_APP_BACK + 'followers/'+this.user.email+"/")
                                        .then((response) => {
                                            this.followers = response.data;
                                            this.followersNum= response.data.length;
                                            })
                                        return response.data;
                                    })

                            }

    },
    setButtonMouseEnter: function(){
             if(this.followButton=="Following")
                 this.followButton="Unfollow"
             else if(this.followButton=="Requested")
                 this.followButton="Unfollow"
             
    },
    setButtonMouseLeave: function(){
            this.followButton=this.followButtonTemp
    },
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
#buttonFollow:hover{
	background-color: white;
    color: #69558d;
    border: 1px solid #69558d;
    

}
#buttonFollow{
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