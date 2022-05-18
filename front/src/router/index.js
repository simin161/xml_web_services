import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import SignIn from '../views/SignIn.vue'

const routes = [
  
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/signIn',
    name: 'SignIn',
    component: SignIn
  },
  
   
]

const router = createRouter({
  
  history: createWebHistory(),
  routes,
})

export default router





