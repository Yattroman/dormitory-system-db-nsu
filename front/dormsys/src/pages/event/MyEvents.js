import {Container, Tab, Tabs} from "react-bootstrap";
import React, {useEffect, useState} from "react";
import {MDBBadge,MDBCard,MDBCol,MDBRow} from "mdb-react-ui-kit";
import {Link} from "react-router-dom";
import AuthService from "../../services/AuthService";
import {Grid} from "@mui/material";
import EventService from "../../services/EventService";
import ClubService from "../../services/ClubService";

export default function MyEvents() {

    const [managingEvents, setManagingEvents] = useState(null);
    const [enrolledEvents, setEnrolledEvents] = useState(null);
    const [errorMsg, setErrorMsg] = useState("");
    const user = AuthService.getCurrentUser();

    const setManagingEventsByManagingClubs = (clubs) => {
        for (const club of clubs){
            EventService.getEnrolledItemsByClub(club.id).then(
                (response) => setManagingEvents(array => [...array, response.data.events]),
                (error) => {
                    const msg = (error.response && error.response.data) || error.message || error.toString();
                    setErrorMsg(msg);
                }
            )
        }
    }
    useEffect(() => {
        /*EventService.getEnrolledItemsByUser(user.userId).then(
            (response) => setEnrolledEvents(response.data.events),
            (error) => {
                const msg = (error.response && error.response.data) || error.message || error.toString();
                setErrorMsg(msg);
            }
        );*/
        ClubService.getUserManagingClubs(user.userId).then(
            (response) => setManagingEventsByManagingClubs(response.data.clubs),
            (error) => {
                const msg = (error.response && error.response.data) || error.message || error.toString();
                setErrorMsg(msg);
            }
        )
    });

    const eventItem = (event) => {
        return (
            // <Grid item md={3} key={club.id}>
            <Grid item md={6} sm={6}>
                <MDBCard className="m-1">
                    <MDBRow>
                        <MDBCol md={8} className="ps-4 pt-4 align-items-center">
                            {/*<h4><Link className="text-dark" to={"/leisure/event/" + event.id}>{event.name}</Link></h4>*/}
                            {/*<p className="text-muted">{event.club.name}</p>*/}
                            <h4><Link className="text-dark" to={"/leisure/event/"+1}>$Event name$</Link></h4>
                            <p className="text-muted">$club$</p>
                        </MDBCol>
                        <MDBCol md={4} className="p-3 justify-content-center">
                            {/*<MDBBadge color="success">{event.takeTime}</MDBBadge>*/}
                            {/*<p className="text-muted">{event.location}</p>*/}
                            <MDBBadge color="success">$date$</MDBBadge>
                            <p className="text-muted">$location$</p>
                        </MDBCol>
                    </MDBRow>
                </MDBCard>
            </Grid>
        )
    };

    return (
        <section className="p-4 bg-light">
            <div className="p-2 d-block text-center">
                <h2>My events information</h2>
            </div>
            <Container className="col col-8 h-100 pb-4">
                <Tabs defaultActiveKey="enrolledEvents" id="uncontrolled-tab-example" className="mb-3">
                    <Tab eventKey="enrolledEvents" title="Enrolled Events">
                        <Grid container spacing={1}>
                            {/*{enrolledEvents ? enrolledEvents.map((event) => eventItem(event)) : <p>No enrolled events</p>}*/}
                            {eventItem()}
                            {eventItem()}
                            {eventItem()}
                            {eventItem()}
                            {eventItem()}
                            {eventItem()}
                        </Grid>
                    </Tab>
                    <Tab eventKey="managingEvents" title="Managing Events">
                        <Grid container spacing={1}>
                            {/*{managingEvents ? managingEvents.map((event) => eventItem(event)) : <p>No managing events</p>}*/}
                            {eventItem()}
                            {eventItem()}
                            {eventItem()}
                        </Grid>
                    </Tab>
                </Tabs>
            </Container>
        </section>
    );
}
