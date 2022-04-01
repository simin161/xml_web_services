
const homePage = { template: '<homepage></homepage>' }

const router = new VueRouter({
	  mode: 'hash',
	  routes: [

	    { path: '/', component: homePage}

	  ]
});

var app = new Vue({
	router,
	el: '#app'
});