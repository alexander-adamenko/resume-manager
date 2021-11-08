import React, {Component, useState} from "react";
import {Button, ButtonGroup, Row} from "react-bootstrap";
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

    return (
        <Row>
            <Button variant="outline-info" onClick={handleSearch}>
                Знайти
            </Button>
            {options[0] !== undefined ?({options}.options[0].username): ("gecnj")}
        </Row>
    );
}

export default UserComponent;