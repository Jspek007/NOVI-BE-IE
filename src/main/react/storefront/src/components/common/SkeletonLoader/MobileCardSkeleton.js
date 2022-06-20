import React from 'react'
import Skeleton from 'react-loading-skeleton';
import "react-loading-skeleton/dist/skeleton.css";

const MobileCardSkeleton = () => {
    return (
        <section className="mobile-loading-cards">
            <section className="product-container">
                {Array(6)
                    .fill()
                    .map((item, index) => (
                        <section key={index} className="product-link">
                            <Skeleton width={150} height={165} />
                            <section className="product">
                                <section className="product-item-price">
                                    <Skeleton width={150} height={20} />
                                </section>
                                <section className="product-item-title">
                                    <Skeleton width={150} height={20} />
                                </section>
                            </section>
                        </section>
                    ))}
            </section>
        </section>
    )
}

export default MobileCardSkeleton
