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
    			    keyUsage: "",
    			    issuerEmail: "",
    			    commonName: "",
    			    givenName: "",
    			    surname: "",
    			    organizationalUnitName: "",
    			    organizationEmail: "",
    			    country: "",
    			    alias: ""
    			},
    			password: "",
    			certificates: [],
    			hasCA : null,
    			flag: null,
    			selected: 'CRL_SIGN',
    			selectedType: 'ROOT'
    		}
    	},
    template: `
    	 <div>
    	      <div style="margin-left: auto; margin-right:auto; width:30%;">
    	        <input type="button" value="Sign out" @click="signOut"/>
    	        <input type="button" value="Create certificate" @click="showCreateFun"/>
    	        <input type="button" value="Show all certificates" @click="showAllCertsFun"/>
    	        <input type="button" value="Check validity" @click="check"/>
    	      </div>
              <div style="margin-left: auto; margin-right:auto; width:30%;" v-show="showCreate != 0 && hasCA || user.userType==='ADMIN'">
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
                						    <select v-model="certificate.receiver" id="receiver" disabled="true" style="width:100%;" @change="fillInputFields">
                						        <option v-for="us in users" :value="us" v-if="us.userType !== 'ADMIN' && us.email !== user.email">
                						            {{us.email}}
                						        </option>
                						    </select>
                						</td>
                					</tr>
                					<br/>
                					<tr>
                					    <td> Given name: </td>
                					    <td><input type="text" disabled="disable" v-model="certificate.givenName"/></td>
                					    <td>Surname: </td>
                					    <td><input type="text" disabled="disable" v-model="certificate.surname"/></td>
                					    <td>Organization: </td>
                					    <td><input type="text" disabled="disable" v-model="certificate.organization"/></td>
                					    <td>Organizational unit name: </td>
                					    <td><input type="text" disabled="disable" v-model="certificate.organizationalUnitName"/></td>
                					    <td>Organization email: </td>
                					    <td><input type="text" disabled="disable" v-model="certificate.organizationEmail"/></td>
                					    <td>Country:</td>
                					    <td><input type="text" disabled="disable" v-model="certificate.country"/></td>
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
                                           <select v-model="selectedType" style="width:100%" @change="changeUserIfRoot" >
                                                <option v-if="user.userType === 'ADMIN'" value="ROOT">Root</option>
                                                <option value="INTERMEDIATE">Intermediate</option>
                                                <option value="END">End entity</option>
                                           </select>
                                         </td>
                                    </tr>
                                    <tr v-if="selectedType != 'ROOT' && selectedType != ''">
                                        <td>Certification path: </td>
                                        <td>
                                            <select v-model="certificate.path">
                                                <option v-for="c in certificates" :value="c.serialNumber">{{c.alias}} {{c.serialNumber}}</option>
                                            </select>
                                        </td>
                                    </tr>
                                    <br/>
                                    <tr>
                                        <td>Alias: </td>
                                        <td><input type="text" v-model="certificate.alias"/></td>
                                    </tr>
                                    <br/>
                                    <tr>
                                        <td>Common name: </td>
                                        <td><input type="text" v-model="certificate.commonName"/></td>
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
                                            <select v-model="selected">
                                                <option value="CRL_SIGN" >CRL_SIGN</option>
                                                <option value="DATA_ENCIPHERMENT">DATA_ENCIPHERMENT</option>
                                                <option value="DECIPHER_ONLY">DECIPHER_ONLY</option>
                                               <!-- <option value="DIGITAL_SIGNATURE">DIGITAL_SIGNATURE</option>-->
                                                <option value="ENCIPHER_ONLY">ENCIPHER_ONLY</option>
                                                <option value="KEY_AGREEMENT">KEY_AGREEMENT</option>
                                                <option value="KEY_CERT_SIGN">KEY_CERT_SIGN</option>
                                                <option value="NON_REPUDIATION">NON_REPUDIATION</option>
                                            </select>
                                        </td>
                                    </tr>
                                    <br/>
                					<tr>
                						<td colSpan="2" text-align="center"><input type="button" :disabled="isCComplete" @click="createCertificate" value="neki tekst"></input></td>
                					</tr>
                </table>
              </div>
              <transition name="fade" @enter="enter">
              <div v-if="showCreate != 0 && !hasCA && user.userType!=='ADMIN'" style="margin-left: auto; margin-right:auto; width: 41%; margin-top: 20px" class="animated fadeIn">
                <img src="One-Does-Not-Simply.jpg"/>
              </div>
              </transition>
              <div style="margin-left: auto; margin-right:auto; width:30%;" v-show="showAllCerts != 0">
                <div v-if="showAllCerts != 0">
                <div v-for="c in certificates" >
                    <div style="border: 1px solid black; padding-bottom: 25px;">
                        <p>Version: {{c.version}}</p>
                        <p>Alias: {{c.alias}}</p>
                        <p>IssuerDN: {{c.issuerDN}}</p>
                        <p>SubjectDN: {{c.subjectDN}}</p>
                        <p>Serial Number: {{c.serialNumber}}</p>
                        <p>Not valid before: {{c.dateFrom}}</p>
                        <p>Not valid after: {{c.dateTo}}</p>
                        <p>Public key: {{c.publicKey}}</p>
                        <p>Signature Algorithm: {{c.signatureAlg}}</p>
                        <p>Signature: {{c.signature}}</p>
                        <input type="button" value="Invalidate" @click="invalidate(c)"/>
                        <input type="button" value="Show details" @click="showDetails(c)"/>
                    </div>
                </div>
                <div v-if="!certificates.length" style="margin-left: auto; margin-right:auto; width: 41%; margin-top: 20px">
                     <img src="john.gif"/>
                </div>
                </div>
              </div>
         </div>
        `
    ,
    computed:{
        isCComplete(){
            validText = /^[a-zA-Z]+$/.test(this.certificate.purpose) &&
              /^[a-zA-Z]+$/.test(this.certificate.alias) &&
              /^[a-zA-Z]+$/.test(this.certificate.commonName);
            validNonEmpty = /\S/.test(this.certificate.validFrom) &&
                /\S/.test(this.certificate.validTo);
            if(validText && validNonEmpty)
                return false;

            return true;
        }
    },
    methods:{
        signOut : function(){
            axios.get("/signOut")
                 .then(response=>{
                    if(response.data == true){
                        localStorage.setItem("logged", "");
                        router.push("/")
                    }
                    else{
                        console.log("ne.")
                    }
                 })
        },
        check : function(){
            axios.get("/checkAndInvalidate")
                 .then(response=> (console.log(response.data)))
        }
        ,
        showDetails : function(c){
            axios.post("/saveChoosenCert", c)
                 .then(response => (router.push("/detailsScreen")))
        },
        changeUserIfRoot : function(){
            if(this.selectedType === 'ROOT'){
                this.certificate.receiver = this.user;
                 this.certificate.givenName = this.certificate.receiver.firstName;
                 this.certificate.surname = this.certificate.receiver.lastName;
                 this.certificate.organization = this.certificate.receiver.organizationName;
                 this.certificate.organizationalUnitName = this.certificate.receiver.organizationUnit;
                 this.certificate.country = this.certificate.receiver.countryId;
                 this.certificate.organizationEmail = this.certificate.receiver.email;
                 document.getElementById('receiver').disabled = true;
            }else{
                document.getElementById('receiver').disabled = false;
            }
        }
        ,
        showCreateFun : function(){

            this.showCreate = 1;
            this.showAllCerts = 0;

            axios.get("/getUsers")
                 .then(response => {this.users = response.data})

            axios.get("/getAllCertsForDropDown")
                 .then(response=>(this.certificates = response.data))

            axios.get("/checkCA")
                 .then(response => (this.hasCA = response.data))

        },
        fillInputFields : function(){
            this.certificate.givenName = this.certificate.receiver.firstName;
            this.certificate.surname = this.certificate.receiver.lastName;
            this.certificate.organization = this.certificate.receiver.organizationName;
            this.certificate.organizationUnitName = this.certificate.receiver.organizationUnit;
            this.certificate.country = this.certificate.receiver.countryId;
            this.certificate.organizationEmail = this.certificate.receiver.email
        },
        showAllCertsFun : function(){

            this.showAllCerts = 1;
            this.showCreate = 0;
             axios.post("/getAllCerts", this.password)
                        .then(response=>(this.certificates = response.data))
        },
        createCertificate : function(){
            this.certificate.issuerEmail = this.user.email;
            this.certificate.receiver = this.certificate.receiver.email;
            this.certificate.keyUsage = this.selected;
            this.certificate.type = this.selectedType;
            if(this.certificate.type === 'ROOT'){
                this.certificate.path = 0;
            }
            axios.post("/createCertificate", this.certificate)
                  .then(response=>(console.log(response.data)))
        },
        enter: function(el, done) {

        		      var that = this;
        },
        invalidate : function(c) {

            axios.post("/invalidateCertificate", c)
            .then(response=>(console.log(response.data)))
        }
    },

    mounted(){
     if(localStorage.getItem("logged") == '')
        router.push('/')

     axios.get("/getLoggedUser", this.password)
          .then(response => {this.user = response.data; localStorage.setItem("logged", "true");});
    }
});
