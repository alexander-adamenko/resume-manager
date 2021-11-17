import React, {Component, useEffect, useState} from "react";
import {Button, ButtonGroup, Card, Col, Row} from "react-bootstrap";
import {User, UserList} from "../../models/User";
import UserService from "../../services/UserService";


const UserComponent = () => {
    const [options, setOptions] = useState<User[]>([]);

    useEffect(() => {
        handleUsers();
    },[]);
    const handleUsers = () => {
        UserService.getAllUsers().then((response) => {
            setOptions(response.data);
        });
    };
    return (
        <>
            <Row>
                {options[0] !== undefined ?("qqqqqqqqqqqqqq"): ("sdfsdfsdfsdfsdf")}
            </Row>
            {options[0] !== undefined ?(
                <Row xs={1} md={4} className="g-3">
                    {options.length && options.map((item, index) => {
                        return (
                            <>
                            <Col key={index}>
                                <Card style={{ width: '18rem'}}>
                                    <Card.Body>
                                        <Card.Title>{item.username}</Card.Title>
                                        <Card.Subtitle className="mb-2 text-muted">{item.firstName} {item.lastName}</Card.Subtitle>
                                        <Card.Text>
                                            {item.roles.map((role, indexR) => {
                                                return (
                                                    <Row>
                                                        <div style={{marginLeft: '10px'}}>{indexR + 1}. {role.name}</div>
                                                    </Row>
                                                );
                                            })}
                                        </Card.Text>
                                        <Card.Link href="#">Card Link</Card.Link>
                                        <Card.Link href="#">Another Link</Card.Link>
                                    </Card.Body>
                                </Card>
                            </Col>
                            </>
                        )
                    })}
                </Row>
            ): ("gecnj")}

        </>
    );

}

export default UserComponent;