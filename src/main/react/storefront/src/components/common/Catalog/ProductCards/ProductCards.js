import React from "react";
import "./ProductCards.scss";
import { Link } from "react-router-dom";

function ProductCards({ data }) {
  return (
    <section className="product-container">
      {data.map((item) => {
        return (
          <Link
            key={item.id}
            className="product-link"
            exact="true"
            to={`/collectie/${item.category}/${item.id}`}
          >
            <section className="product-item-container">
              <section className="image-container">
                <img
                  src={item.image}
                  className="product-item-image"
                  alt={`$item.title`}
                />
              </section>
              <section className="product-info">
                <section className="product-item-price">
                  <p>€{item.price}</p>
                </section>
                <section className="product-item-title">
                  <h5 className="item-title">{item.title}</h5>
                </section>
              </section>
            </section>
          </Link>
        );
      })}
    </section>
  );
}

export default ProductCards;
