import React, {Fragment, useState} from 'react';
import Header from "../../components/common/Header";
import Sidebar from "../../components/common/Sidebar";
import {useParams} from "react-router";
import styles from './AdminPage.module.css';
import OrderData from "../../components/common/AdminData/OrderData/OrderData";
import CustomerData from "../../components/common/AdminData/CustomerData/CustomerData";
import AdminDataHeader from "../../components/common/AdminData/AdminDataHeader/AdminDataHeader";
import CreationForm from "../../components/common/AdminData/CreationForm/CreationForm";

const AdminPage = () => {

    let {domain} = useParams();
    const [loading, isLoading] = useState(false);

    const checkUrlForCreation = () => {
        const url = window.location.href;

        if (url.includes("/new")) {
            return true;
        }
    }

    const renderContent = () => {
        if (checkUrlForCreation()) {
            return (
                <CreationForm />
            )
        } else if (domain === "sales_orders" && !checkUrlForCreation()) {
            return <OrderData />
        } else if (domain === "customers" && !checkUrlForCreation()) {
            return <CustomerData />
        }
    }

    return (
        <Fragment>
            <Header/>
            <AdminDataHeader />
            <section className={styles.page_container}>
                <section className={styles.sidebar_container}>
                    <Sidebar/>
                </section>
                <section className={styles.table_container}>
                    {renderContent()}
                </section>
            </section>
        </Fragment>
    );
};

export default AdminPage;