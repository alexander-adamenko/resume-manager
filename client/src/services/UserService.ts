import axios, { AxiosResponse } from "axios";
import { SERVER_API_URL } from "../constants";
import { User } from "../models/User";

const portfolioEndpoint = `${SERVER_API_URL}/users`;

const axiosInstance = axios.create({ withCredentials: true });

class UserService {
    getAllUsers(): Promise<AxiosResponse<User[]>> {
        return axiosInstance.get(portfolioEndpoint);
    }
}

export default new UserService();
