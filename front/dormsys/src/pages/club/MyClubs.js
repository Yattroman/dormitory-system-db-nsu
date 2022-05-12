import {Container, Row, Tab, Tabs} from "react-bootstrap";
import React, {useEffect, useState} from "react";
import {
    MDBCard,
    MDBCol, MDBContainer, MDBNavbar, MDBNavbarItem, MDBNavbarLink,
    MDBRow
} from "mdb-react-ui-kit";
import {Link} from "react-router-dom";
import ClubService from "../../services/ClubService";
import AuthService from "../../services/AuthService";
import {Grid} from "@mui/material";

export default function MyClubs() {

    const [managingClubs, setManagingClubs] = useState(null);
    const [subscribedClubs, setSubscribedClubs] = useState(null);
    const [errorMsg, setErrorMsg] = useState("");
    const user = AuthService.getCurrentUser();

    useEffect(() => {
        ClubService.getUserSubscribedClubs(user.userId).then(
            (response) => setSubscribedClubs(response.data.clubs),
            (error) => {
                const msg = (error.response && error.response.data) || error.message || error.toString();
                setErrorMsg(msg);
            }
        );
        ClubService.getUserManagingClubs(user.userId).then(
            (response) => setManagingClubs(response.data.clubs),
            (error) => {
                const msg = (error.response && error.response.data) || error.message || error.toString();
                setErrorMsg(msg);
            }
        )
    });

    const clubItem = (club) => {
        return (
            // <Grid item md={3} key={club.id}>
            <Grid item md={4} sm={6}>
                <MDBCard className="m-1">
                    <MDBRow>
                        <MDBCol md={8} className="ps-4 pt-4 align-items-center">
                            {/*<h3><Link className="text-dark" to={"/leisure/club/" + club.id}>{club.name}</Link></h3>*/}
                            {/*<p className="text-muted">@{club.uniqueName}</p>*/}
                            <h4><Link className="text-dark" to={"/leisure/club/"+1}>Club name</Link></h4>
                            <p className="text-muted">@uniquename</p>
                        </MDBCol>
                        <MDBCol md={4} className="p-3 justify-content-center">
                            <img src="https://kitchen-profi.ru/img/f9eea/f9eeadebd7b3bc3f83c35798156e2f36x700x700x80.png"
                                 className="rounded w-100" alt="aligment" />
                        </MDBCol>
                    </MDBRow>
                </MDBCard>
            </Grid>
        )
    };

    return (
        <section className="p-4">
            <div className="p-2 d-block text-center">
                <h2>My clubs information</h2>
            </div>
            <Container className="col col-8 h-100 pb-4">
                <Tabs defaultActiveKey="subscribedClubs" id="uncontrolled-tab-example" className="mb-3">
                    <Tab eventKey="subscribedClubs" title="Subscribed Clubs">
                        <Grid container spacing={1}>
                            {/*{subscribedClubs ? subscribedClubs.map((club) => clubItem(club)) : <p>No subscribed clubs</p>}*/}
                            {clubItem()}
                            {clubItem()}
                            {clubItem()}
                            {clubItem()}
                            {clubItem()}
                            {clubItem()}
                        </Grid>
                    </Tab>
                    <Tab eventKey="managingClubs" title="Managing Clubs">
                        <Grid container spacing={1}>
                            {/*{managingClubs ? managingClubs.map((club) => clubItem(club)) : <p>No managing clubs</p>}*/}
                            {clubItem()}
                            {clubItem()}
                            {clubItem()}
                        </Grid>
                    </Tab>
                </Tabs>
            </Container>
        </section>
    );
}
