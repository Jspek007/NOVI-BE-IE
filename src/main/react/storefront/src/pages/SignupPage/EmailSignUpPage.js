import React from "react";
import { Fragment } from "react";
import "./EmailSignUpPage.scss";
import { Link } from "react-router-dom";
import Title from "../../components/common/Content/TextComponents/Title/Title";
import SubTitle from "../../components/common/Content/TextComponents/SubTitle/SubTitle";
import EmailRegisterForm from "../../components/form/Forms/EmailRegisterForm";

function EmailSignUpPage() {
  return (
    <Fragment>
      <section className="email-register-container">
        <section className="form-header">
          <Title text="Registreer via email" />
          <SubTitle text="Krijg toegang tot honderden designer outlets" />
        </section>

        <section className="email-register-method">
          <EmailRegisterForm />
        </section>
        <p className="terms-information-container">
          Door u aan te melden, gaat u akkoord met onze{" "}
          <Link to="/algemene-voorwaarden" className="link">
            algemene voorwaarden
          </Link>
        </p>

        <section className="already-customer-container">
          <section>
            <p>
              Al wel een account?{" "}
              <Link to="/login" className="link">
                Inloggen
              </Link>
            </p>
          </section>
        </section>
      </section>
    </Fragment>
  );
}

export default EmailSignUpPage;
