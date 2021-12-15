import axios, { AxiosResponse } from "axios";
import { SERVER_API_URL } from "../constants";
import {RoleImpl, User} from "../models/User";
import {UserDetails} from "../models/UserDetails";
import {FormikValues} from "formik";

const portfolioEndpoint = `${SERVER_API_URL}/users`;
const currentUser = `${SERVER_API_URL}/current-user`;
const addRoleToUserEndpoint = `${SERVER_API_URL}/user/add-role`;
const deleteRoleFromUserEndpoint = `${SERVER_API_URL}/user/delete-role`;

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

        if (typeof lastname === "string" && typeof firstname === "string" &&
            typeof username === "string" && typeof oldPassword === "string" &&
            typeof newPassword === "string") {
            params.append("lastname", lastname);
            params.append("firstname", firstname);
            params.append("username", username);
            params.append("oldPassword", oldPassword);
            params.append("newPassword", newPassword)
        }

        return axiosInstance.put(`${SERVER_API_URL}/`+oldUsername, params);
    }

    addRoleToUser(username: string | undefined, roleName: string | undefined): Promise<AxiosResponse<User>>{
        let params = new URLSearchParams();
        if (typeof username === "string" && typeof roleName === "string") {
            params.append("username", username);
            params.append("roleName", roleName);
        }
        return axiosInstance.post(addRoleToUserEndpoint, params);
    }
    removeRoleFromUser(username: string | undefined, roleName: string | undefined): Promise<AxiosResponse<User>>{
        return axiosInstance.post(deleteRoleFromUserEndpoint+"/"+username+"/"+roleName);
    }

    createUser(user: FormikValues){
        let rolesNames: string[] = user.roles;
        user.roles = [];
        for (let i = 0; i < rolesNames.length; i++)
            user.roles.push(new RoleImpl(rolesNames[i]));
        return axiosInstance.post(SERVER_API_URL + '/user/save', user);
    }
}

export default new UserService();