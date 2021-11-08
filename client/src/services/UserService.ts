import axios, { AxiosResponse } from "axios";
import { SERVER_API_URL } from "../constants";
import { User, UserList } from "../models/User";

const portfolioEndpoint = `${SERVER_API_URL}/users`;

const axiosInstance = axios.create({ withCredentials: true });

interface DeleteResponse {
    deleted: boolean;
}

class UserService {
    getAllUsers(): Promise<AxiosResponse<User[]/*UserList*/>> {
        return axiosInstance.get(portfolioEndpoint);
    }

    // getPortfolioById(portfolioId: number): Promise<AxiosResponse<Portfolio>> {
    //     return axiosInstance.get(`${portfolioEndpoint}${portfolioId}`);
    // }
    //
    // updatePortfolio(
    //     portfolioId: number,
    //     portfolio: Portfolio
    // ): Promise<AxiosResponse<Portfolio>> {
    //     return axiosInstance.put(`${portfolioEndpoint}${portfolioId}`, portfolio);
    // }
    //
    // deletePortfolio(portfolioId: number): Promise<AxiosResponse<DeleteResponse>> {
    //     return axiosInstance.delete(`${portfolioEndpoint}${portfolioId}`);
    // }
    //
    // addPortfolio(portfolio: PortfolioDetails): Promise<AxiosResponse<Portfolio>> {
    //     return axiosInstance.post(`${portfolioEndpoint}`, portfolio);
    // }
    //
    // deleteAllPortfolios(): Promise<AxiosResponse<DeleteResponse>> {
    //     return axiosInstance.delete(`${portfolioEndpoint}`);
    // }
}

export default new UserService();
