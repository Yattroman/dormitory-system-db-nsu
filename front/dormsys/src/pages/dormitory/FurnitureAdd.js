import React, {useState} from "react";
import { Container, Button, Row, Form} from "react-bootstrap";
import DormitoryService from "../../services/DormitoryService";

export default function FurnitureAdd() {

    const [message, setMessage] = useState("");

    const [form, setForm] = useState({
        innerCode: '',
        name: '',
        category: '',
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

        console.log(form.name + ' ' + form.innerCode + ' ' + form.category);

        DormitoryService.addFurniture(
            form.name,
            form.innerCode,
            form.category
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
        <Container className="md mt-3 d-flex justify-content-center">
            <Form className="mt-2 col col-8">
                <Row className="justify-content-center">
                    <Form.Group controlId="nameId" className="col col-sm-4">
                        <Form.Label>Name</Form.Label>
                        <Form.Control
                            type = "text"
                            name = "name"
                            placeholder="Name..."
                            onChange={handleChange}
                        />
                    </Form.Group>
                </Row>
                <Row className="justify-content-center">
                    <Form.Group controlId="innerCodeId" className="col col-sm-4">
                        <Form.Label>Inner Code</Form.Label>
                        <Form.Control
                            type = "text"
                            name = "innerCode"
                            placeholder="Inner Code..."
                            onChange={handleChange}
                        />
                    </Form.Group>
                </Row>
                <Row className="justify-content-center">
                    <Form.Group controlId="categoryId" className="col col-sm-4">
                        <Form.Label>Category</Form.Label>
                        <Form.Control
                            type = "text"
                            name = "category"
                            placeholder="Category..."
                            onChange={handleChange}
                        />
                    </Form.Group>
                </Row>
                <Row className="d-flex justify-content-center">
                    <Button type="submit" className="col col-3 btn btn-success btn-lg m-3"
                            onClick={handleSubmit}>
                        Add Furniture
                    </Button>
                </Row>
            </Form>
        </Container>
    )

}
