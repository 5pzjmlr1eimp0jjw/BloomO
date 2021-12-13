import React, { useState, useEffect } from 'react';
import { AwesomeButton } from "react-awesome-button";
import "react-awesome-button/dist/styles.css";
import axios from 'axios';
import TextBox from '../components/TextBox';
import MyModal from './Modal/Modal';
import './views.css';
import Table from "./ZoneTable";


const ZoneLookup = () => {
    const [zipcode, setZipcode] = useState(0);
    const [zoneID, setZoneID] = useState(0);
    const [firstFrost, setFirstFrost] = useState(0);
    const [lastFrost, setLastFrost] = useState(0);
    const [growingDays, setGrowingDays] = useState(0);
    const [tempLowRange, setTempLowRange] = useState(0);
    const [listS, setListS] = useState("");
    const [flag, setFlag] = useState(0);
    const [msg, setMsg] = useState("");

    const requestZone = () => {
        const toSend = {
            zipcode: zipcode
        };

        let config = {
            headers: {
                "Content-Type": "application/json",
                'strict-origin-when-cross-origin': '*',
            }
        }

        axios.post(
            "http://localhost:4567/zone",
            toSend,
            config
        )
            .then(response => {
                if (response.data.zone == "null") {
                    setFlag(1);
                    console.log("No zone found");
                } else {
                    setFlag(0);
                    setZoneID(response.data.zone.zone);
                    setFirstFrost(response.data.zone.avgFirstFrost);
                    setLastFrost(response.data.zone.avgLastFrost);
                    setGrowingDays(response.data.zone.avgGrowingDays);
                    setTempLowRange(response.data.zone.avgTempLow + " - " + response.data.zone.avgTempHigh);
                    setListS(response.data.zone.listOfStates);
                } 
            })

            .catch(function (error) {
                console.log(error);
            });
    }

    return (
        <div className="center-top">
            <h1>Zone Lookup</h1> <br /><br />
            <p>Find information about your hardiness zone by entering your zipcode into the search bar. This will
                look for your zipcode in our database, and if found, we will give you useful gardening information
                based off your hardiness zone! Please know that for your privacy, <u>we do not store information about your zipcode</u>. </p>
            {zoneID == 0 && flag == 0?
                <div className="container">
                    <div className="centered">
                        <TextBox type="text" text="Enter your zipcode here" change={(e) => setZipcode(e.target.value)} />
                        <AwesomeButton onPress={() => requestZone()}>Search</AwesomeButton>
                    </div>
                </div>
            : (null)}
            {flag == 0 && zoneID ?
                <div><br/>
                    <div className = 'results'>
                        <h2>Results for {zipcode}</h2>
                       <Table zone={zoneID} firstFrostDate={firstFrost} lastFrostDate={lastFrost} growingDays={growingDays} tempR={tempLowRange} listOfStates={listS}></Table>
                        </div><br /> <br />
                    <h2>Have another zipcode you want to search?</h2> <br />
                    <div className="container">
                    <div className="center-bottom">
                        <TextBox type="text" text="Enter your zipcode here" change={(e) => setZipcode(e.target.value)} />
                        <AwesomeButton onPress={() => requestZone()}>Search</AwesomeButton>
                    </div>
                </div>
                </div>
                : (null)}
            {flag == 1 ?
                <div className = "container">
                    <div className = "centered">
                    <h4>
                    Sorry, we could not find your zipcode in our database. 
                        </h4>
                        <MyModal />
                    </div>
                    <h2>Have another zipcode you want to search?</h2> <br />
                    <div className="container">
                    <div className="center-bottom">
                        <TextBox type="text" text="Enter your zipcode here" change={(e) => setZipcode(e.target.value)} />
                        <AwesomeButton onPress={() => requestZone()}>Search</AwesomeButton>
                    </div>
                </div>
            </div>: (null)}
        </div>


    );
}

export default ZoneLookup;



