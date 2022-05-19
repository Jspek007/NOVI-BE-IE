import React, {useState, useEffect} from "react";
import {useParams} from "react-router";
import AdminDataService from "../../../../services/adminData.service";
import DataTable from "../DataTable/DataTable";
import AdminDataHeader from "../AdminDataHeader/AdminDataHeader";

const CustomerData = () => {
    let {domain} = useParams();
    const [customerData, setCustomerData] = useState([]);
    const [loading, isLoading] = useState(false);
    const [selectionModel, setSelectionModel] = useState([]);

    const loadAllCustomerData = () => {
        AdminDataService.fetchData(domain)
            .then((data) => {
                setCustomerData(data.data);
            })
            .catch((error) => {
                console.log(error);
            });
    };

    useEffect(() => {
        isLoading(true);
        loadAllCustomerData();
        isLoading(false);
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
            <AdminDataHeader />
            <DataTable
                data={customerData}
                header={header}
                handleChange={
                    (newSelectionModel) => {
                        setSelectionModel(newSelectionModel);
                    }
                }
            />
        </>
    );
};

export default CustomerData;
