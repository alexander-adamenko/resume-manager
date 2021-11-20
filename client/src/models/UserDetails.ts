import {User} from "./User";

export interface UserDetails extends User{
    oldPassword: string;
    newPassword: string;
}