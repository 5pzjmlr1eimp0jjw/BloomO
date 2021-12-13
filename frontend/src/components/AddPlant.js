import {useAuth0} from "@auth0/auth0-react";
import LogoutButton from "./logout-button";
import LoginButton from "./login-button";
import React, {useState, useRef, useEffect} from "react";
import Heart from "react-animated-heart";
import axios from "axios";

const AddPlant = (props) =>{

    const [firstRender, setFirstRender] = useState(true);
    const [alreadyLiked, setAlreadyLiked] = useState(false);
    const [displayName, setDisplayName]= useState("");

    useEffect(() => {
        setDisplayName(`${props.commonName}(${props.latinName})`)
    },[]);

    const firstUpdate2 = useRef(true);
    useEffect(() => {
        if (firstUpdate2.current) {
            firstUpdate2.current = false;
            return;
        }

        //console.log(displayName);
        makeChangeInPlantToDB();


                setFirstRender(false);
                //console.log(firstRender);



        //firstRender = false;
    } ,[displayName])

    const { user} = useAuth0();
    const { sub } = user;
    const [isClick, setClick] = useState(false);


    const firstUpdate = useRef(true);
    useEffect(() => {
        if (firstUpdate.current) {
            firstUpdate.current = false;
            return;
        }
        // console.log("rendered");
        // console.log(displayName)
        makeChangeInPlantToDB();
    },[isClick]);

    const makeChangeInPlantToDB = async () =>{

        const toSend = {
            firstRender: firstRender,
            add: isClick,
            userID: sub,
            plantName: displayName
        }

        const config = {
            headers: {
                "Content-Type": "application/json",
                'strict-origin-when-cross-origin': '*',
            }
        }

        axios.post(
            "http://localhost:4567/add-users-plant",
            toSend,
            config
        )
            .then(
                response => {
                    if (firstRender === true){
                        console.log("response =" +response.data);
                        setClick(response.data);
                    }
            })
            .catch(function (error){
                console.log(error);
            })
    }

    return (
        <div>
            <p>{isClick}</p>
            <Heart isClick={isClick} onClick={() => setClick(!isClick)} />
        </div>
    );
}
export default AddPlant;