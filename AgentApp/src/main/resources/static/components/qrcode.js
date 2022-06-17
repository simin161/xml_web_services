Vue.component('qrcode', {
	data: function(){
		return{

		}
	},
template: `
		<div>
            <div id="qr">
                <p>
                    Scan this Barcode using Google Authenticator app on your phone
                    to use it later in login
                </p>
                <img th:src="${param.qr[0]}"/>
            </div>
		</div>
		`
	,

	computed : {

    },
    methods : {

    },
    mounted(){

    }

});