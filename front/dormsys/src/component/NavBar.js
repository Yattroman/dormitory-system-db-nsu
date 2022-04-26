import React, {Component, useEffect, useState} from "react";
import {Link, useNavigate} from 'react-router-dom';
import AuthService from "../services/AuthService";
import {Container, Nav, Navbar, NavDropdown, NavLink} from "react-bootstrap";

export default function NavBar() {

    const [showDormitoryManagerBoard, setShowDormitoryManagerBoard] = useState(false);
    const [currentUser, setCurrentUser] = useState(undefined);
    const navigate = useNavigate();

    useEffect(() => {
        const user = AuthService.getCurrentUser();
        if (user && user.roles) {
            setCurrentUser(user);
            setShowDormitoryManagerBoard(user.roles.includes("ROLE_DORMITORY_MANAGER", "ROLE_ADMIN"));
            console.log(user);
        }
    }, []);

    const logout = () => {
        AuthService.logout();
        navigate(0);
        navigate("/");
    };

    return (
        <Navbar bg="dark" variant="dark">
            <Container>
                <Navbar.Brand href="#home">University System</Navbar.Brand>
                <Nav className="me-auto">
                    <Nav.Link as={Link} to={"/"}>Home</Nav.Link>
                    {currentUser ?
                        <>
                            <Nav.Link as={Link} to={"/profile"}>Profile</Nav.Link>
                        </> : null
                    }
                    {showDormitoryManagerBoard ?
                        <>
                            <NavDropdown title="Dormitory Manager" id="basic-nav-dropdown">
                                <NavDropdown.Item as={Link} to="/dormitory">Get Dormitory Info</NavDropdown.Item>
                            </NavDropdown>
                        </> : null
                    }
                </Nav>
                <Nav>
                    {!currentUser ?
                        <>
                            <Nav.Link as={Link} to={"/signup"}>Sign Up</Nav.Link>
                            <Nav.Link as={Link} to={"/signin"}>Sign In</Nav.Link>
                        </>
                        :
                        <>
                            <Nav.Link onClick={logout}>Logout</Nav.Link>
                        </>
                    }
                </Nav>
            </Container>
        </Navbar>
    );

}
