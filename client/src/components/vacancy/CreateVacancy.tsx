import React, {useEffect, useState} from "react";
import {Button, Card, Col, Form, InputGroup, Row, Toast} from "react-bootstrap";
import {Skill, SkillsDegreesLevelsCitiesEnglishLevels, Vacancy, VacancySkills} from "../../models/Vacancy";
import {FormikErrors, useFormik} from "formik";
import {Typeahead} from "react-bootstrap-typeahead";
import 'react-bootstrap-typeahead/css/Typeahead.css';
import VacancyService from "../../services/VacancyService";
import {RouteComponentProps} from "react-router-dom";

const CreateVacancyComponent = (props: RouteComponentProps) =>  {
    const [multiSelections, setMultiSelections] = useState<Skill[]>([]);
    const [data, setData] = useState<SkillsDegreesLevelsCitiesEnglishLevels>();
    const [vacancySkills, setVacancySkills] = useState<VacancySkills[]>([]);
    const [successValidation, setSuccessValidation] = useState<boolean>();
    const [show, setShow] = useState(false);
    const toggleShow = () => setShow(!show);

    useEffect(() => {
        handleSkillsDegreesLevelsCities();
    },[]);

    const handleSkillsDegreesLevelsCities = () => {
        VacancyService.getAllSkillsDegreesLevelsCities().then((response) => {
            setData(response.data);
        });
    };

    function validate(values: Vacancy){
        let errors: FormikErrors<Vacancy> = {};
        if (!values.positionTitle) {
            errors.positionTitle = 'Field is empty.';
            setSuccessValidation(false);
        }
        if (values.minimumYearsOfExperience < 0) {
            errors.minimumYearsOfExperience = 'Must be positive integer.';
            setSuccessValidation(false);
        }
        if (vacancySkills.length === 0) {
            errors.vacancySkills = 'Must have at least one skill.';
            setSuccessValidation(false);
        }
        if (values.description === '') {
            errors.description = 'Field is empty.';
            setSuccessValidation(false);
        }
        return errors;
    }

    const formik = useFormik({
        initialValues:{
            id: undefined,
            positionTitle: '',
            isActive: true,
            minimumYearsOfExperience: 0,
            degree: data?.degrees[0] ? (data?.degrees[0]):(''),
            location: data?.cities[0] ? (data?.cities[0]):(''),
            englishLevel: data?.englishLevels[0] ? (data?.englishLevels[0]):(''),
            vacancySkills: [{skill:{name:''}, level:''}],
            description: '',
        },
        onSubmit: values => {
            setSuccessValidation(true);
            values.vacancySkills = vacancySkills;
            if (values.degree === '' && data){
                values.degree = data.degrees[0];
            }
            if (values.location === '' && data){
                values.location = data.cities[0];
            }
            if (values.englishLevel === '' && data){
                values.englishLevel = data.englishLevels[0];
            }
            formik.setErrors(validate(values));
            if (successValidation) {
                VacancyService.createVacancy(values).then(r => {
                    console.log(r.status === 201);
                    toggleShow();
                    //props.history.push("/vacancies/new");
                });
            }
        },
    });

    return(
        <>
            <Toast onClose={() => setShow(false)} show={show} delay={3000} autohide
                   style={{
                       position: 'absolute',
                       right: 40,
                       bottom: 10,
                       width: 250,
                       height: 100,
                   }}>
                <Toast.Header>
                    <strong className="mr-auto">Notification</strong>
                    <small>1 second ago</small>
                </Toast.Header>
                <Toast.Body>&#9989;Changes saved successful!</Toast.Body>
            </Toast>
            {data !== undefined ? (
            <Card className="col-7 m-auto" >
                <h3 className="mt-2 text-center">Create new Vacancy</h3>
                <Card.Body>
                    <Form onSubmit={formik.handleSubmit}>
                        {/*TITLE*/}
                        <Form.Group as={Row}>
                            <Form.Label column md="3">
                                Position Title:
                            </Form.Label>
                            <Col md="9">
                                <InputGroup className="mb-2 mr-sm-2" >
                                    <Form.Control
                                        id="positionTitle"
                                        name="positionTitle"
                                         placeholder="Middle Java Developer"
                                        onChange={formik.handleChange}
                                        onBlur={formik.handleBlur}
                                        isInvalid={!!formik.errors.positionTitle}

                                    />
                                    <Form.Control.Feedback type="invalid">
                                        {formik.errors.positionTitle}
                                    </Form.Control.Feedback>
                                </InputGroup>
                            </Col>
                        </Form.Group>
                        {/*YEARS OF EXPERIENCE*/}
                        <Form.Group as={Row}>
                            <Form.Label column md="4">
                                Years of experience:
                            </Form.Label>
                            <Col md="8">
                                <InputGroup className="mb-2 mr-sm-2 " >
                                    <Form.Control
                                        id="minimumYearsOfExperience"
                                        name="minimumYearsOfExperience"
                                        type="number"
                                        defaultValue={0}
                                        onChange={formik.handleChange}
                                        onBlur={formik.handleBlur}
                                        isInvalid={!!formik.errors.minimumYearsOfExperience}
                                    />
                                    <Form.Control.Feedback type="invalid">
                                        {formik.errors.minimumYearsOfExperience}
                                    </Form.Control.Feedback>
                                </InputGroup>
                            </Col>
                        </Form.Group>
                        {/*DEGREE*/}
                        <Form.Group as={Row}>
                            <Form.Label column md="4">
                                Degree:
                            </Form.Label>
                            <Col md="8">
                                <InputGroup className="mb-2 mr-sm-2" >
                                    <Form.Control
                                        as="select"
                                        id="degree"
                                        name="degree"
                                        defaultValue={data.degrees[0]}
                                        onChange={formik.handleChange}
                                    >
                                        {data.degrees.map((degree, index) => {
                                            return (<option>{degree}</option>);
                                        })}
                                    </Form.Control>
                                </InputGroup>
                            </Col>
                        </Form.Group>
                        {/*LOCATION*/}
                        <Form.Group as={Row}>
                            <Form.Label column md="4">
                                Location:
                            </Form.Label>
                            <Col md="8">
                                <InputGroup className="mb-2 mr-sm-2" >
                                    <Form.Control
                                        as="select"
                                        id="location"
                                        name="location"
                                        onChange={formik.handleChange}
                                        defaultValue={data.cities[0]}
                                    >
                                        {data.cities.map((city, index) => {
                                            return (<option>{city}</option>);
                                        })}
                                    </Form.Control>
                                </InputGroup>
                            </Col>
                        </Form.Group>
                        {/*ENGLISH_LEVEL*/}
                        <Form.Group as={Row}>
                            <Form.Label column md="4">
                                English:
                            </Form.Label>
                            <Col md="8">
                                <InputGroup className="mb-2 mr-sm-2" >
                                    <Form.Control
                                        as="select"
                                        id="englishLevel"
                                        name="englishLevel"
                                        onChange={formik.handleChange}
                                        defaultValue={data.englishLevels[0]}
                                    >
                                        {data.englishLevels.map((value, index) => {
                                            return (<option>{value}</option>);
                                        })}
                                    </Form.Control>
                                </InputGroup>
                            </Col>
                        </Form.Group>
                        {/*SKILLS*/}
                        <Form.Group as={Row}>
                            <Form.Label column md="4">Required Skills</Form.Label>
                            <Col md="8">
                                <Typeahead <Skill>

                                    id="basic-typeahead-multiple"
                                    labelKey="name"
                                    multiple
                                    onChange={e => {
                                        setVacancySkills(vacancySkills.filter(value => e.includes(value.skill)));
                                        setMultiSelections(e);
                                    }}
                                    options={data.skills}
                                    placeholder="Choose several skills..."
                                    selected={vacancySkills.flatMap(value => value.skill)}
                                    isInvalid={!!formik.errors.vacancySkills}
                                />
                                <Form.Control.Feedback type="invalid">
                                    {formik.errors.vacancySkills}
                                </Form.Control.Feedback>
                            </Col>
                        </Form.Group>
                        {/*SKILL_LEVEL*/}
                        {multiSelections.map((skill, index)=>{
                            vacancySkills[index] = {skill: skill, level: vacancySkills[index] !== undefined ? (vacancySkills[index].level): (data.levels[0])};
                            return (
                                <Row>
                                    <Col md="4">
                                        {skill.name}
                                    </Col>
                                    <Col md="8">
                                        <InputGroup className="mb-2 mr-sm-2" key={vacancySkills[index].skill.name}>
                                            <Form.Control
                                                as="select"
                                                id={"vacancySkills["+index+"].level"}
                                                name={"vacancySkills["+index+"].level"}
                                                key={vacancySkills[index].skill.name}
                                                defaultValue={vacancySkills[index].level}
                                                onChange={e => {
                                                    vacancySkills[index].level = e.target.value;
                                                }}
                                            >
                                                {data.levels.map((level, index) => {
                                                    return (<option>{level}</option>);
                                                })}
                                            </Form.Control>
                                        </InputGroup>
                                    </Col>
                                </Row>
                            );
                        })}
                        <Form.Group>
                            <Form.Label>Description</Form.Label>
                            <Form.Control
                                as="textarea"
                                id="description"
                                name="description"
                                placeholder="Write about the company, the project and so on."
                                onChange={formik.handleChange}
                                isInvalid={!!formik.errors.description}
                            />
                            <Form.Control.Feedback type="invalid">
                                {formik.errors.description}
                            </Form.Control.Feedback>
                        </Form.Group>
                        <Button type="submit">
                            Submit
                        </Button>
                    </Form>
                </Card.Body>
            </Card>
            ): (<></>)}
        </>
    );

}

export default CreateVacancyComponent;
