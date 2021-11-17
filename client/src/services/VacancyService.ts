import axios, { AxiosResponse } from "axios";
import { SERVER_API_URL } from "../constants";
import {Skill, SkillsDegreesLevelsCities, Vacancy} from "../models/Vacancy";

const axiosInstance = axios.create({ withCredentials: true });

class UserService {
    getAllVacancies(): Promise<AxiosResponse<Vacancy[]>> {
        return axiosInstance.get(SERVER_API_URL + '/vacancies');
    }

    createVacancy(vacancy: Vacancy): Promise<AxiosResponse<Vacancy>> {
        return axiosInstance.post(SERVER_API_URL + '/vacancies', vacancy);
    }

    getAllSkills(): Promise<AxiosResponse<Skill[]>> {
        return axiosInstance.get(SERVER_API_URL + '/skills');
    }

    getAllSkillsDegreesLevelsCities(): Promise<AxiosResponse<SkillsDegreesLevelsCities>> {
        return axiosInstance.get(SERVER_API_URL + '/skills-degrees-levels-cities');
    }
}

export default new UserService();