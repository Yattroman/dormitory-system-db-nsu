import {useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import AuthService from "../../services/AuthService";
import EventService from "../../services/EventService";
import {MDBBtn, MDBCol, MDBContainer, MDBRow} from "mdb-react-ui-kit";

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
        <MDBContainer className="mt-5">
            <MDBRow className="mt-2">
                <h2 className="text-center">{eventInfo.name}</h2>
            </MDBRow>
            <MDBRow className="mt-3 justify-content-center">
                <MDBCol md={8} sm={8}>
                    <MDBRow className="mb-5">
                        <MDBCol md={8} sm={8}>
                            <h3>Description:</h3>
                            <p>{eventInfo.description}</p>
                        </MDBCol>
                        <MDBCol md={4} sm={4}>
                            <h4>Location:</h4>
                            <p>{eventInfo.location}</p>
                            <h4>Take time:</h4>
                            <p>{eventInfo.takeTime}</p>
                        </MDBCol>
                    </MDBRow>
                    {!enrolled ?
                        <MDBRow className="justify-content-center">
                            <MDBBtn color="dark" className="rounded-8 ps-5 pe-5 col-3" onClick={handleSubscribe}>
                                Enroll
                            </MDBBtn>
                        </MDBRow>
                        :
                        <MDBRow className="justify-content-center">
                            <MDBBtn color="danger" className="rounded-8 ps-5 pe-5 col-3"
                                    onClick={handleUnsubscribe}>
                                Unenroll
                            </MDBBtn>
                        </MDBRow>
                    }
                </MDBCol>
            </MDBRow>
        </MDBContainer>
    )
}
