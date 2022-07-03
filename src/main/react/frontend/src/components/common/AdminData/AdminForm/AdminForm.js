import React from 'react';
import styles from './AdminForm.module.css';
import PrimaryButton from "../../../form/Button/PrimaryButtons";
import {useNavigate} from "react-router";

function AdminForm(props) {
    const navigate = useNavigate();


    return (
        <div className={styles.form_container}>
                <header className={styles.form_header}>
                    <h1>hello</h1>
                    <PrimaryButton
                        children={"Close"}
                        clickHandler={() => navigate(-1)}
                    />
                </header>
        </div>
    );
}

export default AdminForm;