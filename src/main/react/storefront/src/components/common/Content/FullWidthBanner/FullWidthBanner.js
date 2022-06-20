import React from "react";
import "./FullWidthBanner.scss";
import { SubTitle, Title } from "../TextComponents";

const FullWidthBanner = (props) => {
  return (
    <section className="full-width-banner-content">
      <section className="full-width-left-container">
        <section className="call-to-action-container">
          <section className="text-container">
            <Title text={props.callToAction} />
            <SubTitle text={props.paragraph} />
          </section>
          <section className="buttons-container">{props.children}</section>
        </section>
      </section>
      <section className="full-width-banner-image">
        <img
          className="banner-image"
          src={props.imageSrc}
          alt="Your fashion outlet!"
        />
      </section>
    </section>
  );
};

export default FullWidthBanner;
