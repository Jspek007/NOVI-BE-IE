import React from "react";
import "./AboutUs.scss";
import {
  Title,
  SubTitle,
} from "../../components/common/Content/TextComponents";
import GridRowBanner from "../../components/common/Content/GridRowBanner";
import FreeShipping from "../../assets/images/aboutUsIllustrations/free-shipping.svg";
import ReturnService from "../../assets/images/aboutUsIllustrations/return-service.svg";
import CustomerService from "../../assets/images/aboutUsIllustrations/customer-service.svg";
import StyleAdvice from "../../assets/images/aboutUsIllustrations/style-advice.svg";

function AboutUs() {
  return (
    <>
      <section className="about-us-container">
        <Title text="To the point!" />
        <SubTitle
          text="Wij nemen geen genoegen met minder. Onze filosofie is om altijd te verbeteren om het elke dag een beetje
            beter te doen, straight to the point. Maar hoe merk jij dit?"
        />
      </section>
      <GridRowBanner
        imageSrc={FreeShipping}
        imgAlt="Free shipping!"
        sectionTitle="Gratis verzending"
        textContent="Wij streven er voortdurend naar om jouw ervaring met ons zo soepel mogelijk
                                te laten verlopen. In gewone taal verwijst dat volledig naar onze gratis verzending
                                (Algemene voorwaarden zijn van toepassing)
                                Jouw favoriete Point… en ook die van ons."
      />
      <GridRowBanner
        imageSrc={ReturnService}
        imgAlt="Easy returns!"
        sectionTitle="Eenvoudig retourneren"
        textContent="Online shoppen en de juiste maat vinden kan lastig zijn. Daarom willen wij ook het
                           retourproces zo makkelijk mogelijk maken voor jullie. (Algemene voowaarden zijn van toepassing).
                           Nog een favoriet die toegevoegd kan worden aan de Points!"
        flipped
      />
      <GridRowBanner
        imageSrc={CustomerService}
        imgAlt="Here for you!"
        sectionTitle="24/7 Klantenservice"
        textContent="Wij zijn pas tevreden als jij dat ook bent.
                           Dit vertaalt zich in een klantenservice die niet alleen 24/7 voor je klaar staat op de manier die jij prettig vind,
                           maar dit ook nog eens met veel passie doet!"
      />
      <GridRowBanner
        imageSrc={StyleAdvice}
        imgAlt="Getting you dressed!"
        flipped
        sectionTitle="Jouw styleadvies"
        textContent="
            De oude style catalogus is nauwelijks te vergelijken met het geen je tegenwoordig in je hand hebt op je device.
            Dit maakt het voor ons nog leuker onze Pointers een persoonlijk style advies te geven. Geef jouw voorkeur op,
            of voeg items toe aan je wishlist, en wij houden je op de hoogte, indien dit mag van jou."
      />
    </>
  );
}

export default AboutUs;
