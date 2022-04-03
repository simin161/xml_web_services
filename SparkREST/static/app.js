
const homePage = { template: '<homepage></homepage>' }
const firstPage = {template: '<firstpage></firstpage>'}
const router = new VueRouter({
	  mode: 'hash',
	  routes: [
	    { path: '/', component: homePage},
        { path: '/firstPage', component: firstPage}
	  ]
});

var app = new Vue({
	router,
	el: '#app'
});