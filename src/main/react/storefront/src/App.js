import "./App.scss";
import {BrowserRouter as Router, Route, Switch} from "react-router-dom";
import {Footer, Header} from "./components/common";
import AuthContextProvider from "./contexts/AuthContext";
import routes from "./routes/routes";
import Breadcrumbs from "./components/common/Breadcrumbs/Breadcrumbs";
import PrivateRoute from "./routes/PrivateRoute";
import CartContextProvider from "./contexts/CartContext";
import NotFound from "./pages/NotFound/NotFound";


const App = () => (
    <Router>
        <Switch>
            {routes.map(({path, Component, memberOnly, needsCart}, key) => (
                <Route
                    exact
                    path={path}
                    key={key}
                    render={(props) => {
                        const crumbs = routes
                            .filter(({path}) => props.match.path.includes(path))
                            .map(({path, ...rest}) => ({
                                path: Object.keys(props.match.params).length
                                    ? Object.keys(props.match.params).reduce(
                                        (path, param) =>
                                            path.replace(`:${param}`, props.match.params[param]),
                                        path
                                    )
                                    : path,
                                ...rest,
                            }));

                        return (
                            <AuthContextProvider>
                                <CartContextProvider>
                                    <Header/>
                                    <Breadcrumbs crumbs={crumbs}/>
                                    {memberOnly && <PrivateRoute component={Component}/>}
                                    {!memberOnly && <Component {...props} />}
                                    <Footer/>
                                </CartContextProvider>
                            </AuthContextProvider>
                        );
                    }}
                />
            ))}
        </Switch>
    </Router>
);

export default App;
