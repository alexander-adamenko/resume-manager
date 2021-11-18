import {SERVER_API_URL} from "../constants";
import axios, {AxiosResponse} from "axios";
import {Candidate} from "../models/Candidate";

const candidatesEndpoint = `${SERVER_API_URL}/candidates`;

const axiosInstance = axios.create({ withCredentials: true });
const config = {headers: { 'Content-Type': 'multipart/form-data'}}

class CandidateService {
    getNamesUploadedFiles(): Promise<AxiosResponse<string[]/*List with names of resumes*/>> {
        return axiosInstance.get(candidatesEndpoint);
    }

    uploadResumeOfCandidate(resume: File): Promise<AxiosResponse<Candidate[]>>{
        let formData = new FormData();
        formData.append("resume", resume)
        return axiosInstance.post(candidatesEndpoint + "/upload", formData, config)
    }
}

export default new CandidateService();
