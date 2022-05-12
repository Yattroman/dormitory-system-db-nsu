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
        .delete(url + "event/participant", {
            headers: AuthHeader(),
            params: {
                userId: userId,
                eventId: eventId
            }
        });
}

const getEnrolledItemsByUser = (userId) => {
    return axios
        .get(url + "events/user"), {
            headers: AuthHeader(),
            params: {
                userId: userId
            }
        }
}

const getEnrolledItemsByClub = (clubId) => {
    return axios
        .get(url + "events/club"), {
            headers: AuthHeader(),
            params: {
                clubId: clubId
            }
        }
}

const getTopPopularEvents = (n) => {
    return axios
        .get(url + "events/top/" + n, {
            headers: AuthHeader()
        });
}

const EventService = {
    getEventDetails,
    getEventsPage,
    enrollToEvent,
    unenrollFromEvent,
    getEnrolledItemsByClub,
    getEnrolledItemsByUser,
    getTopPopularEvents,
    addEvent
};

export default EventService;

