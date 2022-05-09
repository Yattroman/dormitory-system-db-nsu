import React, {useEffect, useState} from "react";
import { Container, Button, Row, Form} from "react-bootstrap";
import DormitoryService from "../../services/DormitoryService";
import ClubServive from "../../services/ClubService";
import AuthService from "../../services/AuthService";

export default function ClubAdd() {

    const [message, setMessage] = useState("");
    const user = AuthService.getCurrentUser();

    const [form, setForm] = useState({
        name: '',
        description: '',
        uniqueName: '',
    })

    const handleChange = (e) => {
        setForm({...form, [e.target.name]: e.target.value })
    }

    const handleSubmit = (e) => {
        e.preventDefault();

        ClubServive.addClub(
            form.name,
            form.description,
            form.uniqueName,
            user.userId
        ).then(
            (response) => {
                setMessage(response.data.message);
                console.log(response.data.message);
            },
            (error) => {
                const msg = (error.response && error.response.data && error.response.data.message) ||
                    error.message || error.toString();
                setMessage(msg);
                console.log(msg);
            }
        );
    }

    return (
        <Container className="md mt-5 d-flex justify-content-center">
            <Form className="col col-8">
                <h2 className="text-center">Create your own club</h2>
                <Row className="mt-3 mb-1 justify-content-center">
                    <Form.Group controlId="nameId" className="col col-sm-4">
                        <Form.Label>Club name</Form.Label>
                        <Form.Control
                            type = "text"
                            name = "name"
                            placeholder="Name..."
                            onChange={handleChange}
                        />
                    </Form.Group>
                    <Form.Group controlId="uniqueNameId" className="col col-sm-4">
                        <Form.Label>Unique name (short name)</Form.Label>
                        <Form.Control
                            type = "text"
                            name = "uniqueName"
                            placeholder="Unique name..."
                            onChange={handleChange}
                        />
                    </Form.Group>
                </Row>
                <Row className="justify-content-center">
                    <Form.Group controlId="descriptionId" className="col col-8">
                        <Form.Label>Describe your club</Form.Label>
                        <Form.Control
                            as = "textarea"
                            name = "description"
                            placeholder="Description..."
                            rows={4}
                            onChange={handleChange}
                        />
                    </Form.Group>
                </Row>
                <Row className="d-flex justify-content-center">
                    <Button type="submit" className="mt-4 col col-3 btn btn-success btn-lg m-1"
                            onClick={handleSubmit}>
                        Create Club
                    </Button>
                </Row>
            </Form>
        </Container>
    )

}
