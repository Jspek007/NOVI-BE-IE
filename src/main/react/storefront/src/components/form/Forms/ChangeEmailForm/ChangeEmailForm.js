import React, { useEffect, useState } from "react";
import { useAuth } from "../../../../contexts/AuthContext";
import { FaSpinner } from "react-icons/fa";
import PrimaryButton from "../../Buttons/PrimaryButton/PrimaryButton";
import { firebaseErrors } from "../../../../utils/firebaseErrors";
import InputField from "../FormComponents/InputField";
import MyAccountForm from "../FormComponents/MyAccountForm";
import FormButtonContainer from "../FormComponents/FormButtonContainer";
import FormErrorContainer from "../FormComponents/FormErrorContainer/FormErrorContainer";
import Modal from "../../../common/Modal/Modal";
import {
  reauthenticateWithCredential,
  EmailAuthProvider,
  updateEmail,
} from "firebase/auth";
import { SecondaryButton } from "../../Buttons";

function ChangeEmailForm() {
  const auth = useAuth();
  const user = auth.currentUser;
  const [newEmail, setNewEmail] = useState("");
  const [loading, isLoading] = useState(false);
  const [disabled, isDisabled] = useState(true);
  const [error, setError] = useState("");
  const [succesMessage, setSuccesMessage] = useState("");
  const [modalOpen, setModalOpen] = useState(false);
  const [password, setPassword] = useState("");

  const validateFormOnChange = () => {
    if (newEmail === "") {
      isDisabled(true);
    } else {
      isDisabled(false);
    }
  };

  useEffect(() => {
    validateFormOnChange();
  });

  const handleNewEmailChange = (event) => {
    setNewEmail(event.target.value);
    validateFormOnChange();
  };

  const openChangeEmailModal = (event) => {
    event.preventDefault();
    isLoading(true);
    setModalOpen(true);
  };

  const closeChangeEmailModal = (event) => {
    event.preventDefault();
    setModalOpen(false);
    isLoading(false);
  };

  const confirmReAuthentication = (event) => {
    event.preventDefault();
    // Get the current user credentials and the old password
    const credential = EmailAuthProvider.credential(user.email, password);

    // Give the user credentials with the reauthentication to confirm this
    reauthenticateWithCredential(auth.currentUser, credential)
      .then(() => {
        setModalOpen(false);
        changeEmail();
      })
      .catch((error) => {
        setError(firebaseErrors[error.code]);
      });
  };

  const changeEmail = () => {
    updateEmail(user, newEmail)
      .then(() => {
        isLoading(false);
        setSuccesMessage("Uw email is aangepast.");
      })
      .catch((error) => {
        setError(firebaseErrors[error.code]);
        isLoading(false);
        setModalOpen(false);
      });
  };

  return (
    <MyAccountForm>
      <InputField
        inputType="text"
        idValue="new-email"
        placeholder="Nieuw email-adress"
        eventHandler={handleNewEmailChange}
        formSection="my-account"
      />

      {succesMessage && <span className="succes-message">{succesMessage}</span>}
      <FormErrorContainer>{error}</FormErrorContainer>

      <FormButtonContainer>
        <PrimaryButton clickHandler={openChangeEmailModal} disabled={disabled}>
          {loading && <FaSpinner className="loading-spinner" />}
          {loading && <span>Verwerken...</span>}
          {!loading && <span>Email wijzigen</span>}
        </PrimaryButton>
      </FormButtonContainer>
      {modalOpen && (
        <Modal modalBody="U dient opnieuw in te loggen om uw gegevens te wijzigen">
          <section className="reauthentication-modal">
            <span>Geef uw wachtwoord op</span>
            <InputField
              inputType="password"
              placeholder="Uw wachtwoord"
              idValue="confirmPassword"
              eventHandler={(event) => setPassword(event.target.value)}
            />
            <FormErrorContainer>{error}</FormErrorContainer>
            <SecondaryButton
              clickHandler={confirmReAuthentication}
              callToAction="Bevestigen"
              primary
            />
            <SecondaryButton
              clickHandler={closeChangeEmailModal}
              callToAction="Annuleren"
            />
          </section>
        </Modal>
      )}
    </MyAccountForm>
  );
}

export default ChangeEmailForm;
