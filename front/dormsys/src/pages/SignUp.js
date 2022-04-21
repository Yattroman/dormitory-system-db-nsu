import React, {useState} from "react";
import { Container, Button, Row, Col, Form, FormControl } from "react-bootstrap";
import { Link } from "react-router-dom";
import axios from "axios";

export default function SignUp() {

    const url = "http://localhost:8080/api/user/signup";

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

    const resetButton = () => {
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

        if (form.password !== form.confPassword) {
            console.log("password Not Match");
        } else {
            console.log('A form was submitted with full name :'
                + form.firstName + ' '
                + form.surname + ' '
                + form.middleName + ' ' +
                ' and email :' + form.email + '');
            axios.post(url,{
                nickname: form.nickname,
                firstName: form.firstName,
                surname: form.surname,
                middleName: form.middleName,
                password: form.password,
                email: form.email
            }).then( response => {
                console.log(response);
            }).catch( error => {
                console.log(error);
            })
        }
        e.preventDefault();
    }

    return (
        <Container className="md mt-3">
            <Form>
                <Row className="mb-3">
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
                <Row>
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
                <Button type="submit" className="me-4 btn btn-success btn-lg btn-block"
                        onClick={handleSubmit}>
                    Sign up
                </Button>
            </Form>
        </Container>
    )

}