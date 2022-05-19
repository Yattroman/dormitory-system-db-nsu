import React, {useEffect, useState} from "react";
import {Container, Button, Row, Col, Form, FormControl, ButtonGroup} from "react-bootstrap";
import AuthService from "../../services/AuthService";
import EventService from "../../services/EventService";

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

    const [validation, setValidation] = useState({
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
    const checkValidation = () => {
        let errors = validation;

        if (!form.firstName.trim()) {
            errors.firstName = "First name is required";
        } else {
            errors.firstName = "";
        }

        if (!form.surname.trim()) {
            errors.surname = "Last name is required";
        } else {
            errors.surname = "";
        }

        if (!form.nickname.trim()) {
            errors.nickname = "Nickname is required";
        } else {
            errors.nickname = "";
        }

        if (!form.email.trim()) {
            errors.email = "Email is required";
        } else {
            errors.email = "";
        }

        const password = form.password;
        if (!password) {
            errors.password = "Password is required";
        } else if (password.length < 6) {
            errors.password = "Password must be longer than 6 characters";
        } else if (password.length >= 20) {
            errors.password = "Password must shorter than 20 characters";
        } else {
            errors.password = "";
        }

        // if (!form.confPassword) {
        //     errors.confPassword = "Password confirmation is required";
        // } else if (form.confPassword !== form.password) {
        //     errors.confPassword = "Password does not match confirmation password";
        // } else {
        //     errors.confPassword = "";
        // }

        setValidation(errors);
    };

    useEffect(() => {
        checkValidation();
    }, [form]);

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
        <Container className="md mt-3 flex-column">
            <Row className="text-center mt-3"><h2>Create your own account</h2></Row>
            <Row className="justify-content-center mt-3">
                <Form className="col col-6 text-center">
                    <Row className="mb-3 justify-content-center">
                        <Form.Group controlId="firstnameId" className="col col-sm-4">
                            <Form.Label>Firstname</Form.Label>
                            <Form.Control
                                type = "text"
                                name = "firstName"
                                placeholder="Firstname..."
                                onChange={handleChange}
                            />
                            {validation.firstName && <p>{validation.firstName}</p>}
                        </Form.Group>
                        <Form.Group controlId="surnameId" className="col col-sm-4">
                            <Form.Label>Surname</Form.Label>
                            <Form.Control
                                type = "text"
                                name = "surname"
                                placeholder="Surname..."
                                onChange={handleChange}
                            />
                            {validation.surname && <p>{validation.surname}</p>}
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
                            {validation.nickname && <p>{validation.nickname}</p>}
                        </Form.Group>
                        <Form.Group className="mb-3 col col-sm-6" controlId="emailId">
                            <Form.Label>Email</Form.Label>
                            <Form.Control
                                type = "text"
                                name = "email"
                                placeholder="Email..."
                                onChange={handleChange}
                            />
                            {validation.email && <p>{validation.email}</p>}
                        </Form.Group>
                    </Row>
                    <Row>
                        <Form.Group className="mb-3 col col-6" controlId="passwordId">
                            <Form.Label>Password</Form.Label>
                            <Form.Control
                                type = "password"
                                name = "password"
                                placeholder="Password..."
                                onChange={handleChange}
                            />
                            {validation.password && <p>{validation.password}</p>}
                        </Form.Group>
                        <Form.Group className="mb-3 col col-6" controlId="confPasswordId">
                            <Form.Label>Confirm Password</Form.Label>
                            <Form.Control
                                type = "password"
                                name = "confPassword"
                                placeholder="Confirm Password..."
                                onChange={handleChange}
                            />
                            {validation.confPassword && <p>{validation.confPassword}</p>}
                        </Form.Group>
                    </Row>
                    <ButtonGroup className="col-4">
                        <Button type="button" className="btn-success" onClick={handleSubmit}>
                            Sign up
                        </Button>
                        <Button type="button" className="btn btn-danger" onClick={handleReset}>
                            Reset
                        </Button>
                    </ButtonGroup>
                </Form>
            </Row>
        </Container>
    )

}
