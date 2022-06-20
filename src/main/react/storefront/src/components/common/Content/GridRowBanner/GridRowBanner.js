import React from "react";
import "./GridRowBanner.scss";

const GridRowBanner = ({
  imageSrc,
  imgAlt,
  sectionTitle,
  textContent,
  flipped,
}) => {
  const renderGridBanner = () => {
    if (!flipped) {
      return (
        <section className="grid-row-content">
          <img
            draggable={false}
            className="grid-row-image"
            src={imageSrc}
            alt={imgAlt}
          />
          <section className="grid-row-text">
            <h5 className="section-title">{sectionTitle}</h5>
            {textContent}
          </section>
        </section>
      );
    } else {
      return (
        <section className="grid-row-content">
          <section className="grid-row-text">
            <h5 className="section-title">{sectionTitle}</h5>
            {textContent}
          </section>
          <img
            draggable={false}
            className="grid-row-image"
            src={imageSrc}
            alt={imgAlt}
          />
        </section>
      );
    }
  };

  return <section className="grid-row-banner">{renderGridBanner()}</section>;
};

export default GridRowBanner;
