import React, {useEffect, useState} from "react";
import {
    MDBCol,
    MDBContainer,
    MDBInputGroup,
    MDBRow,
    MDBTable,
    MDBTableBody,
    MDBTableHead
} from "mdb-react-ui-kit";
import ClubService from "../../services/ClubService";

export default function ClubsEventsStatistics() {

    const [statistics, setStatistics] = useState(null);
    const [errorMsg, setErrorMsg] = useState(null);

    useEffect(() => {
        ClubService.getClubsEventsStatistics().then(
            (response) => setStatistics(response.data),
            (error) => {
                const msg = (error.response && error.response.data) || error.message || error.toString();
                setErrorMsg(msg);
            }
        );
    }, [statistics]);

    const clubEventStatItem = (item) => {
        return(
            <tr>
                <td>{item.clubName}</td>
                <td>{item.avgParticipantsNumber}</td>
                <td>{item.eventsNumber}</td>
            </tr>
        );
    }

    return (
        <MDBContainer>
            <h2 className="text-center p-4">Top popular events</h2>
            <MDBRow className="justify-content-center">
                <MDBCol md={8}>
                    <MDBTable striped>
                        <MDBTableHead>
                            <tr>
                                <th>Club name</th>
                                <th>Avg participants on event</th>
                                <th>Total events</th>
                            </tr>
                        </MDBTableHead>
                        <MDBTableBody>
                            {statistics ? statistics.map((item) => clubEventStatItem(item)) : <p> No available statistics </p>}
                        </MDBTableBody>
                    </MDBTable>
                </MDBCol>
            </MDBRow>
        </MDBContainer>
    )
}
