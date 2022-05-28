const homePage = { template: '<homepage></homepage>' }
const firstPage = { template: '<firstPage></firstPage>'}
const registerCompany = { template: '<registerCompany></registerCompany>'}
const jobOffersForUser = {template: '<jobOffersForUser></jobOffersForUser>'}
const router = new VueRouter({
	  mode: 'hash',
	  routes: [
	    { path: '/', component: homePage},
	    { path: '/firstPage', component: firstPage},
	    { path: '/registerCompany', component: registerCompany},
	    { path: '/myJobOffers', component: jobOffersForUser}
	  ]
});

var app = new Vue({
	router,
	el: '#app'
});