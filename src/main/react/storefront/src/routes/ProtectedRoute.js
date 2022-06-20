import React from "react";
import { Redirect, Route } from "react-router";

const ProtectedRoute = ({ component: Component, routes, ...rest }) => {
  const hasCart = () => {
    return !!localStorage.getItem("currentCart");
  };

  return (
    <Route
      {...rest}
      render={(props) =>
        hasCart() ? <Component {...props} /> : <Redirect to={"/winkelwagen"} />
      }
    />
  );
};

export default ProtectedRoute;
