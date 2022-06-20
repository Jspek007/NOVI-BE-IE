import React, {useEffect, useState} from "react";
import AdminDataService from "../../../../services/adminData.service";
import {useParams} from "react-router";
import AdminDataHeader from "../AdminDataHeader/AdminDataHeader";
import DataTable from "../DataTable/DataTable";

const AdminData = ({adminData, loading, headers}) => {
    const [, setSelectionModel] = useState([]);

    let headerData = headers.map(function(value) {
        return {
            field: value
        };
    });

    return (
        <>
            <AdminDataHeader state={adminData}/>
            <DataTable
                data={adminData}
                header={headerData}
                handleChange={(newSelectionModel) => {
                    setSelectionModel(newSelectionModel);
                }}
                loading={loading}
            />
        </>
    );
};

export default AdminData;
