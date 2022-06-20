import React from "react";
import "./MyAccountForm.scss";

const MyAccountForm = (props) => {
  return (
    <section className="form-container">
      <section className="change-credentials-form">
        <form className="my-account-form">{props.children}</form>
      </section>
    </section>
  );
};

export default MyAccountForm;
