import React from "react";
import { Row, Jumbotron, Button, Container } from "react-bootstrap";
import { RouteComponentProps } from "react-router-dom";
import "../App.css";

const FrontPageComponent = ({ history }: RouteComponentProps) => {

  return (
    <Row className="bg-secondary min-vh-100">
      <Container className="vertical-center">
        <Jumbotron className="col-lg-10 offset-1 float-md-center text-center">
          <h1>qweqw</h1>
          <hr></hr>
          <p>
            <Button
              variant="info btn-lg"
              onClick={() =>
              {
                window.location.reload();
                history.push("/login/");}}
            >
              eqweq
            </Button>
          </p>
        </Jumbotron>
      </Container>
    </Row>
  );
};

export default FrontPageComponent;
