import React, {useEffect, useState} from 'react';
import {useParams,Link, useHistory} from "react-router-dom";
import TextBox from '../components/TextBox';
import axios from 'axios';
import { AwesomeButton } from "react-awesome-button";
import Table from "./PlantTable";
import AddPlant from "../components/AddPlant";

import ImgUpload from "../components/ImgUpload";
import {useAuth0} from "@auth0/auth0-react";

const plant2Img = new Map();




const PlantLookup = () => {

    const [plantComName, setPlantComName] = useState('');
    const [resLatName, setResLatName] = useState('');
    const [flagForLatin, setFlagForLatin] = useState(false);
    const [flagForLookup, setFlagForLookup] = useState(false);
    const [flagForImgUpload, setFlagForImgUpload] = useState(false);

    const {plantName} = useParams();
    const [searchName, setSearchName] = useState("");
    const [plantID, setPlantID] = useState("");
    const [commonName, setCommonName] = useState("");
    const [latinName, setLatinName] = useState("");
    const [habit, setHabit] = useState("");
    const [height, setHeight] = useState("");
    const [hardiness, setHardiness] = useState("");
    const [growth, setGrowth] = useState("");
    const [moisture, setMoisture] = useState("");
    const [soil, setSoil] = useState("");
    const [shade, setShade] = useState("");
    const [edible, setEdible] = useState("");
    const [medical, setMedical] = useState("");
    const [other, setOther] = useState("");
    const [images, setImage] = useState([]);
    const [msg, setMsg] = useState("");
    const [uploadImages, setImages] = useState([]);
    const [finalUploadImages, setFinalImages] = useState([]);

    const {isAuthenticated} = useAuth0();
    const addPlant = [];


    const [plantPicSize, setPlantPicSize] = useState({});
    const [imgSli1, setImgSli1] = useState("");
    const [imgSli2, setImgSli2] = useState("");
    const [imgSli3, setImgSli3] = useState("");

    useEffect(() => {
        if (plant2Img.has(searchName)) {
            for (let i = 0; i < finalUploadImages.length; i++) {
                plant2Img.get(searchName).push(finalUploadImages[i].data_url);
            }
        } else {
            let temp = [];
            for (let j = 0; j < finalUploadImages.length; j++) {
                temp.push(finalUploadImages[j].data_url);
            }
            plant2Img.set(searchName, temp);
        }
        setPlantPicSize(plant2Img.get(searchName).length);
        setImgSli1(plant2Img.get(searchName)[0]);
        setImgSli2(plant2Img.get(searchName)[1]);
        setImgSli3(plant2Img.get(searchName)[2]);
        setFlagForImgUpload(true);
    }, [finalUploadImages]);

    if (isAuthenticated){
        addPlant.push(<AddPlant latinName = {latinName} commonName = {commonName}/>);
    }


    useEffect(() => {
        requestPlant();
    },[plantName]);

    /**
     * This function takes in the user input for any of the text boxes and
     * handles the input such that the search is not case sensitive.
     * @param userInput
     * @returns {string}
     */
    function handleCaseSensitivity(userInput){
        //First turn the userInput into lowercase
        let lowerCase = userInput.toLowerCase();

        //Get the first letter and make it capital
        let firstLetter = lowerCase.charAt(0).toUpperCase();

        //Create the final string
        let newUserInput = firstLetter + lowerCase.substring(1, lowerCase.length);

        return newUserInput;
    }

    console.log(handleCaseSensitivity("hello"))


    const requestPlantInfo = () => {
        let config = {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            }
        }
        let queries = searchName.split(" ");
        let input = "";
        for (let i = 0; i < queries.length; i++) {
            if (i === 0) {
                input += queries[i];
            } else {
                input += "-" + queries[i]
            }
        }

        axios.get(`https://plants.ces.ncsu.edu/plants/${input}/`,config)
            .then(res => {
                const paragraphs = res.data.split("<p>")
                setMsg("");
                if (paragraphs.length >= 2) {
                    const firstParagraph = paragraphs[1].split("</p>")[0];
                    const resultParagraph = firstParagraph.replace(/(<([^>]+)>)/ig, '')
                    const result = resultParagraph.replace(/&nbsp;/g, '').replace(/&quot;/g, '').replace(/&deg;/g, '');
                    
                    setMsg(result);
                }
            })
    }

    const requestPlant = () => {
        const toSend = {
            plant_Name: handleCaseSensitivity(plantName)
        };

        let config = {
            headers: {
                "Content-Type": "application/json",
                'Access-Control-Allow-Origin': '*',
            }
        }

        axios.post(
            "http://localhost:4567/plant-lookup",
            toSend,
            config
        )
            .then(response => {
                setFlagForImgUpload(false);

                setFlagForLookup(true);
                setFlagForLatin(false);

                let comNames = response.data.plant.CommonName.split("-");
                let actualComName = "";
                for (let i = 0; i < comNames.length; i++) {
                    if (i === 0) {
                        actualComName += comNames[i];
                    } else {
                        actualComName += " or " + comNames[i]
                    }
                }

                requestPlantInfo();
                console.log(response.data.plant.Image);
                setPlantID(response.data.plant.ID);
                setCommonName(actualComName);
                setLatinName(response.data.plant.LatinName);
                setHabit(response.data.plant.Habit);
                setHeight(response.data.plant.Height);
                setHardiness(response.data.plant.Hardiness);
                setGrowth(response.data.plant.Growth);
                setMoisture(response.data.plant.Moisture);
                setSoil(response.data.plant.Soil);
                setShade(response.data.plant.Shade);
                setEdible(response.data.plant.Edible);
                setMedical(response.data.plant.Medical);
                setOther(response.data.plant.Other);
                if (response.data.plant.Image[0] === "u") {
                    setImage("//" + response.data.plant.Image);
                } else {
                    setImage(response.data.plant.Image);
                }
                if (response.data.plant.Image !== "" && plant2Img.has(searchName) === false) {
                    setPlantPicSize(plantPicSize[searchName] = 1);
                    plant2Img.set(searchName, [response.data.plant.Image]);
                    setImages([""])
                } else if (plant2Img.has(searchName) === true) {
                    setImages([""])

                } else {
                    setImages([])
                }
            })

            .catch(function (error) {
                console.log(error);
            });
    }
    const requestPlantLatName = () => {
        console.log(plantComName);
        const toSend = {
            plant_ComName: handleCaseSensitivity(plantComName)
        };

        let config = {
            headers: {
                "Content-Type": "application/json",
                'Access-Control-Allow-Origin': '*',
            }
        }

        axios.post(
            "http://localhost:4567/plant-LatLookup",
            toSend,
            config
        )
            .then(response => {
                setFlagForLatin(true);
                setResLatName(response.data.latName);
                if (resLatName === "null") {
                    console.log("No latin name found for input common Name");
                }
            })

            .catch(function (error) {
                console.log(error);
            });
    }

    const history = useHistory();

    const routeChange = () =>{
        setFlagForLookup(true);
        let path = `/plant-lookup/${searchName}`;
        history.push(path);
        requestPlant();
    }

    return (
        <div className="container">
            {flagForLookup===false && plantID=="" && flagForLatin==false?
                <div className="centered-plant">
                    <h1>Plant Lookup</h1>
                    <br/>
                    <p className="explanationText">Welcome to the plant lookup page! Here, you can look up your favorite plants. You can then find
                        useful information about the plant to help you with your gardening. You can also like the plant
                        that is returned, which will have it show up in your user profile. Having plants in your profile
                        will let us recommend other plants for your garden.
                        <br/>
                        <br/>
                    </p>
                    <div>

                        <TextBox type="text" text="Enter a plant's common name here" change={(e) => setPlantComName(e.target.value)} />
                        <AwesomeButton onPress={() => requestPlantLatName()}>Click to get the plant's latin name.</AwesomeButton>
                    </div> <br/> <br/>
                    <div>
                        <TextBox type="text" text="Enter a plant's latin name here" change={(e) => setSearchName(e.target.value)} />
                        <AwesomeButton onPress={() => routeChange()}>Click to get the plant's information.</AwesomeButton>

                    </div>
                </div>
                : (null)}
            <br />
            <br />
            {flagForLatin && flagForLookup != true ? <div>
                <div className="centered-plant">
                <h1>Plant Lookup</h1>
                    <br/>
                    <p className="explanationText">Welcome to the plant lookup page! Here, you can look up your favorite plants. You can then find
                        useful information about the plant to help you with your gardening. You can also like the plant
                        that is returned, which will have it show up in your user profile. Having plants in your profile
                        will let us recommend other plants for your garden.
                        <br/>
                        <br/>
                    </p>
                    <div>

                        <TextBox type="text" text="Enter a plant's common name here" change={(e) => setPlantComName(e.target.value)} />
                        <AwesomeButton onPress={() => requestPlantLatName()}>Click to get the plant's latin name.</AwesomeButton>
                    </div> <br/> <br/>
                    <div>
                        <TextBox type="text" text="Enter a plant's latin name here" change={(e) => setSearchName(e.target.value)} />
                        <AwesomeButton onPress={() => routeChange()}>Click to get the plant's information.</AwesomeButton>

                    </div>
                    <h3>Results:</h3>
                    {resLatName === "null" ?

                        <div>
                            <h3>No latin name found for input common Name</h3>
                            </div>
                        // : <h3>Latin Name: {resLatName}🌱</h3>}

                            :<Link link to = {`/plant-lookup/${resLatName}`}>{resLatName}🌱</Link>}

                </div>
            </div> : (null)}
            {flagForLookup ?
                <div>
                    <div className="rowC">
                        {images.length === 0

                            ? <div>
                                <ImgUpload flag={setFlagForImgUpload} setFlag={setFlagForImgUpload} images={uploadImages} setImages={setImages} finalize = {setFinalImages}></ImgUpload>
                                <div className="imgStyle">

                                    {flagForImgUpload === false ?
                                        <img src="https://st4.depositphotos.com/14953852/22772/v/600/depositphotos_227725020-stock-illustration-image-available-icon-flat-vector.jpg" alt="plant" width="200" height="200" />
                                        :(null)}
                                </div>
                            </div>
                            : (null)}
                        {images.length !== 0 && plantPicSize < 3?
                            <img className="imgStyle" src={images} alt="plant" />
                            :
                            // <div className = "rowC">
                            //     <AliceCarousel className="imgStyle">
                            //     <img src={imgSli1} />
                            //     <img src={imgSli2} />
                            //     <img src={imgSli3} />
                            // </AliceCarousel>
                            // </div>
                            (null)}
                        <div className="hide" style = {{color: "#FFEFEB"}}>placeholder</div>
                        <div className = "resText">
                            <h2>{latinName}/({commonName})</h2>
                            <ul>{commonName && latinName ? addPlant :null}</ul>
                            <p>{msg}</p>
                        </div>
                    </div>
                    <Table PlantID={plantID} CommonName={commonName} LatinName={latinName} Habit={habit} Height={height} Hardiness={hardiness} Growth={growth} Moisture={moisture} Soil={soil} Shade={shade} Edible={edible} Medical ={edible} Other={other}></Table>
                </div> : (null)}

        </div>

    );

};
export default PlantLookup;