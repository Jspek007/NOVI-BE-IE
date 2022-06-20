import React, { useEffect, useState } from "react";
import axios from "axios";
import { useParams } from "react-router";
import "./ProductDetail.scss";
import ToggleWishlistIcon from "../ToggleWishlistIcon";
import ProductDetailSkeleton from "../../SkeletonLoader/ProductDetailSkeleton";
import CartModal from "../../Modal/CartModal";
import { SubTitle } from "../../Content/TextComponents";
import { FaShoppingCart } from "react-icons/fa";
import { SecondaryButton } from "../../../form/Buttons";
import { Link } from "react-router-dom";
import MobileDetailSkeleton from "../../SkeletonLoader/MobileDetailSkeleton";
import { useCart } from "../../../../contexts/CartContext";
import {PrimaryButton} from "../../../form/Buttons";

function ProductDetail() {
  const [loading, setLoading] = useState(false);
  const [productData, setProductData] = useState([]);
  const { productId } = useParams();
  const getSingleProductApi = `https://fakestoreapi.com/products/${productId}`;
  const [modal, setModal] = useState(false);
  const { cart } = useCart();

  useEffect(() => {
    const fetchSpecificProduct = async () => {
      setLoading(true);
      try {
        const result = await axios.get(getSingleProductApi);
        setProductData(result.data);
      } catch (error) {
        console.log(error);
      }
    };
    fetchSpecificProduct().then(() => {
      setLoading(false);
    });
  }, [getSingleProductApi]);

  const addProductToCart = () => {
    let objectIndex = cart.findIndex((obj) => obj.id === productData.id);
    if (objectIndex === -1) {
      let newCartProduct = {
        title: productData.title,
        price: productData.price,
        image: productData.image,
        id: productData.id,
        qty: 1,
      };
      cart.push(newCartProduct);
      localStorage.setItem("currentCart", JSON.stringify(cart));
    } else {
      cart[objectIndex].qty++;
      localStorage.setItem("currentCart", JSON.stringify(cart));
    }
    setModal(true);
  };

  return (
    <>
      {loading && (
        <>
          <MobileDetailSkeleton />
          <ProductDetailSkeleton />
        </>
      )}
      {!loading && (
        <>
          <section className="product-detail-title">
            {productData.title}
          </section>
          <section className="product-detail-container">
            <section className="product-image-container">
              <ToggleWishlistIcon productData={productData} />
              <img
                src={productData.image}
                className="product-image"
                alt={productData.title}
              />
            </section>
            <section className="product-details">
              <section className="product-detail-description">
                {productData.description}
              </section>
              <CartModal show={modal} handleModal={setModal}>
                <section className="modal-content">
                  <FaShoppingCart className="modal-icon" />
                  <SubTitle text="Product is toegevoegd aan uw winkelwagen." />
                  <Link exact="true" to="/winkelwagen">
                    <SecondaryButton callToAction="Bekijken" />
                  </Link>
                </section>
              </CartModal>
            </section>
          </section>
          <section className="add-to-cart-container">
            <PrimaryButton
              clickHandler={addProductToCart}
              buttonType={"add_to_cart"}
            >
                <span className="product-price">€{productData.price}</span>
                <span className="add-to-cart-message">In winkelwagen</span>
            </PrimaryButton>
          </section>
        </>
      )}
    </>
  );
}

export default ProductDetail;
