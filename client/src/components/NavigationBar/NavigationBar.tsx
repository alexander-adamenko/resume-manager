import React from "react";
import { Navbar, Nav } from "react-bootstrap";
import { LinkContainer } from "react-router-bootstrap";

interface Props {
    isLoggedIn: boolean | null;
}

const NavigationBar = (props: Props) => {

    return props.isLoggedIn ? (
        <Navbar sticky="top" bg="dark" variant="dark">
            <LinkContainer to="/">
                <Navbar.Brand>RESUME MANAGER</Navbar.Brand>
            </LinkContainer>
            <Nav className="mr-auto">
                <LinkContainer to="/vacancies/">
                    <Nav.Link>Vacancies</Nav.Link>
                </LinkContainer>
                <LinkContainer to="/candidates/">
                    <Nav.Link>Candidates</Nav.Link>
                </LinkContainer>
                <LinkContainer to="/candidate/new">
                    <Nav.Link>Upload Resume</Nav.Link>
                </LinkContainer>
                <LinkContainer to="/admin/">
                    <Nav.Link>Admin Page</Nav.Link>
                </LinkContainer>
                <LinkContainer to="/profile/">
                    <Nav.Link>Profile</Nav.Link>
                </LinkContainer>
            </Nav>
            <Nav>
                <LinkContainer to="/">
                    <Nav.Link>Logout</Nav.Link>
                </LinkContainer>
            </Nav>
        </Navbar>
    ) : (
        <Navbar sticky="top" bg="dark" variant="dark">
            <LinkContainer to="/">
                <Navbar.Brand>RESUME MANAGER</Navbar.Brand>
            </LinkContainer>
        </Navbar>
    );
}

export default NavigationBar;