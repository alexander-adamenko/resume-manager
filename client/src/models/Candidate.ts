import {CandidateSkill} from "./CandidateSkill";

export interface Candidate{
    id: number,
    name: string,
    email: string,
    phone: string,
    degree: string,
    englishLevel: string,
    location: string,
    yearsOfExperience: number,
    aboutMe: string,
    filePath: string,
    candidateSkills: CandidateSkill[];
}

export interface CandidateWithProfile extends Candidate{
    pathToProfile: string;
}

export interface CandidateWrapper{
    status: number;
    data: Candidate;
    message: string;
}


