const homePage = { template: '<homepage></homepage>' }
const firstPage = { template: '<firstPage></firstPage>'}
const registerCompany = { template: '<registerCompany></registerCompany>'}
const jobOffersForUser = {template: '<jobOffersForUser></jobOffersForUser>'}
const allOffers = {template: '<allOffers></allOffers>'}
const comments = {template: '<comments></comments>'}
const router = new VueRouter({
	  mode: 'hash',
	  routes: [
	    { path: '/', component: homePage},
	    { path: '/firstPage', component: firstPage},
	    { path: '/registerCompany', component: registerCompany},
	    { path: '/myJobOffers', component: jobOffersForUser},
		{ path: '/allOffers', component: allOffers},
	  ]
});

var app = new Vue({
	router,
	el: '#app'
});