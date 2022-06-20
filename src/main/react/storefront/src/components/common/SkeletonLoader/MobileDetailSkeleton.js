import React from "react";
import Skeleton from "react-loading-skeleton";
import "react-loading-skeleton/dist/skeleton.css";

const MobileDetailSkeleton = () => {
  return (
    <section className="mobile-detail-skeleton">
      <section className="product-detail-title">
        <Skeleton width={350} height={20} />
      </section>
      <section className="product-detail-container">
        <Skeleton width={350} height={300} />
        <section className="product-details">
          <Skeleton width={350} height={20} />
          <Skeleton width={350} height={20} />
          <Skeleton width={350} height={20} />
          <Skeleton width={350} height={20} />
        </section>
        <section className="add-to-cart-container">
          <Skeleton width={220} height={50} />
        </section>
      </section>
    </section>
  );
};

export default MobileDetailSkeleton;
