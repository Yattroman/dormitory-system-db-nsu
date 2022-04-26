import axios from "axios";

const url = "http://localhost:8080/api/auth/";

const register = (nickname, firstName, surname, middleName, password, email) => {
    return axios.post(url + "signup", {
        nickname,
        firstName,
        surname,
        middleName,
        password,
        email
    });
};

const login = (nickname, password) => {
    return axios
        .post(url + "signin", {
            nickname,
            password,
        })
        .then((response) => {
            if (response.data.jwtToken) {
                localStorage.setItem("user", JSON.stringify(response.data));
            }
            return response.data;
        });
};

const logout = () => {
    localStorage.removeItem("user");
};

const getCurrentUser = () => {
    return JSON.parse(localStorage.getItem("user"));
};

const AuthService = {
    register,
    login,
    logout,
    getCurrentUser,
};

export default AuthService;
