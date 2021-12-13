import React, { useState } from "react";
import { Modal, Button } from "react-bootstrap";
import TextBox from "../../components/TextBox";
import axios from "axios";


function MyModal (){
  const [showModal, setShow] = useState(false);
  const [ZCTBA, setZCTBA] = useState(""); // zipcode to be added by the user
  const [ZNTBA, setZNTBA] = useState(""); // zone number to be added by the user

  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  const requestZoneAdd = () => {
    const toSend = {
      zipcodeTBA: ZCTBA,
      zoneNumberTBA: ZNTBA
    };

    let config = {
        headers: {
            "Content-Type": "application/json",
            'Access-Control-Allow-Origin': '*',
        }
    }

    axios.post(
        "http://localhost:4567/zoneAdd",
        toSend,
        config
    )
        .then(response => {
          if (response.data.zone === "success") {
            alert("Zone added successfully");
          }
          handleClose();
        })
        .catch(function (error) {
            console.log(error);
        });
}
  return (
    <>
      <div
        className="d-flex align-items-center justify-content-center"
        style={{ height: "15vh" }}
      >
        <Button variant="primary" onClick={handleShow}>
          Add your Zipcode to our database!
        </Button>
      </div>
      <Modal show={showModal} onHide={handleClose}>
        <Modal.Header closeButton>
          <Modal.Title>Fill out the required fields!</Modal.Title>
        </Modal.Header>
        <Modal.Body>
        <form>
            <TextBox type="text" value="" text="Zipcode" placeholder="(ex) 20910" change={(e) => setZCTBA(e.target.value)}/>
            <TextBox type="text" value="" text="Zone number" placeholder="7a" change={(e) => setZNTBA(e.target.value)}/>
        </form>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleClose}>
            Close
          </Button>
          <Button variant="primary" onClick={requestZoneAdd}>
            Submit
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
}

export default MyModal;