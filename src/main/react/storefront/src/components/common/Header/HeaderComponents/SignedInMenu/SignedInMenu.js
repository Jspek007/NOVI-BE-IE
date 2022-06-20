import React, { useRef } from "react";
import "./SignedInMenu.scss";
import { useAuth } from "../../../../../contexts/AuthContext";
import { FaUser } from "react-icons/fa";
import { Link } from "react-router-dom";
import { useDetectOutsideClick } from "../../../../../helpers/useDetectOutsideClick/useDetectOutsideClick";

function SignedInMenu() {
  const { logout } = useAuth();

  const dropdownRef = useRef(null);
  const [isActive, setIsActive] = useDetectOutsideClick(dropdownRef, false);
  const onClick = () => setIsActive(!isActive);

  return (
    <section className="menu-container-user">
      <button onClick={onClick} className="menu-trigger">
        <span>
          <FaUser className="user-icon" />
        </span>
      </button>
      <nav
        ref={dropdownRef}
        className={`menu ${isActive ? "active" : "inactive"}`}
      >
        <ul>
          <li>
            <Link exact="true" to="/mijn-account" onClick={onClick}>
              Mijn account
            </Link>
          </li>
          <li>
            <Link exact="true" to="/wishlist" onClick={onClick}>
              Wishlist
            </Link>
          </li>
          <li>
            <Link exact="true" to="/" onClick={logout}>
              Uitloggen
            </Link>
          </li>
        </ul>
      </nav>
    </section>
  );
}

export default SignedInMenu;
