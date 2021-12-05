import {Candidate} from "../../models/Candidate";
import {Card, Col, Table} from "react-bootstrap";
import {SERVER_API_URL} from "../../constants";
import React, {useEffect, useState} from "react";
import CandidateService from "../../services/CandidateService";

interface Props {
    history: any
    location: any
    match: any
}

const FullCandidateComponent = (props: Props) => {
    const [candidate, setCandidate] = useState<Candidate>()
    useEffect(() => {
        handleCandidate()
    }, [])

    const handleCandidate = () =>{
        CandidateService.getCandidate(props.match.params.id).then(value => {
            setCandidate(value.data)
        })
    }
    return(
        <div>
            <Col className="col-6 m-auto">
                <Card>
                    <Card.Body>
                        <Card.Title>{candidate?.name}</Card.Title>
                        <Card.Subtitle className="mb-2 text-muted">
                            Email: {candidate?.email}
                        </Card.Subtitle>
                        <Card.Subtitle className="mb-2 text-muted">
                            Phone: {candidate?.phone}
                        </Card.Subtitle>
                        <Card.Text>Degree: {candidate?.degree}</Card.Text>
                        <Card.Text>{candidate?.aboutMe}</Card.Text>
                        <Card.Link
                            href={`${SERVER_API_URL}/resumes/${candidate?.filePath.substring(candidate?.filePath.lastIndexOf("\\") + 1)}`} target="_blank">
                            &#128194;
                        </Card.Link>
                        <Table>
                        <thead>
                        <tr>
                            <th className="text-center">Skill</th>
                            <th className="text-center">Level</th>
                        </tr>
                        </thead>
                        <tbody>
                        {candidate?.candidateSkills.map((candidateSkill, index) => {
                            return (
                                <tr key={index}>
                                    <td>{candidateSkill?.skill.name}</td>
                                    <td>{candidateSkill?.level}</td>
                                </tr>)})}
                        </tbody>
                    </Table>
                    </Card.Body>
                </Card>
            </Col>
        </div>
    )
}

export default FullCandidateComponent;
