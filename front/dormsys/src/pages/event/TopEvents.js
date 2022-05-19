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

export default function TopEvents() {

    const [eventsInfo, setEventsInfo] = useState(null);
    const [errorMsg, setErrorMsg] = useState(null);
    const [settings, setSettings] = useState({
        n: 10,
        participantsMin: 0,
        participantsMax: 1000,
    })

    useEffect(() => {
        if(settings.participantsMin <= settings.participantsMax){
            EventService.getTopPopularEvents(settings).then(
                (response) => setEventsInfo(response.data),
                (error) => {
                    const msg = (error.response && error.response.data) || error.message || error.toString();
                    setErrorMsg(msg);
                }
            );
        }
    }, [settings]);

    const handleChange = (e) => {
        setSettings({...settings, [e.target.name]: e.target.value })
    }

    const topEventItem = (event) => {
        return(
            <tr>
                <td>{event.name}</td>
                <td>{event.location}</td>
                <td>{event.participantsNumber}</td>
                <td>{event.clubOrg}</td>
            </tr>
        );
    }

    return (
        <MDBContainer>
            <h2 className="text-center p-4">Top popular events</h2>
            <MDBRow className="justify-content-center">
                <MDBCol md={2}>
                    <MDBInputGroup textBefore='N' className='mb-3'>
                        <input className='form-control' type='text' placeholder="Events to show" name="n" onChange={handleChange} />
                    </MDBInputGroup>
                </MDBCol>
                <MDBCol md={3}>
                    <MDBInputGroup textBefore='MIN' className='mb-3'>
                        <input className='form-control' type='text' placeholder="Participants min"
                               name="participantsMin" onChange={handleChange}/>
                    </MDBInputGroup>
                </MDBCol>
                <MDBCol md={3}>
                    <MDBInputGroup textBefore='MAX' className='mb-3'>
                        <input className='form-control' type='text' placeholder="Participants max"
                                name="participantsMax" onChange={handleChange}/>
                    </MDBInputGroup>
                </MDBCol>
            </MDBRow>
            <MDBRow className="justify-content-center">
                <MDBCol md={8}>
                    <MDBTable striped>
                        <MDBTableHead>
                            <tr>
                                <th>Event</th>
                                <th>Location</th>
                                <th>Participants</th>
                                <th>Club manager</th>
                            </tr>
                        </MDBTableHead>
                        <MDBTableBody>
                            {eventsInfo ? eventsInfo.map((event) => topEventItem(event)) : <p> No top events </p>}
                        </MDBTableBody>
                    </MDBTable>
                </MDBCol>
            </MDBRow>
        </MDBContainer>
    )
}
