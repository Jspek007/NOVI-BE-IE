import React, { useEffect, useState } from "react";
import axios from "axios";
import { CategoryInformation, CatalogFilter } from "../../../components/common";
import ProductCardSkeleton from "../../../components/common/SkeletonLoader/ProductCardSkeleton";
import MobileCardSkeleton from "../../../components/common/SkeletonLoader/MobileCardSkeleton";

function WomensCollection() {
  const womensCollectionApi =
    "https://fakestoreapi.com/products/category/women's%20clothing";
  const [loading, setLoading] = useState(false);
  const [productData, setProductData] = useState([]);

  useEffect(() => {
    const fetchWomensProductData = async () => {
      setLoading(true);
      try {
        const result = await axios.get(womensCollectionApi);
        setProductData(result.data);
      } catch (error) {
        console.log(error);
      }
    };
    fetchWomensProductData().then(() => {
      setLoading(false);
    });
  }, []);

  return (
    <>
      <CategoryInformation index={1} />
      <CatalogFilter productData={productData} />
      {loading && (
        <>
          <ProductCardSkeleton />
          <MobileCardSkeleton />
        </>
      )}
    </>
  );
}

export default WomensCollection;
