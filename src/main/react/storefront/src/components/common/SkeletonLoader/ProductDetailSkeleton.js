import React from "react";
import Skeleton from "react-loading-skeleton";
import "react-loading-skeleton/dist/skeleton.css";

const ProductDetailSkeleton = () => {
  return (
    <>
      <section className="desktop-detail-skeleton">
        <section className="product-detail-title">
          <Skeleton width={1000} height={35} />
        </section>
        <section className="product-detail-container">
          <section className="product-image-container">
            <Skeleton width={450} height={350} />
          </section>
          <section className="product-details">
            <section className="product-detail-title">
              <Skeleton width={450} height={25} />
              <Skeleton width={450} height={25} />
              <Skeleton width={450} height={25} />
              <Skeleton width={450} height={25} />
              <Skeleton width={450} height={25} />
              <Skeleton width={450} height={25} />
            </section>
          </section>
        </section>
        <section className="add-to-cart-container">
          <Skeleton width={1000} height={50} />
        </section>
      </section>
    </>
  );
};

export default ProductDetailSkeleton;
