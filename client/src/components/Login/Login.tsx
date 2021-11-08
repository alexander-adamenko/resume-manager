import React, { useState } from "react";
import { Row, Container, Jumbotron, Modal, Button } from "react-bootstrap";

import LoginService from "../../services/LoginService";
import LoginForm from "./LoginForm";
import { RouteComponentProps } from "react-router-dom";
import { LoginDetails } from "../../models/AccountDetails";


const LoginComponent = (props: RouteComponentProps) => {
  const [registerError, setRegisterError] = useState(false);
  const [registerErrorMessage, setRegisterErrorMessage] = useState("");
  const [loginError, setLoginError] = useState(false);
  const [loginErrorMessage, setLoginErrorMessage] = useState("");

  // const register = ({username, password}: LoginDetails) => {
  //   RegisterService.register({username, password})
  //     .then((_response) => {
  //       // successful registration is immediately followed by auto-login
  //       login({username, password});
  //     })
  //     .catch((err) => {
  //       if (err.response) {
  //         if (err.response.status === 409) {
  //           // CONFLICT status code: registering with duplicate username
  //           setRegisterErrorMessage(
  //             `${err.response.data.title}: ${err.response.data.message}`
  //           );
  //           setRegisterError(true);
  //         }
  //       } else {
  //         // other errors
  //         setRegisterErrorMessage("Не вдалося зареєструватись.");
  //         setRegisterError(true);
  //       }
  //     });
  // };

  const login = ({username, password}: LoginDetails) => {
    LoginService.login({username, password})
      .then((_response) => {
        // redirect to portfolios page
        props.history.push("/admin/");
      })
      .catch((err) => {
        if (err.response) {
          if (err.response.status === 422) {
            // UNPROCESSABLE_ENTITY status code: bad username or password
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
          <Jumbotron className="col-lg-10 offset-1 mx-auto text-center">
            <LoginForm loginAction={login} />
          </Jumbotron>
        </Container>
      </Row>

      <Modal show={registerError} onHide={() => setRegisterError(false)}>
        <Modal.Header closeButton>
          <Modal.Title>Error</Modal.Title>
        </Modal.Header>
        <Modal.Body>{registerErrorMessage}</Modal.Body>
        <Modal.Footer>
          <Button onClick={() => setRegisterError(false)}>Okay</Button>
        </Modal.Footer>
      </Modal>

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
