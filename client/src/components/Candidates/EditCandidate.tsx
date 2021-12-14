import React, {useEffect, useState} from "react";
import {Button, Col, Form, FormLabel, InputGroup, Table} from "react-bootstrap";
import CandidateService from "../../services/CandidateService";
import {Candidate} from "../../models/Candidate";
import {CandidateSkill} from "../../models/CandidateSkill";
import {Skill, SkillsDegreesLevelsCitiesEnglishLevels} from "../../models/Vacancy";
import VacancyService from "../../services/VacancyService";
import {FormikErrors, useFormik} from "formik";

interface Props {
    parsedCandidate: Candidate;
}

const EditCandidateComponent = ({parsedCandidate}: Props) => {
    const [candidate, setCandidate] = useState<Candidate>(parsedCandidate);
    const [skillSet, setSkillSet] = useState<CandidateSkill[]>(parsedCandidate.candidateSkills || [])
    const [data, setData] = useState<SkillsDegreesLevelsCitiesEnglishLevels>();
    const [isSubmitted, setSubmit] = useState<boolean>(false)
    const [successValidation, setSuccessValidation] = useState<boolean>();

    const handleSkillsDegreesLevelsCities = () => {
        VacancyService.getAllSkillsDegreesLevelsCities().then((response) => {
            setData(response.data);
        });
    };

    useEffect(() => {
        handleSkillsDegreesLevelsCities()
        setCandidate(parsedCandidate)
    }, [candidate]);

    const addSkill = () => {
        const emptyCandidateSkill: CandidateSkill = new CandidateSkill(new Skill(""), data?.levels[0])
        skillSet.push(emptyCandidateSkill)
        setSkillSet([...skillSet])
    };

    const removeSkill = (index: number) => {
        skillSet.splice(index, 1)
        setSkillSet([...skillSet])
    };

    function validate(values: Candidate){
        let errors: FormikErrors<Candidate> = {};
        if (!values.name) {
            errors.name = 'Field is empty.'
            setSuccessValidation(false);
        }
        if(!values.phone){
            errors.phone = 'Field is empty.'
            setSuccessValidation(false);
        }
        if(!values.email){
            errors.email = 'Field is empty.'
            setSuccessValidation(false);
        }
        if(!values.aboutMe){
            errors.aboutMe = 'Field is empty.'
            setSuccessValidation(false);
        }
        if(values.aboutMe.length > 2000){
            errors.aboutMe = 'Must be less than 2000 characters'
            setSuccessValidation(false);
        }
        return errors;
    }

    const formik = useFormik({
        initialValues:{
            id: candidate.id,
            name: candidate.name,
            phone: candidate.phone,
            email: candidate.email,
            englishLevel: candidate.englishLevel,
            location: candidate.location,
            yearsOfExperience: candidate.yearsOfExperience,
            aboutMe: candidate.aboutMe,
            degree: candidate.degree || "",
            filePath: candidate.filePath,
            candidateSkills: candidate.candidateSkills,
        },
        onSubmit: values => {
            setSuccessValidation(true);
            values.candidateSkills = skillSet;
            if (values.degree === "" && data){
                values.degree = data.degrees[0];
            }
            if (values.location === "" && data){
                values.location = data.cities[0];
            }
            if (values.englishLevel === "" && data){
                values.englishLevel = data.englishLevels[0];
            }
            formik.setErrors(validate(values));
            if (successValidation) {
                CandidateService.createCandidate(values).then(r =>{
                    setSubmit(true)
                })
            }
        },
    });


    return (
        <div>
            {!isSubmitted && candidate && data &&
            <Form onSubmit={formik.handleSubmit}>
                <h3 className="text-center">Parsed candidate:</h3>
                <Form.Group as={Col}>
                    <FormLabel>Name</FormLabel>
                    <Col>
                        <InputGroup>
                            <Form.Control
                                id="name"
                                name="name"
                                defaultValue={formik.initialValues.name}
                                onChange={formik.handleChange}
                                isInvalid={!!formik.errors.name}>
                            </Form.Control>
                            <Form.Control.Feedback type="invalid">
                                {formik.errors.name}
                            </Form.Control.Feedback>
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
                                onChange={formik.handleChange}
                                isInvalid={!!formik.errors.email}>
                            </Form.Control>
                            <Form.Control.Feedback type="invalid">
                                {formik.errors.email}
                            </Form.Control.Feedback>
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
                                onChange={formik.handleChange}
                                isInvalid={!!formik.errors.phone}>
                            </Form.Control>
                            <Form.Control.Feedback type="invalid">
                                {formik.errors.phone}
                            </Form.Control.Feedback>
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
                                onChange={formik.handleChange}
                                isInvalid={!!formik.errors.aboutMe}>
                            </Form.Control>
                            <Form.Control.Feedback type="invalid">
                                {formik.errors.aboutMe}
                            </Form.Control.Feedback>
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
                <Form.Group as={Col}>
                    <FormLabel>English level</FormLabel>
                    <Col>
                        <InputGroup className="mb-2 mr-sm-2" >
                            <Form.Control
                                as="select"
                                id="engLevel"
                                name="engLevel"
                                defaultValue={formik.initialValues.englishLevel}
                                onChange={formik.handleChange}>
                                {data.englishLevels.map((engLvl, index) => {
                                    return (<option  key={index}>{engLvl}</option>);
                                })}
                            </Form.Control>
                        </InputGroup>
                    </Col>
                </Form.Group>
                <Form.Group as={Col}>
                    <FormLabel>City</FormLabel>
                    <Col>
                        <InputGroup className="mb-2 mr-sm-2" >
                            <Form.Control
                                as="select"
                                id="location"
                                name="location"
                                defaultValue={formik.initialValues.location}
                                onChange={formik.handleChange}>
                                {data.cities.map((city, index) => {
                                    return (<option  key={index}>{city}</option>);
                                })}
                            </Form.Control>
                        </InputGroup>
                    </Col>
                </Form.Group>
                <Table>
                    <thead>
                        <tr>
                            <th className="text-center">Skill</th>
                            <th className="text-center">Level</th>
                        </tr>
                    </thead>
                    <tbody>
                    {skillSet?.length > 0 &&
                        skillSet.map((candidateSkill, index) => {
                        return (
                            <tr key={index}>
                                <td>
                                    <Form.Group>
                                        <Col>
                                            <InputGroup>
                                                <Form.Control
                                                    value={skillSet[index].skill.name}
                                                    isInvalid={!!formik.errors.candidateSkills}
                                                    onChange={e => {
                                                        skillSet[index].skill.name = e.target.value
                                                        setSkillSet([...skillSet])
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
                                                    key={skillSet[index].level}
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
                                <td><Button className="bg-light" onClick={() => removeSkill(index)}>&#128465;</Button></td>
                            </tr>
                    )
                })}
                    <tr><td><Button className="mt-1" onClick={addSkill}>Add skill</Button></td></tr>
                    </tbody>
                </Table>

                <Button className="md-4" type="submit">Submit</Button>
                <br/>
                <br/>
            </Form>
            }
            {isSubmitted && <div>
                <h3>Candidate successfully submitted &#9989;</h3>
            </div>}
        </div>
    );
}


export default EditCandidateComponent;
