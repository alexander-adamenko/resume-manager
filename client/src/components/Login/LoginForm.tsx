import React, { useState } from "react";

import { Row, Col, Button, Form, InputGroup } from "react-bootstrap";

import { useFormik } from "formik";
import * as Yup from "yup";
import { LoginDetails } from "../../models/AccountDetails";

enum SubmitType {
  Login,
  Register,
}

interface Props {
  loginAction: (loginDetails: LoginDetails) => void;
}

function LoginForm(props: Props) {
  const [passwordShow, setPasswordShow] = useState(false);

  const formik = useFormik({
    initialValues: {
      username: "",
      password: "",
      submissionType: SubmitType.Login,
    },

    validationSchema: Yup.object({
      username: Yup.string()
        .required("Required")
        .typeError("Must be a string of characters")
        .min(4, "Must be between 4 to 16 characters")
        .max(16, "Must be between 4 to 16 characters"),

      password: Yup.string()
        .required("Required")
        .typeError("Must be a string of characters")
        .min(8, "Must be between 8 to 16 characters")
        .max(16, "Must be between 8 to 16 characters"),
    }),

    onSubmit: (values) => {
      if (values.submissionType === SubmitType.Login) {
        props.loginAction({
          username: values.username,
          password: values.password,
        });
      }
    },
    enableReinitialize: true,
  });

  return (
    <Form onSubmit={formik.handleSubmit}>
      <Form.Group as={Row} style={{marginBottom: '10px'}}>
        <Form.Label column md="4" className="text-end">
          Login
        </Form.Label>
        <Col md="6">
          <InputGroup className="mr-sm-2">
            <Form.Control
              id="inputUsername"
              name="username"
              value={formik.values.username}
              onChange={formik.handleChange}
              isInvalid={!!formik.errors.username}
            />
            <Form.Control.Feedback type="invalid">
              {formik.errors.username}
            </Form.Control.Feedback>
          </InputGroup>
        </Col>
        <Col md="2">
        </Col>
      </Form.Group>
      <Form.Group as={Row} style={{marginBottom: '10px'}}>
        <Form.Label column md="4" className="text-end">
          Password
        </Form.Label>
        <Col md="6">
          <InputGroup className="mr-sm-2">
            <Form.Control
              id="inputPassword"
              name="password"
              type={passwordShow ? "text" : "password"}
              value={formik.values.password}
              onChange={formik.handleChange}
              isInvalid={!!formik.errors.password}
            />
              <InputGroup.Text>Show</InputGroup.Text>
              <InputGroup.Checkbox
                onClick={() => setPasswordShow(!passwordShow)}
              />
            <Form.Control.Feedback type="invalid">
              {formik.errors.password}
            </Form.Control.Feedback>
          </InputGroup>
        </Col>
        <Col md="2">
        </Col>
      </Form.Group>
      <Form.Group as={Row} >
      <Col md="8">
      </Col>
      <Col md="2">
      <Button
        type="submit"
        value="login"
        variant="primary"
        onClick={() => {
          formik.setFieldValue("submissionType", SubmitType.Login, false);
          formik.handleSubmit();
        }}
      >
        Sing in
      </Button>
      </Col>
      <Col md="2">
      </Col>
      </Form.Group>
    </Form>
  );
}

export default LoginForm;
