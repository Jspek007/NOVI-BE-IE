import React from "react";
import "./CategoryInformation.scss";
import { CategoryData } from "./CategoryData";
import { SubTitle, Title } from "../../Content/TextComponents";

function CategoryInformation({ index }) {
  return (
    <section className="category-banner-container">
      <Title text={CategoryData[index].seoTitle} />
      <SubTitle text={CategoryData[index].description} />
    </section>
  );
}

export default CategoryInformation;
