<template>
<nav  class="navbar navbar-fixed-top navbar-expand" style="background-color: white; list-style: none; box-shadow: rgba(0, 0, 0, 0.15) 1.95px 1.95px 2.6px; ">
      <div class="container-fluid" style="background-color: white; text-align: right">
      <a class="navbar-brand"   >
      <img src="../assets/dislinktLogo.jpg" alt="" width="200" height="80" >
      </a>
     <button id="buttonhomepage" class="btn" @click="sign()" style="font-size: 150%">Sign up</button>
     
      </div>
    </nav>



        
       
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
#slikadiv{
  background-image: url('../assets/background.jpg');
  background-repeat: no-repeat;
  background-size: 100% 100%;
  height: 700px
     
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
</style>
