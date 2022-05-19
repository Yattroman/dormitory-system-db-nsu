import { Route, Routes } from 'react-router-dom';
import 'mdb-ui-kit/css/mdb.min.css';

import Home from './pages/Home';
import SignUp from './pages/auth/SignUp';
import Layout from './component/Layout';
import SignIn from "./pages/auth/SignIn";
import RoomsGallery from "./pages/dormitory/RoomsGallery";
import RoomInfo from "./pages/dormitory/RoomInfo";
import FurnitureAdd from "./pages/dormitory/FurnitureAdd";
import ClubAdd from "./pages/club/ClubAdd";
import ClubInfo from "./pages/club/ClubInfo";
import EventAdd from "./pages/event/EventAdd";
import AllClubs from "./pages/club/AllClubs";
import ProfileLayout from "./component/ProfileLayout";
import EventInfo from "./pages/event/EventInfo";
import TopClubs from "./pages/club/TopClubs";
import TopEvents from "./pages/event/TopEvents";
import AllEvents from "./pages/event/AllEvents";
import ClubsEventsStatistics from "./pages/club/ClubsEventsStatistics";
import FilteredEvents from "./pages/event/FilteredEvents";


export default function App()  {

    return (
        <div>
            <Routes>
                <Route path="/" element={<Layout/>}>
                    <Route index element={<Home/>} />
                    <Route path = "signup" element={<SignUp/>}/>
                    <Route path = "signin" element={<SignIn/>}/>
                    <Route path = "profile" element={<ProfileLayout/>}/>
                    <Route path="dormitory/:dormitoryName/">
                        <Route path = "rooms" element={<RoomsGallery />}/>
                        <Route path = "room/:id" element={<RoomInfo />}/>
                        <Route path = "furniture/add" element={<FurnitureAdd />}/>
                    </Route>
                    <Route path="leisure/">
                        <Route path = "clubs" element={<AllClubs/>}/>
                        <Route path = "clubs/events/avg" element={<ClubsEventsStatistics/>}/>
                        <Route path = "clubs/top" element={<TopClubs/>}/>
                        <Route path = "club/:id" element={<ClubInfo/>}/>
                        <Route path = "club/add" element={<ClubAdd/>}/>
                        <Route path = "events" element={<AllEvents/>}/>
                        <Route path = "events/top" element={<TopEvents/>}/>
                        <Route path = "event/:id" element={<EventInfo/>}/>
                        <Route path = "event/add" element={<EventAdd/>}/>
                    </Route>
                    <Route path = "*" element={<h1>other</h1>}/>
                </Route>
            </Routes>
        </div>
    );
}
