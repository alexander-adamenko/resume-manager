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
        .min(1, "Must be between 1 to 255 characters")
        .max(255, "Must be between 1 to 255 characters"),

      password: Yup.string()
        .required("Required")
        .typeError("Must be a string of characters")
        .min(1, "Must be between 1 to 255 characters")
        .max(255, "Must be between 1 to 255 characters"),
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
      <Form.Group as={Row}>
        <Form.Label column md="2">
          Логін
        </Form.Label>
        <Col md="5">
          <InputGroup className="mr-sm-2">
            <Form.Control
              id="inputUsername"
              name="username"
              value={formik.values.username}
              onChange={formik.handleChange}
            />
          </InputGroup>
        </Col>
        {formik.touched.username && formik.errors.username ? (
          <Col md="4">{formik.errors.username}</Col>
        ) : null}
      </Form.Group>
      <Form.Group as={Row}>
        <Form.Label column md="2">
          Пароль
        </Form.Label>
        <Col md="5">
          <InputGroup className="mr-sm-2">
            <Form.Control
              id="inputPassword"
              name="password"
              type={passwordShow ? "text" : "password"}
              value={formik.values.password}
              onChange={formik.handleChange}
            />
            <InputGroup.Append>
              <InputGroup.Text>Показати</InputGroup.Text>
              <InputGroup.Checkbox
                onClick={() => setPasswordShow(!passwordShow)}
              />
            </InputGroup.Append>
          </InputGroup>
        </Col>
        {formik.touched.password && formik.errors.password ? (
          <Col md="4">{formik.errors.password}</Col>
        ) : null}
      </Form.Group>
      <Button
        type="submit" // so ENTER to submit form works
        value="login"
        className="m-2"
        variant="primary"
        onClick={() => {
          // technically not correct since setting field value directly
          // is async and should return a callable Promise:
          // form might be submitted before the submissionType has changed
          formik.setFieldValue("submissionType", SubmitType.Login, false);
          formik.handleSubmit();
        }}
      >
        Увійти
      </Button>
      <Button
        value="register"
        className="m-2"
        variant="secondary"
        onClick={() => {
          // same hack
          formik.setFieldValue("submissionType", SubmitType.Register, false);
          formik.handleSubmit();
        }}
      >
        Зареєструватись
      </Button>
    </Form>
  );
}

export default LoginForm;
