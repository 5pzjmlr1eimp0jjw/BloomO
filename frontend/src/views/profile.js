// src/views/profile.js

import React, {useEffect, useState} from "react";
import {Link} from "react-router-dom";
import { useAuth0 } from "@auth0/auth0-react";
import axios from "axios";
import {AwesomeButton} from "react-awesome-button";
import TextBox from "../components/TextBox";
import ReactFlexyTable from "react-flexy-table"
import "react-flexy-table/dist/index.css"
//import "./AwesomeButton.css";

const Profile = () => {
    const { user } = useAuth0();
    const { name, picture, email, sub} = user;
    const [plants, setPlants] = useState([]);
    const [recommendedPlants, setRecommendedPlants] = useState([]);
    const [hardinessZone, setHardinessZone] = useState("");
    const [textBox, setTextBox] = useState([]);
    const plantList = [];
    const [recommendationList, setRecommendationList] = useState([]);


    useEffect(() => {
        console.log(recommendedPlants[0]);
    },[recommendedPlants]);


    useEffect(()=>{
        requestUsersPlants(false);
    },[]);

    const requestUsersPlants = (isRecommendation) =>{

        const toSend = {
            isRecommendation: isRecommendation,
            zone: hardinessZone,
            userID: sub
        }

        const config = {
            headers: {
                "Content-Type": "application/json",
                'strict-origin-when-cross-origin': '*',
            }
        }

        axios.post(
            "http://localhost:4567/users-plant",
            toSend,
            config
        )
            .then(response => {
                if (isRecommendation) {
                    if (plants.length>0) {
                        console.log(getArray(`${response.data.recommendations}`));
                        const array = getArray(`${response.data.recommendations}`);
                        if (!(array[0] === "")) {
                            setRecommendedPlants(array);
                        } else {
                            setRecommendedPlants(["No Recommendation Generated"]);
                        }
                    } else {
                        setRecommendedPlants(["No Recommendation Generated"]);
                    }
                } else {
                    const array = getArray(`${response.data.usersPlant}`);
                    if (!(array[0]=== "")) {
                        setPlants(array);
                    }
                }
            })
            .catch(function (error){
                console.log(error);
            })

    }

    function getArray(string){
        return string.replace(/"+/g, '').replace("[","").replace("]","").split(",");
    }

    if (plants.length > 0){
        plants.forEach(element =>
            plantList.push(
                <div>
                    <Link to={getLink(getLatinName(element))} className="PlantLink">{element}</Link>
                </div>
            ));
    }

    useEffect(()=>{
        if (recommendedPlants.length > 0){
            recommendedPlants.forEach(element =>
                //console.log(getLatinName(element)));
                recommendationList.push(
                    <div>
                        <Link to={getLink(getLatinName(element))} className="PlantLink">{element}</Link>
                    </div>
                ));}
    }, [recommendedPlants]);


    function getLink(string){
        string = string.replace(/[\[\]']+/g, '');
        console.log(string);
        return `/plant-lookup/${string}`
    }

    function getLatinName(string) {
        return string.split("(")[1].replace(")","");
    }

    function handleRecommendations(){
        popupTextBox();
        //requestUsersPlants(true);
    }

    function popupTextBox() {
        if (plants.length > 0){
            setTextBox(
            [<TextBox type="text" text="Hardiness Zone" change={(e) => setHardinessZone(e.target.value)} />,
                <AwesomeButton className = "aws-btn" ripple ={true} onPress={()=>requestUsersPlants(true)}>Search</AwesomeButton>,
                <br/>, <br/>, <h5>Recommended Plants:</h5>]
        );} else {
            setTextBox(<p>Like Some Plants to Generate Recommendations!</p>);
        }
    }


    return (
        <div>
            <div className="row align-items-center profile-header">
                <div className="col-md-2 mb-3">
                    <img
                        src={picture}
                        alt="Profile"
                        className="rounded-circle img-fluid profile-picture mb-3 mb-md-0"
                    />
                </div>
                <div className="col-md text-center text-md-left">
                    <h2>{name}</h2>
                    {/*<AwesomeButton onPress={() => requestUsersPlants()}>Show Plants</AwesomeButton>*/}
                    <p className="lead text-muted">{email}</p>
                    <br/>
                    <h5>Liked Plants:</h5>
    
                    <ul>{plantList}</ul>
                    <br/>
                    <AwesomeButton className = "aws-btn" ripple ={true} onPress={()=>handleRecommendations()}>Generate Recommendations</AwesomeButton>
                    <ul>{textBox}</ul>
                    <ul>{recommendationList}</ul>
                </div>
            </div>
        {/*    <div className="row">*/}
        {/*/!*<pre className="col-12 text-light bg-dark p-4">*!/*/}
        {/*/!*  {JSON.stringify(user, null, 2)}*!/*/}
        {/*/!*</pre>*!/*/}
        {/*        /!*<pre className="col-12 text-light bg-dark p-4">*!/*/}
        {/*        /!*  /!*{JSON.stringify(user, null, 2)}*!/*!/*/}
        {/*        /!*</pre>*!/*/}
        {/*    </div>*/}
        </div>
    );
};

export default Profile;