import React, {useEffect, useState} from "react";
import {
    MDBCol,
    MDBContainer,
    MDBInput,
    MDBInputGroup,
    MDBRow,
    MDBTable,
    MDBTableBody,
    MDBTableHead
} from "mdb-react-ui-kit";
import EventService from "../../services/EventService";
import ClosestEvents from "./ClosestEvents";
import FilteredEvents from "./FilteredEvents";

export default function AllEvents() {

    return (
        <>
            <ClosestEvents/>
            <FilteredEvents/>
        </>
    )
}
