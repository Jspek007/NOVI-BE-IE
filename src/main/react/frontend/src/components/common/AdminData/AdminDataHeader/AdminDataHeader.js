import React, {useEffect} from 'react';
import styles from './AdminDataHeader.module.css';
import PrimaryButton from "../../../form/Button/PrimaryButtons";
import {useParams} from "react-router";

const AdminDataHeader = () => {
    const prepareDomainName = () => {
        if (domain.includes("_")) {
            return domain.charAt(0).toUpperCase() + domain.slice(1) + domain.replace("_", " ")
        } else {
            return domain.charAt(0).toUpperCase() + domain.slice(1);
        }
    }

    const {domain} = useParams();
    const domainName = prepareDomainName();

    return (
        <section className={styles.admin_header_container}>
            <h1>{domainName}</h1>
            <PrimaryButton
                type="submit"
                variant="primary"
                clickHandler={() => console.log("hello")}
            >
                Create customer
            </PrimaryButton>
        </section>
    );
};

export default AdminDataHeader;