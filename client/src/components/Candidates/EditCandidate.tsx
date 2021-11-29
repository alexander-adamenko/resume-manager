import React, {useEffect, useState} from "react";
import {Button, Col, Form, FormControl, FormLabel, InputGroup, Row, Table} from "react-bootstrap";
import CandidateService from "../../services/CandidateService";
import {Candidate} from "../../models/Candidate";
import {CandidateSkill} from "../../models/CandidateSkill";
import {Skill, SkillsDegreesLevelsCities} from "../../models/Vacancy";
import VacancyService from "../../services/VacancyService";
import {useFormik} from "formik";

interface Props {
    parsedCandidate: Candidate;
}

const EditCandidateComponent = ({parsedCandidate}: Props) => {
    const [candidate, setCandidate] = useState<Candidate>(parsedCandidate);
    const [skillSet, setSkillSet] = useState<CandidateSkill[]>([])
    const [data, setData] = useState<SkillsDegreesLevelsCities>();

    const handleSkillsDegreesLevelsCities = () => {
        VacancyService.getAllSkillsDegreesLevelsCities().then((response) => {
            setData(response.data);
        });
    };
    useEffect(() => {
        handleSkillsDegreesLevelsCities()
        setCandidate(parsedCandidate)
        setSkillSet(candidate.candidateSkills || [])
    }, [candidate]);

    const addSkill = () => {
        const emptyCandidateSkill: CandidateSkill = new CandidateSkill(new Skill(""), data?.levels[0])
        skillSet.push(emptyCandidateSkill)
        setSkillSet([...skillSet])
    };

    const formik = useFormik({
        initialValues:{
            name: candidate.name,
            phone: candidate.phone,
            email: candidate.email,
            aboutMe: candidate.aboutMe,
            degree: candidate.degree || "",
            filePath: candidate.filePath,
            candidateSkills: candidate.candidateSkills,
        },
        onSubmit: values => {
            values.candidateSkills = skillSet;
            if (values.degree === "" && data){
                values.degree = data.degrees[0];
            }
            CandidateService.createCandidate(values).then(r =>{
            })
        },
    });


    return (
        <div>
            {candidate && data &&
            <Form onSubmit={formik.handleSubmit}>
                <Form.Group as={Col}>
                    <FormLabel>Name</FormLabel>
                    <Col>
                        <InputGroup>
                            <Form.Control
                                id="name"
                                name="name"
                                defaultValue={formik.initialValues.name}
                                onChange={formik.handleChange}>
                            </Form.Control>
                        </InputGroup>
                    </Col>
                </Form.Group>

                <Form.Group as={Col}>
                    <FormLabel>Email</FormLabel>
                    <Col>
                        <InputGroup className="" >
                            <Form.Control
                                id="email"
                                name="email"
                                defaultValue={formik.initialValues.email}
                                onChange={formik.handleChange}>
                            </Form.Control>
                        </InputGroup>
                    </Col>
                </Form.Group>

                <Form.Group as={Col}>
                    <FormLabel>Phone</FormLabel>
                    <Col>
                        <InputGroup className="" >
                            <Form.Control
                                id="phone"
                                name="phone"
                                defaultValue={formik.initialValues.phone}
                                onChange={formik.handleChange}>
                            </Form.Control>
                        </InputGroup>
                    </Col>
                </Form.Group>

                <Form.Group as={Col}>
                    <FormLabel>About me</FormLabel>
                    <Col>
                        <InputGroup className="h-300">
                            <Form.Control className="h-300"
                                id="aboutMe"
                                name="aboutMe"
                                as="textarea"
                                defaultValue={formik.initialValues.aboutMe}
                                onChange={formik.handleChange}>
                            </Form.Control>
                        </InputGroup>
                    </Col>
                </Form.Group>

                <Form.Group as={Col}>
                    <FormLabel>Degree</FormLabel>
                    <Col>
                        <InputGroup className="mb-2 mr-sm-2" >
                            <Form.Control
                                as="select"
                                id="degree"
                                name="degree"
                                defaultValue={formik.initialValues.degree}
                                onChange={formik.handleChange}>
                                {data.degrees.map((degree, index) => {
                                    return (<option  key={index}>{degree}</option>);
                                })}
                            </Form.Control>
                        </InputGroup>
                    </Col>
                </Form.Group>
                {/*<FormControl type="text" value={degree} onChange={(e) => setDegree(e.target.value)}/>*/}

                <Table>
                    <thead>
                        <tr>
                            <th className="text-center">Skill</th>
                            <th className="text-center">Level</th>
                        </tr>
                    </thead>
                    <tbody>
                    {skillSet?.length > 0
                        && skillSet.map((candidateSkill, index) => {
                        return (
                            <tr>
                                <td>
                                    <Form.Group>
                                        <Col>
                                            <InputGroup>
                                                <Form.Control
                                                    defaultValue={candidateSkill.skillDto.name}
                                                    onChange={e => {
                                                    skillSet[index].skillDto.name = e.target.value;
                                                }}>
                                                </Form.Control>
                                            </InputGroup>
                                        </Col>
                                    </Form.Group>
                                </td>
                                <td>
                                    <Form.Group>
                                        <Col>
                                            <InputGroup>
                                                <Form.Control
                                                    as="select"
                                                    className="mb-2 mr-sm-2"
                                                    key={candidateSkill.level}
                                                    onChange={e => {
                                                        skillSet[index].level = e.target.value;
                                                    }}
                                                    defaultValue={candidateSkill.level}>
                                                    {data.levels.map((level, index) => {
                                                        return (<option key={index}>{level}</option>);
                                                    })}
                                                </Form.Control>
                                            </InputGroup>
                                        </Col>
                                    </Form.Group>
                                </td>
                            </tr>
                    )
                })}
                    <tr><td><Button className="mt-1" onClick={addSkill}>Add skill</Button></td></tr>
                    </tbody>
                </Table>

                <Button className="md-4" type="submit">Submit</Button>

            </Form>
            }

            <br/>
            <br/>
        </div>
    );
}


export default EditCandidateComponent;
