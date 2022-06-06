import React, {useState} from 'react';

const DynamicForm = (props) => {

    const [formData, setFormData] = useState([]);
    const onSubmit = (event) => {
        console.log("Click me");
    }



    return (
        <form action=""></form>
    );
};

export default DynamicForm;