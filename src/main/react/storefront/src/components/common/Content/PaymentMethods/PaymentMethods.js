import React from "react";
import "./PaymentMethods.scss";
import { ReactComponent as Klarna } from "../../../../assets/images/paymentMethods/Klarna.svg";
import { ReactComponent as ApplePay } from "../../../../assets/images/paymentMethods/ApplePay.svg";
import { ReactComponent as GooglePay } from "../../../../assets/images/paymentMethods/Google_pay.svg";
import { ReactComponent as Mastercard } from "../../../../assets/images/paymentMethods/Mastercard.svg";
import { ReactComponent as Paypal } from "../../../../assets/images/paymentMethods/Paypal.svg";

function PaymentMethods() {
  return (
    <section className="payment-methods-container">
      <Klarna className="container-item" />
      <ApplePay className="container-item" />
      <GooglePay className="container-item" />
      <Mastercard className="container-item" />
      <Paypal className="container-item" />
    </section>
  );
}

export default PaymentMethods;
