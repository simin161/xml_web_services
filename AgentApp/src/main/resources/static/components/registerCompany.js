Vue.component('registerCompany', {
	data: function(){
		return{
            company : {
                name : "",
                field: "",
                description: "",
                contactInfo: ""
            }
		}
	},
template: `
		<div>
        <nav  class="navbar navbar-fixed-top navbar-expand"  style="background-color: #3e214f; list-style: none; box-shadow: rgba(0, 0, 0, 0.15) 1.95px 1.95px 2.6px; ">
        <div class="container-fluid" style="background-color: #3e214f; text-align: right">
        <a class="navbar-brand"   >
        <img src="../images/cover.png" alt="" width="200" height="80" >
        </a>
        <button class="btn" id="buttonPurple"  @click="logOut">
        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-arrow-right" viewBox="0 0 16 16">
  <path fill-rule="evenodd" d="M1 8a.5.5 0 0 1 .5-.5h11.793l-3.147-3.146a.5.5 0 0 1 .708-.708l4 4a.5.5 0 0 1 0 .708l-4 4a.5.5 0 0 1-.708-.708L13.293 8.5H1.5A.5.5 0 0 1 1 8z"/>
</svg> Log out</button>
        </div>
      </nav>
      <br>
      <br>

            <div style="padding:2%">
            <br>
            <h1>Register company</h1>
            <br>
            <label><b>Company name</b></label>
            <input  class="form-control" type="text" v-model="company.name" placeholder="Company name"/>
            <label><b>Field</b></label>
            <input class="form-control" type="text" v-model="company.field" placeholder="Field"/>
            <label><b>Contact information</b></label>
            <textarea  class="form-control" v-model="company.contactInfo" placeholder="Contact information"/>
            <label><b>Description</b></label>
            <textarea  class="form-control" v-model="company.description" placeholder="Description"/>
            <br>
            <button :disabled="isEditComplete" class="btn" id="buttonPurple"  @click="registerCompany">Register company</button>
             </div>
		</div>
		`
	,

	computed : {
        isEditComplete(){
           let validCName = /^[ a-zA-Z0-9]+$/.test(this.company.name);
           let validContactInfo = /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(.\w{2,3})+$/.test(this.company.contactInfo) || /^[0-9{8}]+$/.test(this.company.contactInfo);
           let validField = /^[a-zA-Z]+$/.test(this.company.field);
           let validDesc = /^[ a-zA-Z.,]+$/.test(this.company.description);
           if(validCName && validContactInfo && validField && validDesc)
               return false
           return true;
        }
    },
    methods : {
        registerCompany : function(){
             axios.defaults.headers.common["Authorization"] =
                                       localStorage.getItem("agentUser");
            axios.post("/api/registerCompany", this.company)
                 .then((response) => {
                     console.log(response.data)
                     router.push("/firstPage")
                    });
        },
        logOut : function(){
            localStorage.setItem("agentUser", "");
                        router.push("/");
        }
    },
    mounted(){

    }

});