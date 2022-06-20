import React from "react";
import "./Header.scss";
import Navigation from "./HeaderComponents/Navigation/Navigation";
import HeaderActionMenu from "./HeaderComponents/HeaderActionMenu/HeaderActionMenu";
import BurgerMenu from "./HeaderComponents/BurgerMenu";
import { Link } from "react-router-dom";
import { FaShoppingCart } from "react-icons/fa";
import { useAuth } from "../../../contexts/AuthContext";

function Header() {
  const auth = useAuth();

  return (
    <>
      <div className="header-container">
        <BurgerMenu />
        <Navigation />
        <a href="/" draggable="false" className="main-header-logo">
          FashionPoint
        </a>
        {!auth.currentUser && (
          <section className="right-header-section">
            <HeaderActionMenu />
          </section>
        )}
        {auth.currentUser && (
          <>
            <Link exact="true" to="/winkelwagen">
              <FaShoppingCart className="header-cart-icon" />
            </Link>
            <section className="right-header-section">
              <HeaderActionMenu />
            </section>
          </>
        )}
      </div>
    </>
  );
}

export default Header;
