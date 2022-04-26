import AuthService from "../services/AuthService";

export default function AuthHeader() {
    const user = AuthService.getCurrentUser();

    if (user && user.jwtToken) {
        return { 'Authorization': user.jwtToken};
    } else {
        return {};
    }
}
