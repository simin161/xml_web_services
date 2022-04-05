Vue.component('firstpage', {
	data: function(){
    		return{
    			showPage: 0,
    			user: {
    			    firstName: "",
                    lastName: "",
                    organizationName: "",
                    organizationUnit: "",
                    countryId: "",
                    email: ""
    			},
    			logUser: {
    			    email: "",
    			    password: "",
    			},
    			showCreate: 0,
    			showAllCerts: 0
    		}
    	},
    template: `
    	 <div>
    	      <div style="margin-left: auto; margin-right:auto; width:30%;">
    	        <input type="button" value="Sign out" @click="signOut"/>
    	        <input type="button" value="Create certificate" @click="showCreateFun"/>
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
                						<td> <input type="text"  placeholder="Ovde treba ime i prezime ko ga izdaje?"> </input> </td>
                					</tr>
                					<br/>
                					<tr>
                						<td>Certificate issued to:</td>
                						<td><input type="text"  placeholder="Za koga izdajemo?"></input></td>
                					</tr>
                					<br/>
                					<tr>
                						<td>Valid from:</td>
                						<td><input type="text"  placeholder="neki tekst"></input></td>
                					</tr>
                					<br/>
                					<tr>
                						<td>Valid to:</td>
                						<td><input type="text"  placeholder="neki tekst"></input></td>
                					</tr>
                					<br/>
                					<tr>
                						<td>Certificate purpose: </td>
                						<td><input type="text"  placeholder="neki tekst"></input></td>
                					</tr>
                					<br/>
                					<tr>
                                        <td>Certificate status: </td>
                                        <td><input type="text"  placeholder="neki tekst"></input></td>
                                    </tr>
                                    <br/>
                                    <tr>
                                        <td>Public key: </td>
                                        <td><input type="text"  placeholder="neki tekst"></input></td>
                                    </tr>
                                    <br/>
                                    <tr>
                                        <td>Revocation status: </td>
                                        <td><input type="text"  placeholder="neki tekst"></input></td>
                                    </tr>
                                    <br/>
                                    <tr>
                                        <td>Signature: </td>
                                        <td><input type="text"  placeholder="neki tekst"></input></td>
                                    </tr>
                                    <br/>
                					<tr>
                						<td colSpan="2" text-align="center"><input type="button" @click="createCertificate" value="neki tekst"></input></td>
                					</tr>

                </table>
              </div>
              <div style="margin-left: auto; margin-right:auto; width:30%;" v-show="showAllCerts != 0">
                <p>SVI CERTS</p>
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

        },

        showAllCertsFun : function(){

            this.showAllCerts = 1;
            this.showCreate = 0;

        },

        createCertificate : function(){



        }
    },

    mounted(){


    }
});
