import axios from 'axios'
import { getToken } from '@/utils/auth'

const service = axios.create({
  timeout: '8000'
});

service.interceptors.request.use(config => {
    if (getToken()) {
      config.headers['X-Token'] = getToken().token;
    }
    return config;
});

export default service
