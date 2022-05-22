<template>

<div  >
  <div class="row" id="slikadiv" >
    <div class=" justify-content-center" style="padding: 5%; padding-left: 20%; padding-right: 20%; ">
       <div class="row" style="height: 20%; padding-bottom: 5%;" >
         <button class="btn" @click="sign()" style=" background-color: black; color: white; font-size: 200%">Show me</button>
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

#nav a.router-link-exact-active {
  color: #42b983;
}
</style>
