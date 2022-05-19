import {Container, Row} from "react-bootstrap";
import {useEffect, useState} from "react";
import ClubService from "../../services/ClubService";
import {useParams} from "react-router-dom";
import {MDBBtn, MDBBtnGroup} from "mdb-react-ui-kit";
import AuthService from "../../services/AuthService";

export default function ClubInfo() {

    const {id} = useParams();
    const [clubInfo, setClubInfo] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [statusMsg, setStatusMsg] = useState("");
    const [subscribed, setSubscribed] = useState(false);
    const user = AuthService.getCurrentUser();

    function checkIfClubsContainCertainClub(clubs) {
        for (const club of clubs) {
            console.log(id)
            if (club.id === id) {
                return true;
            }
        }
        return false;
    }

    useEffect(() => {
        ClubService.getClubDetails(id).then(
            (response) => setClubInfo(response.data),
            (error) => {
                const msg = (error.response && error.response.data) || error.message || error.toString();
                setErrorMsg(msg);
            }
        );
        ClubService.getUserSubscribedClubs(user.userId).then(
            (response) => setSubscribed(checkIfClubsContainCertainClub(response.data.clubs)),
            (error) => {
                const msg = (error.response && error.response.data) || error.message || error.toString();
                setErrorMsg(msg);
            }
        )
    }, [id]);

    const handleSubscribe = () => {
        ClubService.subscribeToClub(user.userId, id).then(
            (response) => setStatusMsg(statusMsg),
            (error) => {
                const msg = (error.response && error.response.data) || error.message || error.toString();
                setErrorMsg(msg);
            }
        )
        setSubscribed(true)
    }

    const handleUnsubscribe = () => {
        ClubService.unsubscribeFromClub(user.userId, id).then(
            (response) => setStatusMsg(statusMsg),
            (error) => {
                const msg = (error.response && error.response.data) || error.message || error.toString();
                setErrorMsg(msg);
            }
        )
        setSubscribed(false)
    }

    return (
        <Container>
            <div className="p-2 text-center">
                <h1>Club: {clubInfo.name}</h1>
            </div>
            <div>
                <Row className="mt-2">
                    <h2 className="text-center">Main Info:</h2>
                </Row>
                <hr/>
                <Row className="mt-3 justify-content-center">
                    <div className="col col-5">
                        <Row>
                            <h3>Unique name (short name):</h3>
                            <p>{clubInfo.uniqueName}</p>
                            <h3>Description:</h3>
                            <p>{clubInfo.description}</p>
                        </Row>
                        {!subscribed ?
                            <Row className="justify-content-center">
                                <MDBBtn color="dark" className="rounded-8 ps-5 pe-5 col-4" onClick={handleSubscribe}>
                                    Subscribe
                                </MDBBtn>
                            </Row>
                            :
                            <Row className="justify-content-center">
                                <MDBBtn color="danger" className="rounded-8 ps-5 pe-5 col-4"
                                        onClick={handleUnsubscribe}>
                                    Unsubscribe
                                </MDBBtn>
                            </Row>
                        }
                    </div>
                    <div className="col col-3">
                        <img
                            src='http://phoenixritu.files.wordpress.com/2012/12/black_circle.jpg'
                            className='img-fluid rounded-circle'
                            alt=''
                        />
                    </div>
                </Row>
            </div>
            <div>
                <Row className="mt-2">
                    <h2 className="text-center">Events:</h2>
                </Row>
                <hr/>
                <Row className="mt-3 justify-content-center">
                    <div className="col col-4">
                        <h3>Current Events: </h3>
                    </div>
                    <div className="col col-4">
                        <h3>Last 5 events: </h3>
                    </div>
                </Row>
            </div>
        </Container>
    )

}
