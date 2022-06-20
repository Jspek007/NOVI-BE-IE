import Home from "../pages/Homepage/Home";
import CollectionPage from "../pages/Collections/CollectionPage/CollectionPage";
import Customerservice from "../pages/CustomerService/Customerservice";
import AboutUs from "../pages/AboutUs/AboutUs";
import MensCollection from "../pages/Collections/MensCollection/MensCollection";
import WomensCollection from "../pages/Collections/WomensCollection/WomensCollection";
import ProductDetail from "../components/common/Catalog/ProductDetail";
import LoginPage from "../pages/LoginPage/LoginPage";
import SignupPage from "../pages/SignupPage/SignupPage";
import EmailSignUpPage from "../pages/SignupPage/EmailSignUpPage";
import ForgotPasswordPage from "../pages/ForgotPassword/ForgotPasswordPage/ForgotPasswordPage";
import TermsAndConditions from "../pages/TermsAndConditions/TermsAndConditions";
import PrivacyPage from "../pages/PrivacyPage/PrivacyPage";
import MyAccountPage from "../pages/MyAccountPage/MyAccountPage";
import Wishlist from "../pages/Wishlist/Wishlist";
import CartPage from "../pages/CartPage/CartPage";
import ShippingPage from "../pages/Checkout/ShippingPage/ShippingPage";
import PaymentPage from "../pages/Checkout/PaymentPage/PaymentPage";
import SuccesPage from "../pages/Checkout/Successpage/SuccesPage";
import NotFound from "../pages/NotFound/NotFound";

const routes = [
  {
    path: "/",
    name: "Home",
    Component: Home,
    memberOnly: false,
  },
  {
    path: "/klantenservice",
    name: "Klantenservice",
    Component: Customerservice,
    memberOnly: false,
  },
  {
    path: "/over-ons",
    name: "Over ons",
    Component: AboutUs,
  },
  {
    path: "/collectie",
    name: "Collectie",
    Component: CollectionPage,
    memberOnly: true,
  },
  {
    path: "/collectie/heren",
    name: "Heren",
    Component: MensCollection,
    memberOnly: true,
  },
  {
    path: "/collectie/dames",
    name: "Dames",
    Component: WomensCollection,
    memberOnly: true,
  },
  {
    path: `/collectie/:categoryName/:productId`,
    name: "Huidig product",
    Component: ProductDetail,
    memberOnly: true,
  },
  {
    path: "/login",
    name: "Login",
    Component: LoginPage,
    memberOnly: false,
  },
  {
    path: "/aanmelden",
    name: "Aanmelden",
    Component: SignupPage,
    memberOnly: false,
  },
  {
    path: "/email-register",
    name: "Registreren",
    Component: EmailSignUpPage,
    memberOnly: false,
  },
  {
    path: "/wachtwoord-vergeten",
    name: "Wachtwoord vergeten",
    Component: ForgotPasswordPage,
    memberOnly: false,
  },
  {
    path: "/algemene-voorwaarden",
    name: "Algemene Voorwaarden",
    Component: TermsAndConditions,
    memberOnly: false,
  },
  {
    path: "/privacy",
    name: "Privacy",
    Component: PrivacyPage,
    memberOnly: false,
  },
  {
    path: "/mijn-account",
    name: "Mijn account",
    Component: MyAccountPage,
    memberOnly: true,
  },
  {
    path: "/wishlist",
    name: "Wishlist",
    Component: Wishlist,
    memberOnly: true,
  },
  {
    path: "/winkelwagen",
    name: "Winkelwagen",
    Component: CartPage,
    memberOnly: true,
  },
  {
    path: "/checkout/shipping",
    name: "Checkout",
    Component: ShippingPage,
    memberOnly: true,
  },
  {
    path: "/checkout/payment",
    name: "Checkout",
    Component: PaymentPage,
    memberOnly: true,
  },
  {
    path: "/checkout/success",
    name: "Checkout",
    Component: SuccesPage,
    memberOnly: true,
  },
    {
        name: "NotFound",
        Component: NotFound,
    }
];

export default routes;
