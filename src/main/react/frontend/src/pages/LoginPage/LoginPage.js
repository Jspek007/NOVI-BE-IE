import React from 'react';
import styles from './LoginPage.module.css';
import LoginForm from "../../components/form/Forms/Components/LoginForm";

const LoginPage = () => {
    return (
        <>
            <div className={styles.login_page}>
                <LoginForm/>
            </div>
        </>
    );
};

export default LoginPage;