import UserInfoForm from "../../components/form/Forms/UserInfoForm/UserInfoForm";
import ChangeEmailForm from "../../components/form/Forms/ChangeEmailForm";
import ChangePasswordForm from "../../components/form/Forms/ChangePasswordForm";
import DeleteAccountForm from "../../components/form/Forms/DeleteAccountForm";

export const accordionData = [
  {
    id: 1,
    title: "Persoonlijke gegevens",
    content: <UserInfoForm />,
  },
  {
    id: 2,
    title: "Email address wijzigen",
    content: <ChangeEmailForm />,
  },
  {
    id: 3,
    title: "Wachtwoord wijzigen",
    content: <ChangePasswordForm />,
  },
  {
    id: 4,
    title: "Verwijder mijn account",
    content: <DeleteAccountForm />,
  },
];
