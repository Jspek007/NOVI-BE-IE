import React, {Fragment, useState, useEffect, useCallback} from 'react';
import Header from "../../components/common/Header";
import Sidebar from "../../components/common/Sidebar";
import {useParams} from "react-router";
<<<<<<< HEAD
import styles from './AdminPage.module.css';
import OrderData from "../../components/common/AdminData/OrderData/OrderData";
import CustomerData from "../../components/common/AdminData/CustomerData/CustomerData";
import AdminDataHeader from "../../components/common/AdminData/AdminDataHeader/AdminDataHeader";
import CreationForm from "../../components/common/AdminData/CreationForm/CreationForm";
=======
import styles from "./AdminPage.module.css";
import AdminData from "../../components/common/AdminData/AdminData/AdminData";
>>>>>>> Make table header dynamic
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
<<<<<<< HEAD
=======
    const [adminData, setAdminData] = useState([]);
    const [headers, setHeaders] = useState([]);
>>>>>>> Make table header dynamic

    const loadAdminData = () => {
        AdminDataService.fetchData(domain)
            .then((response) => {
                setAdminData(response.data);
            })
<<<<<<< HEAD
            .finally(() => isLoading(false))
            .catch((error) => console.log(error));
=======
            .finally(() => {
                    isLoading(false);
                }
            )
>>>>>>> Make table header dynamic
    };

    useEffect(() => {
        loadAdminData();
    }, []);

    const checkUrlForCreation = () => {
        const url = window.location.href;

<<<<<<< HEAD
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
=======
    useEffect(() => {
        if (!adminData) {
            return null;
        }
            const header = Object.keys(Object.assign({}, ...adminData));
            setHeaders(header);
            isLoading(false);
    }, [adminData]);

    return (
        <Fragment>
            <Header/>
            <section className={styles.page_container}>
                <section className={styles.sidebar_container}>
                    <Sidebar/>
                </section>
                {
                    adminData
                    &&
                    <section className={styles.table_container}>
                            <AdminData adminData={adminData} headers={headers} loading={loading}/>
                        // )}
                    </section>
                }
            </section>
        </Fragment>
    )
>>>>>>> Make table header dynamic
};

export default AdminPage;