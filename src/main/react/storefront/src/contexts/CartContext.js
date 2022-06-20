import { createContext, useContext, useEffect, useState } from "react";

const CartContext = createContext({
  cart: [],
});

export const useCart = () => useContext(CartContext);

export default function CartContextProvider({ children }) {
  const [cart, ] = useState(
    JSON.parse(localStorage.getItem("currentCart"))
      ? JSON.parse(localStorage.getItem("currentCart"))
      : []
  );

  useEffect(() => {
    return localStorage.getItem("currentCart")
      ? JSON.parse(localStorage.getItem("currentCart"))
      : [];
  }, []);

  const value = {
    cart,
  };

  return <CartContext.Provider value={value}>{children}</CartContext.Provider>;
}
