import React, {Component, useState} from "react";
import {Button, ButtonGroup, Card, Row} from "react-bootstrap";
import {User, UserList} from "../../models/User";
import UserService from "../../services/UserService";


const UserComponent = () => {
    const [options, setOptions] = useState<User[]>([]);

    const handleSearch = () => {
        UserService.getAllUsers().then((response) => {
            console.log("weqweqwe")
            console.log(response.data.toString())
            const options: User[] = response.data;
            setOptions(options);
            //setIsLoading(false);
            //return response.data.users;
        });
    };
    let asd= 0;

//{options}.options[0].username
    return (
        <>
            <Row>
                <Button variant="outline-info" onClick={handleSearch}>
                    Знайти
                </Button>
            </Row>
                {options[0] !== undefined ?(
                    {options}.options.map(user => {
                        asd++;
                        if (asd%3 ==0) {
                            return (
                                <Card style={{width: '18rem'}}>
                                    <Card.Body>
                                        <Card.Title>{user.username}</Card.Title>
                                        <Card.Subtitle className="mb-2 text-muted">{user.firstName} {user.lastName}</Card.Subtitle>
                                        <Card.Text>
                                            qweqweqwe
                                        </Card.Text>
                                        <Card.Link href="#">Card Link</Card.Link>
                                        <Card.Link href="#">Another Link</Card.Link>
                                    </Card.Body>
                                </Card>
                            );
                        }
                        else {
                            return (
                                <Card style={{width: '18rem'}}>
                                    <Card.Body>
                                        <Card.Title>{user.username}</Card.Title>
                                        <Card.Subtitle className="mb-2 text-muted">{user.firstName} {user.lastName}</Card.Subtitle>
                                        <Card.Text>
                                            qweqweqwe
                                        </Card.Text>
                                        <Card.Link href="#">Card Link</Card.Link>
                                        <Card.Link href="#">Another Link</Card.Link>
                                    </Card.Body>
                                </Card>
                            );
                        }
                    })
                ): ("gecnj")}

        </>
    );
}

export default UserComponent;