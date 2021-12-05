import {CandidateSkill} from "./CandidateSkill";

export interface Candidate{
    id: number,
    name: string,
    email: string,
    phone: string,
    degree: string,
    aboutMe: string,
    filePath: string,
    candidateSkills: CandidateSkill[];
}

export interface CandidateWrapper{
    status: number;
    data: Candidate;
    message: string;
}


