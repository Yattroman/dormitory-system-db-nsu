import React, {useState} from "react";
import { Container, Button, Row, Col, Form, FormControl } from "react-bootstrap";
import { Link } from "react-router-dom";

export default function SignUp() {

    const [form, setForm] = useState({
        nickname: '',
        firstName: '',
        secondName: '',
        middleName: '',
        password: '',
        confPassword: '',
        email: ''
    })

    const handleChange = (e) => {
        setForm({...form, [e.target.name]: e.target.value })
    }

    const resetButton = () => {
        setForm({
            nickname: '',
            firstName: '',
            secondName: '',
            middleName: '',
            password: '',
            confPassword: '',
            email: ''
        });
    }

    const handleSubmit = (e) => {
        if (form.password !== form.confPassword) {
            console.log("password Not Match");
        } else {
            console.log('A form was submitted with Name :"' + form.nickname +
                '" and Email :"' + form.email + '"');
        }
        e.preventDefault();
    }

    return (
        <Container>
            <Form>
                <Row className="mb-3">
                    <Form.Group controlId="firstnameId" className="col col-sm-4">
                        <Form.Label>Firstname</Form.Label>
                        <Form.Control
                            type = "text"
                            name = "firstname"
                            placeholder="Firstname..."
                            onChange={handleChange}
                        />
                    </Form.Group>
                    <Form.Group controlId="secondnameId" className="col col-sm-4">
                        <Form.Label>Secondname</Form.Label>
                        <Form.Control
                            type = "text"
                            name = "secondname"
                            placeholder="Secondname..."
                            onChange={handleChange}
                        />
                    </Form.Group>
                    <Form.Group controlId="middlenameId" className="col col-sm-4">
                        <Form.Label>Middlename</Form.Label>
                        <Form.Control
                            type = "text"
                            name = "middlename"
                            placeholder="Middlename..."
                            onChange={handleChange}
                        />
                    </Form.Group>
                </Row>
                <Row>
                    <Form.Group className="mb-3 col col-sm-6" controlId="nicknameId" >
                        <Form.Label>Nickname</Form.Label>
                        <Form.Control
                            type = "text"
                            name = "nickname"
                            value={form.firstName}
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
                <Button type="submit" className="me-4 btn btn-success btn-lg btn-block"
                        onClick={handleSubmit}>
                    Sign up
                </Button>
            </Form>
        </Container>
    )

}