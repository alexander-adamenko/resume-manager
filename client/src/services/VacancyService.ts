import axios, { AxiosResponse } from "axios";
import { SERVER_API_URL } from "../constants";
import {Skill, SkillsDegreesLevelsCitiesEnglishLevels, Vacancy} from "../models/Vacancy";

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

    getAllSkillsDegreesLevelsCities(): Promise<AxiosResponse<SkillsDegreesLevelsCitiesEnglishLevels>> {
        return axiosInstance.get(SERVER_API_URL + '/skills-degrees-levels-cities-english-levels');
    }

    getVacancy(id: number): Promise<AxiosResponse<Vacancy>>{
        let params = new URLSearchParams();
        params.append("id", String(id));
        return axiosInstance.get(`${SERVER_API_URL}/vacancies/abc`, { params: { id: id } });
    }

    updateVacancy(vacancy: Vacancy): Promise<AxiosResponse>{
        return axiosInstance.put(SERVER_API_URL + '/vacancies', vacancy);
    }

    matchVacancy(id: number) {
        let params = new URLSearchParams();
        params.append("id", String(id));
        return axiosInstance.get(`${SERVER_API_URL}/match`, { params: { id: id } });
    }
}

export default new UserService();