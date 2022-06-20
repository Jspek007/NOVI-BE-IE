import React from "react";
import { Route, Redirect } from "react-router";
import { useAuth } from "../contexts/AuthContext";

const PrivateRoute = ({ component: Component, routes, ...rest }) => {
  const auth = useAuth();

  const isLoggedIn = () => {
    return !!auth.currentUser;
  };

  return (
    <Route
      {...rest}
      render={(props) =>
        isLoggedIn() ? <Component {...props} /> : <Redirect to={"/login"} />
      }
    />
  );
};

export default PrivateRoute;
