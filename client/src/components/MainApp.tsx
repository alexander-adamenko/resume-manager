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

import { useEffect } from "react";
import Loading from "./Loading";
import Error from "./Error";
import Admin from "./admin/Admin";
import CreateVacancy from "./vacancy/CreateVacancy";
import UploadingCandidate from "./Candidates/UploadingCandidate";
import Vacancies from "./vacancy/Vacancies";
import Profile from "./profile/Profile";
import Candidates from "./Candidates/Candidates";
import FullCandidateComponent from "./Candidates/FullCandidate";

const PrivateRoute = ({ ...props }) => {
  useEffect(() => {
    LoginService.isLoggedIn()
      .then((response) => {
        props.setUsername(response.data);
        props.setIsLoggedIn(true);
      })
      .catch(() => props.setIsLoggedIn(false));
  }, []);

  return props.isLoggedIn === null ? (
    <Loading />
  ) : props.isLoggedIn ? (
    <Route {...props} />
  ) : (
    <Redirect to="/login/" />
  );
};

const MainApp = () => {
  const [isLoggedIn, setIsLoggedIn] = useState(null);
  const [username, setUsername] = useState("");

  return (
    <>
      <Router>
        <NavigationBar  isLoggedIn={isLoggedIn === null ? false : isLoggedIn} />
        <div className="container-fluid flex-grow-1">
          <Switch>
            <Route path="/" exact component={Login} />
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
            <PrivateRoute
                isLoggedIn={isLoggedIn}
                setIsLoggedIn={setIsLoggedIn}
                username={username}
                setUsername={setUsername}
                exact path="/vacancies"
                render={(props: RouteComponentProps) => (
                    <Vacancies />
                )}
            />
            <PrivateRoute
                isLoggedIn={isLoggedIn}
                setIsLoggedIn={setIsLoggedIn}
                username={username}
                setUsername={setUsername}
                path="/vacancies/new"
                render={(props: RouteComponentProps) => (
                    <CreateVacancy history={props.history} location={props.location} match={props.match} />
                )}
            />
            <PrivateRoute
                isLoggedIn={isLoggedIn}
                setIsLoggedIn={setIsLoggedIn}
                username={username}
                setUsername={setUsername}
                exact path="/candidates"
                render={(props: RouteComponentProps) => (
                    <Candidates/>
                )}
            />
            <PrivateRoute
                isLoggedIn={isLoggedIn}
                setIsLoggedIn={setIsLoggedIn}
                username={username}
                setUsername={setUsername}
                exact path="/candidates/:id"
                render={(props: RouteComponentProps) => (
                    <FullCandidateComponent history={props.history} location={props.location} match={props.match} />
                )}
            />
            <PrivateRoute
                isLoggedIn={isLoggedIn}
                setIsLoggedIn={setIsLoggedIn}
                username={username}
                setUsername={setUsername}
                path="/candidate/new/"
                render={(props: RouteComponentProps) => (
                    <UploadingCandidate />
                )}
            />
            <PrivateRoute
                isLoggedIn={isLoggedIn}
                setIsLoggedIn={setIsLoggedIn}
                username={username}
                setUsername={setUsername}
                path="/profile"
                render={(props: RouteComponentProps) => (
                    <Profile/>
                )}
            />
            <Route component={Error} />
          </Switch>
        </div>
        <footer className="bg-dark text-center fixed-bottom" style={{bottom:'0', width: '100%'}}>
          123312
        </footer>
      </Router>
    </>
  );
};

export default MainApp;
