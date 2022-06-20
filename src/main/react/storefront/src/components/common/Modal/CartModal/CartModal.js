import React from 'react';
import "./CartModal.scss";

const CartModal = (props) => {
    return (
        <section className={`cart-modal-container ${props.show ? 'cart-modal-container-shown' : `cart-modal-container-hidden`}`}
                 onTransitionEnd={() => props.handleModal(false)}
        >
            {props.children}
        </section>
    );
};

export default CartModal;