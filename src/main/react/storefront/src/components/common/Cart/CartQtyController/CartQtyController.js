import React from "react";
import "./CartQtyController.scss";
import { AiOutlinePlus, AiOutlineMinus } from "react-icons/ai";

const CartQtyController = ({
  increase,
  cartData,
  updateCart,
  specificProduct,
}) => {
  const cartIndex = cartData
    .map(function (e) {
      return e.title;
    })
    .indexOf(specificProduct.title);

  const decreaseCartQty = () => {
    if (cartData[cartIndex].qty === 1) {
      cartData.splice(cartIndex, 1);
      localStorage.setItem("currentCart", JSON.stringify(cartData));
      let newCart = JSON.parse(localStorage.getItem("currentCart"));
      updateCart(newCart);
    } else {
      cartData[cartIndex].qty--;
      localStorage.setItem("currentCart", JSON.stringify(cartData));
      let newCart = JSON.parse(localStorage.getItem("currentCart"));
      updateCart(newCart);
    }
  };

  const increaseCartQty = () => {
    cartData[cartIndex].qty++;
    localStorage.setItem("currentCart", JSON.stringify(cartData));
    let newCart = JSON.parse(localStorage.getItem("currentCart"));
    updateCart(newCart);
  };

  if (!increase) {
    return <AiOutlineMinus className="cart-button" onClick={decreaseCartQty} />;
  } else {
    return <AiOutlinePlus className="cart-button" onClick={increaseCartQty} />;
  }
};

export default CartQtyController;
