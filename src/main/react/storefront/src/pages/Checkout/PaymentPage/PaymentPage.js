import { React, useEffect } from "react";
import CheckoutSummary from "../../../components/common/Checkout/CheckoutSummary/CheckoutSummary";
import CheckoutInputItem from "../../../components/common/Checkout/CheckoutInputs/CheckoutInputItem";
import Klarna from "../../../assets/images/paymentMethods/Klarna.svg";
import ApplePay from "../../../assets/images/paymentMethods/ApplePay.svg";
import GooglePay from "../../../assets/images/paymentMethods/Google_pay.svg";
import MasterCard from "../../../assets/images/paymentMethods/Mastercard.svg";
import Paypal from "../../../assets/images/paymentMethods/Paypal.svg";
import { SecondaryButton } from "../../../components/form/Buttons";
import { Link } from "react-router-dom";
import { SubTitle } from "../../../components/common/Content/TextComponents";
import ProtectedRoute from "../../../routes/ProtectedRoute";

const PaymentPage = () => {
  let cartData = JSON.parse(localStorage.getItem("checkoutData"))
    ? JSON.parse(localStorage.getItem("checkoutData"))
    : [];
  let shippingMethod = JSON.parse(localStorage.getItem("shippingMethod"))
    ? JSON.parse(localStorage.getItem("shippingMethod"))
    : [];

  const createOrder = () => {
    localStorage.removeItem("currentCart");
    localStorage.removeItem("shippingMethod");
  };

  const hasCart = () => {
    return !!localStorage.getItem("currentCart");
  };

  useEffect(() => {
    hasCart();
  });

  return (
    <>
      {hasCart() && (
        <section className="checkout-container">
          <section className="shipping-information">
            <CheckoutSummary
              cartData={cartData}
              shippingMethod={shippingMethod}
            />
          </section>
          <section className="inputs-container">
            <SubTitle text="Betaalmethode: " />
            <CheckoutInputItem
              imageSource={Klarna}
              inputText="Klarna"
              payment="payment"
              inputName="payment"
            />
            <CheckoutInputItem
              imageSource={ApplePay}
              inputText="ApplePay"
              payment="payment"
              inputName="payment"
            />
            <CheckoutInputItem
              imageSource={GooglePay}
              inputText="GooglePay"
              payment="payment"
              inputName="payment"
            />
            <CheckoutInputItem
              imageSource={MasterCard}
              inputText="MasterCard"
              payment="payment"
              inputName="payment"
            />
            <CheckoutInputItem
              imageSource={Paypal}
              inputText="PayPal"
              payment="payment"
              inputName="payment"
            />
            <section className="confirm-checkout-container">
              <Link exact="true" to="/checkout/success">
                <SecondaryButton
                  callToAction="Afrekenen"
                  primary
                  clickHandler={createOrder}
                />
              </Link>
            </section>
          </section>
        </section>
      )}
      {!hasCart() && <ProtectedRoute />}
    </>
  );
};

export default PaymentPage;
