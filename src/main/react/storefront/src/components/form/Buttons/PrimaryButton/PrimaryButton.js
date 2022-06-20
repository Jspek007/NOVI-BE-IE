import React from "react";
import "../SecondaryButton/SecondaryButton.scss";
import "./PrimaryButton.scss";

function PrimaryButton(props) {
  return (
    <button
      type="submit"
      className={`primary-button ${props.buttonType || ""}`}
      onClick={props.clickHandler}
      disabled={props.disabled || false}
    >
      {props.children}
    </button>
  );
}

export default PrimaryButton;
