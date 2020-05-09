//后端接口基础路径
export const BASE_API_URL = 'http://localhost:9000';

//用户中心路径
export const USER_API_URL = BASE_API_URL + '/users';
//普通账户登录
export const GENERAL_LOGIN_URL = USER_API_URL + '/generalLogin';
//GitHub账户登录
export const GITHUB_LOGIN_URL = USER_API_URL + '/githubLogin';
//拉取用户信息
export const GET_USER_INFO_URL = USER_API_URL + '/userInfo';
//发送验证码
export const CHECK_CODE_URL = USER_API_URL + '/sendCheckCode';
//注册
export const REGISTER_USER_URL = USER_API_URL + '/register';
