import React, {Component, useEffect, useState} from "react";
import VacancyService from "../../services/VacancyService";
import {Vacancy} from "../../models/Vacancy";
import {Card, Col, Row} from "react-bootstrap";
import {LinkContainer} from "react-router-bootstrap";
import {generatePath, Router} from "react-router";
import {Route, RouteComponentProps, BrowserRouter} from "react-router-dom";
import VacancyComponent from "./Vacancy";
import Switch from "react-bootstrap/Switch";
import Error from "../Error";
import {render} from "react-dom";

const VacanciesComponent = (props: any) => {
    const [vacancies, setVacancies] = useState<Vacancy[]>([]);
    const [currentVacancyId, setCurrentVacancyId] = useState<number>();
    useEffect(() => {
        handleVacancies();

    },[]);
    const handleVacancies = () => {
        VacancyService.getAllVacancies().then((response) => {
            setVacancies(response.data);
        });
    };

    const AComponent = () => {
      return(
          <>
              {/*<Col>*/}
              {/*    <Card style={{ width: '18rem'}}>*/}
              {/*        <Card.Body>*/}
              {/*            <Placeholder as={Card.Title} animation="glow">*/}
              {/*                <Placeholder xs={6} bg="primary"/>*/}
              {/*            </Placeholder>*/}
              {/*            <Placeholder as={Card.Subtitle} animation="glow">*/}
              {/*                <Placeholder xs={3} bg="secondary"/>*/}
              {/*            </Placeholder>*/}

              {/*            <Placeholder as={Card.Text} animation="glow">*/}
              {/*                <Placeholder xs={9} />*/}
              {/*                <Placeholder xs={4} />*/}
              {/*                <Placeholder xs={4} />{' '}*/}
              {/*                <Placeholder xs={6} />*/}
              {/*                <Placeholder xs={7} />*/}
              {/*                <Placeholder xs={6} />*/}
              {/*                <Placeholder xs={12} />*/}
              {/*                <Placeholder xs={12} />*/}
              {/*                <Placeholder xs={5} />*/}
              {/*            </Placeholder>*/}
              {/*        </Card.Body>*/}
              {/*    </Card>*/}
              {/*</Col>*/}
          </>
      )
    }

    return (
        <>
            {vacancies[0] !== undefined ?(
                <Row xs={1} md={4} className="g-3 mt-3" style={{marginBottom: '50px'}}>
                    {vacancies.length && vacancies.map((item, index) => {
                        return (
                            <>
                                <Col key={index}>
                                    <Card style={{ width: '30rem', height: '100%', display: 'flex', flexDirection: 'column', justifyContent: 'space-between'}}>
                                        <Card.Body>
                                            <LinkContainer to={`/vacancies/${item.id}/`}>
                                                <Card.Link>
                                                <Card.Title>{item.positionTitle}</Card.Title>
                                                </Card.Link>
                                            </LinkContainer>
                                            <Card.Subtitle className="mb-2 text-muted">{item.location}</Card.Subtitle>
                                            <Card.Text style={{marginLeft: '10px'}}>
                                                    <Row>
                                                        Minimal years of experience: {item.minimumYearsOfExperience}
                                                    </Row>
                                                    <Row>
                                                        Required degree: {item.degree}
                                                    </Row>
                                                    <Row>
                                                        Required skills:
                                                    </Row>
                                                    {item.vacancySkills.map((vacancySkill, indexR) => {
                                                        return (
                                                            <Row>
                                                                {indexR + 1}. {vacancySkill.skill.name} {vacancySkill.level}
                                                            </Row>
                                                        );
                                                    })}
                                            </Card.Text>
                                        </Card.Body>
                                    </Card>
                                </Col>
                            </>
                        )
                    })}
                </Row>
            ): (
                <Row xs={1} md={4} className="g-3">
                    <AComponent/><AComponent/><AComponent/><AComponent/><AComponent/><AComponent/><AComponent/><AComponent/><AComponent/><AComponent/><AComponent/><AComponent/>
                </Row>

            )}
        </>
    );
}

export default VacanciesComponent;
