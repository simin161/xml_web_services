import React from 'react';
import axios from 'axios';
const UpdateWorkExperience = () => {
  var lista=[]
  function update(e){
    e.stopPropagation();
    console.log("ikad???");
    var workPlace = document.getElementById("workPlace").value;
    var workTitle = document.getElementById("workTitle").value;
    var from = document.getElementById("from").value;
    var to = document.getElementById("to").value;

    lista.push({})
      /**axios.post('/api/register', {
                                    'firstName' : firstName,
                                    'lastName' : lastName,
                                    'email' : email,
                                    'username' : username,
                                    'password' : password
                                  })
      .then(function (response) {
        console.log(response);
      })
      .catch(function (error) {
        console.log(error);
      });*/
   
  }
  return (
    <div className="card edit-profile-card">
        <div className="card-body">
            <form>
           <h3>Work experience</h3>
            <hr/>
               
           <div className="row">
                <div className="col form-group">
                    <p id="p">Work place</p>
                    <input id="workPlace" type="text"  className="form-control" required />
                </div>

                <div className="col form-group">
                    <p id="p">Work title</p>   
                    <input id="workTitle" type="text" pattern="[0-9]+\.?[0-9]*" className="form-control" required/>       
                </div> 
           
          </div>
          <div className="row">

                <div className="col form-group">
                    <p id="p">From</p>   
                    <input id="from"  type="text" pattern="[0-9]+\.?[0-9]*" className="form-control" required />       
                </div> 
                 
                <div className="col form-group">
                    <p id="p">To</p>   
                    <input  id="to" pattern="[0-9]+\.?[0-9]*" className="form-control"  required/>
                </div> 

               <div  className="col form-group">
                    <p id="p">&nbsp;</p>   
                    <button onClick={ e => update(e)}>add</button>
                </div> 
               
          </div>
          </form>
          <hr/>
          <br/> <br/>

                    
                </div>
    </div>
  );
};
  
export default UpdateWorkExperience;