import React from 'react';
import "./Modal.scss";

const Modal = (props) => {
    return (
        <section className="modal-background">
            <section className="modal-container">
                <section className="modal-body">
                    {props.modalBody}
                </section>
                <section className="modal-footer">
                    {props.children}
                </section>
            </section>
        </section>
    );
};

export default Modal;