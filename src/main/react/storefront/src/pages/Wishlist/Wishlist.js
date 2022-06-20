import React from "react";
import { ProductCards } from "../../components/common";
import "./Wishlist.scss";
import {
  SubTitle,
  Title,
} from "../../components/common/Content/TextComponents";

function Wishlist() {
  let wishlistArray = localStorage.getItem("wishlist");
  let wishlistItems = JSON.parse(localStorage.getItem("wishlist"));

  if (wishlistArray === "[]" || !wishlistArray) {
    return (
      <>
        <section className="wishlist-title-container">
          <Title text="Mijn wishlist" />
        </section>
        <section className="wishlist-content">
          <SubTitle text="Geen producten gevonden in uw wishlist. Voeg items toe om ze hier te zien." />
        </section>
      </>
    );
  } else {
    return (
      <>
        <section className="wishlist-title-container">
          <Title text="Mijn wishlist" />
        </section>
        <section className="wishlist-product-container">
          {wishlistItems && <ProductCards data={wishlistItems} />}
        </section>
      </>
    );
  }
}

export default Wishlist;
