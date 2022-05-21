<template>
 <div className="card edit-profile-card">
        <div className="card-body">
           <h3>Education</h3>
            <hr/>
               
           <div className="row">
                <div className="col form-group">
                    <p id="p">School</p>
                    <input id="school" v-model="ed.school" type="text"  className="form-control" />
                </div>

                <div className="col form-group">
                    <p id="p">Degree</p>   
                    <input id="degree" v-model="ed.degree" type="text" pattern="[0-9]+\.?[0-9]*" className="form-control" />       
                </div> 
                 
                <div className="col form-group">
                    <p id="p">Field of study</p>   
                    <input id="field" v-model="ed.fieldOfStudy" type="text" pattern="[0-9]+\.?[0-9]*" className="form-control" />
                </div>  
           
          </div>
          <div className="row">

                <div className="col form-group">
                    <p id="p">From</p>   
                    <input type="date" v-model="ed.from" id="from" />
                </div> 
                 
                <div className="col form-group">
                    <p id="p">To</p>   
                    <input type="date" v-model="ed.to" id="to"/>
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
      ed:{
        school : '',
        degree : '',
        fieldOfStudy : '',
        from : '',
        to : ''
      }
    };
  },
  mounted() {
    
  },
  methods: {
  update : function(){
      axios.defaults.headers.common["Authorization"] =
                             localStorage.getItem("loggedUser");
      axios.post(process.env.VUE_APP_BACK + 'education',this.ed)
              .then((response) => {
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
