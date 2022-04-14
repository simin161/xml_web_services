import React from 'react';
import ReactBootstrap, {Jumbotron, Button, Col, Grid, Panel, FormGroup} from 'react-bootstrap'
import {
  Nav,
  NavLink,
  Bars,
  NavMenu,
  NavBtn,
  NavBtnLink,
} from './NavbarElements';
  
class Navbar extends React.Component {
  render(){
    return (
    <>
      <Nav>
        <Bars />
  
        <NavMenu>
          {/* Second Nav */}
          {/* <NavBtnLink to='/sign-in'>Sign In</NavBtnLink> */}
        </NavMenu>
        <NavBtn>
          <NavBtnLink style={{marginLeft: "750px"}} to='/signin'>Sign In/Sign Up</NavBtnLink>
        </NavBtn>
      </Nav>
    </>
    )
  }
}
  
export default Navbar;