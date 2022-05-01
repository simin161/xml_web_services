import React from 'react';
import axios from 'axios';
const UpdateEducation = () => {
  
  return (
    <div className="card edit-profile-card">
        <div className="card-body">
           <h3>Education</h3>
            <hr/>
               
           <div className="row">
                <div className="col form-group">
                    <p id="p">School</p>
                    <input v-model="names" type="text"  className="form-control" />
                </div>

                <div className="col form-group">
                    <p id="p">Degree</p>   
                    <input v-model="prices"  type="text" pattern="[0-9]+\.?[0-9]*" className="form-control" />       
                </div> 
                 
                <div className="col form-group">
                    <p id="p">Field of study</p>   
                    <input v-model="prices"  type="text" pattern="[0-9]+\.?[0-9]*" className="form-control" />
                </div>  
           
          </div>
          <div className="row">

                <div className="col form-group">
                    <p id="p">From</p>   
                    <input v-model="prices"  type="text" pattern="[0-9]+\.?[0-9]*" className="form-control" />       
                </div> 
                 
                <div className="col form-group">
                    <p id="p">To</p>   
                    <input v-model="prices"  type="text" pattern="[0-9]+\.?[0-9]*" className="form-control" />
                </div> 

               <div  className="col form-group">
                    <p id="p">&nbsp;</p>   
                    <button >add</button>
                </div> 
           
          </div>
          <hr/>
          <br/> <br/>

         
                    
                </div>
    </div>
  );
};
  
export default UpdateEducation;