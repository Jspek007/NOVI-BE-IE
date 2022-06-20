import React, { useEffect, useState } from "react";
import "./CheckoutForm.scss";
import InputField from "../FormComponents/InputField";
import Subtitle from "../../../common/Content/TextComponents/SubTitle/SubTitle";
import SecondaryButton from "../../Buttons/SecondaryButton/SecondaryButton";
import { Link } from "react-router-dom";
import Title from "../../../common/Content/TextComponents/Title/Title";
import CheckoutInputItem from "../../../common/Checkout/CheckoutInputs/CheckoutInputItem";
import PostNLImage from "../../../../assets/images/shippingMethods/PostNL_logo.svg.png";
import DPDImage from "../../../../assets/images/shippingMethods/DPD.jpeg";
import DHLImage from "../../../../assets/images/shippingMethods/DHL.png";
import CartSummary from "../../../common/Cart/CartSummary";

const CheckoutForm = () => {
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [email, setEmail] = useState("");
  const [zipCode, setZipCode] = useState("");
  const [houseNumber, setHouseNumber] = useState("");
  const [houseNumberAdition, setHouseNumberAdition] = useState("");
  const [street, setStreet] = useState("");
  const [city, setCity] = useState("");
  const [phoneNumber, setPhoneNumber] = useState("");
  const [shippingMethod, setShippingMethod] = useState("");
  const [cartItems] = useState(JSON.parse(localStorage.getItem("currentCart")));

  useEffect(() => {
    let newShippingMethod = {
      shippingMethod,
    };
    localStorage.setItem("shippingMethod", JSON.stringify(newShippingMethod));
  });

  const saveCheckoutData = () => {
    let newCheckoutData = {
      firstName,
      lastName,
      email,
      zipCode,
      houseNumber,
      houseNumberAdition,
      street,
      city,
      phoneNumber,
    };
    localStorage.setItem("checkoutData", JSON.stringify(newCheckoutData));
  };

  return (
    <>
      <section className="checkout-form-container">
        <Title text="Adres gegevens" />
        <form>
          <Subtitle text="Voornaam" />
          <InputField
            formSection="checkout"
            inputType="text"
            idValue="firstName"
            placeholder="Voornaam"
            eventHandler={(event) => setFirstName(event.target.value)}
          />
          <Subtitle text="Achternaam" />
          <InputField
            formSection="checkout"
            inputType="text"
            idValue="lastName"
            placeholder="Achternaam"
            eventHandler={(event) => setLastName(event.target.value)}
          />
          <Subtitle text="E-mail adres" />
          <InputField
            formSection="checkout"
            inputType="text"
            idValue="email"
            placeholder="E-mail adres"
            eventHandler={(event) => setEmail(event.target.value)}
            required
          />
          <Subtitle text="Postcode" />
          <InputField
            formSection="checkout"
            inputType="text"
            idValue="zipCode"
            placeholder="Postcode"
            eventHandler={(event) => setZipCode(event.target.value)}
          />
          <Subtitle text="Huisnummer & toevoeging" />
          <section className="side-by-side">
            <InputField
              formSection="checkout"
              inputType="number"
              idValue="houseNumber"
              placeholder="Huisnummer"
              eventHandler={(event) => setHouseNumber(event.target.value)}
            />
            <InputField
              formSection="checkout"
              inputType="text"
              idValue="houseNumberAdition"
              placeholder="Toevoeging"
              eventHandler={(event) =>
                setHouseNumberAdition(event.target.value)
              }
            />
          </section>
          <Subtitle text="Straatnaam" />
          <InputField
            formSection="checkout"
            inputType="text"
            idValue="streetName"
            placeholder="Straatnaam"
            eventHandler={(event) => setStreet(event.target.value)}
          />
          <Subtitle text="Plaatsnaam" />
          <InputField
            formSection="checkout"
            inputType="text"
            idValue="city"
            placeholder="Plaatsnaam"
            eventHandler={(event) => setCity(event.target.value)}
          />
          <Subtitle text="Telefoonnummer" />
          <InputField
            formSection="checkout"
            inputType="number"
            idValue="phoneNumber"
            placeholder="Telefoonnummer"
            eventHandler={(event) => setPhoneNumber(event.target.value)}
          />
        </form>
      </section>
      <section className="information-container">
        <Title text="Verzendmethode" />
        <section className="inputs-container">
          <CheckoutInputItem
            imageSource={PostNLImage}
            inputText={"Verzending door PostNL"}
            shippingMethod="PostNL"
            eventHandler={(event) => setShippingMethod(event.target.value)}
            inputName="shipping"
            value="PostNL"
          />
          <CheckoutInputItem
            imageSource={DPDImage}
            inputText={"Verzending door DPD"}
            shippingMethod="DPD"
            eventHandler={(event) => setShippingMethod(event.target.value)}
            inputName="shipping"
            value="DPD"
          />
          <CheckoutInputItem
            imageSource={DHLImage}
            inputText={"Verzending door DHL"}
            shippingMethod="DHL"
            eventHandler={(event) => setShippingMethod(event.target.value)}
            inputName="shipping"
            value="DHL"
          />
          <CartSummary cartData={cartItems} />
        </section>

        <section className="checkout-cart-button">
          <Link exact="true" to="/checkout/payment">
            <SecondaryButton
              callToAction="Volgende"
              primary
              clickHandler={saveCheckoutData}
            />
          </Link>
        </section>
      </section>
    </>
  );
};

export default CheckoutForm;
