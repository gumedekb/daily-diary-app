import axios from "axios";

const api = axios.create({
   baseURL: 'https://localhost:8443/api',   // backend url
});

// Intercept requests to add Authorization header if token exists

api.interceptors.request.use(config => {
   const token = localStorage.getItem('jwtToken');
   if (token) {
      config.headers.Authorization = `Bearer ${token}`;
   }
   return config;
});

export default api;