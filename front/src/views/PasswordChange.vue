<template>

<div class="container" >

  <div class="card" style="margin-top: 5%">
    <div class="card-body">
      <h3 style="text-align: left;">Change password</h3>
      <hr>
    <input class="form-control" type="password" style="padding: 1%;" v-model="oldPassword" placeholder="Old password"/>
    <br>
    <input class="form-control"  type="password" style="padding: 1%" v-model="newPassword" placeholder="New password"/>
    <br>
    <input class="form-control"  type="password" style="padding: 1%" v-model="confirmPassword" placeholder="Confirm password"/>
    <div style="text-align: right;">
      <br>
    <button  type="button"  :disabled="isComplete"  @click="change" style="width: 30%; "  class="btn btn-primary">Confirm</button>
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
                    this.$swal.fire({
                        position: 'top-end',
                          icon: 'success',
                        title: 'Password changed!',
                      showConfirmButton: false,
                      timer: 1500
                   })
                   this.$router.push("/homepage")
                   return response;
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
