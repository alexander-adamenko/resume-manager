import React, {useEffect, useState} from "react";
import {Button, Form, FormControl, FormLabel} from "react-bootstrap";
import {useForm} from "react-hook-form";
import CandidateService from "../../services/CandidateService";


const UploadingCandidateComponent = () => {

    const [resumes, setResumes] = useState<string[]>([]);
    const {register, handleSubmit} = useForm();

    useEffect(() => {
        handleResumes()
    });

    const onSubmit = (data: any) => {
        console.log(data)
        console.log(data.resume[0]);
        CandidateService.uploadResumeOfCandidate(data.resume[0])
            .then((response) => {
            });
    }

    const handleResumes = () => {
        CandidateService.getNamesUploadedFiles()
            .then((response) => {
                const resumes: string[] = response.data;
                setResumes(resumes);
            });
    };


    return (
        <div>
            <Form onSubmit={handleSubmit(onSubmit)}>
                <FormLabel>Upload file</FormLabel>
                <FormControl type="file" {...register("resume")}/>
                <Button className="mt-1" type="submit">Submit</Button>
            </Form>

            <div>
                <hr/>
                <h2 className="text-center">All uploaded files</h2>
                {resumes.length > 0  && resumes.map((item, index) => {
                    return (
                        <div>
                            <p>{item}</p>
                            <Button className="mb-2">Parse</Button>
                        </div>
                    )
                })}
            </div>

        </div>
    );
}


export default UploadingCandidateComponent;
