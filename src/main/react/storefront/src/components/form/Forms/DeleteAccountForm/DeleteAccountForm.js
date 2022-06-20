import React, { useState } from "react";
import PrimaryButton from "../../Buttons/PrimaryButton/PrimaryButton";
import { useAuth } from "../../../../contexts/AuthContext";
import { useHistory } from "react-router";
import { deleteUser } from "firebase/auth";
import { FaSpinner } from "react-icons/fa";
import MyAccountForm from "../FormComponents/MyAccountForm";
import FormButtonContainer from "../FormComponents/FormButtonContainer";
import Modal from "../../../common/Modal/Modal";
import { SecondaryButton } from "../../Buttons";

function DeleteAccountForm() {
  const [loading, isLoading] = useState(false);
  const auth = useAuth();
  const history = useHistory();
  const [modalOpen, setModalOpen] = useState(false);

  const confirmDeleteUser = (event) => {
    event.preventDefault();
    setModalOpen(false);
    isLoading(true);
    deleteUser(auth.currentUser).finally(history.push("/"));
    isLoading(false);
  };

  const promptUserBeforeDelete = (event) => {
    event.preventDefault();
    setModalOpen(true);
  };

  return (
    <MyAccountForm>
      <p className="my-account-item-title">
        Weet u zeker dat u uw account wilt verwijderen?
      </p>

      {modalOpen && (
        <Modal modalBody="Weet u zeker dat u uw account wilt verwijderen? Hiermee zullen al uw gegevens verloren gaan.">
          <SecondaryButton
            primary
            callToAction="Ja"
            clickHandler={confirmDeleteUser}
          />
          <SecondaryButton
            callToAction="Nee"
            clickHandler={() => setModalOpen(false)}
          />
        </Modal>
      )}

      <FormButtonContainer>
        <PrimaryButton clickHandler={promptUserBeforeDelete}>
          {loading && <FaSpinner className="loading-spinner" />}
          {loading && <span>Uw account wordt verwijderd</span>}
          {!loading && <span>Account verwijderen</span>}
        </PrimaryButton>
      </FormButtonContainer>
    </MyAccountForm>
  );
}

export default DeleteAccountForm;
