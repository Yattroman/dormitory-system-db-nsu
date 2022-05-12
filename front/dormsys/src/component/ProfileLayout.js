import Profile from "../pages/Profile";
import MyClubs from "../pages/club/MyClubs";
import MyEvents from "../pages/event/MyEvents";

export default function ProfileLayout() {
    return (
        <>
            <Profile/>
            <MyClubs/>
            <MyEvents/>
        </>
    )
}
