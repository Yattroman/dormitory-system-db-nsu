import { NavLink, Outlet } from 'react-router-dom';

export default function Layout(){
    return(
        <>
            <header>
                <NavLink to="/">Home</NavLink>
                <NavLink to="/profile">Profile</NavLink>
                <NavLink to="/signup">Sign Up</NavLink>
                <NavLink to="/login">Login</NavLink>
            </header>
            <main>
                <Outlet/>
            </main>
            <footer className="bg-light fixed-bottom py-5">
                &copy; Made by Yattroman. 2022.
            </footer>
        </>
    )
}
