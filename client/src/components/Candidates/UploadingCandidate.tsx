import React, {useEffect, useState} from "react";
import {Button, Form, FormControl, FormLabel} from "react-bootstrap";
import {useForm} from "react-hook-form";
import CandidateService from "../../services/CandidateService";
import EditCandidate from "./EditCandidate";

import {Candidate} from "../../models/Candidate";



const UploadingCandidateComponent = () => {
    const [candidate, setCandidate] = useState<Candidate>();
    const {register, handleSubmit} = useForm();

    const onSubmit = (data: any) => {
        CandidateService.uploadResumeOfCandidate(data.resume[0])
            .then((response) => {
                setCandidate(response.data);
                console.log(response.data);
            });
    }


    return (
        <div>
            <div>
                <Form onSubmit={handleSubmit(onSubmit)}>
                    <FormLabel>Upload file</FormLabel>
                    <FormControl type="file" {...register("resume")}/>
                    <Button className="mt-1" type="submit">Submit</Button>
                </Form>
            </div>
            <hr/>

                <div className="m-auto col-7">
                    {candidate != null && <div>
                        <h3 className="text-center">Parsed candidate:</h3>
                        <EditCandidate parsedCandidate={candidate} key={candidate.email}/>
                    </div>}
                    {candidate == null && <div>
                        <h3 className="text-center">Choose file and press "Submit" button</h3>
                    </div>}
                </div>
            </div>

    );
}


export default UploadingCandidateComponent;
