<template>
<div className="page-content page-container"  style="margin: auto" id="page-content"> 
    <input type="text" placeholder="Enter your email :)" v-model="email"/>  
    <input :disabled="isComplete" @click="navigate" type="button" value="Confirm (or maybe not, maybe this is fishing hehe, we gonna spaaammmmmm)"/>
 </div>
</template>
<script>
   import axios from "axios";
   import swal from "sweetalert";
  export default{
  data() {
    return {
        email : ''
    }
  },
  mounted() {
     
  },
  methods: {
      navigate : function(){
          const path= window.location.pathname.split('/')[1]
          if(path === 'passwordless'){
              axios.post(process.env.VUE_APP_BACK + 'passwordlessLogin', {"email" : this.email})
                   .then((response) => {
                       if(response.data === true)
                        swal({  
                            title: "Email sent!",  
                            text: "Check your inbox for a new message with credentials about passwordless login!",  
                            icon: "success",  
                            button: "Confirm",  
                      });
                      else
                        swal({  
                            title: "Invalid email!",  
                            text: "We couldn't find an account associated with the given email. Please check your input and try again.",  
                            icon: "error",  
                            button: "Confirm",  
                      });
                   });
          }else{
              axios.post(process.env.VUE_APP_BACK + 'forgottenPassword', {"email" : this.email})
                   .then((response) => {
                       alert(response.data + " check data");
                   });
          }
      }
  },
  computed:{
      isComplete(){
          var validEmail = /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(.\w{2,3})+$/.test(this.email)
          if(validEmail)
            return false;
        
         return true;
      }
  }
};
</script>

<style>
</style>
