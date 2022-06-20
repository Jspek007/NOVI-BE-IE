import React, { useRef, useState, useEffect, useCallback } from "react";
import "./Searchbar.scss";
import { GoSearch } from "react-icons/go";
import { useDetectOutsideClick } from "../../../../../helpers/useDetectOutsideClick/useDetectOutsideClick";
import axios from "axios";
import { Link } from "react-router-dom";
import { FaSpinner } from "react-icons/fa";

const Searchbar = () => {
  const searchbarRef = useRef(null);
  const [searchbar, setSearchBar] = useDetectOutsideClick(searchbarRef);
  const showSearchBar = useCallback(() => {
    setSearchValue("");
    setSearchBar(!searchbar);
  }, [searchbar, setSearchBar]);
  const fullCatalogApi = "https://fakestoreapi.com/products";
  const [catalog, setCatalog] = useState([]);
  const [searchValue, setSearchValue] = useState("");
  const [loading, isLoading] = useState(false);

  useEffect(() => {
    isLoading(true);
    axios.get(fullCatalogApi).then((res) => {
      setCatalog(res.data);
      isLoading(false);
    });
  }, []);

  const handleSearchRedirect = useCallback(() => {
    showSearchBar();
    setSearchValue("");
  }, [showSearchBar]);

  const handleSearchValue = (event) => {
    let userInput = event.target.value;
    setSearchValue(userInput.toLowerCase());
  };

  return (
    <>
      <section className="menu-container-search" onClick={showSearchBar}>
        <section className="menu-trigger">
          <span>
            <GoSearch className="search-icon" />
          </span>
        </section>
      </section>

      {searchbar && (
        <section className="searchbar-container" ref={searchbarRef}>
          <section className="input_container">
            <input
              type="text"
              className={searchbar ? "searchbar active" : "searchbar"}
              placeholder="Zoek naar producten..."
              onChange={handleSearchValue}
              autoFocus
            />
          </section>
          <section className="searchbar-item-container">
            {searchValue !== "" &&
              catalog
                .filter((item) => {
                  if (
                    (item.title &&
                      item.title.toLowerCase().includes(searchValue)) ||
                    (item.description &&
                      item.description.toLowerCase().includes(searchValue))
                  ) {
                    return true;
                  }
                  return false;
                })
                .map((item) => (
                  <>
                    {loading && <FaSpinner className="loading-spinner" />}
                    <Link
                      exact="true"
                      to={`/collectie/${item.category}/${item.id}`}
                      onClick={handleSearchRedirect}
                      key={item}
                    >
                      <section className="search-item">
                        <section className="search-image-container">
                          <img
                            src={item.image}
                            alt={item.title}
                            className="search-image"
                          />
                        </section>
                        <span className="search-item-name">{item.title}</span>
                      </section>
                    </Link>
                  </>
                ))}
          </section>
        </section>
      )}
    </>
  );
};

export default Searchbar;
