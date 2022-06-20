import React from 'react';
import "./AccountMenu.scss";
import {Link} from "react-router-dom/";
import {SubTitle} from "../../Content/TextComponents";

function AccountMenu() {
    return (
        <section className="my-account-menu-container">
            <SubTitle text="Mijn account" />
            <ul className="my-account-menu">
                <Link exact="true" to="/mijn-account" className="my-account-menu-item">Mijn profiel</Link>
                <Link exact="true" to="/wishlist" className="my-account-menu-item">Mijn wishlist</Link>
                <Link exact="true" to="/klantenservice" className="my-account-menu-item">Klantenservice</Link>
                <Link exact="true" to="/klantenservice" className="my-account-menu-item">Retourneren</Link>
            </ul>
        </section>
    );
}

export default AccountMenu;