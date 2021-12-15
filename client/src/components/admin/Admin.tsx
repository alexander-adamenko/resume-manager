import React, {useEffect, useState} from "react";
import {Button, Col, Row, Table} from "react-bootstrap";
import {Role, User} from "../../models/User";
import UserService from "../../services/UserService";
import RoleService from "../../services/RoleService";
import { LinkContainer } from "react-router-bootstrap";
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome'
import {faPlus, faTrash} from '@fortawesome/free-solid-svg-icons'

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

    function getDiffOfRoles(arr: Role[]): Role[]{
        let arrMap = arr.map(value => value.name)
        return roles.filter(x => !arrMap.includes(x.name));
    }

    const addRoleToUser = (username: string | undefined, e: HTMLSelectElement) => {
        UserService.addRoleToUser(username, e.options[e.selectedIndex].value).then((response) => {
            handleUsers();
        });
    }
    const removeRoleFromUser = (username: string | undefined, e: HTMLSelectElement) => {
        UserService.removeRoleFromUser(username, e.options[e.selectedIndex].value).then((response) => {
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
        <div>
            <Row  xs={4}>
                <Col lg="11">
                    <LinkContainer to="/user/new" style={{float:"right", margin:"1%"}}>
                        <Button variant="outline-dark">Create new user</Button>
                    </LinkContainer>
                </Col>
            </Row>
            <div className="col-9 m-auto">
            <div className="container-xl">
                <div className="table-title">
                    <h2 className="p-4 text-center">Manage <b>Users</b></h2>
                </div>
            </div>
            {users.length != 0 ? (
                <div>
                    <Table className="table table-striped table-hover">
                        <thead>
                        <tr>
                            <th>Firstname</th>
                            <th>Lastname</th>
                            <th>Username</th>
                            <th>Roles</th>
                            <th>Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        {users.length && users.map((item, index) => {
                            return (
                                <tr>
                                    <td>{item.firstName}</td>
                                    <td>{item.lastName}</td>
                                    <td>{item.username}</td>
                                    <td>{item.roles.map((role, indexR) => {
                                        return (<div>
                                            <div className="w-45">{indexR + 1}. {role.name}</div>
                                        </div>)})}
                                    </td>
                                    <td>
                                        <div>{item.roles.length > 0 && <div>
                                                <select className="m-2" id={"rolesDel" + index} style={{ background:"white"}}>
                                                    {item.roles.map((role) => {
                                                        return (<option style={{ background:"white"}}>{role.name}</option>)
                                                    })}
                                                </select>
                                                <FontAwesomeIcon icon={faTrash} id={"del" + index}
                                                                 color='red'
                                                                 onClick={() => removeRoleFromUser(item.username,
                                                                     document.getElementById("rolesDel" + index) as HTMLSelectElement)}
                                                                 size="1x" style={{cursor: "pointer"}}
                                                                 onMouseOver={() => setColor(document.getElementById("del" + index))}
                                                                 onMouseLeave={() => delColor(document.getElementById("del" + index))}/>
                                            </div>}
                                        </div>
                                        <div>{getDiffOfRoles(item.roles).length > 0 && <div>
                                                <select className="m-2" id={"rolesAdd" + index} style={{ background:"white"}}>
                                                    {getDiffOfRoles(item.roles).map((role) => {
                                                        return (<option style={{ background:"white"}}>{role.name}</option>)
                                                    })}
                                                </select>
                                                <FontAwesomeIcon icon={faPlus} id={"add"+index}
                                                                 color='green'
                                                                 onClick={() => addRoleToUser(item.username,
                                                                     document.getElementById("rolesAdd" + index) as HTMLSelectElement)}
                                                                 size="lg" style={{cursor: "pointer"}}
                                                                 onMouseOver={() => setColor(document.getElementById("add" + index))}
                                                                 onMouseLeave={() => delColor(document.getElementById("add" + index))}/>
                                            </div>}
                                        </div>
                                    </td>
                            </tr>
                        )})}
                        </tbody>
                    </Table>
                </div>
            ): ("Failed to load users") }
            </div>
        </div>
    );

}

export default UserComponent;
