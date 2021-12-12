import React, { useState } from "react";
import { Row, Container, Modal, Button } from "react-bootstrap";

import LoginService from "../../services/LoginService";
import LoginForm from "./LoginForm";
import { RouteComponentProps } from "react-router-dom";
import { LoginDetails } from "../../models/AccountDetails";


const LoginComponent = (props: RouteComponentProps) => {
  const [loginError, setLoginError] = useState(false);
  const [loginErrorMessage, setLoginErrorMessage] = useState("");

  const login = ({username, password}: LoginDetails) => {
    LoginService.login({username, password})
      .then((_response) => {
        props.history.push("/admin/");
      })
      .catch((err) => {
        if (err.response) {
          if (err.response.status === 422) {
            setLoginErrorMessage(
              `${err.response.data.title}: ${err.response.data.message}`
            );
            setLoginError(true);
          }
        } else {
          // other errors
          setLoginErrorMessage("Не вдалося увійти.");
          setLoginError(true);
        }
      });
  };

  return (
    <>
      <Row className="bg-secondary min-vh-100">
        <Container className="vertical-center">

          <div className="p-5 mb-4 bg-light rounded-3 mx-auto text-center col-6">
              <LoginForm loginAction={login} />
          </div>
        </Container>
      </Row>

      <Modal show={loginError} onHide={() => setLoginError(false)}>
        <Modal.Header closeButton>
          <Modal.Title>Error</Modal.Title>
        </Modal.Header>
        <Modal.Body>{loginErrorMessage}</Modal.Body>
        <Modal.Footer>
          <Button onClick={() => setLoginError(false)}>Okay</Button>
        </Modal.Footer>
      </Modal>
    </>
  );
}

export default LoginComponent;
