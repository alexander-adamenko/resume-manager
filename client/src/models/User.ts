export interface User {
    username: string;
    firstName: string;
    lastName: string;
    roles: Role[];
}
export interface Role {
    name: string;
}

export interface UserList {
    users: User[];
}