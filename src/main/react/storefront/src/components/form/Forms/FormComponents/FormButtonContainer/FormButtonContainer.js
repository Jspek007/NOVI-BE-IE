import React from "react";
import "./FormButtonContainer.scss";

const FormButtonContainer = (props) => {
  return <section className="button-container">{props.children}</section>;
};

export default FormButtonContainer;
