import axios, { AxiosResponse } from "axios";
import { SERVER_API_URL } from "../constants";
import { LoginDetails } from "../models/AccountDetails";

const loginEndpoint = `${SERVER_API_URL}/v1/login/`;

const axiosInstance = axios.create({withCredentials: true});

// interface LoginResponse {
//   jwtToken: string;
// }
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
    //return axiosInstance.post(`${loginEndpoint}`, user);
    const params = new URLSearchParams()
    params.append('username', user.username)
    params.append('password', user.password)
    return axiosInstance.post('http://127.0.0.1:8080/api/v1/login', params, config);
  }

  isLoggedIn(): Promise<AxiosResponse<IsLoggedInResponse>> {
    //return axiosInstance.get(`${loginEndpoint}`);
    return axiosInstance.get('http://127.0.0.1:8080/api/v1/is-authenticated');
  }
}

export default new LoginService();
