import {Container, Row} from "react-bootstrap";

export default function Profile() {

    return (
        <section>
            <div>
                <div className="p-2 d-block bg-light text-center">
                    <h1>Profile Info!</h1>
                </div>
                <div className="d-flex d-flex-row mt-5 justify-content-center">
                    <div className="col col-3">
                        <ul>
                            <li>Nickname: </li>
                            <li>Fullname: </li>
                            <li>Email: </li>
                            <li>Age: </li>
                            <li>Gender: </li>
                        </ul>
                    </div>
                    <div className="col col-3">
                        <h2>
                            place for photo...
                        </h2>
                    </div>
                </div>
            </div>
        </section>
    )

}