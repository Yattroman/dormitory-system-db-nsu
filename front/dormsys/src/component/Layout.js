import { Outlet } from 'react-router-dom';
import NavBar from "./NavBar";

export default function Layout(){

    return(
        <div className="d-flex flex-column min-vh-100">
            <header>
                <NavBar/>
            </header>
            <main className="mb-5">
                <Outlet/>
            </main>
            <footer className="bg-light mt-auto py-5 text-center" >
                &copy; Made by Yattroman. 2022.
            </footer>
        </div>
    )
}
