// src/components/nav-bar.js

import React from "react";

import MainNav from "./main-nav";
import AuthNav from "./auth-nav";
// import './Nav.css'

const NavBar = () => {
  return (
      // <div className= "home">
          <div className="nav-container mb-3">
              <nav className="navbar navbar-expand-md navbar-light bg-light">
                  <div className="container">
                      <div className="navbar-brand logo" />
                      <MainNav />
                      <AuthNav />
                  </div>
              </nav>
      </div>
    // </div>
  );
};

export default NavBar;
