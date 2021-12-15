import axios, { AxiosResponse } from "axios";
import { SERVER_API_URL } from "../constants";
import { LoginDetails } from "../models/AccountDetails";

const axiosInstance = axios.create({withCredentials: true});

interface LoginResponse {
  access_token: string;
  refresh_token: string;
}

interface IsLoggedInResponse {
  username: string;
}

const config = {
  headers: {
    'Content-Type': 'application/x-www-form-urlencoded'
  }
}
class LoginService {

  login(user: LoginDetails): Promise<AxiosResponse<LoginResponse>> {
    const params = new URLSearchParams()
    params.append('username', user.username)
    params.append('password', user.password)
    return axiosInstance.post(SERVER_API_URL + '/login', params, config);
  }

  isLoggedIn(): Promise<AxiosResponse<string>> {
    return axiosInstance.get(SERVER_API_URL + '/is-authenticated');
  }
}

export default new LoginService();
