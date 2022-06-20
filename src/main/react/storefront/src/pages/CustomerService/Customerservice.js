import React from "react";
import "./Customerservice.scss";
import { Title } from "../../components/common/Content/TextComponents";
import CustomerServiceBlock from "../../components/common/CustomerServiceBlock";

function Customerservice() {
  return (
    <section className="page-wrapper">
      <section className="main-content">
        <section className="page-title">
          <Title text="Klantenservice" />
        </section>
        <section className="page-content">
          <CustomerServiceBlock
            sectionTitle="Meest gestelde vragen"
            questionOne="Ik mis een artikel uit mijn bestelling? Wat is een deellevering?"
            questionTwo="Mijn ontvangen Track en Trace link werkt niet?"
            questionThree="Waarom kan ik niet met Klarna betalen?"
            questionFour="Wat is de levertijd van mijn bestelling?"
          />
          <CustomerServiceBlock
            sectionTitle="Contact"
            questionOne="Hoe zijn jullie te bereiken?"
            questionTwo="Hebben jullie ook fisieke winkels?"
            questionThree="Waar zit jullie centraal kantoor?"
            questionFour="Waar kan ik een klacht indienen?"
          />
          <CustomerServiceBlock
            sectionTitle="Bestellen"
            questionOne="Bestellen"
            questionTwo="Een bestelling plaatsen"
            questionThree="Prijzen"
            questionFour="FashionConnect"
          />
          <CustomerServiceBlock
            sectionTitle="Betalen"
            questionOne="Veilig betalen bij FashionPoint"
            questionTwo="Onze betaalmethoden"
            questionThree="Betaalvoorwaarden"
            questionFour="Cadeaukaarten"
          />
          <CustomerServiceBlock
            sectionTitle="Garantie"
            questionOne="Heb ik ook garantie?"
            questionTwo="Mijn artikel is beschadigd. Wat kan ik doen?"
            questionThree="Mijn artikel is verkeerd ontvangen. Wat kan ik doen?"
            questionFour="Hoe weet ik of jullie artikelen echt zijn?"
          />
          <CustomerServiceBlock
            sectionTitle="Privacy"
            questionOne="Privacy policy"
            questionTwo="Gegevens"
            questionThree="Cookies"
            questionFour="Verkopen jullie mijn gegevens?"
          />
        </section>
      </section>
    </section>
  );
}

export default Customerservice;
