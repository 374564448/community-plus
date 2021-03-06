//后端接口基础路径
export const BASE_API_URL = 'http://localhost:9000';


//用户中心
//用户API路径
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


//内容中心
//分类API路径
export const CATEGORY_API_URL = BASE_API_URL + '/category';
//文章API路径
export const ARTICLE_API_URL = BASE_API_URL + '/articles';
//文章保存路径
export const ARTICLE_SAVED_API_URL =  ARTICLE_API_URL+ '/save';
//获取保存文章路径
export const GET_SAVED_ARTICLE_URL = ARTICLE_API_URL + '/save/';
//获取需要编辑的文章路径
export const GET_EDIT_ARTICLE_URL = ARTICLE_API_URL + "/edit/"
//文章发布路径
export const ARTICLE_PUBLISH_URL = ARTICLE_API_URL + '/publish';
//分页 & 条件 获取文章列表路径
export const ARTICLE_LIST_URL = ARTICLE_API_URL + '/q';
//文章详情API
export const ARTICLE_DETAIL_URL = ARTICLE_API_URL + '/';
//个人文章列表
export const PROFILE_ARTICLE_LIST_URL = ARTICLE_API_URL + "/profile/list/"
//删除文章
export const ARTICLE_DELETE_URL = ARTICLE_API_URL + "/delete/"

//评论
export const COMMENT_API_URL = BASE_API_URL + "/comment"
export const COMMENT_CREATE_URL = COMMENT_API_URL + "/"
export const GET_COMMENT_LIST_URL = COMMENT_API_URL + "/list"
export const DELETE_COMMENT_URL = COMMENT_API_URL + "/delete/"
export const COMMENT_GET_ARTICLE_ID_URL = COMMENT_API_URL + "/getArticleId/"



//云中心
//上传文件API路径
export const UPLOAD_API_URL = BASE_API_URL + '/upload';
//上传图片luj
export const UPLOAD_IMAGE_URL = UPLOAD_API_URL + '/image';

//通知中心
export const NOTIFICATIONS_API_URL = BASE_API_URL + "/notifications"
export const NOTIFICATIONS_UN_READ_COUNT_URL = NOTIFICATIONS_API_URL + "/unReadCount/"
export const NOTIFICATIONS_LIST_URL = NOTIFICATIONS_API_URL + "/list/"
export const NOTIFICATIONS_READ_URL = NOTIFICATIONS_API_URL + "/read/"
