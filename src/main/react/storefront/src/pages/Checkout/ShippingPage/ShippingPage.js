import React, { useEffect } from "react";
import "../CheckoutPages.scss";
import CheckoutForm from "../../../components/form/Forms/CheckoutForm";
import ProtectedRoute from "../../../routes/ProtectedRoute";

const ShippingPage = () => {
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
          <CheckoutForm />
        </section>
      )}
      {!hasCart() && <ProtectedRoute />}
    </>
  );
};

export default ShippingPage;
