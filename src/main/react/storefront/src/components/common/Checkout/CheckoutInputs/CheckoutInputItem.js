import React from "react";
import "./CheckoutInputItem.scss";

const CheckoutInputItem = (props) => {
  return (
    <section className="input-item">
      <label>
        <input
          type="radio"
          name={props.inputName}
          onClick={props.eventHandler}
          value={props.value}
        />
        <img
          src={props.imageSource}
          className={`option-image ${props.payment}`}
          alt={props.inputName}
        />
        <span className="input-text">{props.inputText}</span>
      </label>
    </section>
  );
};

export default CheckoutInputItem;
