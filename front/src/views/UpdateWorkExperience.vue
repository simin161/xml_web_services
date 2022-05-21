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
                    <button @click="update">add</button>
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
    
  },
  methods: {
  update : function(){
      axios.defaults.headers.common["Authorization"] =
                             localStorage.getItem("loggedUser");
      axios.post(process.env.VUE_APP_BACK +'workExperiences', this.workExp)
      .then(function (response) {
        console.log(response);
        this.$router.push("/profilePage")
      })
      .catch(function (error) {
        console.log(error);
      });
  }}
};
</script>

<style>
</style>
