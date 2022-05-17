import React, {useEffect, useState} from 'react';
import AdminDataService from "../../../../services/adminData.service";
import {useParams} from "react-router";

const OrderData = () => {

    let {domain} = useParams();
    const [orderData, setOrderData] = useState({});

    useEffect(() => {
        AdminDataService.fetchData(domain)
            .then((response) => {
                setOrderData(response.description);
                console.log(orderData);
            })
            .catch((error) => {
                console.log(error);
            })
    }, []);

    return (
        <div>
            <h1>Hello orders</h1>
        </div>
    );
};

export default OrderData;