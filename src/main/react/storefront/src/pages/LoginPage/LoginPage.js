import React, {Fragment} from "react";
import LoginForm from "../../components/form/Forms/LoginForm";
import "./LoginPage.scss";
import {Link} from "react-router-dom";
import Title from "../../components/common/Content/TextComponents/Title/Title";
import SubTitle from "../../components/common/Content/TextComponents/SubTitle/SubTitle";
import {useHistory, useLocation} from "react-router";
import {useAuth} from "../../contexts/AuthContext";
import {handleRedirectToOrBack} from "../../helpers/handleRedirectToOrBack/handleRedirectToOrBack";
import {LoginProviderButton} from "../../components/form/Buttons";
import {FaFacebook} from "react-icons/fa";
import {FcGoogle} from "react-icons/fc";

function LoginPage() {

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
            <section className="login-container">
                <section className="form-header">
                    <Title text="Fijn dat je er weer bent!"/>
                    <SubTitle text="Login op je eigen manier"/>
                </section>

                <section className="login-method-container">
                    <section className="third-party-methods">
                        <SubTitle text="Kies voor eenvoud"/>
                        <section className="third-party-login-buttons">
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
                                <FcGoogle className="button-icon"/>
                                Google
                            </LoginProviderButton>
                        </section>
                    </section>
                    <section className="email-form-login">
                        <SubTitle text="Of per email"/>
                        <LoginForm/>
                    </section>
                </section>
                <p className="alternative-register-container">
                    Wachtwoord vergeten?{" "}
                    <Link to="/wachtwoord-vergeten" className="link">
                        Klik hier
                    </Link>
                </p>
                <p className="alternative-register-container">
                    Toch nog geen account?{" "}
                    <Link to="/aanmelden" className="link">
                        Aanmelden
                    </Link>
                </p>
            </section>
        </Fragment>
    );
}

export default LoginPage;
