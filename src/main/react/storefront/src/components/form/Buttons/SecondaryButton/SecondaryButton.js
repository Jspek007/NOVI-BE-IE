import React from "react";
import "./SecondaryButton.scss";

function SecondaryButton({ primary, callToAction, clickHandler }) {
  const renderContent = () => {
    if (!primary) {
      return (
        <button
          type="button"
          className="secondary-button"
          onClick={clickHandler}
        >
          {callToAction}
        </button>
      );
    } else {
      return (
        <button
          type="button"
          className="secondary-button-flipped"
          onClick={clickHandler}
        >
          {callToAction}
        </button>
      );
    }
  };

  return <span>{renderContent()}</span>;
}

export default SecondaryButton;
