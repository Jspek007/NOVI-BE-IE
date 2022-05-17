import React from "react";
import styles from './Inputfield.module.css';

const InputField = ({
                        error,
                        inputType,
                        idValue,
                        placeholder,
                        eventHandler,
                        value,
                    }) => {
    return (
        <fieldset>
            <input
                className={styles.input_field}
                type={inputType}
                id={idValue}
                placeholder={placeholder}
                onChange={eventHandler}
                value={value}
            />
            {error && <span className="error-message">{error}</span>}
        </fieldset>
    );
};

export default InputField;