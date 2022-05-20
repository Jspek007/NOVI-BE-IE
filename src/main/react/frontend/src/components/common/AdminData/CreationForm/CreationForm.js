import React, {useState} from 'react';
import styles from './CreationForm.module.css';
import {TextField} from "@mui/material";

const CreationForm = (props) => {

    const [formFields, setFormFields] = useState(props.formFields);

    const submit = (event) => {
        event.preventDefault();
        console.log(formFields);
    }

    return (
        <section className={styles.form_container}>
            <form onSubmit={submit}>
                {formFields.map((form, index) => {
                    return (
                        <div key={index}>
                            <TextField />
                        </div>
                    )
                })}
            </form>
        </section>
    );
};

export default CreationForm;