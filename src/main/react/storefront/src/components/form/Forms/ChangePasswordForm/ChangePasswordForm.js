import React, { useEffect, useState } from "react";
import PrimaryButton from "../../Buttons/PrimaryButton/PrimaryButton";
import { useAuth } from "../../../../contexts/AuthContext";
import {
  EmailAuthProvider,
  reauthenticateWithCredential,
  updatePassword,
} from "firebase/auth";
import { FaSpinner } from "react-icons/fa";
import { firebaseErrors } from "../../../../utils/firebaseErrors";
import InputField from "../FormComponents/InputField";
import MyAccountForm from "../FormComponents/MyAccountForm";
import FormErrorContainer from "../FormComponents/FormErrorContainer/FormErrorContainer";
import FormButtonContainer from "../FormComponents/FormButtonContainer";
import Modal from "../../../common/Modal/Modal";
import SecondaryButton from "../../Buttons/SecondaryButton/SecondaryButton";

function ChangePasswordForm() {
  const [newPassword, setNewPassword] = useState("");
  const [loading, isLoading] = useState(false);
  const [succesMessage, setSuccesMessage] = useState("");
  const [error, setError] = useState("");
  const [disabled, isDisabled] = useState(true);
  const [modalOpen, setModalOpen] = useState(false);
  const [password, setPassword] = useState("");

  const auth = useAuth();
  const user = auth.currentUser;

  const formValidation = () => {
    if (!newPassword) {
      isDisabled(true);
    } else {
      isDisabled(false);
    }
  };

  useEffect(() => {
    formValidation();
  });

  const handleNewPasswordChange = (event) => {
    setNewPassword(event.target.value);
    formValidation();
  };

  const showAuthenticationModal = (event) => {
    event.preventDefault();
    isLoading(true);
    setModalOpen(true);
  };

  const closeAuthenticationModal = () => {
    setModalOpen(false);
    isLoading(false);
  };

  const confirmReAuthentication = (event) => {
    event.preventDefault();

    const credential = EmailAuthProvider.credential(user.email, password);

    reauthenticateWithCredential(user, credential)
      .then(() => {
        setModalOpen(false);
        changePassword();
      })
      .catch((error) => {
        setError(firebaseErrors[error.code]);
      });
  };

  const changePassword = async () => {
    updatePassword(auth.currentUser, newPassword)
      .then(() => {
        setSuccesMessage("Uw wachtwoord is aangepast!");
        isLoading(false);
      })
      .catch((error) => {
        setError(firebaseErrors[error.code]);
        isLoading(false);
      });
  };

  return (
    <MyAccountForm>
      <InputField
        inputType="password"
        idValue="new-password"
        placeholder="Nieuw wachtwoord"
        eventHandler={handleNewPasswordChange}
        formSection="my-account"
      />
      <span className="succes-message">{succesMessage}</span>

      {modalOpen && (
        <Modal modalBody="U dient opnieuw in te loggen om uw gegevens te wijzigen">
          <section className="reauthentication-modal">
            <InputField
              inputType="password"
              placeholder="Uw wachtwoord"
              idValue="confirmPassword"
              eventHandler={(event) => setPassword(event.target.value)}
            />
            <FormErrorContainer>{error}</FormErrorContainer>
            <section className="modal-button-container">
              <SecondaryButton
                clickHandler={confirmReAuthentication}
                primary
                callToAction="Bevestigen"
              />
              <SecondaryButton
                clickHandler={closeAuthenticationModal}
                callToAction="Annuleren"
              />
            </section>
          </section>
        </Modal>
      )}
      <FormButtonContainer>
        <PrimaryButton
          clickHandler={showAuthenticationModal}
          disabled={disabled}
        >
          {loading && <FaSpinner className="loading-spinner" />}
          {loading && <span>Verwerken...</span>}
          {!loading && <span>Wachtwoord wijzigen</span>}
        </PrimaryButton>
      </FormButtonContainer>
    </MyAccountForm>
  );
}

export default ChangePasswordForm;
