export const handleRedirectToOrBack = ({ history, location }) => {
  history.replace(location.state?.from ?? "/");
};
