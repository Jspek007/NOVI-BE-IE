import React, {useEffect, useState} from 'react';
import styles from './Header.module.css';
import jwt from 'jwt-decode';
import AuthService from "../../../services/auth.service";


const Header = () => {
    useEffect(() => {
        getSignedInUser();
    }, []);

    const [user, setUser] = useState();

    const getSignedInUser = () => {
        if (!localStorage.getItem('userInformation')) {
            return null;
        } else {
            setUser(jwt(localStorage.getItem('userInformation')).sub);
        }
    }

    const handleLogout = () => {
        AuthService.logout();
        window.location.reload();
    }


    return (
        <div className={styles.container}>
            <div className={styles.header_left}>
                <section>Welcome back, {user}</section>
            </div>
            <div className={styles.header_right}>
                <section className={styles.logout_button} onClick={handleLogout}>Logout</section>
            </div>
        </div>

    );
};

export default Header;