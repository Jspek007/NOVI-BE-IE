import PrimaryButton from "../../Buttons/PrimaryButton/PrimaryButton";
import React, { useState } from "react";
import { useAuth } from "../../../../contexts/AuthContext";
import { useHistory, useLocation } from "react-router";
import { handleRedirectToOrBack } from "../../../../helpers/handleRedirectToOrBack/handleRedirectToOrBack";
import { firebaseErrors } from "../../../../utils/firebaseErrors";
import { FaSpinner } from "react-icons/fa";
import InputField from "../FormComponents/InputField";

function LoginForm() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const { login } = useAuth();
  const history = useHistory();
  const location = useLocation();
  const [loading, isLoading] = useState(false);
  const [error, setError] = useState("");

  const onSubmitLogin = async (event) => {
    event.preventDefault();
    isLoading(true);
    login(email, password)
      .then(() => {
        handleRedirectToOrBack({ history, location });
      })
      .catch((error) => {
        isLoading(false);
        setError(firebaseErrors[error.code]);
      });
  };

  return (
    <form>
      <InputField
        labelName="email"
        inputType="text"
        idValue="email-adress"
        placeholder="Email"
        value={email}
        eventHandler={(event) => setEmail(event.target.value)}
      />

      <InputField
        labelName="password"
        inputType="password"
        idValue="password"
        placeholder="Wachtwoord"
        value={password}
        eventHandler={(event) => setPassword(event.target.value)}
      />

      <section className="button-container">
        <PrimaryButton clickHandler={onSubmitLogin}>
          {loading && <FaSpinner className="loading-spinner" />}
          {loading && <span>U wordt ingelogd</span>}
          {!loading && <span>Inloggen</span>}
        </PrimaryButton>
      </section>
      <section className="error-container">
        <span className="error-message">{error}</span>
      </section>
    </form>
  );
}

export default LoginForm;
