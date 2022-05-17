import React, {useState, useEffect} from 'react';
import styles from '../AdminData.module.css';
import {useParams} from "react-router";
import AdminDataService from "../../../../services/adminData.service";

const CustomerData = () => {

    let {domain} = useParams();
    const [customerData, setCustomerData] = useState({});
    const [loading, isLoading] = useState(false);

    useEffect(() => {
        isLoading(true);
        AdminDataService.fetchData(domain)
            .then((response) => {
                setCustomerData(response.data);
                isLoading(false);
            })
            .catch((error) => {
                return error;
            })
    }, []);

    return (
        <table className={styles.admin_table}>
            <thead className={styles.admin_table_head}>
            <tr className={styles.admin_table_row}>
                <th>Id</th>
                <th>Customer Id</th>
                <th>Firstname</th>
                <th>Insertion</th>
                <th>Lastname</th>
                <th>Phone number</th>
                <th>Email-address</th>
            </tr>
            </thead>
        </table>
    );
};

export default CustomerData;