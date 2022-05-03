import React, { useState,useEffect} from 'react';
import axios from 'axios';
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import {useNavigate} from "react-router-dom";
const UpdateWorkExperience = () => {
  const navigate=useNavigate()
  const [startDate, setStartDate] = useState();
         const [endDate, setEndDate] = useState();
  const email= window.location.pathname.split('/')[2]

  function update(){
 
    var workPlace = document.getElementById("workPlace").value;
    var workTitle = document.getElementById("workTitle").value;
    var from = document.getElementById("from").value;
    var to = document.getElementById("to").value;

   

      axios.post(process.env.REACT_APP_BACKEND_URL +'workExperiences', {
                                    'workPlace' : workPlace,
                                    'workTitle' : workTitle,
                                    'from' : from,
                                    'to' : to,
                                    'email' : email
                                  })
      .then(function (response) {
        console.log(response);
        navigate("/profilePage/"+email)
      })
      .catch(function (error) {
        console.log(error);
      });
   
  }
  return (
    <div className="card edit-profile-card">
        <div className="card-body">
    
           <h3>Work experience</h3>
            <hr/>
               
           <div className="row">
                <div className="col form-group">
                    <p id="p">Work place</p>
                    <input id="workPlace" type="text"  className="form-control" required />
                </div>

                <div className="col form-group">
                    <p id="p">Work title</p>   
                    <input id="workTitle" type="text" className="form-control" required/>       
                </div> 
           
          </div>
          <div className="row">

                <div className="col form-group">
                    <p id="p">From</p>   
                    <DatePicker id="from" dateFormat={"yyyy-MM-dd"} selected={ startDate } onChange={(date)=>setStartDate(date)} ></DatePicker>       
                </div> 
                 
                <div className="col form-group">
                    <p id="p">To</p>   
                    <DatePicker id="to" dateFormat={"yyyy-MM-dd"} selected={ endDate } onChange={(date)=>setEndDate(date)} ></DatePicker>
                </div> 

               <div  className="col form-group">
                    <p id="p">&nbsp;</p>   
                    <button onClick={update}>add</button>
                </div> 
               
          </div>
        
          <hr/>
          <br/> <br/>

                    
                </div>
    </div>
  );
};
  
export default UpdateWorkExperience;