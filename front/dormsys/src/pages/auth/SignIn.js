import React, {useState} from "react";
import {Container, Button, Row, Col, Form, FormControl, Alert, Card, CardImg} from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import AuthService from "../../services/AuthService";
import {MDBCard, MDBCardImage} from "mdb-react-ui-kit";

export default function SignIn() {

    let navigate = useNavigate();

    const [message, setMessage] = useState("");

    const [form, setForm] = useState({
        nickname: '',
        password: '',
    })

    const required = (value) => {
        if (!value) {
            return (
                <Alert className="alert-danger" role="alert">
                    Required field!
                </Alert>
            );
        }
    };

    const handleChange = (e) => {
        setForm({...form, [e.target.name]: e.target.value })
    }

    const handleSubmit = (e) => {
        e.preventDefault();

        AuthService.login(form.nickname, form.password)
            .then(() => {
                    navigate("/profile");
                    window.location.reload();
                },
                (error) => {
                    const msg =
                        (error.response && error.response.data && error.response.data.message) ||
                        error.message || error.toString();
                    setMessage(msg);
                }

        )
    }

    return (
        <Container className="md mt-3 d-flex justify-content-center align-items-center h-100">
            <MDBCard className="p-4">
                <MDBCardImage
                    src="//ssl.gstatic.com/accounts/ui/avatar_2x.png"
                    alt="profile-img"
                    className="profile-img-card"
                />
                <Form>
                    <Form.Group className="mb-3" controlId="nicknameId" >
                        <Form.Label>Nickname</Form.Label>
                        <Form.Control
                            type = "text"
                            name = "nickname"
                            placeholder="Nickname..."
                            onChange={handleChange}
                        />
                    </Form.Group>
                    <Form.Group className="mb-3" controlId="passwordId">
                        <Form.Label>Password</Form.Label>
                        <Form.Control
                            type = "text"
                            name = "password"
                            placeholder="Password..."
                            onChange={handleChange}
                        />
                    </Form.Group>
                    <Button type="submit" className="me-4 btn btn-success btn-lg btn-block"
                            onClick={handleSubmit}>
                        Sign In
                    </Button>
                    {message && (
                        <Form.Group>
                            <Alert className="alert-danger" role="alert">
                                {message}
                            </Alert>
                        </Form.Group>
                    )}
                </Form>
            </MDBCard>
        </Container>
    )

}
