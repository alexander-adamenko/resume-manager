import React, { useState } from "react";
import {
  BrowserRouter as Router,
  Redirect,
  Route,
  RouteComponentProps,
  Switch,
} from "react-router-dom";

import "bootstrap/dist/css/bootstrap.min.css";

import Login from "./Login/Login";
import NavigationBar from "./NavigationBar/NavigationBar";
import LoginService from "../services/LoginService";
import FrontPage from "./FrontPage";

import { useEffect } from "react";
import Loading from "./Loading";
import Error from "./Error";
import Admin from "./admin/Admin";
import {UserList} from "../models/User";

const PrivateRoute = ({ ...props }) => {
  useEffect(() => {
    LoginService.isLoggedIn()
      .then((response) => {
        props.setUsername(response.data.username);
        props.setIsLoggedIn(true);
      })
      .catch(() => props.setIsLoggedIn(false));
  }, [props]);

  return props.isLoggedIn === null ? (
    <Loading />
  ) : props.isLoggedIn ? (
    <Route {...props} />
  ) : (
    <Redirect to="/login/" />
  );
};

const MainApp = () => {
  const [asd, setAsd] = useState<UserList | null>(
      null
  );

  const [isLoggedIn, setIsLoggedIn] = useState(null);
  const [username, setUsername] = useState("");


  // refactor the PrivateRoute stuff soon
  return (
    <>
      <Router>
        <NavigationBar  isLoggedIn={isLoggedIn === null ? false : isLoggedIn} />
        <div className="container-fluid flex-grow-1">
          <Switch>
            {/* some paths DON'T have to be exact in this case */}
            <Route path="/" exact component={FrontPage} />
            <Route path="/login/" component={Login} />
            <PrivateRoute
              isLoggedIn={isLoggedIn}
              setIsLoggedIn={setIsLoggedIn}
              username={username}
              setUsername={setUsername}
              path="/admin/"
              render={(props: RouteComponentProps) => (
                  <Admin />
              )}
            />
            <Route component={Error} />
          </Switch>
        </div>
        <footer className="bg-dark text-center">
          123312
        </footer>
      </Router>
    </>
  );
};

export default MainApp;
