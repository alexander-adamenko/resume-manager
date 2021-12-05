import React, {useEffect, useState} from "react";
import {Button, Card, Col, Form, FormControl, FormLabel, Spinner} from "react-bootstrap";
import {useForm} from "react-hook-form";
import CandidateService from "../../services/CandidateService";
import EditCandidate from "./EditCandidate";

import {Candidate} from "../../models/Candidate";



const UploadingCandidateComponent = () => {
    const [candidate, setCandidate] = useState<Candidate>();
    const {register, handleSubmit} = useForm();

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


    return (
        <div>
            <div className="p-3">
                <Form onSubmit={handleSubmit(onSubmit)}>
                    <FormLabel>Upload file</FormLabel>
                    <FormControl type="file" {...register("resume")}/>
                    <Button className="mt-1" type="submit">Submit</Button>
                </Form>
            </div>
            <hr/>

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
