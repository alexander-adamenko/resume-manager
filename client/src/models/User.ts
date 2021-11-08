export interface User {
    username: string;
    firstName: string;
    lastName: string;
    roles: string[];
}

export interface UserList {
    users: User[];
}