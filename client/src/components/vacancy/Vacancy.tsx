import {Skill, SkillsDegreesLevelsCitiesEnglishLevels, Vacancy, VacancySkills} from "../../models/Vacancy";
import {RouteComponentProps} from "react-router-dom";
import {Button, Card, Col, Form, InputGroup, Placeholder, Row, Spinner, Toast} from "react-bootstrap";
import CreateVacancy from "./CreateVacancy";
import React, {useEffect, useState} from "react";
import VacancyService from "../../services/VacancyService";
import {FormikErrors, useFormik} from "formik";
import {Typeahead} from "react-bootstrap-typeahead";
import 'react-bootstrap-typeahead/css/Typeahead.css';
import 'react-bootstrap-table-next/dist/react-bootstrap-table2.min.css';
import {Candidate} from "../../models/Candidate";
import CandidatesTable from "./CandidatesTable";

const VacancyComponent = (props: any) => {
    const [vacancies, setVacancies] = useState<Vacancy[]>([]);
    const [currentVacancyId, setCurrentVacancyId] = useState<number>();
    const [vacancy, setVacancy] = useState<Vacancy>();

    const [isLoaded, setIsLoaded] = useState<boolean>(true);
    const [show, setShow] = useState(false);
    const toggleShow = () => setShow(!show);
    const [candidates, setCandidates] = useState<Candidate[]>();

    const [multiSelections, setMultiSelections] = useState<Skill[]>([]);
    const [data, setData] = useState<SkillsDegreesLevelsCitiesEnglishLevels>();
    const [vacancySkills, setVacancySkills] = useState<VacancySkills[]>([]);
    const [successValidation, setSuccessValidation] = useState<boolean>();

    useEffect(() => {
        if (props.location.pathname !== '/vacancies/new') {
            setIsLoaded(false);
            handleSkillsDegreesLevelsCities();
            VacancyService.getVacancy(props.match.params.id).then((response) => {
                setVacancy(response.data);
                setVacancySkills(response.data.vacancySkills);
                setMultiSelections(response.data.vacancySkills.flatMap(value => value.skill))
                setCurrentVacancyId(props.match.params.id);
                setIsLoaded(true);
            });
        }
    },[]);

    const handleSkillsDegreesLevelsCities = () => {
        VacancyService.getAllSkillsDegreesLevelsCities().then((response) => {
            setData(response.data);
        });
    };

    function match () {
        if (vacancy && vacancy.id) {
            VacancyService.matchVacancy(vacancy.id).then((response) => {
                if (response.status === 200) {
                    console.log(response);
                    setCandidates(response.data);
                }
            });
        }
    }

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
            id: -1,
            positionTitle: '',
            isActive: true,
            minimumYearsOfExperience: -1,
            degree: '',
            location: '',
            englishLevel: '',
            vacancySkills:  [{skill:{name:''}, level:''}],
            description: '',
        },
        onSubmit: values => {
            setSuccessValidation(true);
            if (vacancy) {
                values.id = vacancy.id !== undefined ? vacancy.id: -1;
                values.vacancySkills = vacancySkills;
                if (values.minimumYearsOfExperience == -1) {
                    values.minimumYearsOfExperience = vacancy.minimumYearsOfExperience;
                }
                if (values.positionTitle == '') {
                    values.positionTitle = vacancy.positionTitle;
                }
                if (values.degree == '') {
                    values.degree = vacancy.degree;
                }
                if (values.location == '') {
                    values.location = vacancy.location;
                }
                if (values.englishLevel == '') {
                    values.englishLevel = vacancy.englishLevel;
                }
                if (values.description == '') {
                    values.description = vacancy.description;
                }
            }
            console.log(values);
            VacancyService.updateVacancy(values).then((response) => {
                    if (response.status === 200){
                        toggleShow();

                    }
            });
        },
    });

    return (
        <>
            {props.location.pathname === '/vacancies/new' ? (
                <CreateVacancy history={props.history} location={props.location} match={props.match} />
            ): (
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
                    {!isLoaded &&<div className="d-flex justify-content-center"><Spinner className="" animation="border" variant="info" /></div>}
                    {data !== undefined && vacancy != undefined && vacancySkills.length > 0 && multiSelections.length > 0? (
                        <Card style={{margin: 'auto', width:'500px', marginBottom: '50px', marginTop: '50px'}}>
                            <Card.Header>Edit Vacancy</Card.Header>
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
                                                    defaultValue={vacancy.positionTitle}
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
                                            <InputGroup className="mb-2 mr-sm-2" >
                                                <Form.Control
                                                    id="minimumYearsOfExperience"
                                                    name="minimumYearsOfExperience"
                                                    type="number"
                                                    defaultValue={vacancy.minimumYearsOfExperience}
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
                                                    defaultValue={vacancy.degree}
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
                                                    defaultValue={vacancy.location}
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
                                                    defaultValue={vacancy.englishLevel}
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
                                                    vacancy.vacancySkills = vacancy.vacancySkills.filter(value => e.includes(value.skill));
                                                    e.forEach(value => {
                                                        if(!vacancy.vacancySkills.flatMap(value => value.skill).includes(value)){
                                                            vacancy.vacancySkills.push({skill:value, level:data.levels[0]})
                                                        }
                                                    })
                                                }}
                                                options={data.skills}
                                                placeholder="Choose several skills..."
                                                isInvalid={!!formik.errors.vacancySkills}
                                                selected={vacancy.vacancySkills.flatMap(value => value.skill)}
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
                                            defaultValue={vacancy.description}
                                        />
                                        <Form.Control.Feedback type="invalid">
                                            {formik.errors.description}
                                        </Form.Control.Feedback>
                                    </Form.Group>
                                    <Button type="submit">
                                        SaveChanges
                                    </Button>
                                    <Button onClick={match}>
                                        Match
                                    </Button>
                                </Form>
                            </Card.Body>
                        </Card>
                    ): (<></>)}
                    {candidates ? (<CandidatesTable candidates={candidates} />): ("123")}
                </>
                )
            }
        </>
    );
}
export default VacancyComponent;