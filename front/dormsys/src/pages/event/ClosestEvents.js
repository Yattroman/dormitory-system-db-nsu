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

export default function ClosestEvents() {

    const [errorMsg, setErrorMsg] = useState(null);
    const [closestEvents, setClosestEvents] = useState(null);

    useEffect(() => {
        EventService.getClosestEvents().then(
            (response) => setClosestEvents(response.data),
            (error) => {
                const msg = (error.response && error.response.data) || error.message || error.toString();
                setErrorMsg(msg);
            }
        );
    }, [closestEvents]);

    const closestEventItem = (event) => {
        return(
            <tr>
                <td>{event.name}</td>
                <td>{event.location}</td>
                <td>{event.takeTime}</td>
                <td>{event.participantsNumber}</td>
                <td>{event.clubOrg}</td>
            </tr>
        );
    }

    return (
        <MDBContainer>
            <MDBRow className="text-center mt-3">
                <h3>Closest events</h3>
            </MDBRow>
            <MDBRow className="justify-content-center">
                <MDBCol md={9}>
                    <MDBTable striped>
                        <MDBTableHead>
                            <tr>
                                <th>Event</th>
                                <th>Location</th>
                                <th>Take time</th>
                                <th>Participants</th>
                                <th>Club manager</th>
                            </tr>
                        </MDBTableHead>
                        <MDBTableBody>
                            {closestEvents ? closestEvents.map((event) => closestEventItem(event)) : <p> No closest events </p>}
                        </MDBTableBody>
                    </MDBTable>
                </MDBCol>
            </MDBRow>
        </MDBContainer>
    )
}
