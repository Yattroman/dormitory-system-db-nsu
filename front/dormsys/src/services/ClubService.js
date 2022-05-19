import axios from "axios";
import AuthHeader from "../util/AuthHeader";

const url = "http://localhost:8080/api/leisure/";

const getClubDetails = (clubId) => {
    return axios
        .get(url + "club/" + clubId, {
            headers: AuthHeader()
        });
}

const addClub = (name, description, uniqueName, clubManagerId) => {
    return axios
        .post(url + "club", {
            name: name,
            description: description,
            uniqueName: uniqueName,
            clubManager : {
                id: clubManagerId
            }
        }, {
            headers: AuthHeader(),
        });
}

const getClubsPage = (page, size) => {
    return axios
        .get(url + "clubs", {
            headers: AuthHeader(),
            params: {
                page: page,
                size: size
            }
        });
}

const subscribeToClub = (userId, clubId) => {
    return axios
        .post(url + "club/participant", {
            userId: userId,
            clubId: clubId
        }, {
            headers: AuthHeader(),
        });
}

const unsubscribeFromClub = (userId, clubId) => {
    return axios
        .delete(url + "club/participant",  {
            headers: AuthHeader(),
            params: {
                userId: userId,
                clubId: clubId
            }
        });
}

const getUserSubscribedClubs = (userId) => {
    return axios
        .get(url + "clubs/user",  {
            headers: AuthHeader(),
            params: {
                userId: userId,
            }
        });
}

const getUserManagingClubs = (userId) => {
    return axios
        .get(url + "clubs/managing",  {
            headers: AuthHeader(),
            params: {
                userId: userId,
            }
        });
}

const getTopPopularClubs = (n) => {
    return axios
        .get(url + "clubs/top/" + n, {
            headers: AuthHeader()
        });
}

const getClubsEventsStatistics = () => {
    return axios
        .get(url + "clubs/events/avg", {
            headers: AuthHeader()
        });
}

const ClubService = {
    getClubDetails,
    getClubsPage,
    subscribeToClub,
    unsubscribeFromClub,
    getUserSubscribedClubs,
    getUserManagingClubs,
    getTopPopularClubs,
    addClub,
    getClubsEventsStatistics
};

export default ClubService;

