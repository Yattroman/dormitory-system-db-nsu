import React, {useEffect, useState} from "react";
import {Link, useNavigate, useParams} from "react-router-dom";
import {Grid} from "@mui/material";
import {MDBBadge, MDBBtn, MDBBtnGroup, MDBCard, MDBCardBody, MDBCardHeader, MDBCardText} from "mdb-react-ui-kit";
import {Col, Container, Row} from "react-bootstrap";
import Pagination from "@vlsergey/react-bootstrap-pagination";
import ClubService from "../../services/ClubService";

export default function AllClubs() {

    const [clubs, setClubs] = useState([]);
    const [errorMsg, setErrorMsg] = useState("");

    let navigate = useNavigate();

    const [pagesState, setPagesState] = useState({
        currentPage: 1,
        totalPages: 1,
        pageSize: 6
    });

    function getClubsInfoData(){
        ClubService.getClubsPage(pagesState.currentPage-1, pagesState.pageSize).then(
            (response) => {
                setClubs(response.data.clubs);
                setPagesState({...pagesState,
                    currentPage: response.data.currentPage + 1,
                    totalPages: response.data.totalPages
                });
            },
            (error) => {
                const msg = (error.response && error.response.data) || error.message ||error.toString();
                setErrorMsg(msg);
            }
        );
    }

    useEffect(() => {
        getClubsInfoData();
    }, [pagesState.currentPage]);

    const handlePageChange = (currentPage) => {
        setPagesState({...pagesState, currentPage: currentPage });
    }

    const handleClubDetailsShow = (clubId) => {
        navigate("/leisure/club/" + clubId);
    }

    const handleSubscribeClub = (clubId) => {
        navigate("/leisure/club/" + clubId);
    }

    const clubInfo = (club) => {
        return (
            <Grid item md={4} key={club.id}>
                <MDBCard className="h-100">
                    <MDBCardBody className="justify-content-center">
                        <MDBCardText>
                            <Container className="d-flex flex-column justify-content-between">
                                <Row className="mb-2">
                                    <Col md={8} sm={8}>
                                        <Link to={"/leisure/club/" + club.id} className="pe-2 align-middle">{club.name}</Link>
                                    </Col>
                                    <Col md={4} sm={4}>
                                        <MDBBadge color="success">
                                            @{club.uniqueName}
                                        </MDBBadge>
                                    </Col>
                                </Row>
                                <Row>
                                    <Col md={6} sm={6}>
                                        <MDBBtn color="dark" className="pe-5 ps-5" rounded={7} onClick={(e) => handleSubscribeClub(club.id)}>Subscribe</MDBBtn>
                                    </Col>
                                    <Col md={6} sm={6}>
                                        <p>Participants: </p>
                                    </Col>
                                </Row>
                            </Container>
                        </MDBCardText>
                    </MDBCardBody>
                </MDBCard>
            </Grid>
        )
    };

    return (
        <Container>
            <Row className="mt-3 mb-3">
                <Grid container spacing={2}>
                    { clubs ? clubs.map((club) => clubInfo(club)) : null }
                </Grid>
            </Row>
            <Row>
                <Pagination name="page"
                            value={pagesState.currentPage - 1}
                            totalPages={pagesState.totalPages}
                            showFirstLast={pagesState.totalPages >= 10}
                            atBeginEnd={0}
                            aroundCurrent={2}
                            onChange={(e) => {
                                const pageIndex = e.target.value + 1;
                                console.log(pageIndex);
                                handlePageChange(pageIndex);
                            }}/>
            </Row>
        </Container>
    );

}
