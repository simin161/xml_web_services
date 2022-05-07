import React from 'react';
import Navbar from '../components/Navbar';
const Home = () => {
  return (
    <div
      style={{
        display: 'flex',
        justifyContent: 'Right',
        alignItems: 'Right',
        height: '100vh'
      }}
    >
      <Navbar></Navbar>

    </div>
  );
};
  
export default Home;