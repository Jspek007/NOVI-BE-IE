import React, { useRef } from "react";
import "./MinicartMenu.scss";
import { FaShoppingCart } from "react-icons/fa";
import MinicartItems from "../MinicartItems";
import { SecondaryButton } from "../../../../../form/Buttons";
import FormButtonContainer from "../../../../../form/Forms/FormComponents/FormButtonContainer";
import { Link } from "react-router-dom";
import SubTitle from "../../../../Content/TextComponents/SubTitle/SubTitle";
import { useDetectOutsideClick } from "../../../../../../helpers/useDetectOutsideClick/useDetectOutsideClick";

const MinicartMenu = () => {
  const dropdownRef = useRef(null);
  const [isActive, setIsActive] = useDetectOutsideClick(dropdownRef);
  const onClick = () => setIsActive(!isActive);
  let cartItems = JSON.parse(localStorage.getItem("currentCart"));
  let cartArray = localStorage.getItem("currentCart");

  if (cartArray === "[]" || !cartArray) {
    return (
      <section className="menu-container-cart">
        <section className="menu-trigger" onClick={onClick}>
          <span>
            <FaShoppingCart className="user-icon" />
          </span>
          <section
            ref={dropdownRef}
            className={`minicart-menu ${isActive ? `active` : `inactive`}`}
          >
            <SubTitle text="Jouw winkelwagen" />
            <SubTitle text="Uw winkelwagen is leeg." />
          </section>
        </section>
      </section>
    );
  } else {
    return (
      <section className="menu-container-cart">
        <section className="menu-trigger" onClick={onClick}>
          <span>
            <FaShoppingCart className="user-icon" />
          </span>
          <section
            ref={dropdownRef}
            className={`minicart-menu ${isActive ? `active` : `inactive`}`}
          >
            <SubTitle text="Jouw winkelwagen" />
            <>
              <MinicartItems cartData={cartItems} />
              <FormButtonContainer>
                <Link exact="true" to="/winkelwagen">
                  <SecondaryButton primary callToAction="Winkelwagen" />
                </Link>
              </FormButtonContainer>
            </>
          </section>
        </section>
      </section>
    );
  }
};

export default MinicartMenu;
