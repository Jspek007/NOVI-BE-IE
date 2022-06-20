import React from "react";
import {Link} from "react-router-dom";
import ScrollToTop from "../../../helpers/ScrollToTop/ScrollToTop";
import "./LegalTermsBanner.scss";

function LegalTermsBanner() {
  return (
    <section className="legal-container">
      <section className="legal-container-item legal-item-left">
        <span>© 2021-2021 FashionPoint</span>
      </section>
      <section className="legal-container-item legal-item-right">
        <ScrollToTop />
        <Link to="/privacy" draggable="false" className="footer-link">
          <span className="legal-row-items" draggable="false">
            Privacy
          </span>
        </Link>

        <ScrollToTop />
        <Link
          to="/algemene-voorwaarden"
          draggable="false"
          className="footer-link"
        >
          <span className="legal-row-items">Algemene voorwaarden</span>
        </Link>
      </section>
    </section>
  );
}

export default LegalTermsBanner;
