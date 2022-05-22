<template>
<div className="page-content page-container" id="page-content" style="margin: auto">   
    <input type="password" v-model="oldPassword" placeholder="Old password"/>
    <input type="password" v-model="newPassword" placeholder="New password"/>
    <input type="password" v-model="confirmPassword" placeholder="Confirm password"/>
    <input type="button" :disabled="isComplete" value="Confirm" @click="change"/>
</div>
</template>
<script>
  import axios from "axios";
  export default{
  data() {
    return {
        oldPassword : '',
        newPassword : '',
        confirmPassword : '',
    }
  },
  mounted() {
     if(localStorage.getItem("loggedUser") === ''){
          this.$router.push("/signIn")
      }
  },
  methods: {
      change : function(){
        axios.defaults.headers.common["Authorization"] =
                             localStorage.getItem("loggedUser");
        axios.post(process.env.VUE_APP_BACK + "changePassword", {"oldPassword" : this.oldPassword, "newPassword" : this.newPassword})
             .then((response) => {
                 alert(response.data);
             })
      }
  },
  computed: {
      isComplete(){
          var validPassword = /^(?=.*\d)(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z]).{8,}$/.test(this.newPassword);
          if(validPassword && this.newPassword === this.confirmPassword)
            return false;
          return true;
      }
  }
};
</script>

<style>
</style>
