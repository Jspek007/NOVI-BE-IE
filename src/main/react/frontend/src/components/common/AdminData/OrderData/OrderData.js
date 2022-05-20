import React, {useEffect, useState} from 'react';
import AdminDataService from "../../../../services/adminData.service";
import {useParams} from "react-router";
import AdminDataHeader from "../AdminDataHeader/AdminDataHeader";
import DataTable from "../DataTable/DataTable";

const OrderData = () => {

    let {domain} = useParams();

    const [orderData, setOrderData] = useState({});
    const [loading, isLoading] = useState(true);
    const [, setSelectionModel] = useState([]);

    const loadAllOrderData = () => {
        setTimeout(() => {
            AdminDataService.fetchData(domain)
                .then((data) => {
                    setOrderData(data.data);
                })
                .finally(() => isLoading(false))
                .catch((error) => {
                    console.log(error);
                });
        }, 2000);
    };

    useEffect(() => {
        loadAllOrderData();
    }, []);

    const header = [
        {
            field: "entityId",
            headerName: "ID",
            width: 25,
        },
        {
            field: "customerId",
            headerName: "Customer ID",
            width: 100,
        },
        {
            field: "customerFirstName",
            headerName: "Firstname",
            width: 100,
        },
        {
            field: "customerInsertion",
            headerName: "Middle name",
            width: 100,
        },
        {
            field: "customerLastName",
            headerName: "Lastname",
            width: 100,
        },
        {
            field: "customerEmail",
            headerName: "Email-address",
            width: 200,
        },
        {
            field: "grandTotal",
            headerName: "Grand total",
            width: 100,
        },
        {
            field: "totalItems",
            headerName: "Order items",
            width: 100,
        },
        {
            field: "createdAtDate",
            headerName: "Order date",
            width: 200,
        },
    ]

    return (
        <>
            <DataTable
                data={orderData}
                header={header}
                handleChange={
                    (newSelectionModel) => {
                        setSelectionModel(newSelectionModel);
                    }
                }
                handleRowId={(row) => row.entityId}
                loading={loading}
            />
        </>
    );
};

export default OrderData;