import React from 'react';
import {NavLink} from "react-router-dom";
import "./Navigation.scss";

function Navigation() {
    return (

            <header className="main-header">
                <section className="left-header-section">
                    <NavLink to="/klantenservice" className="left-header-section-item"
                             activeClassName="section-item-active">
                        Klantenservice
                    </NavLink>

                    <NavLink to="/over-ons" className="left-header-section-item section-item"
                             activeClassName="section-item-active">
                        Over ons
                    </NavLink>

                    <NavLink to="/collectie" className="left-header-section-item section-item"
                             activeClassName="section-item-active">
                        Collectie
                    </NavLink>
                </section>
            </header>
    );
}

export default Navigation;