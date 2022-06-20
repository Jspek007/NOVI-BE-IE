import React from 'react';
import "./NotFound.scss";
import NotFoundImage from "../../assets/images/notFound/not_found.svg"
import {SubTitle, Title} from "../../components/common/Content/TextComponents";
import {Link} from "react-router-dom";
import {PrimaryButton} from "../../components/form/Buttons";

const NotFound = () => {
    return (
        <section className="not-found-container">
            <img src={NotFoundImage} alt="Page not found" className="not-found-image"/>
            <Title text="Woopsie.. We hebben uw wens helaas niet kunnen vinden!"/>
            <SubTitle text="Onderstaande button zet u weer op het juiste spoor!"/>
            <section className="not-found-redirect">
                <Link exact to="/">
                    <PrimaryButton buttonType="not-found">
                        <span>Ga terug naar de winkel</span>
                    </PrimaryButton>
                </Link>
            </section>
        </section>
    );
};

export default NotFound;