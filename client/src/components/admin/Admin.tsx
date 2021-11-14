import React, {useEffect, useState} from "react";
import {Card, Col, Row} from "react-bootstrap";
import {User} from "../../models/User";
import UserService from "../../services/UserService";


const UserComponent = () => {
    const [users, setUsers] = useState<User[]>([]);

    useEffect(() => {
        handleUsers();
    },[]);
    const handleUsers = () => {
        UserService.getAllUsers().then((response) => {
            setUsers(response.data);
        });
    };
    return (
        <>
            <Row>
                {users[0] !== undefined ?("qqqqqqqqqqqqqq"): ("sdfsdfsdfsdfsdf")}
            </Row>
            {users[0] !== undefined ?(
                <Row xs={1} md={4} className="g-3">
                    {users.length && users.map((item, index) => {
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