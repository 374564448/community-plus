//后端接口基础路径
export const BASE_API_URL = 'http://localhost:9000';


//用户中心
//用户API路径
export const USER_API_URL = BASE_API_URL + '/user';
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


//内容中心
//分类API路径
export const CATEGORY_API_URL = BASE_API_URL + '/category';
//文章API路径
export const ARTICLE_API_URL = BASE_API_URL + '/article';
//文章保存路径
export const ARTICLE_SAVED_API_URL =  ARTICLE_API_URL+ '/save';
//获取保存文章路径
export const GET_SAVED_ARTICLE_URL = ARTICLE_API_URL + '/getTheSavedArticle';
//文章发布路径
export const ARTICLE_PUBLISH_URL = ARTICLE_API_URL + '/publish';
//分页 & 条件 获取文章列表路径
export const ARTICLE_LIST_URL = ARTICLE_API_URL + '/q';

//云中心
//上传文件API路径
export const UPLOAD_API_URL = BASE_API_URL + '/upload';
//上传图片luj
export const UPLOAD_IMAGE_URL = UPLOAD_API_URL + '/image';
