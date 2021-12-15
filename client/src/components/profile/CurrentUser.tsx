import axios, { AxiosResponse } from "axios";
import { SERVER_API_URL } from "../../constants";
import {UserDetails} from "../../models/UserDetails";

const profileEndpoint = `${SERVER_API_URL}/current-user`;

const axiosInstance = axios.create({ withCredentials: true });

class CurrentUser {
    get(): Promise<AxiosResponse<UserDetails>> {
        return axiosInstance.get(profileEndpoint);
    }
}

export default new CurrentUser().get();