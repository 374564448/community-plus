//后端接口基础路径
export const BASE_API_URL = 'http://localhost:9010';

//用户中心路径
export const USER_API_URL = BASE_API_URL + '/users';
//普通账户登录
export const GENERAL_LOGIN_URL = USER_API_URL + '/generalLogin';
//GitHub账户登录
export const GITHUB_LOGIN_URL = USER_API_URL + '/githubLogin';
//拉取用户信息
export const GET_USER_INFO_URL = USER_API_URL + '/userInfo';

//GitHub代理路径
//export const GITHUB_PROXY_URL = 'http://localhost:8888';
