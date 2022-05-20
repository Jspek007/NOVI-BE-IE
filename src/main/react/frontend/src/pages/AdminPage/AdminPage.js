import React, {Fragment, useEffect, useState} from 'react';
import Header from "../../components/common/Header";
import Sidebar from "../../components/common/Sidebar";
import {useParams} from "react-router";
import styles from './AdminPage.module.css';
import OrderData from "../../components/common/AdminData/OrderData/OrderData";
import CustomerData from "../../components/common/AdminData/CustomerData/CustomerData";

const AdminPage = () => {

    let {domain} = useParams();
    const [loading, isLoading] = useState(false);
    const [adminData, setAdminData] = useState([]);

    return (
        <Fragment>
            <Header/>
            <section className={styles.page_container}>
                <section className={styles.sidebar_container}>
                    <Sidebar/>
                </section>
                <section className={styles.table_container}>
                    {domain === "sales_orders" && (
                        <OrderData/>
                    )}
                    {domain === "customers" && (
                        <CustomerData/>
                    )}
                </section>
            </section>

        </Fragment>
    );
};

export default AdminPage;