Vue.component('details-screen', {
	data: function(){
    		return{
    			c:null,
    			certsAbove: [],
    			pdf: null
    		}
    	},
    template: `
            <div>
                <div>
                    <input type="button" value="Create pdf" @click="createPdf"/>
                    <a :href="pdf">Show pdf</a>
                    <input type="button" value="See certs above" @click="getCertsAbove"/>
                </div>
                <br/>
                <div>
                    <div v-for="c in certsAbove">
                        {{c.alias}} {{c.serialNumber}}
                    </div>
                <div>
                <br/>
                <p>Details: </p>
                <div style="padding-left: 20px">
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
                </div>
            </div>
        `
    ,
    methods:{
        getCertsAbove : function(){
            axios.get("/getCertsAbove", this.c.serialNumber)
                 .then(response=>(this.certsAbove = response.data))
        },
        createPdf : function(){
            axios.post("/getPdf")
                 .then(response => (response = response.data));
        }
    },
    mounted(){
            axios.get("/getChoosenCert")
                 .then(response => {
                    this.c = response.data;
                    this.pdf = this.c.serialNumber + ".pdf";
                    console.log(this.pdf);

                  });
    }
});
