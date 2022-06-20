import React from "react";
import {Fragment} from "react";
import {Link} from "react-router-dom";
import "./SignupPage.scss";
import Title from "../../components/common/Content/TextComponents/Title/Title";
import SubTitle from "../../components/common/Content/TextComponents/SubTitle/SubTitle";
import {LoginProviderButton} from "../../components/form/Buttons";
import {FiMail} from "react-icons/fi";
import {FaFacebook} from "react-icons/fa";
import {useHistory, useLocation} from "react-router";
import {useAuth} from "../../contexts/AuthContext";
import {handleRedirectToOrBack} from "../../helpers/handleRedirectToOrBack/handleRedirectToOrBack";
import {FcGoogle} from "react-icons/fc";

function SignupPage() {
    const history = useHistory();
    const location = useLocation();
    const {signInWithFacebook, signInWithGoogle} = useAuth();

    const facebookLogin = () => {
        signInWithFacebook()
            .then(() => {
                handleRedirectToOrBack({history, location});
            })
            .catch((e) => console.log(e.message));
    }

    const googleLogin = () => {
        signInWithGoogle()
            .then(() => {
                handleRedirectToOrBack({history, location});
            })
            .catch((e) => console.log(e.message));
    }

    return (
        <Fragment>
            <section className="signup-container">
                <section className="signup-message-container">
                    <Title text="Aanmelden"/>
                    <SubTitle text="Krijg toegang tot onze fashion outlets"/>
                </section>
                <section className="actions-container">
                    <section className="action-buttons-container">
                        <LoginProviderButton
                            provider="facebook"
                            clickHandler={facebookLogin}
                        >
                            <FaFacebook className="button-icon"/>
                            Facebook
                        </LoginProviderButton>
                        <LoginProviderButton
                            provider="google"
                            clickHandler={googleLogin}
                            >
                            <FcGoogle className="button-icon" />
                            Google
                        </LoginProviderButton>
                        <Link to="/email-register" className="nav-link">
                            <LoginProviderButton
                                provider="email"
                            >
                                <FiMail className="button-icon"/>
                                Email
                            </LoginProviderButton>
                        </Link>
                    </section>
                </section>
                <section className="info-section">
                    <p>
                        Lees eerst onze{" "}
                        <Link to="/algemene-voorwaarden" className="link">
                            algemene voorwaarden
                        </Link>
                    </p>
                    <p>
                        Al wel een account?{" "}
                        <Link to="/login" className="link">
                            Inloggen
                        </Link>
                    </p>
                </section>
            </section>
        </Fragment>
    );
}

export default SignupPage;
