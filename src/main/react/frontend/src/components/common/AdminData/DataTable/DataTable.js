import React from 'react';
import {LinearProgress} from "@mui/material";
import {DataGrid, GridToolbar} from "@mui/x-data-grid";

const DataTable = (props) => {
<<<<<<< HEAD
    return (
        <div style={{ height: 750, width: "80%" }}>
            <DataGrid
                rows={props.data}
                columns={props.header}
                pageSize={20}
                rowsPerPageOptions={[20]}
                checkboxSelection
                onSelectionModelChange={props.handleChange}
                selectionModel={props.selectionModel}
                components={{
                    Toolbar: GridToolbar,
                    LoadingOverlay: LinearProgress,
            }}
                componentsProps={{
                    toolbar: {
                        showQuickFilter: true,
                        quickFilterProps: { debounceMs: 500},
                    }
                }}
                getRowId={props.handleRowId}
                loading={props.loading}
            />
        </div>
    );
=======
  return (
    <div style={{ height: 750, width: "80%" }}>
      <DataGrid
        rows={props.data}
        columns={props.header}
        pageSize={20}
        rowsPerPageOptions={[20]}
        checkboxSelection
        onSelectionModelChange={props.handleChange}
        selectionModel={props.selectionModel}
        components={{
          Toolbar: GridToolbar,
          LoadingOverlay: LinearProgress,
        }}
        loading={props.loading}
        componentsProps={{
          toolbar: {
            showQuickFilter: true,
            quickFilterProps: { debounceMs: 500 },
          },
        }}
        getRowId={(row) => row.id || row.customerId}
      />
    </div>
  );
>>>>>>> Make table header dynamic
};

export default DataTable;