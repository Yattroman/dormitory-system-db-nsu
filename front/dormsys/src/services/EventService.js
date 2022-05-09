import axios from "axios";
import AuthHeader from "../util/AuthHeader";

const url = "http://localhost:8080/api/leisure/";

const getEventDetails = (eventId) => {
    return axios
        .get(url + "event/" + eventId, {
            headers: AuthHeader()
        });
}

const addEvent = (name, location, description, takeTime, clubId) => {
    return axios
        .post(url + "event/club", {
            name: name,
            location: location,
            description: description,
            takeTime: takeTime,
            club: {
                id: clubId
            }
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

const enrollToEvent = (userId, eventId) => {
    return axios
        .post(url + "event/participant", {
            userId: userId,
            eventId: eventId
        }, {
            headers: AuthHeader(),
        });
}

const unenrollFromEvent = (userId, eventId) => {
    return axios
        .delete(url + "event/participant",  {
            headers: AuthHeader(),
            params: {
                userId: userId,
                eventId: eventId
            }
        });
}

const ClubServive = {
    getEventDetails,
    getEventsPage,
    enrollToEvent,
    unenrollFromEvent,
    addEvent
};

export default ClubServive;

