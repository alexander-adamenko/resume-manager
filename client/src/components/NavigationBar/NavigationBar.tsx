import React from "react";
import {Navbar, Nav, NavDropdown, Col} from "react-bootstrap";
import { LinkContainer } from "react-router-bootstrap";

interface Props {
    isLoggedIn: boolean | null;
}

const NavigationBar = (props: Props) => {

    return props.isLoggedIn ? (
        <Navbar sticky="top" bg="dark" variant="dark" >
            <LinkContainer to="/vacancies" style={{marginLeft: '10px'}}>
                <Navbar.Brand >RESUME MANAGER</Navbar.Brand>
            </LinkContainer>
            <Nav className="mr-auto">
                <NavDropdown id="navbarScrollingDropdown" title="Vacancies">
                    <LinkContainer to="/vacancies">
                       <NavDropdown.Item>List</NavDropdown.Item>
                    </LinkContainer>
                    <LinkContainer to="/vacancies/new">
                        <NavDropdown.Item>Create</NavDropdown.Item>
                    </LinkContainer>
                </NavDropdown>
                <LinkContainer to="/candidates">
                    <Nav.Link>Candidates</Nav.Link>
                </LinkContainer>
                <LinkContainer to="/candidate/new">
                    <Nav.Link>Upload Resume</Nav.Link>
                </LinkContainer>
                <LinkContainer to="/admin">
                    <Nav.Link>Admin Page</Nav.Link>
                </LinkContainer>
                <LinkContainer to="/profile">
                    <Nav.Link>Profile</Nav.Link>
                </LinkContainer>
            </Nav>
            <Nav>
                <Nav.Link href="/login">Logout</Nav.Link>
            </Nav>
        </Navbar>
    ) : (
        <Navbar sticky="top" bg="dark" variant="dark">
            <LinkContainer to="/" style={{marginLeft: '10px'}}>
                <Navbar.Brand>
                    RESUME MANAGER
                </Navbar.Brand>
            </LinkContainer>
        </Navbar>
    );
}

export default NavigationBar;
