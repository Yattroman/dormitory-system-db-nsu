import axios from "axios";
import AuthHeader from "../util/AuthHeader";

const url = "http://localhost:8080/api/leisure/";

const getEventDetails = (eventId) => {
    return axios
        .get(url + "event/" + eventId, {
            headers: AuthHeader()
        });
}

const addEvent = (name, location, description) => {
    return axios
        .post(url + "event", {
            name,
            location,
            description
        }, {
            headers: AuthHeader(),
        });
}

const getEventsPage = (page, size) => {
    return axios
        .get(url + "events", {
            headers: AuthHeader(),
            params: {
                page: page,
                size: size
            }
        });
}

const ClubServive = {
    getEventDetails,
    getEventsPage,
    addEvent
};

export default ClubServive;

