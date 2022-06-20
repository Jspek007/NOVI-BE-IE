import React from "react";
import "./CategoryBanner.scss";
import { Link } from "react-router-dom";
import CategoryActionButton from "../../../form/Buttons/CategoryActionButton";

function CategoryBanner({ leftCategoryText, rightCategoryText }) {
  return (
    <section className="category-container">
      <section className="category-items-side-by-side">
        <Link to="/collectie/heren" className="category-link">
          <section className="category-item category-item-left">
            <h2 className="category-text category-text-left" draggable="false">
              {leftCategoryText}
              <CategoryActionButton />
            </h2>
          </section>
        </Link>

        <Link to="/collectie/dames" className="category-link">
          <section className="category-item category-item-right">
            <h2 className="category-text category-text-right" draggable="false">
              {rightCategoryText}
              <CategoryActionButton />
            </h2>
          </section>
        </Link>
      </section>
    </section>
  );
}

export default CategoryBanner;
