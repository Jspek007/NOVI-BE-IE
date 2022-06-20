import React from "react";
import { SignedInMenu } from "../../../index";
import { SecondaryButton } from "../../../../form/Buttons";
import { useAuth } from "../../../../../contexts/AuthContext";
import { Link } from "react-router-dom";
import MinicartMenu from "../Minicart/MinicartMenu";
import { FaUser } from "react-icons/fa";
import Searchbar from "../Searchbar";

function HeaderActionMenu() {
  const auth = useAuth();

  return (
    <>
      {auth.currentUser && (
        <>
          <Searchbar />
          <MinicartMenu />
          <SignedInMenu />
        </>
      )}

      {!auth.currentUser && (
        <>
          <section className="mobile-header-menu">
            <Link exact="true" to={"login"}>
              <FaUser />
            </Link>
          </section>
          <section className="header-menu">
            <Link exact="true" to="/login">
              <SecondaryButton callToAction="Inloggen" />
            </Link>
            <Link exact="true" to="/aanmelden">
              <SecondaryButton callToAction="Aanmelden" primary />
            </Link>
          </section>
        </>
      )}
    </>
  );
}

export default HeaderActionMenu;
