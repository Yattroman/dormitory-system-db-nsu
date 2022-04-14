import { NavLink, Outlet } from 'react-router-dom';

export default function Layout(){
    return(
        <>
            <header>
                <NavLink to="/">Home</NavLink>
                <NavLink to="/profile">Profile</NavLink>
            </header>
            <main>
                <Outlet/>
            </main>
            <footer>
                &copy; Made by Yattroman. 2022.
            </footer>
        </>
    )
}
