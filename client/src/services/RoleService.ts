import axios, {AxiosResponse} from "axios";
import {Role} from "../models/User";
import {SERVER_API_URL} from "../constants";
const portfolioEndpoint = `${SERVER_API_URL}/roles`;
const axiosInstance = axios.create({ withCredentials: true });

class RoleService {
    getAllRoles(): Promise<AxiosResponse<Role[]>> {
        return axiosInstance.get(portfolioEndpoint);
    }
}

export default new RoleService();