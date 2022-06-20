import React from "react";
import "./MyAccountPage.scss";
import AccountMenu from "../../components/common/MyAccount/AccountMenu";
import { accordionData } from "./AccordionData";
import Accordion from "../../components/common/MyAccount/Accordion/Accordion";

function MyAccountPage() {
  return (
    <section className="my-account-container">
      <AccountMenu />
      <section className="accordion-container">
        <section className="my-account-accordion">
          {accordionData.map(({ title, content }) => (
            <Accordion key={title} title={title} content={content} />
          ))}
        </section>
      </section>
    </section>
  );
}

export default MyAccountPage;
