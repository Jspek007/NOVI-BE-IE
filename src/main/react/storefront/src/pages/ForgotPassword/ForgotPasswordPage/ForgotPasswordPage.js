import React, { useEffect, useState } from "react";
import "./ForgotPasswordPage.scss";
import {PrimaryButton} from "../../../components/form/Buttons";
import { useAuth } from "../../../contexts/AuthContext";
import { useHistory } from "react-router";
import { FaSpinner } from "react-icons/fa";
import { firebaseErrors } from "../../../utils/firebaseErrors";
import InputField from "../../../components/form/Forms/FormComponents/InputField";
import {
  SubTitle,
  Title,
} from "../../../components/common/Content/TextComponents";
import Modal from "../../../components/common/Modal/Modal/Modal";
import SecondaryButton from "../../../components/form/Buttons/SecondaryButton/SecondaryButton";

function ForgotPasswordPage() {
  const history = useHistory();
  const { forgotPassword } = useAuth();
  const [email, setEmail] = useState("");
  const [error, setError] = useState("");
  const [loading, isLoading] = useState(false);
  const [disabled, isDisabled] = useState(true);
  const [modalOpen, setModalOpen] = useState(false);

  const validateInputField = () => {
    if (email !== "") {
      isDisabled(false);
    } else {
      isDisabled(true);
    }
  };

  useEffect(() => {
    validateInputField();
  });

  const submitPasswordResetMail = (event) => {
    event.preventDefault();
    isLoading(true);
    forgotPassword(email)
      .then(() => {
        setModalOpen(true);
        isLoading(false);
      })
      .catch((error) => {
        setError(firebaseErrors[error.code]);
        isLoading(false);
      });
  };

  const goToLogin = () => {
    history.push("/login");
  };

  return (
    <section className="forgotten-password-container">
      <Title text="Wachtwoord vergeten?" />
      <SubTitle text={`Geef ons je email, wij geven een mailtje terug.`} />
      <section className="forgotten-password-input-container">
        <form>
          <InputField
            labelName="email"
            inputType="text"
            idValue="email-adress"
            placeholder="Email-adress"
            value={email}
            eventHandler={(event) => setEmail(event.target.value)}
          />
          <section className="error-container">
            <span className="error-message">{error}</span>
          </section>
          <section className="button-container">
            <PrimaryButton
              clickHandler={submitPasswordResetMail}
              disabled={disabled}
            >
              {loading && <FaSpinner className="loading-spinner" />}
              {loading && <span>Verwerken...</span>}
              {!loading && <span>Resetten</span>}
            </PrimaryButton>
          </section>
        </form>
        {modalOpen && (
          <Modal
            modalBody={`We hebben u een wachtwoord reset email gestuurd.\n Check ook even uw spambox.`}
          >
            <SecondaryButton
              clickHandler={() => goToLogin()}
              callToAction="Inloggen"
            />
            <SecondaryButton
              clickHandler={() => setModalOpen(false)}
              callToAction="Sluiten"
            />
          </Modal>
        )}
      </section>
    </section>
  );
}

export default ForgotPasswordPage;
