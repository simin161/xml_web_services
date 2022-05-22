<template>
    <div className="card edit-profile-card">
        <div className="card-body">
    
           <h3>Work experience</h3>
            <hr/>
               
           <div className="row">
                <div className="col form-group">
                    <p id="p">Work place</p>
                    <input id="workPlace" v-model="workExp.workPlace" type="text"  className="form-control" required />
                </div>

                <div className="col form-group">
                    <p id="p">Work title</p>   
                    <input id="workTitle" v-model="workExp.workTitle" type="text" className="form-control" required/>       
                </div> 
           
          </div>
          <div className="row">

                <div className="col form-group">
                    <p id="p">From</p>   
                    <input type="date" v-model="workExp.from" id="from" >      
                </div> 
                 
                <div className="col form-group">
                    <p id="p">To</p>   
                    <input type="date" v-model="workExp.to" id="to" >
                </div> 

               <div  className="col form-group">
                    <p id="p">&nbsp;</p>   
                    <input type="button" :disabled="isComplete" @click="update" value="add"/>
                </div> 
               
          </div>
        
          <hr/>
          <br/> <br/>

                    
                </div>
    </div>
</template>

<script>
   import axios from "axios";
  export default{
  data() {
    return {
      workExp:{
        workPlace : '',
        workTitle : '',
        from : '',
        to : '',
        email : ''
      }
    };
  },
  mounted() {
     if(localStorage.getItem("loggedUser") === ''){
          this.$router.push("/signIn")
      }
  },
  methods: {
  update : function(){
      axios.defaults.headers.common["Authorization"] =
                             localStorage.getItem("loggedUser");
      axios.post(process.env.VUE_APP_BACK +'workExperiences', this.workExp)
      .then((response) => {
        console.log(response);
        this.$router.push("/profilePage")
      })
      .catch(function (error) {
        console.log(error);
      });
    }
  },
  computed:{
    isComplete(){
        var validSchool = /[()[\]{}<>]/.test(this.workExp.workPlace);
        var validSkills = /[()[\]{}<>]/.test(this.workExp.workTitle);
        var validFrom = /\S/.test(this.workExp.from);
        var validTo = /\S/.test(this.workExp.to);

        if(validTo && validFrom && !validSchool && !validSkills 
          && /\S/.test(this.workExp.workPlace)  && /\S/.test(this.workExp.workTitle)){
            return false;
          }

        return true;
    }
  }

};
</script>

<style>
</style>
