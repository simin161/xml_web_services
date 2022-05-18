<template>
<div className="card edit-profile-card">
                <div className="card-body">
                    <div className="inputFieldsDiv">
                    <h3>Personal infromation</h3>
                    <hr/>
                        <div className="row">
                        <div className="col-lg-3">
                        <p>First name</p>
                        </div>
                        <div className="col-lg-9">
                        <input id="firstName" type="text" required/>
                        </div>
                        </div>
                        <div className="row">
                        <div className="col-lg-3">
                        <p>Last name</p>
                        </div>
                        <div className="col-lg-9">
                        <input id="lastName" type="text" required/>
                        </div>
                        </div>
                        <div className="row">
                        <div className="col-lg-3">
                        <p>Birthday</p>
                        </div>
                        <div className="col-lg-9">
                        <input type="date" id="birthday" />
                        </div>
                        </div>
                        <div className="row">
                        <div className="col-lg-3">
                        <p>Gender</p>
                        </div>
                        <div id="molimTe" className="col-lg-9">
                                <select id="gender">
                                    <option>
                                        MALE
                                    </option>
                                    <option>
                                        FEMALE
                                    </option>
                                </select>
                        </div>
                        </div>
                        <div className="row">
                        <div className="col-lg-3">
                        <p>Phone num</p>
                        </div>
                        <div className="col-lg-9">
                        <input id="phone" type="text" />
                        </div>
                        </div>
                        <div className="row">
                        <div className="col-lg-3">
                        <p>Email</p>
                        </div>
                        <div className="col-lg-9">
                        <input id="email" type="text" disabled/>
                        </div>
                        </div>
                        <div className="row">
                        <div className="col-lg-3">
                        <p>Username</p>
                        </div>
                        <div className="col-lg-9">
                        <input id="username" type="text" disabled/>
                        </div>
                        </div>  
                        <div className="row">
                        <div className="col-lg-3">
                        <p>Private</p>
                        </div>
                        <div id="molimTe" className="col-lg-9">
                                <select id="private">
                                    <option>
                                        YES
                                    </option>
                                    <option>
                                        NO
                                    </option>
                                </select>
                        </div>
                        </div>                   
                        </div>
                        <h3>Biography</h3>
                        <hr/>
                        <textarea id="biography"></textarea>
                        <h3>Interests</h3>
                        <hr/>
                        <textarea id="interests"></textarea>

                        <h3>Skills</h3>
                        <hr/>
                        <textarea id="skills"></textarea>

                        <button @click="update">Save changes</button>
                  
                </div>
    </div>
</template>
<script>
 import axios from "axios";
  export default{
  data() {
    return {
    };
  },
  mounted() {
    
  },
  methods: {    
  }
};
</script>
<style>
</style>