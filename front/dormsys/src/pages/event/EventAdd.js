import React, {useState} from "react";
import { Container, Button, Row, Form} from "react-bootstrap";
import DormitoryService from "../../services/DormitoryService";
import ClubServive from "../../services/ClubService";
import EventService from "../../services/EventService";
import {MDBDropdown, MDBDropdownItem, MDBDropdownMenu, MDBDropdownToggle} from "mdb-react-ui-kit";

export default function EventAdd() {

    const [message, setMessage] = useState("");

    const [form, setForm] = useState({
        name: '',
        description: '',
        uniqueName: '',
    })

    const handleChange = (e) => {
        setForm({...form, [e.target.name]: e.target.value })
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

        EventService.addEvent(
            form.name,
            form.location,
            form.description
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

    const clubsDropDown = () => {
        return (
            <MDBDropdown>
                <MDBDropdownToggle caret color="dark">
                    Organise by
                </MDBDropdownToggle>
                <MDBDropdownMenu basic>
                    <MDBDropdownItem>Club 1</MDBDropdownItem>
                    <MDBDropdownItem>Club 2</MDBDropdownItem>
                    <MDBDropdownItem>Club 3</MDBDropdownItem>
                    <MDBDropdownItem divider />
                    <MDBDropdownItem>Myself</MDBDropdownItem>
                </MDBDropdownMenu>
            </MDBDropdown>
        );
    }

    return (
        <Container className="md mt-3 d-flex justify-content-center">
            <div className="col col-8">
                <Form className="mb-2">
                    <Row className="mb-1 justify-content-center">
                        <Form.Group controlId="nameId" className="col col-4">
                            <Form.Label>Event name</Form.Label>
                            <Form.Control
                                type = "text"
                                name = "name"
                                placeholder="Name..."
                                onChange={handleChange}
                            />
                        </Form.Group>
                        <Form.Group controlId="locationId" className="col col-4">
                            <Form.Label>Event location</Form.Label>
                            <Form.Control
                                type = "text"
                                name = "location"
                                placeholder="Location..."
                                onChange={handleChange}
                            />
                        </Form.Group>
                    </Row>
                    <Row className="justify-content-center">
                        <Form.Group controlId="descriptionId" className="col col-8">
                            <Form.Label>Describe event</Form.Label>
                            <Form.Control
                                as = "textarea"
                                name = "description"
                                placeholder="Description..."
                                rows={4}
                                onChange={handleChange}
                            />
                        </Form.Group>
                    </Row>
                </Form>
                <Row className="justify-content-center">
                    <div className="col col-4">
                        {clubsDropDown()}
                    </div>
                    <div className="col col-4">

                    </div>
                </Row>
                <Row className="d-flex justify-content-center">
                    <Button type="submit" className="mt-4 col col-3 btn btn-success btn-lg m-1"
                            onClick={handleSubmit}>
                        Create Event
                    </Button>
                </Row>
            </div>
        </Container>
    )

}
