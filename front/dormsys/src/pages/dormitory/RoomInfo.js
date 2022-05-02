import {useNavigate, useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import DormitoryService from "../../services/DormitoryService";
import {Container} from "@mui/material";
import {Col, Row} from "react-bootstrap";
import {MDBBtn, MDBBtnGroup, MDBCol, MDBIcon, MDBRow} from "mdb-react-ui-kit";
import UserService from "../../services/UserService";

export default function RoomInfo() {

    const {id} = useParams();
    const [roomInfo, setRoomInfo] = useState("");
    const [errorMsg, setErrorMsg] = useState("");

    const [searchedUser, setSearchedUser] = useState("");
    const [searchedFurniture, setSearchedFurniture] = useState("");

    const [statusMsg, setStatusMsg] = useState("");

    const [search, setSearch] = useState({
        furnitureInnerCode: '',
        userNickname: '',
    })

    let navigate = useNavigate();

    useEffect(() => {
        DormitoryService.getRoomDetails(id).then(
            (response) => {
                setRoomInfo(response.data);
            },
            (error) => {
                const msg = (error.response && error.response.data) || error.message || error.toString();
                setErrorMsg(msg);
            }
        );
    }, [id, roomInfo])

    const handleChange = (e) => {
        setSearch({...search, [e.target.name]: e.target.value})
    }

    const inhabitantInfo = (id, fullname, email, nickname, searched) => {
        return (
            <li className="list-group-item d-flex justify-content-between align-items-center">
                <div>
                    <div className="fw-bold">{fullname}</div>
                    {!searched ? <div className="text-muted">{email}</div> : null}
                    <div className="text-muted">{nickname}</div>
                </div>
                {!searched ?
                    <div className="d-flex align-items-center">
                        <span className="badge rounded-pill badge-success me-3">Inhabitant</span>
                        <MDBBtn type="button"
                                class="btn btn-danger btn-rounded"
                                onClick={() => handleRemoveInhabitant(id)}>
                            Remove
                        </MDBBtn>
                    </div>
                    :
                    <MDBBtn class="btn btn-outline-success btn-rounded"
                            onClick={() => handleAddInhabitant(id)}>
                        Add
                    </MDBBtn>
                }
            </li>
        )
    }

    const furnitureInfo = (id, name, innerNumber, category, searched) => {
        return (
            <li className="list-group-item d-flex justify-content-between align-items-center">
                <div>
                    <div className="fw-bold">{name}</div>
                    <div className="text-muted">{innerNumber}</div>
                </div>
                {!searched ?
                    <div className="d-flex align-items-center">
                        <span className="badge rounded-pill badge-info me-3">{category}</span>
                        <MDBBtn type="button"
                                class="btn btn-danger btn-rounded"
                                onClick={() => handleRemoveFurniture(id)}>Remove
                        </MDBBtn>
                    </div>
                    :
                    <MDBBtn type="button"
                            class="btn btn-outline-success btn-rounded"
                            onClick={() => handleAddFurniture(id)}>
                        Add
                    </MDBBtn>
                }
            </li>
        )
    }

    const handleFurnitureSearch = () => {
        DormitoryService.getFurnitureDetailsByInnerNumber(search.furnitureInnerCode).then(
            (response) => {
                setSearchedFurniture(response.data)
            },
            (error) => {
                const msg = (error.response && error.response.data) || error.message || error.toString();
                setErrorMsg(msg);
            }
        );
    }

    const handleInhabitantSearch = () => {
        UserService.getUserDetailsInfoByNickname(search.userNickname).then(
            (response) => {
                setSearchedUser(response.data)
            },
            (error) => {
                const msg = (error.response && error.response.data) || error.message || error.toString();
                setErrorMsg(msg);
            }
        );
    }

    const handleRemoveInhabitant = (inhabitantId) => {
        DormitoryService.removeInhabitantFromRoom(inhabitantId).then(
            (response) => {
                setStatusMsg(response.data)
            },
            (error) => {
                const msg = (error.response && error.response.data) || error.message || error.toString();
                setErrorMsg(msg);
            }
        )
    }

    const handleAddInhabitant = (inhabitantId) => {
        DormitoryService.addInhabitantIntoRoom(roomInfo.id, inhabitantId, 100).then(
            (response) => {
                setStatusMsg(response.data)
            },
            (error) => {
                const msg = (error.response && error.response.data) || error.message || error.toString();
                setErrorMsg(msg);
            }
        )
    }


    const handleRemoveFurniture = (furnitureId) => {
        DormitoryService.removeFurnitureFromRoom(furnitureId).then(
            (response) => {
                setStatusMsg(response.data)
            },
            (error) => {
                const msg = (error.response && error.response.data) || error.message || error.toString();
                setErrorMsg(msg);
            }
        )
    }

    const handleAddFurniture = (furnitureId) => {
        DormitoryService.addFurnitureIntoRoom(roomInfo.id, furnitureId).then(
            (response) => {
                setStatusMsg(response.data)
            },
            (error) => {
                const msg = (error.response && error.response.data) || error.message || error.toString();
                setErrorMsg(msg);
            }
        )
    }

    const searchItemsBox = (placeholder, inputName, onClickHandler) => {
        return (
            <MDBRow className="mb-3 mt-3">
                <MDBCol>
                    <div className="input-group md-form form-sm form-1 pl-0">
                        <MDBBtn color="dark" size="sm" type="submit" onClick={onClickHandler}>
                            Search
                        </MDBBtn>
                        <input
                            className="form-control my-0 py-1"
                            type="text"
                            placeholder={placeholder}
                            aria-label="Search"
                            name={inputName}
                            onChange={handleChange}
                        />
                    </div>
                </MDBCol>
            </MDBRow>
        );
    }

    return (
        <Container className="mt-3">
            <Row>
                <Col md={8}>
                    <h1>Room Info</h1>
                    <hr/>
                    <div>
                        <h3>Basic Info</h3>
                        <ul>
                            <li>Номер комнаты: {roomInfo.roomNumber}</li>
                            <li>Количество спальных мест: {roomInfo.bedsNumber}</li>
                        </ul>
                    </div>
                    <hr/>
                    <div>
                        <h3>Inhabitants</h3>
                        <ul className="list-group list-group-light">
                            {roomInfo && roomInfo.inhabitants ?
                                roomInfo.inhabitants.map(
                                    (inhabitant) =>
                                        inhabitantInfo(
                                            inhabitant.id,
                                            inhabitant.user.firstName + " " + inhabitant.user.surname + " " +
                                            (inhabitant.user.middleName ? inhabitant.user.middleName : ""),
                                            inhabitant.user.email,
                                            inhabitant.user.nickname,
                                            false)
                                ) : null
                            }
                            {inhabitantInfo(1, "Roman Denisovich Yatmanov", "r.yatmanov@g.nsu.ru", "yattroman", false)}
                            {inhabitantInfo(1, "Roman Denisovich Yatmanov", "r.yatmanov@g.nsu.ru", "yattroman", false)}
                            {inhabitantInfo(1, "Roman Denisovich Yatmanov", "r.yatmanov@g.nsu.ru", "yattroman", false)}
                        </ul>
                    </div>
                    <hr/>
                    <div>
                        <h3>Furniture</h3>
                        <ul className="list-group list-group-light">
                            {roomInfo && roomInfo.furnitures ?
                                roomInfo.furnitures.map(
                                    (furniture) =>
                                        furnitureInfo(
                                            furniture.id,
                                            furniture.name,
                                            furniture.innerNumber,
                                            furniture.category,
                                            false)
                                ) : null
                            }
                            {furnitureInfo(1, "Стол письменный, большой", "12032ап", "Письменные принадлежности", false)}
                        </ul>
                    </div>
                </Col>
                <Col md={4}>
                    <h1>Edit Room</h1>
                    <hr/>
                    <div className="mt-3">
                        {searchItemsBox("Enter nickname of person...", "userNickname", handleInhabitantSearch)}
                        {searchedUser ? inhabitantInfo(
                            searchedUser.id,
                            searchedUser.firstName + " " + searchedUser.surname + " " +
                            (searchedUser.middleName ? searchedUser.middleName : ""),
                            searchedUser.email,
                            searchedUser.nickname,
                            true) : null}
                        {searchItemsBox("Enter furniture inner number...", "furnitureInnerCode", handleFurnitureSearch)}
                        {searchedFurniture ? furnitureInfo(
                            searchedFurniture.id,
                            searchedFurniture.name,
                            searchedFurniture.innerNumber,
                            searchedFurniture.category,
                            true) : null}
                    </div>
                </Col>
            </Row>
        </Container>
    );

}
