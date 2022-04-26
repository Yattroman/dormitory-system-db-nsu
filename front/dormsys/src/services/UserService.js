import axios from "axios";
import AuthHeader from "../util/AuthHeader";

const url = "http://localhost:8080/api/user/";

const getDefaultUserInfo = () => {
    return axios.get( url, {headers: AuthHeader()});
};

const getDormitoryManagerBoard = () => {
    return axios.get( url, {headers: AuthHeader()});
}

const UserService = {
    getDefaultUserInfo,
};

export default UserService;
