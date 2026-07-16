import axios from 'axios';

// Relative path: same-origin in production (Spring Boot serves the built frontend),
// proxied to localhost:8080 by vite.config.js during dev (npm run dev).
const API_BASE_URL = '/api';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
  timeout: 10000,
});

// Add request/response interceptors for debugging
api.interceptors.request.use(request => {
  console.log('🚀 API Request:', request.method.toUpperCase(), request.url);
  return request;
});

api.interceptors.response.use(
  response => {
    console.log('✅ API Response:', response.status, response.config.url);
    return response;
  },
  error => {
    console.error('❌ API Error:', {
      message: error.message,
      status: error.response?.status,
      data: error.response?.data,
      url: error.config?.url
    });
    return Promise.reject(error);
  }
);

export const loginUser = async (email, password) => {
  try {
    const response = await api.post('/users/login', { email, password });
    return response.data;
  } catch (error) {
    console.error('Login failed');
    throw error;
  }
};

export const getTransactions = async (userId) => {
  try {
    const response = await api.get(`/transactions/user/${userId}`);
    return response.data;
  } catch (error) {
    console.error('Failed to load transactions');
    throw error;
  }
};

export const addTransaction = async (transaction) => {
  try {
    const response = await api.post('/transactions', transaction);
    return response.data;
  } catch (error) {
    console.error('Failed to add transaction');
    throw error;
  }
};

export const searchTransactions = async (userId, params) => {
  try {
    const response = await api.get('/transactions/search', { params: { userId, ...params } });
    return response.data;
  } catch (error) {
    console.error('Failed to search transactions');
    throw error;
  }
};