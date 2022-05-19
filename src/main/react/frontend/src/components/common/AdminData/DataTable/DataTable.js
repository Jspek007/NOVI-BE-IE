import React from 'react';
import {CircularProgress} from "@mui/material";
import {DataGrid, GridToolbar} from "@mui/x-data-grid";

const DataTable = (props) => {
    return (
        <div style={{ height: 750, width: "80%" }}>
            {props.loading && <CircularProgress />}
            <DataGrid
                rows={props.data}
                columns={props.header}
                pageSize={20}
                rowsPerPageOptions={[20]}
                checkboxSelection
                onSelectionModelChange={props.handleChange}
                selectionModel={props.selectionModel}
                components={{ Toolbar: GridToolbar}}
                componentsProps={{
                    toolbar: {
                        showQuickFilter: true,
                        quickFilterProps: { debounceMs: 500},
                    }
                }}
            />
        </div>
    );
};

export default DataTable;