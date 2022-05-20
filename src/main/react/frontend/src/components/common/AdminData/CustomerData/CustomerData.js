import React, {useState, useEffect} from "react";
import {useParams} from "react-router";
import AdminDataService from "../../../../services/adminData.service";
import DataTable from "../DataTable/DataTable";
import AdminDataHeader from "../AdminDataHeader/AdminDataHeader";
import {CircularProgress} from "@mui/material";

const CustomerData = () => {
    let {domain} = useParams();
    const [customerData, setCustomerData] = useState([]);
    const [loading, isLoading] = useState(true);
    const [selectionModel, setSelectionModel] = useState([]);

    const loadAllCustomerData = () => {
        setTimeout(() => {
            AdminDataService.fetchData(domain)
                .then((data) => {
                    setCustomerData(data.data);
                })
                .finally(() => isLoading(false))
                .catch((error) => {
                    console.log(error);
                });
        }, 2000);
    };

    useEffect(() => {
        loadAllCustomerData();
    }, []);

    const header = [
        {
            field: "id",
            headerName: "ID",
            width: 70,
        },
        {
            field: "customerId",
            headerName: "Customer ID",
            width: 100,
        },
        {
            field: "firstName",
            headerName: "Firstname",
            width: 100,
        },
        {
            field: "insertion",
            headerName: "Middlename",
            width: 100,
        },
        {
            field: "lastName",
            headerName: "Lastname",
            width: 100,
        },
        {
            field: "phoneNumber",
            headerName: "Phonenumber",
            width: 130,
        },
        {
            field: "emailAddress",
            headerName: "Email-address",
            width: 200,
        },
    ];

    return (
        <>
            <DataTable
                data={customerData}
                header={header}
                handleChange={
                    (newSelectionModel) => {
                        setSelectionModel(newSelectionModel);
                    }
                }
                loading={loading}
            />
        </>
    );
};

export default CustomerData;
