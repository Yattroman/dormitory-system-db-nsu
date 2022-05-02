import React, {useEffect, useState} from "react";
import {Container, Row} from "react-bootstrap";
import Pagination from "@vlsergey/react-bootstrap-pagination";
import {Grid} from "@mui/material"
import {MDBBtn, MDBBtnGroup, MDBCard, MDBCardBody, MDBCardHeader, MDBCardText} from "mdb-react-ui-kit"
import DormitoryService from "../../services/DormitoryService";
import {useNavigate, useParams} from "react-router-dom";

export default function RoomsGallery() {

    const [rooms, setRooms] = useState([]);
    const [errorMsg, setErrorMsg] = useState("");

    const {dormitoryName} = useParams();

    let navigate = useNavigate();

    const [pagesState, setPagesState] = useState({
        currentPage: 1,
        totalPages: 1,
        pageSize: 12
    });

    function getRoomsInfoData(){
        DormitoryService.getRoomsGallery(dormitoryName, pagesState.currentPage-1, pagesState.pageSize).then(
            (response) => {
                console.log("For paginator: " + pagesState.currentPage-1);
                setRooms(response.data.rooms);
                setPagesState({...pagesState,
                    currentPage: response.data.currentPage + 1,
                    totalPages: response.data.totalPages
                });
                console.log("Total pages: " + response.data.totalPages);
            },
            (error) => {
                const msg = (error.response && error.response.data) || error.message ||error.toString();
                setErrorMsg(msg);
            }
        );
    }

    useEffect(() => {
        console.log("Updated: " + pagesState.currentPage);
        getRoomsInfoData();
    }, [pagesState.currentPage]);

    const handlePageChange = (currentPage) => {
        console.log("Value to change: " + currentPage);
        setPagesState({...pagesState, currentPage: currentPage });
    }

    const handleRoomShow = (roomId) => {
        navigate("/dormitory/" + dormitoryName + "/room/" + roomId);
    }

    const roomInfo = (roomInfo) => {
        return (
            <Grid item md={3} key={roomInfo.id}>
                <MDBCard>
                    {/*<Card.Img variant="top" src="..." alt="Text" />*/}
                    <MDBCardBody>
                        <MDBCardHeader className="text-center"><h3>{roomInfo.roomNumber}</h3></MDBCardHeader>
                        <MDBCardText className="p-2">
                            Inhabitant 1. <br/>
                            Inhabitant 2. <br/>
                            Inhabitant 3.
                        </MDBCardText>
                        <MDBBtnGroup className="d-flex align-items-center">
                            <MDBBtn color="dark" onClick={(e) => handleRoomShow(roomInfo.id)}>View</MDBBtn>
                            {/*<MDBBtn color="dark">Edit</MDBBtn>*/}
                        </MDBBtnGroup>
                    </MDBCardBody>
                </MDBCard>
            </Grid>
        )
    };

    return (
        <Container>
            <Row className="mt-3 mb-3">
                <Grid container spacing={2}>
                    { rooms ? rooms.map((room) => roomInfo(room)) : null }
                </Grid>
            </Row>
            <Row className="d-flex justify-content-center">
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
};

