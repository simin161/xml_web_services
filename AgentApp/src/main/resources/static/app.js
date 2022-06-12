const homePage = { template: '<homepage></homepage>' }
const firstPage = { template: '<firstPage></firstPage>'}
const registerCompany = { template: '<registerCompany></registerCompany>'}
const jobOffersForUser = {template: '<jobOffersForUser></jobOffersForUser>'}
const allOffers = {template: '<allOffers></allOffers>'}
const comments = {template: '<comments></comments>'}
const emailForm = {template: '<emailForm></emailForm>'}
const router = new VueRouter({
	  mode: 'hash',
	  routes: [
	    { path: '/', component: homePage},
	    { path: '/firstPage/:id', component: firstPage,
	        children: [
                {
                    path: '/firstPage',
                    component: firstPage
                }
	        ]
	    },
	    { path: '/registerCompany', component: registerCompany},
	    { path: '/myJobOffers', component: jobOffersForUser},
		{ path: '/allOffers', component: allOffers},
		{ path: '/passwordless', component: emailForm,
		    children: [
                 {
                     path: '/forgottenPassword',
                     component: emailForm
                 }
            ]}
	  ]
});

var app = new Vue({
	router,
	el: '#app'
});