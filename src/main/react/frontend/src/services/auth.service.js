import axios from 'axios';

const API_URL = "http://localhost:8080/login";

class AuthService {
    login(username, password) {
        return axios.post(
            API_URL,
            {},
            {
                params: {
                    username, password
                }
            }
        ).then((response) => {
            return response;
        })
            .catch(error => {
                return error;
            })
    };

    logout() {
        localStorage.removeItem('user');
        localStorage.removeItem('userInformation');
    }

    getCurrentUser() {
        return JSON.parse(localStorage.getItem("user"));
    }
}

export default new AuthService();