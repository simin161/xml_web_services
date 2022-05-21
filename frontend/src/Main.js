import React from 'react';
import { BrowserRouter, Route,Routes, NavLink, HashRouter } from 'react-router-dom';
import SignIn from './pages/SignIn';
import Home from './pages/Home';
import AccountSettings from './pages/AccountSettings';
import ProfilePage from './pages/ProfilePage';
import EditProfile from './pages/EditProfile'
import UpdateEducation from './pages/UpdateEducation'
import UpdateWorkExperience  from './pages/UpdateWorkExperience';
import CreatePost from './pages/CreatePost';

const Main = () => {
  return (
        <Routes> {/* The Switch decides which component to show based on the current URL.*/}
            <Route exact path='/' element={<Home/>}></Route>
            <Route exact path='/signin' element={<SignIn/>}></Route>
            <Route exact path='/accountSettings' element={<AccountSettings/>}></Route>
            <Route exact path='/profilePage/:email' element={<ProfilePage/>}></Route>
            <Route exact path='/editProfile/:email' element={<EditProfile/>}></Route>
            <Route exact path='/updateEducation/:email' element={<UpdateEducation/>}></Route>
            <Route exact path='/updateWorkExperience/:email' element={<UpdateWorkExperience/>}></Route>
            <Route exact path='/newPost' element={<CreatePost/>}></Route>
        </Routes>
  );
}

export default Main;