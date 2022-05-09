import React, {useEffect, useState} from "react";
import {Container, Button, Row, Form} from "react-bootstrap";
import EventService from "../../services/EventService";
import ClubService from "../../services/ClubService";
import AuthService from "../../services/AuthService";
import {MDBBadge} from "mdb-react-ui-kit";

export default function EventAdd() {

    const [message, setMessage] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [managingClubs, setManagingClubs] = useState("");
    const [form, setForm] = useState({
        name: '',
        description: '',
        uniqueName: '',
        takeTime: '',
        clubId: ''
    })
    const user = AuthService.getCurrentUser();

    useEffect(() => {
        ClubService.getUserManagingClubs(user.userId).then(
            (response) => {
                setManagingClubs(response.data.clubs);
            },
            (error) => {
                const msg = (error.response && error.response.data) || error.message || error.toString();
                setErrorMsg(msg);
            }
        );
    }, [form]);

    const handleChange = (e) => {
        console.log(e.target.value);
        setForm({...form, [e.target.name]: e.target.value});
    }

    const handleSubmit = (e) => {
        e.preventDefault();

        EventService.addEvent(
            form.name,
            form.location,
            form.description,
            form.takeTime,
            form.clubId
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

    const ClubsDropdown = ({label, name, value, clubs, onChange}) => {
        return (
            <label>
                {label}
                <select name={name} onChange={onChange} value={value}>
                    {clubs.map((club) => (
                        <option value={club.id} key={club.id}>{club.name}</option>
                    ))}
                </select>
            </label>
        );
    };

    return (
        <Container className="md mt-5 d-flex justify-content-center">
            <Form className="col col-8">
                <h2 className="text-center">Create your own event</h2>
                <Row className="mb-1 mt-3 justify-content-center">
                    <Form.Group controlId="nameId" className="col col-4">
                        <Form.Label>Event name</Form.Label>
                        <Form.Control
                            type="text"
                            name="name"
                            placeholder="Name..."
                            onChange={handleChange}
                        />
                    </Form.Group>
                    <Form.Group controlId="locationId" className="col col-4">
                        <Form.Label>Event location</Form.Label>
                        <Form.Control
                            type="text"
                            name="location"
                            placeholder="Location..."
                            onChange={handleChange}
                        />
                    </Form.Group>
                </Row>
                <Row className="justify-content-center">
                    <Form.Group controlId="descriptionId" className="col col-8">
                        <Form.Label>Describe event</Form.Label>
                        <Form.Control
                            as="textarea"
                            name="description"
                            placeholder="Description..."
                            rows={4}
                            onChange={handleChange}
                        />
                    </Form.Group>
                </Row>
                <Row className="justify-content-center align-items-center">
                    <div className="col col-2">
                        {managingClubs ?
                            <ClubsDropdown
                                label="Organise by"
                                clubs={managingClubs}
                                name={"clubId"}
                                value={form.clubId}
                                onChange={handleChange}
                            />
                            :
                            <MDBBadge color="danger">No Own Clubs!</MDBBadge>
                        }
                    </div>
                    <div className="col col-6">
                        <Form.Group controlId="takeTimeId">
                            <Form.Label>Select Date event take place:</Form.Label>
                            <Form.Control
                                type="date"
                                name="takeTime"
                                placeholder="Take timme.."
                                onChange={handleChange}/>
                        </Form.Group>
                    </div>
                </Row>
                <Row className="d-flex justify-content-center">
                    <Button type="submit" className="mt-4 col col-3 btn btn-success btn-lg m-1"
                            onClick={handleSubmit}>
                        Create Event
                    </Button>
                </Row>
            </Form>
        </Container>
    )

}
