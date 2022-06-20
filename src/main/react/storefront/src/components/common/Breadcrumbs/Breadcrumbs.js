import React from "react";
import "./Breadcrumbs.scss";
import { Link } from "react-router-dom";

const Breadcrumbs = ({ crumbs }) => {
  if (crumbs.length <= 1) {
    return null;
  }

  return (
    <section className="breadcrumbs">
      {crumbs.map(({ name, path }, key) =>
        key + 1 === crumbs.length ? (
          <span className="breadcrumb__active" key={key}>
            {name}
          </span>
        ) : (
          <Link className="breadcrumb__item" key={key} to={path}>
            {name}
          </Link>
        )
      )}
    </section>
  );
};

export default Breadcrumbs;
