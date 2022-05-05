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

const ClubService = {
    getClubDetails,
    getClubsPage,
    addClub
};

export default ClubService;

