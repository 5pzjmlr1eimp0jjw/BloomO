import React from "react";
import './home.css'

const logo = "https://i.ibb.co/zJ0LwrM/BloomO-2.png";

const Hero = () => (
    <html>

    <div className="text-center hero" >
        <img src="https://i.ibb.co/zJ0LwrM/BloomO-2.png" alt="Screen-Shot-2021-12-11-at-7-14-24-PM"  alt="BloomO logo" width="300" />
        <h1 className="mb-4">BloomO</h1>
        <h4> Welcome to BloomO!</h4>

        <p className="lead">
            Our app is meant to provide you with a seamless garden experience. Start by logging in in the top left courner.
            Then use the various tools in the hotbar to build your garden's roots!
        </p>
    </div>
    </html>

);

export default Hero;
