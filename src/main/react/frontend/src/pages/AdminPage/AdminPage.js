import React, {Fragment, useState, useEffect, useCallback} from 'react';
import Header from "../../components/common/Header";
import Sidebar from "../../components/common/Sidebar";
import {useParams} from "react-router";
import styles from './AdminPage.module.css';
import OrderData from "../../components/common/AdminData/OrderData/OrderData";
import CustomerData from "../../components/common/AdminData/CustomerData/CustomerData";
import AdminDataHeader from "../../components/common/AdminData/AdminDataHeader/AdminDataHeader";
import CreationForm from "../../components/common/AdminData/CreationForm/CreationForm";
import AdminDataService from "../../services/adminData.service";
import usePrevious from "../../hooks/usePrevious";

const AdminPage = () => {

    let {domain} = useParams();

    const click = () => {
        console.log(previousState);
        console.log(adminData);
    }


    const [adminData, setAdminData] = useState([]);
    const previousState = usePrevious(adminData);
    const [loading, isLoading] = useState(true);

    const loadAdminData = () => {
        AdminDataService.fetchData(domain)
            .then((response) => {
                setAdminData(response.data);
            })
            .finally(() => isLoading(false))
            .catch((error) => console.log(error));
    };

    useEffect(() => {
        loadAdminData();
    }, []);

    const checkUrlForCreation = () => {
        const url = window.location.href;

        if (url.includes("/new")) {
            return true;
        }
    }

    const renderContent = () => {
        if (checkUrlForCreation()) {
            return true;
        } else if (domain === "sales_orders" && !checkUrlForCreation()) {
            return <OrderData/>
        } else if (domain === "customers" && !checkUrlForCreation()) {
            return <CustomerData/>
        }
    };

    {
        if (checkUrlForCreation()) return (
            <Fragment>
                <Header/>
                <Sidebar/>
                <CreationForm/>
            </Fragment>
        )
    }
    {
        if (!checkUrlForCreation()) return (
            <Fragment>
                <Header/>
                <AdminDataHeader/>
                <section className={styles.page_container}>
                    <section className={styles.sidebar_container}>
                        <Sidebar/>
                    </section>
                    <section className={styles.table_container} onClick={click}>
                        {renderContent()}
                    </section>
                </section>
            </Fragment>
        )
    }
};

export default AdminPage;