import React from 'react';
import styles from './ErrorContainer.module.css';

const ErrorContainer = (props) => {
    return (
        <div className={styles.error_container}>
            <span className={styles.error_message}>{props.children}</span>
        </div>
    );
};

export default ErrorContainer;