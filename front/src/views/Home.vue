<template>


 <div class="slikadiv">
<nav  class="navbar navbar-fixed-top navbar-expand" style="background-color: white; list-style: none; box-shadow: rgba(0, 0, 0, 0.15) 1.95px 1.95px 2.6px; ">
      <div class="container-fluid" style="background-color: white; text-align: right">
      <a class="navbar-brand"   >
      <img src="../assets/dislinktLogo.jpg" alt="" width="200" height="80" >
      </a>
   
     
      </div>
    </nav>
    <div class="container">
    
      <div class="row">
        <div class="col-sm-6">

        
      </div>
      <div class="col-sm-6" style="margin-top: 11%; padding-left: 5%; padding-right: 5%">

         <p class="sp" >Expand your network.</p>
         <p class="sp" >Get serious.</p>
         <p class="sp" >Try disLinkt.</p>
            <hr>
        <button id="buttonhomepage" class="btn" @click="sign()" style="font-size: 150%; width: 70%;">Start now</button>
      </div>

      </div>
      
    </div>
    
  </div>
       
</template>

<script>
export default {

  data() {
    return {
    };
  },
  mounted() {
    
  },
  methods: {
       sign: function(){
         this.$router.push("/signIn")
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

.slikadiv{
  background-image: url('../assets/landing.webp');
  background-repeat: no-repeat;
  background-size: 100% 80%;
  height: 900px;
  
  
     
}
.container .sp {
     font-family:   Georgia, 'Times New Roman', Times, serif;
     color: rgb(53, 52, 52);
     font-size: 40px;
     font-weight: 700;
     font-stretch: extra-expanded;
     
}
</style>
