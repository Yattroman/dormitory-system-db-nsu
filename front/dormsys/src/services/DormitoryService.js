import axios from "axios";
import AuthHeader from "../util/AuthHeader";

const url = "http://localhost:8080/api/dormitory/";

const addInhabitantIntoRoom = (roomId, userId, daysToLive) => {
    return axios
        .post(url + "room/inhabitant", {
            roomId,
            userId,
            daysToLive
        }, {
            headers: AuthHeader(),
        });
}

const addFurnitureIntoRoom = (roomId, furnitureId) => {
    return axios
        .post(url + "room/furniture", {
            roomId,
            furnitureId
        }, {
            headers: AuthHeader(),
        });
}

const removeInhabitantFromRoom = (inhabitantId) => {
    return axios
        .delete(url + "room/inhabitant", {
                headers: AuthHeader(),
                params: {
                    inhabitantId: inhabitantId
                }
            }
        );
}

const removeFurnitureFromRoom = (furnitureId) => {
    return axios
        .delete(url + "room/furniture", {
            headers: AuthHeader(),
            params: {
                furnitureId: furnitureId
            }
        });
}

const getRoomsPage = (dormitoryName, page, size) => {
    return axios
        .get(url + dormitoryName + "/rooms", {
            headers: AuthHeader(),
            params: {
                page: page,
                size: size
            }
        });
}

const getRoomDetails = (roomId) => {
    return axios
        .get(url + "room/" + roomId, {
            headers: AuthHeader()
        });
}

const getInhabitantDetailsByNickname = (nickname) => {
    return axios
        .get(url + "inhabitant/" + nickname, {
            headers: AuthHeader()
        });
}

const getFurnitureDetailsByInnerNumber = (innerNumber) => {
    return axios
        .get(url + "furniture/" + innerNumber, {
            headers: AuthHeader()
        });
}

const addFurniture = (name, innerCode, category) => {
    return axios
        .post(url + "furniture", {
            name,
            innerCode,
            category
        }, {
            headers: AuthHeader(),
        });
}

const DormitoryService = {
    getRoomsPage,
    getRoomDetails,
    getInhabitantDetailsByNickname,
    getFurnitureDetailsByInnerNumber,
    addFurniture,
    addInhabitantIntoRoom,
    removeInhabitantFromRoom,
    addFurnitureIntoRoom,
    removeFurnitureFromRoom
};

export default DormitoryService;

