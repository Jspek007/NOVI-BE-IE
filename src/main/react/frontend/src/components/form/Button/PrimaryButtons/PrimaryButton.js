import React from 'react';
import styles from './PrimaryButton.module.css';

const PrimaryButton = (props) => {
    return (
        <button
            type={props.type}
            className={[styles.primary_button, styles[props.variant]].join(' ')}
            onClick={props.clickHandler}
            disabled={props.disabled || false}
            >
            {props.children}
        </button>
    );
};

export default PrimaryButton;