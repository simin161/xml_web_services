import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import SignIn from '../views/SignIn.vue'
import EditProfile from '../views/EditProfile.vue'
import ProfilePage from '../views/ProfilePage.vue'
import AccountSettings from '../views/AccountSetting.vue'
import UpdateEducation from '../views/UpdateEducation.vue'
import UpdateWorkExperience from '../views/UpdateWorkExperience.vue'

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
  {
    path: '/editProfile',
    name: 'EditProfile',
    component: EditProfile
  },
  {
    path: '/profilePage',
    name: 'ProfilePage',
    component: ProfilePage
  },
  {
    path: '/accountSettings',
    name: 'AccountSettings',
    component: AccountSettings
  },
  {
    path: '/updateEducation',
    name: 'UpdateEducation',
    component: UpdateEducation
  },
  {
    path: '/updateWorkExperience',
    name: 'UpdateWorkExperience',
    component: UpdateWorkExperience
  }
   
]

const router = createRouter({
  
  history: createWebHistory(),
  routes,
})

export default router





