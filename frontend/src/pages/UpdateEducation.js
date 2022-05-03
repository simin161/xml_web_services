import React, { useState,useEffect} from 'react';
import axios from 'axios';
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import {useNavigate} from "react-router-dom";
const UpdateEducation = () => {
         const navigate=useNavigate()
         const [startDate, setStartDate] = useState();
         const [endDate, setEndDate] = useState();
         const email= window.location.pathname.split('/')[2]


         function update(){
       
       
            var school = document.getElementById("school").value;
            var degree = document.getElementById("degree").value;
            var field = document.getElementById("field").value;
            var from = document.getElementById("from").value;
            var to = document.getElementById("to").value;
            
            axios.post(process.env.REACT_APP_BACKEND_URL + 'education',
             {
                                            'email' : email,
                                            'school' : school,
                                            'degree' : degree,
                                            'fieldOfStudy' : field,
                                            'from' : from,
                                            'to' : to
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
           <h3>Education</h3>
            <hr/>
               
           <div className="row">
                <div className="col form-group">
                    <p id="p">School</p>
                    <input id="school" type="text"  className="form-control" />
                </div>

                <div className="col form-group">
                    <p id="p">Degree</p>   
                    <input id="degree"  type="text" pattern="[0-9]+\.?[0-9]*" className="form-control" />       
                </div> 
                 
                <div className="col form-group">
                    <p id="p">Field of study</p>   
                    <input id="field"  type="text" pattern="[0-9]+\.?[0-9]*" className="form-control" />
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
  
export default UpdateEducation;