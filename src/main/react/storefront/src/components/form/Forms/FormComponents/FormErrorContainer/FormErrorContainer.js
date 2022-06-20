import React from "react";
import "./FormErrorContainer.scss";

const FormErrorContainer = (props) => {
  return (
    <section className="error-container">
      <span className="error-message">{props.children}</span>
    </section>
  );
};

export default FormErrorContainer;
