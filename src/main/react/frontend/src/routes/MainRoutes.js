import React from 'react';
import { Routes, Route, Navigate} from "react-router-dom";
import ProtectedRoute from "./PrivateRoute";
import DashboardPage from "../pages/DashboardPage/DashboardPage";
import PublicRoute from "./PublicRoute";
import LoginPage from "../pages/LoginPage/LoginPage";
import AdminPage from "../pages/AdminPage";

const MainRoutes = () => (
    <Routes>
        <Route path="/admin" element={<ProtectedRoute />}>
            <Route path="/admin" element={<DashboardPage />}/>
            <Route path="/admin/:domain" element={<AdminPage />} />
        </Route>

        <Route path="/" element={<PublicRoute />}>
            <Route path="/" element={<LoginPage />}/>
        </Route>
    </Routes>
)

export default MainRoutes;