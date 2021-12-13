import React, {useState, useEffect} from "react";
import {Button, Container, Form} from "react-bootstrap";
import {UserDetails} from "../../models/UserDetails";
import UserService from "../../services/UserService";

const Profile = () => {
    const [userDetails, setUserDetails] = useState<UserDetails>();
    const [lastname, setLastname] = useState(userDetails?.lastName)
    const [firstname, setFirstname] = useState(userDetails?.firstName)
    const [username, setUsername] = useState(userDetails?.username)
    const [oldPassword, setOldPassword]=useState("");
    const [newPassword, setNewPassword]=useState("");
    const [newRepeatPassword, setNewRepeatPassword]=useState("");
    const [errorMessage, setErrorMessage]=useState("");
    let [oldPasswordShown, setOldPasswordShown]=useState(false);
    let [newPasswordShown, setNewPasswordShown]=useState(false);
    let [newRepeatPasswordShown, setNewRepeatPasswordShown]=useState(false);

    const loadUser = () => {
        UserService.getCurrentUser().then((response) => {
            const userDetails: UserDetails = response.data;
            setLastname(userDetails?.lastName);
            setFirstname(userDetails?.firstName);
            setUsername(userDetails?.username);
            setOldPassword("");
            setNewPassword("");
            setNewRepeatPassword("");
            setUserDetails(userDetails);
            setErrorMessage("")
        });
    };

    useEffect(() => {
        loadUser();
    }, []);
    const updateUser = () => {
        if (matchPasswords()) {
            UserService.updateUser(userDetails?.username, lastname, firstname, username, oldPassword, newPassword)
        .then(r => {
            setErrorMessage(r.data);
        })
        }
        else setErrorMessage("Passwords don't match");
    };
    const matchPasswords = () => {
        return newPassword == newRepeatPassword;
    }
    return (
        <Container className="center">
                <h2 className="p-4 text-center">Manage <b>Profile</b></h2>
            <Form>
                <Container className="justify-content-center align-items-md-center">
                    <label htmlFor="lastname" className="grey-text">
                        Lastname
                    </label>
                    <input type="text" name="lastname" value={lastname}
                           onChange={(e) => setLastname(e.target.value)}
                           className="form-control"/>
                    <br/>
                    <label htmlFor="firstname" className="grey-text">
                        Firstname
                    </label>
                    <input type="text" name={firstname} value={firstname}
                           onChange={(e) => setFirstname(e.target.value)}
                           className="form-control"/>
                    <br/>
                    <label htmlFor="username" className="grey-text">
                        Username
                    </label>
                    <input type="text" name="username1" value={username}
                           onChange={(e) => setUsername(e.target.value)}
                           className="form-control"/>
                    <br/><p><label htmlFor="oldPassword" className="grey-text">
                        Old password
                    </label>
                    <input autoComplete="off" type={oldPasswordShown ? "text" : "password"} name="oldPassword1" value={oldPassword}
                           onChange={(e) => setOldPassword(e.target.value)}
                           className="form-control"/>
                    <span>Show</span>
                    <input className="m-2" type="checkbox" checked={oldPasswordShown}
                           onChange={(e) => setOldPasswordShown(e.target.checked)}/>
                    </p>
                    <p><label htmlFor="newPassword" className="grey-text">
                        New password
                    </label>
                    <input type={newPasswordShown ? "text" : "password"} name="newPassword" value={newPassword}
                           onChange={(e) => setNewPassword(e.target.value)}
                           className="form-control"/>
                    <span>Show</span>
                    <input className="m-2" type="checkbox" checked={newPasswordShown}
                           onChange={(e) => setNewPasswordShown(e.target.checked)}/>
                    </p>
                    <p><label htmlFor="newRepeatPassword" className="grey-text">
                        Repeat new password
                    </label>
                    <input type={newRepeatPasswordShown ? "text" : "password"} name="newRepeatPassword"
                           value={newRepeatPassword}
                           onChange={(e) => {setNewRepeatPassword(e.target.value);}}
                           className="form-control"/>
                   <span>Show</span>
                        <input className="m-2" type="checkbox" checked={newRepeatPasswordShown}
                               onChange={(e) => setNewRepeatPasswordShown(e.target.checked)}/>
                    </p>
                </Container>
             <p className="text-center mt-4">
                    <span className="text-danger">{errorMessage}</span>
                    <Button variant="outline-info" onClick={updateUser}>Save</Button>
                </p>
            </Form>
        </Container>


    )
}

export default Profile;
