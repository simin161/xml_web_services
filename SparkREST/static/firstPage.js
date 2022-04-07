Vue.component('firstpage', {
	data: function(){
    		return{
    			user: null,
    			users : [],
    			showCreate: 0,
    			showAllCerts: 0,
    			certificate: {
    			    type: "",
    			    receiver: "",
    			    validFrom: "",
    			    validTo: "",
    			    purpose: "",
    			    path: "",
    			    key: "",
    			    keyUsage: ""
    			},
    			password: "",
    			certificates: []
    		}
    	},
    template: `
    	 <div>
    	      <div style="margin-left: auto; margin-right:auto; width:30%;">
    	        <input type="button" value="Sign out" @click="signOut"/>
    	        <input type="button" value="Create certificate" @click="showCreateFun"/>
    	        <input type="button" value="Create self-signed certificate" @click="createSSCert"/>
    	        <input type="button" value="Show all certificates" @click="showAllCertsFun"/>
    	      </div>
              <div style="margin-left: auto; margin-right:auto; width:30%;" v-show="showCreate != 0">
                <p>KREIRAJ</p>
                <table>
                					</br>
                					<tr>
                			   			<th colSpan="3" text-align="center" class="header">Certificate data</th>
                			   		</tr>
                			   		<br/>
                					<tr >
                						<td>Certificate issued by: </td>
                						<td> <input type="text" disabled="true"  v-model="user.email"> </input> </td>
                					</tr>
                					<br/>
                					<tr>
                						<td>Certificate issued to:</td>
                						<td>
                						    <select v-model="certificate.receiver" style="width:100%;">
                						        <option v-for="us in users" :value="us.email">
                						            {{us.email}}
                						        </option>
                						    </select>
                						</td>
                					</tr>
                					<br/>
                					<tr>
                						<td>Valid from:</td>
                						<td><input style="width:97%;" v-model="certificate.validFrom" type="date"></input></td>
                					</tr>
                					<br/>
                					<tr>
                						<td>Valid to:</td>
                						<td><input style="width:97%;" v-model="certificate.validTo" type="date"></input></td>
                					</tr>
                					<br/>
                					<tr>
                						<td>Certificate purpose: </td>
                						<td><input type="text" v-model="certificate.purpose" placeholder="neki tekst"></input></td>
                					</tr>
                					<br/>
                					<tr>
                                        <td>Certificate status: </td>
                                        <td><input type="text" disabled="true"  value="OK"></input></td>
                                    </tr>
                                    <br/>
                                    <tr>
                                         <td>Certificate type: </td>
                                         <td>
                                           <select v-model="certificate.type" style="width:100%">
                                                <option style="display:none;">-----</option>
                                                <option value="ROOT">Root</option>
                                                <option value="INTERMEDIATE">Intermediate</option>
                                                <option value="END">End entity</option>
                                           </select>
                                         </td>
                                    </tr>
                                    <tr v-if="certificate.type != 'ROOT' && certificate.type != ''">
                                        <td>Certification path: </td>
                                        <td>
                                            <select v-model="certificate.path">
                                                <option v-for="c in certificates" :value="c">{{c}}</option>
                                            </select>
                                        </td>
                                    </tr>
                                    <br/>
                                    <tr>
                                        <td>Revocation status: </td>
                                        <td><input type="text" disabled="true"  value="OK"></input></td>
                                    </tr>
                                    <br/>
                                    <tr>
                                        <td>Key usage:</td>
                                        <td>
                                            <select v-model="certificate.keyUsage">
                                                <option value="CRL_SIGN">CRL_SIGN</option>
                                                <option value="DATA_ENCIPHERMENT">DATA_ENCIPHERMENT</option>
                                                <option value="DECIPHER_ONLY">DECIPHER_ONLY</option>
                                                <option value="DIGITAL_SIGNATURE">DIGITAL_SIGNATURE</option>
                                                <option value="ENCIPHER_ONLY">ENCIPHER_ONLY</option>
                                                <option value="KEY_AGREEMENT">KEY_AGREEMENT</option>
                                                <option value="KEY_CERT_SIGN">KEY_CERT_SIGN</option>
                                                <option value="NON_REPUDIATION">NON_REPUDIATION</option>
                                            </select>
                                        </td>
                                    </tr>
                                    <br/>
                					<tr>
                						<td colSpan="2" text-align="center"><input type="button" @click="createCertificate" value="neki tekst"></input></td>
                					</tr>
                </table>
              </div>
              <div style="margin-left: auto; margin-right:auto; width:30%;" v-show="showAllCerts != 0">
                <table>
                    <tr>
                        <td colSpan="2" text-align="center"><input type="button" @click="showAllCertsFunction" value="Load certs"></input></td>
                    </tr>
                </table>
                <div v-for="c in certificates">
                    <div style="border: 1px solid black; padding-bottom: 25px;">
                        <p>{{c}}</p>
                    </div>
                </div>
              </div>
         </div>
        `
    ,
    methods:{
        signOut : function(){
            axios.get("/signOut")
                 .then(response=>{
                    if(response.data == true){
                        router.push("/")
                    }
                    else{
                        console.log("ne.")
                    }
                 })
        },
        showCreateFun : function(){

            this.showCreate = 1;
            this.showAllCerts = 0;

            axios.get("/getUsers")
                 .then(response => {this.users = response.data})

            axios.get("/getAllCertsSerNums", this.password)
                 .then(response=>(this.certificates = response.data))

        },

        showAllCertsFun : function(){

            this.showAllCerts = 1;
            this.showCreate = 0;

        },

        createCertificate : function(){
            axios.post("/createCertificate", this.certificate)
                 .then(response=>(console.log(response.data)))
        },
        createSSCert :function(){
            axios.post("/createSSCertificate")
                 .then(response=>(console.log(response.data)))
        },

        showAllCertsFunction : function(){

            axios.post("/getAllCerts", this.password)
            .then(response=>(this.certificates = response.data))

        }
    },

    mounted(){
            axios.get("/getLoggedUser", this.password)
                 .then(response => {this.user = response.data});
    }
});
