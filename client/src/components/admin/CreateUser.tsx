import React, {useEffect, useState} from "react";
import {Formik, Field, FormikValues} from 'formik';
import {Button, Card, Col, Form, InputGroup, Row} from "react-bootstrap";
import UserService from "../../services/UserService";
import RoleService from "../../services/RoleService";
import {Role} from "../../models/User";
import { useHistory } from "react-router-dom";
const CreateUser = () => {
    const [allRoles, setAllRoles] = useState<Role[]>([]);
    const [error, setError] = useState("");
    let history = useHistory();
    let [passwordShown, setPasswordShown]=useState(false);
    let [repeatPasswordShown, setRepeatPasswordShown]=useState(false);

    useEffect(() => {
        handleRoles();
    }, []);

    const handleRoles = () => {
        RoleService.getAllRoles().then((response) => {
            setAllRoles(response.data);
        });
    };

    function createUser(values: FormikValues) {
        if (values.password === values.submittedPassword) {
            if (values.roles.length !== 0) {
                if (values.username.length >= 4 && values.username.length <= 20) {
                    if (values.password.length >= 8 && values.password.length <= 20) {
                        UserService.createUser(values).then(r => console.log(r.data));
                        history.push('/admin');
                        history.go(0);
                    } else setError("The password must be between 8 and 20 characters long.");
                } else setError("The username " + values.username + " must be between 4 and 20 characters long.");
            } else setError("The user must have at least one role.");
        } else setError("Passwords don't match");
    }

    return (<Formik
        initialValues={{username: '', password: '', firstName: '', lastName: '', submittedPassword: '', roles: []}}

        onSubmit={(values) => {
            createUser(values);
        }}
    >
        {({
              values,
              errors,
              touched,
              handleChange,
              handleBlur,
            handleSubmit
          }) => (
            <Card className="col-7 m-auto">
                <h3 className="mt-2 text-center">Create new User</h3>
                <Card.Body>
                    <Form onSubmit={handleSubmit}>
                        {/*FIRSTNAME*/}
                        <Form.Group as={Row}>
                            <Form.Label column md="4">
                                Firstname:
                            </Form.Label>
                            <Col md="8">
                                <InputGroup className="mb-2 mr-sm-2">
                                    <Form.Control
                                        name="firstName"
                                        onChange={handleChange}
                                        onBlur={handleBlur}
                                        value={values.firstName}
                                        required
                                    />
                                </InputGroup>
                            </Col>
                        </Form.Group>
                        {/*LASTNAME*/}
                        <Form.Group as={Row}>
                            <Form.Label column md="4">
                                Lastname:
                            </Form.Label>
                            <Col md="8">
                                <InputGroup className="mb-2 mr-sm-2">
                                    <Form.Control
                                        name="lastName"
                                        onChange={handleChange}
                                        onBlur={handleBlur}
                                        value={values.lastName}
                                        required
                                    />
                                </InputGroup>
                            </Col>
                        </Form.Group>
                        {/*USERNAME*/}
                        <Form.Group as={Row}>
                            <Form.Label column md="4">
                                Username:
                            </Form.Label>
                            <Col md="8">
                                <InputGroup className="mb-2 mr-sm-2">
                                    <Form.Control
                                        name="username"
                                        onChange={handleChange}
                                        onBlur={handleBlur}
                                        value={values.username}
                                        required
                                    />
                                </InputGroup>
                            </Col>
                        </Form.Group>
                        {errors.username && touched.username && <div>{errors.username}</div>}
                        {/*PASSWORD*/}
                        <Form.Group as={Row}>
                            <Form.Label column md="4">
                                Password:
                            </Form.Label>
                            <Col md="8">
                                <Form.Control
                                    type={passwordShown ? "text" : "password"}
                                    name="password"
                                    onChange={handleChange}
                                    onBlur={handleBlur}
                                    value={values.password}
                                    required
                                />
                                <span>Show</span>
                                <input className="m-2" type="checkbox" checked={passwordShown}
                                       onChange={(e) => setPasswordShown(e.target.checked)}/>
                            </Col>
                        </Form.Group>
                        {/*SUBMITTED PASSWORD*/}
                        <Form.Group as={Row}>
                            <Form.Label column md="4">
                                Submit password:
                            </Form.Label>
                            <Col md="8">
                                <Form.Control
                                    type={repeatPasswordShown ? "text" : "password"}
                                    id="submittedPassword"
                                    onChange={handleChange}
                                    onBlur={handleBlur}
                                    required
                                />
                                <span>Show</span>
                                <input className="m-2" type="checkbox" checked={repeatPasswordShown}
                                       onChange={(e) => setRepeatPasswordShown(e.target.checked)}/>
                            </Col>
                        </Form.Group>
                        {/*ROLE*/}
                        <Form.Group as={Row}>
                            <Form.Label column md="4">
                                Role:
                            </Form.Label>
                            <Col md="8">
                                <Form>
                                    {allRoles.map((role) => {
                                        return (
                                            <InputGroup className="mb-2 mr-sm-2">
                                                <label><Field
                                                    type="checkbox"
                                                    name="roles"
                                                    value={role.name}
                                                    style={{margin:"5px"}}/>
                                                    {role.name}
                                                </label>
                                            </InputGroup>)
                                    })}
                                </Form>
                            </Col>
                        </Form.Group>
                        <p className="text-danger">{error}</p>
                        <Button type="submit">
                            Submit
                        </Button>
                    </Form>
                </Card.Body>
            </Card>
        )}
    </Formik>)
};
export default CreateUser;
