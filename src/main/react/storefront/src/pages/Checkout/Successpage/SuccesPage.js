import React from "react";
import "./SuccesPage.scss";
import SuccessPageIllustration from "../../../assets/images/checkoutImage/success-order.svg";
import Title from "../../../components/common/Content/TextComponents/Title/Title";
import { SubTitle } from "../../../components/common/Content/TextComponents";

const SuccesPage = () => {
  let cartData = JSON.parse(localStorage.getItem("checkoutData"))
    ? JSON.parse(localStorage.getItem("checkoutData"))
    : [];

  const generateOrderNumber = (min, max) => {
    return Math.floor(Math.random() * (max - min) + min);
  };

  return (
    <section className="order-success-container">
      <Title text="Bedankt voor jou bestelling met ordernummer: " />
      <SubTitle text={generateOrderNumber(100000, 999999)} />
      <img
        src={SuccessPageIllustration}
        alt="Order is placed!"
        className="checkout-success-image"
      />

      <section className="customer-information">
        <span>{"Hallo " + cartData.firstName}</span>
        <p>
          We hebben jou bestelling goed ontvangen, en gaan voor je aan de slag!
        </p>
        <p>
          Zodra jou bestelling klaar is in ons magazijn, ontvang je van ons een
          mailtje om je bestelling te volgen!
        </p>
        <p>
          Mocht je toch nog vragen hebben, onze klantenservice staat altijd voor
          je klaar!
        </p>
      </section>
    </section>
  );
};

export default SuccesPage;
