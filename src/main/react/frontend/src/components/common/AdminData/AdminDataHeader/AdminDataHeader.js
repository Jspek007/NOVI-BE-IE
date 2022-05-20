import React, {useEffect, useState} from 'react';
import styles from './AdminDataHeader.module.css';
import PrimaryButton from "../../../form/Button/PrimaryButtons";
import {useNavigate, useParams} from "react-router";

const AdminDataHeader = () => {
    const prepareDomainName = () => {
        if (domain.includes("_")) {
            return domain.charAt(0).toUpperCase() + domain.slice(1) + domain.replace("_", " ")
        } else {
            return domain.charAt(0).toUpperCase() + domain.slice(1);
        }
    };

    const {domain} = useParams();
    const domainName = prepareDomainName();
    const navigate = useNavigate();

    const buttonLink = () => {
        navigate(`${"new"}`);
    }


    return (
        <section className={styles.admin_header_container}>
            <h1>{domainName}</h1>
            <PrimaryButton
                type="submit"
                variant="primary"
                clickHandler={() => buttonLink()}
            >
                Create {domain}
            </PrimaryButton>
        </section>
    );
};

export default AdminDataHeader;