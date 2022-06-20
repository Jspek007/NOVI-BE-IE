import React from "react";
import "./DeliveryInformation.scss";
import { GiCheckMark } from "react-icons/gi";

function DeliveryInformation() {
  return (
    <section id="usp-banner-footer" className="footer-usp">
      <span className="footer-usp">
        <GiCheckMark className="usp-icon-footer" /> Levering binnen 1-4 dagen
      </span>
      <span className="usp-item">
        <GiCheckMark className="usp-icon-footer" /> Gratis verzenden vanaf €75,-
      </span>
      <span className="usp-item">
        <GiCheckMark className="usp-icon-footer" /> Gratis retouren binnen 45
        dagen
      </span>
    </section>
  );
}

export default DeliveryInformation;
