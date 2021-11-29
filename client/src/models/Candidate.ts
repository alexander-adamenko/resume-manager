import {CandidateSkill} from "./CandidateSkill";

export interface Candidate{
    name: string,
    email: string,
    phone: string,
    degree: string,
    aboutMe: string,
    filePath: string,
    candidateSkills: CandidateSkill[];
}


