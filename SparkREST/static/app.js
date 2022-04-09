
const homePage = { template: '<homepage></homepage>' }
const firstPage = {template: '<firstpage></firstpage>'}
const details = {template: '<details-screen></details-screen>'}
const router = new VueRouter({
	  mode: 'hash',
	  routes: [
	    { path: '/', component: homePage},
        { path: '/firstPage', component: firstPage},
        { path: '/detailsScreen', component: details}
	  ]
});

var app = new Vue({
	router,
	el: '#app'
});