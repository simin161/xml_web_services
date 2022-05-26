import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import SignIn from '../views/SignIn.vue'
import EditProfile from '../views/EditProfile.vue'
import ProfilePage from '../views/ProfilePage.vue'
import AccountSettings from '../views/AccountSetting.vue'
import UpdateEducation from '../views/UpdateEducation.vue'
import UpdateWorkExperience from '../views/UpdateWorkExperience.vue'
import YetAnotherEmailForm from '../views/YetAnotherEmailForm.vue'
import PasswordChange from '../views/PasswordChange.vue'
import Homepage from '../views/Homepage.vue'
import CreateNewPost from  '../views/CreateNewPost.vue'
import ShowProfile from '../views/ShowProfile.vue'
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
    path: '/profilePage/:id',
    name: 'ProfilePage',
    component: ProfilePage,
    children: [

      {
        path: '/profilePage',
        component: ProfilePage
      }

    ]
  },
  {
    path: '/showProfile/:id/:username',
    name: 'ShowProfile',
    component: ShowProfile,
    children: [

      {
        path: '/showProfile/:username',
        component: ShowProfile
      }

    ]
  },
  {
    path: '/homepage/:id',
    name: 'Homepage',
    component: Homepage,
    children: [

      {
        path: '/homepage',
        component: Homepage
      }

    ]
  },
  {
    path: '/createNewPost/:id',
    name: 'CreateNewPost',
    component: CreateNewPost,
    children: [

      {
        path: '/createNewPost',
        component: CreateNewPost
      }

    ]
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
  },
  {
    path: '/passwordless',
    name: 'YetAnotherEmailForm',
    component: YetAnotherEmailForm,
    children: [

      {
        path: '/forgottenPassword',
        component: ProfilePage
      }

    ]
  },
  {
    path: '/changePassword',
    name: 'PasswordChange',
    component: PasswordChange
  }
]

const router = createRouter({
  
  history: createWebHistory(),
  routes,
})

export default router





