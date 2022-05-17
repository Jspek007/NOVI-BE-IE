import axios from 'axios';

class AdminDataService {
    fetchData(domain) {
        const API_URL = `/api/v1/${domain}/get/all`;
        const token = localStorage.getItem('userInformation');
        try {
            const result = axios.get(
                API_URL, {
                    headers: {
                        "Authorization": `Bearer ${token}`
                    }
                }
            );
            return result;
        } catch (error) {
            return error;
        }
    }
}

export default new AdminDataService();