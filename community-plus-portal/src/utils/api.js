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


//短信中心
export const SMS_API_URL = BASE_API_URL + '/sms';
//发送验证码
export const SMS_CHECK_CODE_URL = SMS_API_URL + '/sendCheckCode';
