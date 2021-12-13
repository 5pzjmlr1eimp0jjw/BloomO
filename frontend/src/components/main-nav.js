import {NavLink} from "react-router-dom";
import React from "react";
import './home.css'
import  './Nav.css'

const MainNav = () => (
  <div className="navbar-nav-mr-auto">
    <NavLink
      to="/"
      exact
      className="nav-link"
      activeClassName="router-link-exact-active"
    >
      Home
    </NavLink>
    <NavLink
      to="/plant-lookup/home"
      exact
      className="nav-link"
      activeClassName="router-link-exact-active"
    >
      Plant Lookup
    </NavLink>
    <NavLink
      to="/zone-lookup"
      exact
      className="nav-link"
      activeClassName="router-link-exact-active"
    >
      Zone Lookup
    </NavLink>
    <NavLink
      to="/profile"
      exact
      className="nav-link"
      activeClassName="router-link-exact-active"
    >
      Profile
    </NavLink>
  </div>
);

export default MainNav;
