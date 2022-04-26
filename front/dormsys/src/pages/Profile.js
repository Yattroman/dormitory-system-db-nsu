import {Container, Row} from "react-bootstrap";
import UserService from "../services/UserService";
import {useEffect, useState} from "react";

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
        <section>
            <Container>
                <div>
                    <div className="p-2 d-block bg-light text-center">
                        <h1>Profile Info!</h1>
                    </div>
                    <div className="d-flex d-flex-row mt-5 justify-content-center">
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
                            <h2>
                                place for photo...
                            </h2>
                        </div>
                    </div>
                </div>
            </Container>
        </section>
    )

}
