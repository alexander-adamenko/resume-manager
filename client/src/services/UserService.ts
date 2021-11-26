import axios, { AxiosResponse } from "axios";
import { SERVER_API_URL } from "../constants";
import { User } from "../models/User";
import {UserDetails} from "../models/UserDetails";

const portfolioEndpoint = `${SERVER_API_URL}/users`;
const currentUser = `${SERVER_API_URL}/current-user`;

const axiosInstance = axios.create({ withCredentials: true });


class UserService {
    getAllUsers(): Promise<AxiosResponse<User[]>> {
        return axiosInstance.get(portfolioEndpoint);
    }

    getCurrentUser(): Promise<AxiosResponse<UserDetails>>{
        return axiosInstance.get(currentUser);
    }
    updateUser(oldUsername: string | undefined, lastname: string | undefined, firstname: string | undefined,
               username: string | undefined, oldPassword: string | undefined,
               newPassword: string | undefined): Promise<AxiosResponse<string>>{
        let params = new URLSearchParams();
        if (typeof lastname === "string") {
            params.append("lastname", lastname)
        }
        if (typeof firstname === "string") {
            params.append("firstname", firstname)
        }
        if (typeof username === "string") {
            params.append("username", username)
        }
        if (typeof oldPassword === "string") {
            params.append("oldPassword", oldPassword)
        }
        if (typeof newPassword === "string") {
            params.append("newPassword", newPassword)
        }

        return axiosInstance.put(`${SERVER_API_URL}/`+oldUsername, params)
    }

}

export default new UserService();
