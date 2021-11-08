import React, {Component, useState} from "react";
import {Button, ButtonGroup, Card, Col, Row} from "react-bootstrap";
import {User, UserList} from "../../models/User";
import UserService from "../../services/UserService";


const UserComponent = () => {
    const [options, setOptions] = useState<User[]>([]);

    const handleUsers = () => {
        UserService.getAllUsers().then((response) => {
            console.log("weqweqwe")
            console.log(response.data.toString())
            const options: User[] = response.data;
            setOptions(options);
        });
    };
    UserService.getAllUsers().then((response) => {
        console.log("weqweqwe")
        console.log(response.data.toString())
        const options: User[] = response.data;
        setOptions(options);
    });

    return (
        <>
            <Row>
                <Button variant="outline-info" onClick={handleUsers}>
                    refresh
                </Button>
            </Row>
                {options[0] !== undefined ?(
                    /////////////////////////////
                    // {options}.options.map(user => {
                    //     asd++;
                    //     if (asd%3 ==0) {
                    //         return (
                    //             <Card style={{width: '18rem'}}>
                    //                 <Card.Body>
                    //                     <Card.Title>{user.username}</Card.Title>
                    //                     <Card.Subtitle className="mb-2 text-muted">{user.firstName} {user.lastName}</Card.Subtitle>
                    //                     <Card.Text>
                    //                         qweqweqwe
                    //                     </Card.Text>
                    //                     <Card.Link href="#">Card Link</Card.Link>
                    //                     <Card.Link href="#">Another Link</Card.Link>
                    //                 </Card.Body>
                    //             </Card>
                    //         );
                    //     }
                    //     else {
                    //         return (
                    //             <Card style={{width: '18rem'}}>
                    //                 <Card.Body>
                    //                     <Card.Title>{user.username}</Card.Title>
                    //                     <Card.Subtitle className="mb-2 text-muted">{user.firstName} {user.lastName}</Card.Subtitle>
                    //                     <Card.Text>
                    //                         qweqweqwe
                    //                     </Card.Text>
                    //                     <Card.Link href="#">Card Link</Card.Link>
                    //                     <Card.Link href="#">Another Link</Card.Link>
                    //                 </Card.Body>
                    //             </Card>
                    //         );
                    //     }
                    // })
                    ///////////////////////////////////////
                    //row row-cols-1 row-cols-md-2 g-4
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