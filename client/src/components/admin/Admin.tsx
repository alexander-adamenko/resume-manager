import React, {useEffect, useState} from "react";
import {Card, Col, Row} from "react-bootstrap";
import {User, Role} from "../../models/User";
import UserService from "../../services/UserService";
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome'
import {faTrash, faPlus} from '@fortawesome/free-solid-svg-icons'
import RoleService from "../../services/RoleService";

const UserComponent = () => {
    const [users, setUsers] = useState<User[]>([]);
    const [roles, setRoles] = useState<Role[]>([]);

    useEffect(() => {
        handleUsers();
        handleRoles();
    }, []);

    const handleUsers = () => {
        UserService.getAllUsers().then((response) => {
            setUsers(response.data);
        });
    };
    const handleRoles = () => {
        RoleService.getAllRoles().then((response) => {
            setRoles(response.data);
        });
    };
    const addRoleToUser = (username: string | undefined, e: HTMLSelectElement) => {
        UserService.addRoleToUser(username, e.options[e.selectedIndex].value).then((response) => {
            handleUsers();
        });
    }
    const removeRoleFromUser = (username: string | undefined, roleName: string | undefined) => {
        UserService.removeRoleFromUser(username, roleName).then((response) => {
            handleUsers();
        });
    }
    const setColor = (e: HTMLElement | null) => {
        // @ts-ignore
        e.style.color = "red";
    }
    const delColor = (e: HTMLElement | null) => {
        // @ts-ignore
        e.style.color = "black";
    }
    return (
        <>
            <Row>
                {users[0] !== undefined ? ("qqqqqqqqqqqqqq") : ("sdfsdfsdfsdfsdf")}
            </Row>
            {users[0] !== undefined ? (
                <Row xs={1} md={4} className="g-3">
                    {users.length && users.map((item, index) => {
                        return (
                            <>
                                <Col key={index}>
                                    <Card style={{width: '18rem'}}>
                                        <Card.Body>
                                            <Card.Title>{item.username}</Card.Title>
                                            <Card.Subtitle
                                                className="mb-2 text-muted">{item.firstName} {item.lastName}</Card.Subtitle>
                                            <Card.Text>
                                                {item.roles.map((role, indexR) => {
                                                    return (
                                                        <Row>
                                                            <div
                                                                style={{marginLeft: '10px'}}>{indexR + 1}. {role.name}</div>
                                                            <FontAwesomeIcon icon={faTrash}
                                                                             id={"del" + index + "." + indexR}
                                                                             onClick={() => removeRoleFromUser(item.username, role.name)}
                                                                             size="1x" style={{cursor: "pointer"}}
                                                                             onMouseOver={() => setColor(document.getElementById("del" + index + "." + indexR))}
                                                                             onMouseLeave={() => delColor(document.getElementById("del" + index + "." + indexR))}/>
                                                        </Row>
                                                    );
                                                })}


                                            </Card.Text>
                                            <div>
                                                <select id={"role" + index} style={{ background:"white"}}>
                                                    {roles.map((role) => {
                                                        return (<option style={{ background:"white"}}>{role.name}</option>)
                                                    })
                                                    }
                                                </select>
                                                <FontAwesomeIcon icon={faPlus} id={"add"+index}
                                                                 onClick={() => addRoleToUser(item.username,
                                                                     document.getElementById("role" + index) as HTMLSelectElement)}
                                                                 size="lg" style={{cursor: "pointer"}}
                                                                 onMouseOver={() => setColor(document.getElementById("add"+index))}
                                                                 onMouseLeave={() => delColor(document.getElementById("add"+index))}/>
                                            </div>
                                            <Card.Link href="#">Card Link</Card.Link>
                                            <Card.Link href="#">Another Link</Card.Link>
                                        </Card.Body>
                                    </Card>
                                </Col>
                            </>

                        )
                    })}
                </Row>
            ) : ("gecnj")}

        </>
    );

}

export default UserComponent;