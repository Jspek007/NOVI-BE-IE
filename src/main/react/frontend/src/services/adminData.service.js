import axios from 'axios';

class AdminDataService {
    async fetchData(domain) {
        const API_URL = `/api/v1/${domain}/get/all`;
        const token = localStorage.getItem('userInformation');
        try {
            return await axios.get(
                API_URL, {
                    headers: {
                        "Authorization": `Bearer ${token}`
                    }
                }
            );
        } catch (error) {
            return error;
        }
    }
}

export default new AdminDataService();