import {Container, Row} from "react-bootstrap";
import UserService from "../services/UserService";
import {useEffect, useState} from "react";
import MyClubs from "./club/MyClubs";

export default function Profile() {

    const [userInfo, setUserInfo] = useState("");
    const [errorMsg, setErrorMsg] = useState("");

    useEffect(() => {
        UserService.getDefaultUserInfo().then(
            (response) => setUserInfo(response.data),
            (error) => {
                const msg = (error.response && error.response.data) || error.message ||error.toString();
                setErrorMsg(msg);
            }
        );
    }, []);

    return (
        <section className="bg-light p-4">
            <div className="d-block text-center">
                <h2>Main profile information</h2>
            </div>
            <Container>
                <div>
                    <div className="d-flex d-flex-row mt-4 align-items-center justify-content-center">
                        <div className="col col-3">
                            <ul>
                                <li>Nickname: {userInfo.nickname}</li>
                                <li>Fullname: {userInfo.firstName} {userInfo.surname} {userInfo.middleName}</li>
                                <li>Email: {userInfo.email}</li>
                                <li>Date Birth: {userInfo.dateBirth}</li>
                                <li>Gender: {userInfo.gender}</li>
                            </ul>
                        </div>
                        <div className="col col-3">
                            <img width={200} height={200} src="https://cdn-icons-png.flaticon.com/512/17/17004.png"/>
                        </div>
                    </div>
                </div>
            </Container>
        </section>
    )

}
