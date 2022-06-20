import React from 'react';
import "./LoginProviderButton.scss";

const LoginProviderButton = (props) => {
    return (
        <button className={`login-button ${props.provider}`}
            onClick={props.clickHandler}
        >
            {props.children}
        </button>
    );
};

export default LoginProviderButton;