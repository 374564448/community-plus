import Vue from 'vue'
// 导入路由插件
import Router from 'vue-router'

import index from '@/views/index'
import login_github from "@/views/user/login_github";
import register from "@/views/user/register";
import publish from "@/views/article/publish";
import articles from "@/views/article/articles";
import error from "@/views/error";
import notifications from "@/views/notification/notifications";
import notification from "@/views/notification/notification";

// 安装路由
Vue.use(Router);

// 配置路由
export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'index',
      component: index
    },
    {
      path: '/auth/register',
      name: 'register',
      component: register
    },
    {
      path: '/login_github',
      name: 'login_github',
      component: login_github
    },
    {
      path: '/articles/publish',
      name: 'publish',
      component: publish
    },
    {
      path: '/articles/:id(\\d+)',
      name: 'articles',
      component: articles
    },
    {
      path: '/notifications',
      name: '',
      component: notifications,
      children: [
        {path: '',name: 'notification',component: notification}
      ]
    },
    {
      path: '*',
      name: 'error',
      component: error
    }



  ]
});
