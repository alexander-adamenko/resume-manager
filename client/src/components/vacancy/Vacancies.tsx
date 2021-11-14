import React, {useEffect, useState} from "react";
import VacancyService from "../../services/VacancyService";
import {Vacancy} from "../../models/Vacancy";
import {Card, Col, Row} from "react-bootstrap";
import {LinkContainer} from "react-router-bootstrap";

const VacancyComponent = () => {
    const [vacancies, setVacancies] = useState<Vacancy[]>([]);

    useEffect(() => {
        handleVacancies();
    },[]);

    const handleVacancies = () => {
        VacancyService.getAllVacancies().then((response) => {
            setVacancies(response.data);
        });
    };

    return (
        <>
            {vacancies[0] !== undefined ?(
                <Row xs={1} md={4} className="g-3">
                    {vacancies.length && vacancies.map((item, index) => {
                        return (
                            <>
                                <Col key={index}>
                                    <Card style={{ width: '18rem'}}>
                                        <Card.Body>
                                            <Card.Title>{item.positionTitle}</Card.Title>
                                            <Card.Subtitle className="mb-2 text-muted">{item.location}</Card.Subtitle>
                                            <Card.Text>
                                                <div style={{marginLeft: '10px'}}>
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
                                                            <div style={{marginLeft: '10px'}}>{indexR + 1}. {vacancySkill.skill.name} {vacancySkill.level}</div>
                                                        </Row>
                                                    );
                                                })}
                                                {item.description}
                                                </div>
                                            </Card.Text>
                                            <LinkContainer to="/">
                                            <Card.Link>Card Link</Card.Link>
                                            </LinkContainer>
                                            <Card.Link href="#">Another Link</Card.Link>
                                        </Card.Body>
                                    </Card>
                                </Col>
                            </>
                        )
                    })}
                </Row>
            ): ("Failed to upload data.")}
        </>
    );
}

export default VacancyComponent;