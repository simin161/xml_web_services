import React from 'react';
import { BrowserRouter, Route,Routes } from 'react-router-dom';
import SignUp from './pages/SignUp';
import SignIn from './pages/SignIn';
import Home from './pages/Home';

const Main = () => {
  return (
        <Routes> {/* The Switch decides which component to show based on the current URL.*/}
            <Route exact path='/' element={<Home/>}></Route>
            <Route exact path='/signup' element={<SignUp/>}></Route>
            <Route exact path='/signin' element={<SignIn/>}></Route>
        </Routes>
  );
}

export default Main;