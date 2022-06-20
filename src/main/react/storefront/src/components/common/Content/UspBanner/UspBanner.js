import React from "react";
import "./UspBanner.scss";
import { useInView } from "react-intersection-observer";

const UspBanner = ({ imageSrc, textTitle, seoText, flipped }) => {
  const { ref, inView } = useInView({
    threshold: 0.1,
    triggerOnce: true,
  });

  const renderContent = () => {
    if (!flipped) {
      return (
        <section className="usp-container">
          <section className="usp__content-container">
            <section className="usp__image-container">
              <img className="usp__image" src={imageSrc} alt="fashion-outlet" />
            </section>
            <section className="usp__text">
              <h2>{textTitle}</h2>
              <p>{seoText}</p>
            </section>
          </section>
        </section>
      );
    } else {
      return (
        <section className="usp-container usp-container-flipped">
          <section className="usp__content-container">
            <section className="usp__text">
              <h2>{textTitle}</h2>
              <p>{seoText}</p>
            </section>
            <section className="usp__image-container">
              <img className="usp__image" src={imageSrc} alt="fashion-outlet" />
            </section>
          </section>
        </section>
      );
    }
  };

  return (
    <section
      className={
        inView ? "usp-container usp__container--zoom" : "usp__container"
      }
      ref={ref}
    >
      {renderContent()}
    </section>
  );
};

export default UspBanner;
