import React, {useEffect, useState} from "react";
import {Button, Card, Col, Form, Spinner} from "react-bootstrap";
import {useForm} from "react-hook-form";
import CandidateService from "../../services/CandidateService";
import EditCandidate from "./EditCandidate";

import {Candidate} from "../../models/Candidate";



const UploadingCandidateComponent = () => {
    const [candidate, setCandidate] = useState<Candidate>();
    const {register, handleSubmit} = useForm();
    const [resumes, setResumes] = useState<string[]>([])
    const [isLoaded, setIsLoaded] = useState<boolean>(true);

    useEffect(() => {
        handleResumes()
    }, [candidate]);

    const handleResumes = () =>{
        CandidateService.getNamesUploadedFiles().then(response =>
            setResumes(response.data)
        )
    }

    const onSubmit = (data: any) => {
        const resumeFile = data.resume[0];
        if(resumeFile != undefined){
            setIsLoaded(false);
            CandidateService.uploadResumeOfCandidate(data.resume[0])
                .then((response) => {
                    if(response.data.status == 200){
                        setCandidate(response.data.data);
                    } else {
                        alert(response.data.message)
                    }
                    setIsLoaded(true)
                });
        }else alert("Choose file!")
    }


    const handleParseResume = (item: string) => {
        setIsLoaded(false);
        CandidateService.parseChosenResume(item).then(value =>{
            setCandidate(value.data)
            setIsLoaded(true);
        }
    )
    }

    const handleDeleteResume = (item: string) => {
        CandidateService.deleteChosenResume(item).then(handleResumes)
    }

    return (
        <div>
            <div className="p-3">
                <Form onSubmit={handleSubmit(onSubmit)}>
                    <h4>Upload file</h4>
                    <Form.Group controlId="formFile" className="mb-3">
                        <Form.Control type="file"  {...register("resume")}/>
                        <Button className="mt-1" type="submit">Submit</Button>
                    </Form.Group>
                </Form>
            </div>
            <div className="row">
                <div className="col-3">
                    <h3 className="card-title text-center">Resumes</h3>
                    {resumes.length > 0 && resumes.map((item, index) => {
                        return (
                            <Col key={index}>
                                <Card>
                                    <Card.Body>
                                        <Card.Title>{item}</Card.Title>
                                        <Button className="m-1"
                                                variant="outline-info"
                                                size="sm"
                                                onClick={() => handleParseResume(item)}>
                                            Re-parse
                                        </Button>
                                        <Button className="m-1"
                                                variant="outline-danger"
                                                size="sm"
                                                onClick={() => handleDeleteResume(item)}>
                                            Delete
                                        </Button>
                                    </Card.Body>
                                </Card>
                            </Col>

                        )})}
                </div>
                <div className="col-6 m-2">
                    {candidate != null && isLoaded && <div>
                        <EditCandidate parsedCandidate={candidate} key={candidate.email}/>
                    </div>}
                    {candidate == null && isLoaded  && <div><h3 className="text-center">Choose file and press "Submit" button</h3></div>}
                    {!isLoaded &&<div className="d-flex justify-content-center"><Spinner className="" animation="border" variant="info" /></div>}
                </div>
            </div>

        </div>

    );
}


export default UploadingCandidateComponent;
