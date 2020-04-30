import axios from 'axios'

const service = axios.create({
  baseURL: '/login',
  timeout: '5000'
})

export default service
