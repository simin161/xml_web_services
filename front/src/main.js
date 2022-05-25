import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
//bootstrap
import "bootstrap/dist/css/bootstrap.min.css";
import 'bootstrap';

//alertovi
import VueSweetalert2 from 'vue-sweetalert2';
import 'sweetalert2/dist/sweetalert2.min.css'; 


createApp(App).use(App).use(router).use(VueSweetalert2).mount('#app');



