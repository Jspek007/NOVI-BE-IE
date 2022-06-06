import React, { useState } from "react";
import { useNavigate } from "react-router";
import styles from "./LoginForm.module.css";
import PrimaryButton from "../../../Button/PrimaryButtons";
import AuthService from "../../../../../services/auth.service";
import ErrorContainer from "../ErrorContainer";
import { CircularProgress, TextField } from "@mui/material";

const LoginForm = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [message, setMessage] = useState("");
  const [loading, isLoading] = useState(false);
  const navigate = useNavigate();

  const handleLogin = async (event) => {
    isLoading(true);
    event.preventDefault();
    AuthService.login(username, password)
      .then((response) => {
        localStorage.setItem("userInformation", response.data.access_token);
        localStorage.setItem("user", "true");
        isLoading(false);
        navigate("/admin");
      })
      .catch((error) => {
        setMessage(error.getMessage());
        console.warn(error);
        isLoading(false);
      });
  };

  const validateForm = () => {
    return username.length > 0 && password.length > 0;
  };

  return (
    <div className={styles.container}>
      <form className={styles.login_form}>
        <TextField
          label="username"
          required
          variant="filled"
          placeholder="Username"
          onChange={(event) => setUsername(event.target.value)}
        />
        <TextField
          label="password"
          required
          type="password"
          variant="filled"
          placeholder="Password"
          onChange={(event) => setPassword(event.target.value)}
        />
        <PrimaryButton
          type="submit"
          variant="primary"
          clickHandler={(event) => handleLogin(event)}
          disabled={!validateForm()}
        >
          {loading && (
            <>
              <CircularProgress />
              <span>Logging in...</span>
            </>
          )}
          {!loading && <span>Login</span>}
        </PrimaryButton>
        {message && <ErrorContainer>{message}</ErrorContainer>}
      </form>
    </div>
  );
};

export default LoginForm;
