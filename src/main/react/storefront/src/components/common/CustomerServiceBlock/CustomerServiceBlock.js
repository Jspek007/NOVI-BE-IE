import React from "react";
import { SubTitle } from "../Content/TextComponents";
import "./CustomerServiceBlock.scss";

const CustomerServiceBlock = (props) => {
  return (
    <section className="customer-service-block">
      <section className="section-title">
        <SubTitle text={props.sectionTitle} />
      </section>
      <section className="question-container">
        <ul className="customer-questions">
          <li className="question-item">{props.questionOne}</li>
          <li className="question-item">{props.questionTwo}</li>
          <li className="question-item">{props.questionThree}</li>
          <li className="question-item">{props.questionFour}</li>
        </ul>
      </section>
    </section>
  );
};

export default CustomerServiceBlock;
