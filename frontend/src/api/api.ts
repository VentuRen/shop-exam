import axios from "axios";

const API_URL = "http://localhost:8080/api";

export const api = axios.create({
  baseURL: API_URL,
  headers: {
    "Content-Type": "application/json",
  },
});

// 🔹 Agregar interceptor para incluir token, pero NO en `/auth/login`
api.interceptors.request.use((config) => {
  if (!config.url?.includes("/auth/login")) { // 🔥 Evitar enviar token en login
    const token = localStorage.getItem("token");
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
  }
  return config;
});

export default api;
