import React from ".";
import {useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import AuthService from "../../services/AuthService";
import EventService from "../../services/EventService";
import {Container} from "@mui/material";
import {Row} from "react-bootstrap";
import {MDBBtn} from "mdb-react-ui-kit";

export default function EventInfo(){
    const {id} = useParams();
    const [eventInfo, setEventInfo] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [enrolled, setEnrolled] = useState("");
    const [statusMsg, setStatusMsg] = useState("");
    const user = AuthService.getCurrentUser();

    useEffect(() => {
        EventService.getEventDetails(id).then(
            (response) => setEventInfo(response.data),
            (error) => {
                const msg = (error.response && error.response.data) || error.message ||error.toString();
                setErrorMsg(msg);
            }
        );
    }, [id]);

    const handleSubscribe = () => {
        EventService.enrollToEvent(user.userId, id).then(
            (response) => setStatusMsg(statusMsg),
            (error) => {
                const msg = (error.response && error.response.data) || error.message ||error.toString();
                setErrorMsg(msg);
            }
        )
        setEnrolled(true)
    }

    const handleUnsubscribe = () => {
        EventService.unenrollFromEvent(user.userId, id).then(
            (response) => setStatusMsg(statusMsg),
            (error) => {
                const msg = (error.response && error.response.data) || error.message ||error.toString();
                setErrorMsg(msg);
            }
        )
        setEnrolled(false)
    }

    return(
        <Container>
            <Row className="mt-2">
                <h2 className="text-center">Main Info:</h2>
            </Row>
            <hr/>
            <Row className="mt-3 justify-content-center">
                <div className="col col-5">
                    <Row>
                        <h3>Description:</h3>
                        <p>{eventInfo.description}</p>
                        <p>Take time:</p>
                        <p>{eventInfo.takeTime}</p>
                    </Row>
                    {!enrolled ?
                        <Row className="justify-content-center">
                            <MDBBtn color="dark" className="rounded-8 ps-5 pe-5 col-4" onClick={handleSubscribe}>
                                Enroll
                            </MDBBtn>
                        </Row>
                        :
                        <Row className="justify-content-center">
                            <MDBBtn color="danger" className="rounded-8 ps-5 pe-5 col-4"
                                    onClick={handleUnsubscribe}>
                                Unenroll
                            </MDBBtn>
                        </Row>
                    }
                </div>
            </Row>
        </Container>
    )
}
