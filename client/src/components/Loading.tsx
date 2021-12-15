import React from "react";
import {Row, Container, Spinner, Jumbotron} from "react-bootstrap";

const Loading = () => (
  <Row className="bg-secondary min-vh-100">
    <Container className="vertical-center">
      <Jumbotron className="col-4 offset-4 mx-auto text-center">
        <Spinner animation="border" />
      </Jumbotron>
    </Container>
  </Row>
);

export default Loading;
