import React, { useRef } from "react";
import "./BurgerMenu.scss";
import { Link } from "react-router-dom";
import { FaBars } from "react-icons/fa";
import { AiOutlineClose } from "react-icons/ai";
import { IconContext } from "react-icons";
import { SidebarData } from "./SidebarData";
import { useAuth } from "../../../../../contexts/AuthContext";
import { useDetectOutsideClick } from "../../../../../helpers/useDetectOutsideClick/useDetectOutsideClick";

const BurgerMenu = () => {
  const dropdownRef = useRef(null);
  const [sidebar, setSidebar] = useDetectOutsideClick(dropdownRef);
  const { logout } = useAuth();
  const auth = useAuth();

  const showSideBar = () => setSidebar(!sidebar);

  return (
    <>
      <IconContext.Provider value={{ color: "#000000\\n" }}>
        <section className="navbar">
          <Link to="#" className="menu-bars">
            <FaBars onClick={showSideBar} />
          </Link>
        </section>

        <nav
          ref={dropdownRef}
          className={sidebar ? "nav-menu active" : "nav-menu"}
        >
          <ul className="nav-menu-items" onClick={showSideBar}>
            <li className="navbar-toggle">
              <Link to="#" className="menu-bars">
                <AiOutlineClose />
              </Link>
            </li>
            {SidebarData.map((item, index) => {
              return (
                <li key={index} className={item.cName}>
                  <Link exact="true" to={item.path}>
                    <span>{item.title}</span>
                  </Link>
                </li>
              );
            })}
            {auth.currentUser && (
              <span className="menu-action-logout" onClick={logout}>
                Uitloggen
              </span>
            )}
            {!auth.currentUser && (
              <span className="menu-action-logout">
                <Link exact="true" to="/login">
                  Inloggen
                </Link>
              </span>
            )}
            <span className="menu-about-us">
              <Link exact="true" to="/over-ons">
                Over ons
              </Link>
            </span>
          </ul>
        </nav>
      </IconContext.Provider>
    </>
  );
};

export default BurgerMenu;
