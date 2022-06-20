import React, { useEffect, useState } from "react";
import "./CatalogFilter.scss";
import { ProductCards } from "../../index";

function CatalogFilter({ productData }) {
  const [data, setData] = useState([]);
  const [sortType, setSortType] = useState("aanbevolen");

  useEffect(() => {
    const sortArray = (type) => {
      const types = {
        aanbevolen: "id",
        prijsAflopend: "price",
        prijsOplopend: "price",
      };
      const sortProperty = types[type];
      const sorted = [...productData].sort((a, b) => {
        if (type === "aanbevolen" || type === "prijsOplopend") {
          return a[sortProperty] - b[sortProperty];
        }
        return b[sortProperty] - a[sortProperty];
      });
      setData(sorted);
    };
    sortArray(sortType);
  }, [productData, sortType]);

  return (
    <>
      <section className="page-sorter">
        <select
          className="sorter-select"
          onChange={(event) => setSortType(event.target.value)}
        >
          <option value="aanbevolen" className="sorter-value">
            Aanbevolen
          </option>
          <option value="prijsAflopend" className="sorter-value">
            Prijs aflopend
          </option>
          <option value="prijsOplopend" className="sorter-value">
            Prijs oplopend
          </option>
        </select>
      </section>
      <ProductCards data={data} />
    </>
  );
}

export default CatalogFilter;
