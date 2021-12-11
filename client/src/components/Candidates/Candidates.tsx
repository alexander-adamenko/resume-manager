import React, {useEffect, useState} from "react";
import {Candidate} from "../../models/Candidate";
import CandidateService from "../../services/CandidateService";

import {SERVER_API_URL} from "../../constants";
import {Card, Col, Row} from "react-bootstrap";
import {LinkContainer} from "react-router-bootstrap";



const CandidatesComponent = () => {
    const [candidates, setCandidates] = useState<Candidate[]>([]);

    useEffect(() => {
        handleCandidates()
    }, []);

    const handleCandidates = () => {
        CandidateService.getAllCandidates()
            .then((response) => {
                console.log(response.data)
                setCandidates(response.data);
            });
    };

    return (
        <div>
            <div className="col-9 m-auto">
                <h3 className="text-center m-3">All Candidates</h3>
                <Row xs={1} md={4} className="g-3">
                    {candidates.length > 0 && candidates.map((item, index) => {
                        return (
                            <div>
                            {item.name != null &&
                            <Col key={index}>
                                <Card style={{ width: '18rem' }}>
                                    <Card.Body>
                                        <Card.Title>{item.name}</Card.Title>
                                        <Card.Subtitle className="mb-2 text-muted">{item.email} {item.phone}</Card.Subtitle>
                                        <Card.Text>Degree: {item.degree}</Card.Text>
                                        <Card.Text>{item.aboutMe}</Card.Text>
                                        <Card.Link
                                            href={`${SERVER_API_URL}/resumes/${item.filePath.substring(item.filePath.lastIndexOf("\\") + 1)}`} target="_blank">
                                            &#128194;
                                        </Card.Link>
                                        <LinkContainer to={`/candidates/${item.id}`}>
                                            <Card.Link>See full</Card.Link>
                                        </LinkContainer>
                                    </Card.Body>
                                </Card>
                            </Col>}
                            </div>

                        )}
                    )}
                </Row>
            </div>
        </div>
    )
}
export default CandidatesComponent;
