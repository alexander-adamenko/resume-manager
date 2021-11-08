import React from "react";
import { Row, Container, Jumbotron } from "react-bootstrap";

const Error = () => (
  <Row className="bg-secondary min-vh-100">
    <Container className="vertical-center">
      <Jumbotron className="col-6 offset-3 mx-auto text-center">
        <h1>404: Page not found.</h1>
      </Jumbotron>
    </Container>
  </Row>
);

export default Error;
