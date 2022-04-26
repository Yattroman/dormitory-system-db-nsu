import React, {useState} from "react";
import { Container, Button, Row, Col, Form, FormControl } from "react-bootstrap";
import AuthService from "../../services/AuthService";

export default function SignUp() {

    const [message, setMessage] = useState("");

    const [form, setForm] = useState({
        nickname: '',
        firstName: '',
        middleName: '',
        surname: '',
        password: '',
        confPassword: '',
        email: ''
    })

    const handleChange = (e) => {
        setForm({...form, [e.target.name]: e.target.value })
    }

    const handleReset = () => {
        setForm({
            nickname: '',
            firstName: '',
            middleName: '',
            surname: '',
            password: '',
            confPassword: '',
            email: ''
        });
    }

    const handleSubmit = (e) => {
        const config = {
            headers: {
                'Access-Control-Allow-Origin':'*',
                'Access-Control-Allow-Methods': 'POST, GET',
                'Access-Control-Allow-Headers': 'Content-Type, Authorization',
                'Content-Type': 'application/json',
            }
        }

        e.preventDefault();

        AuthService.register(
            form.nickname,
            form.firstName,
            form.surname,
            form.middleName,
            form.password,
            form.email
        ).then(
            (response) => {
                setMessage(response.data.message);
            },
            (error) => {
                const msg =
                    (error.response && error.response.data && error.response.data.message) ||
                    error.message || error.toString();
                setMessage(msg);
            }
        );
     }

    return (
        <Container className="md mt-3 d-flex justify-content-center">
            <Form className="col col-6">
                <Row className="mb-3 justify-content-center">
                    <Form.Group controlId="firstnameId" className="col col-sm-4">
                        <Form.Label>Firstname</Form.Label>
                        <Form.Control
                            type = "text"
                            name = "firstName"
                            placeholder="Firstname..."
                            onChange={handleChange}
                        />
                    </Form.Group>
                    <Form.Group controlId="surnameId" className="col col-sm-4">
                        <Form.Label>Surname</Form.Label>
                        <Form.Control
                            type = "text"
                            name = "surname"
                            placeholder="Surname..."
                            onChange={handleChange}
                        />
                    </Form.Group>
                    <Form.Group controlId="middlenameId" className="col col-sm-4">
                        <Form.Label>Middlename</Form.Label>
                        <Form.Control
                            type = "text"
                            name = "middleName"
                            placeholder="Middlename..."
                            onChange={handleChange}
                        />
                    </Form.Group>
                </Row>
                <Row className="justify-content-center">
                    <Form.Group className="mb-3 col col-sm-6" controlId="nicknameId" >
                        <Form.Label>Nickname</Form.Label>
                        <Form.Control
                            type = "text"
                            name = "nickname"
                            placeholder="Nickname..."
                            onChange={handleChange}
                        />
                    </Form.Group>
                    <Form.Group className="mb-3 col col-sm-6" controlId="emailId">
                        <Form.Label>Email</Form.Label>
                        <Form.Control
                            type = "text"
                            name = "email"
                            placeholder="Email..."
                            onChange={handleChange}
                        />
                    </Form.Group>
                </Row>
                <Form.Group className="mb-3 col col-sm-4" controlId="passwordId">
                    <Form.Label>Password</Form.Label>
                    <Form.Control
                        type = "text"
                        name = "password"
                        placeholder="Password..."
                        onChange={handleChange}
                    />
                </Form.Group>
                <Form.Group className="mb-3 col col-sm-4" controlId="confPasswordId">
                    <Form.Label>Confirm Password</Form.Label>
                    <Form.Control
                        type = "text"
                        name = "confPassword"
                        placeholder="Confirm Password..."
                        onChange={handleChange}
                    />
                </Form.Group>
                <Row className="justify-content-center">
                    <Button type="submit" className="me-4 col col-sm-2 btn btn-success btn-lg btn-block m-1"
                            onClick={handleSubmit}>
                        Sign up
                    </Button>
                    <Button type="submit" className="me-4 col col-sm-2 btn btn-danger btn-lg btn-block m-1"
                            onClick={handleReset}>
                        Reset
                    </Button>
                </Row>
            </Form>
        </Container>
    )

}
