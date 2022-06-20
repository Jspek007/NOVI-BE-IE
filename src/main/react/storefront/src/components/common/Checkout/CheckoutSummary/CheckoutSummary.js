import React from "react";
import "./CheckoutSummary.scss";
import { SecondaryButton } from "../../../form/Buttons";
import { Link } from "react-router-dom";
import { SubTitle } from "../../Content/TextComponents";

const CheckoutSummary = (props) => {
  return (
    <>
      <section className="summary-container">
        <section className="customer-data-container">
          <SubTitle text="Verzendgegevens: " />
          <span>
            {props.cartData.firstName + " " + props.cartData.lastName}
          </span>
          <span>
            {props.cartData.street +
              " " +
              props.cartData.houseNumber +
              " " +
              props.cartData.houseNumberAdition}
          </span>
          <span>{props.cartData.zipCode + ", " + props.cartData.city}</span>
          <span>{props.cartData.phoneNumber}</span>
        </section>
        <Link exact="true" to="/checkout/shipping">
          <SecondaryButton callToAction="Bewerken" />
        </Link>
      </section>
      <section className="summary-container">
        <section className="customer-data-container">
          <SubTitle text="Verzendmethode" />
          <span>
            {"Verzending door: " + props.shippingMethod.shippingMethod}
          </span>
        </section>
        <Link exact="true" to="/checkout/shipping">
          <SecondaryButton callToAction="Bewerken" />
        </Link>
      </section>
    </>
  );
};

export default CheckoutSummary;
