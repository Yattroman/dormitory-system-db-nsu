import {Component} from "react";
import { Route, Routes } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css'

import Home from './pages/Home';
import SignUp from './pages/SignUp';
import Login from './pages/Login';
import Profile from './pages/Profile';
import Layout from './component/Layout';

export default function App()  {
    return (
        <div>
            <Routes>
                <Route path="/" element={<Layout/>}>
                    <Route index element={<Home />} />
                    <Route path = "login" element ={<Login />}/>
                    <Route path = "signup" element={<SignUp />}/>
                    <Route path = "profile" element={<Profile />}/>
                    <Route path = "*" element={<h1>other</h1>}/>
                </Route>
            </Routes>
        </div>
    );
}