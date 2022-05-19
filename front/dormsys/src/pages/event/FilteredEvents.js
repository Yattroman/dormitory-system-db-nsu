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
import EventService from "../../services/EventService";
import Pagination from "@vlsergey/react-bootstrap-pagination";

export default function FilteredEvents() {

    const [eventsInfo, setEventsInfo] = useState(null);
    const [errorMsg, setErrorMsg] = useState(null);
    const [settings, setSettings] = useState({
        currentPage: 1,
        pageSize: 30,
        totalPages: 0,
        sortField: "name",
        sortType: "desc",
        search: ""
    })

    const [filter, setFilter] = useState({
        name: "",
        location: "",
        takeTime: ""
    })

    const filterToSearchConverter = () => {
        let searchRequest = "";
        if(filter.name !== ""){
            searchRequest += "name:" + filter.name + ",";
        }
        if(filter.location !== ""){
            searchRequest += "location:" + filter.location + ",";
        }
        if(filter.takeTime !== ""){
            searchRequest += "takeTime;" + filter.takeTime + ",";
        }
        return searchRequest;
    }

    useEffect(() => {
        settings.search = filterToSearchConverter();
        EventService.getEventsPage(settings).then(
            (response) => {
                setEventsInfo(response.data.events)
                /*setSettings({...settings,
                    currentPage: response.data.currentPage + 1,
                    totalPages: response.data.totalPages
                });*/
            },
            (error) => {
                const msg = (error.response && error.response.data) || error.message || error.toString();
                setErrorMsg(msg);
            }
        );
    }, [filter, settings]);

    const handleSettingsChange = (e) => {
        setSettings({...settings, [e.target.name]: e.target.value })
    }

    const handleFilterChange = (e) => {
        setFilter({...filter, [e.target.name]: e.target.value })
    }

    const eventItem = (event) => {
        return(
            <tr>
                <td className="w-50">{event.name}</td>
                <td className="w-20">{event.location}</td>
                <td className="w-20">{event.takeTime}</td>
            </tr>
        );
    }

    const handlePageChange = (currentPage) => {
        setSettings({...settings, currentPage: currentPage });
    }

    return (
        <>
            <MDBContainer>
                <h2 className="text-center p-4">All events</h2>
                <MDBRow className="justify-content-center">
                    <MDBCol md={4}>
                        <MDBInputGroup textBefore=">" className='mb-3'>
                            <input className='form-control' type='text' placeholder="Event name"
                                   name="name" onChange={handleFilterChange}/>
                        </MDBInputGroup>
                    </MDBCol>
                    <MDBCol md={2}>
                        <MDBInputGroup textBefore='>' className='mb-3'>
                            <input className='form-control' type='text' placeholder="Location"
                                   name="location" onChange={handleFilterChange}/>
                        </MDBInputGroup>
                    </MDBCol>
                    <MDBCol md={2}>
                        <MDBInputGroup textBefore='>' className='mb-3'>
                            <input className='form-control' type='date' placeholder="Date"
                                   name="takeTime" onChange={handleFilterChange}/>
                        </MDBInputGroup>
                    </MDBCol>
                    <MDBCol md={1}>
                        <select name="sortType" className='form-select' onChange={handleSettingsChange}>
                            <option value="desc">DESC</option>
                            <option value="asc">ASC</option>
                        </select>
                    </MDBCol>
                    <MDBCol md={2}>
                        <select name="sortField" className='form-select' onChange={handleSettingsChange}>
                            <option value="name">Name</option>
                            <option value="location">Location</option>
                            <option value="takeTime">Date</option>
                        </select>
                    </MDBCol>
                </MDBRow>
                <MDBRow className="justify-content-center">
                    <MDBCol md={11}>
                        <MDBTable striped>
                            <MDBTableHead>
                                <tr>
                                    <th>Event</th>
                                    <th>Location</th>
                                    <th>Date</th>
                                </tr>
                            </MDBTableHead>
                            <MDBTableBody>
                                {eventsInfo ? eventsInfo.map((event) => eventItem(event)) : <p> No suitable events </p>}
                            </MDBTableBody>
                        </MDBTable>
                    </MDBCol>
                </MDBRow>
                <MDBRow>
                    <Pagination name="page"
                                className="justify-content-center"
                                value={settings.currentPage - 1}
                                totalPages={settings.totalPages}
                                showFirstLast={settings.totalPages >= 10}
                                atBeginEnd={0}
                                aroundCurrent={2}
                                onChange={(e) => {
                                    const pageIndex = e.target.value + 1;
                                    handlePageChange(pageIndex);
                                }}/>
                </MDBRow>
            </MDBContainer>
        </>
    )
}
