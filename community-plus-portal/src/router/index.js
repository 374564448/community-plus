import Vue from 'vue'
// 导入路由插件
import Router from 'vue-router'

import index from '@/views/index'
import login_github from "@/views/user/login_github";
import register from "@/views/user/register";
import publish from "@/views/article/publish";
import edit from "@/views/article/edit";
import articles from "@/views/article/articles";
import notifications from "@/views/notification/notifications";
import notification from "@/views/notification/notification";
import profiles from "@/views/profile/profiles";
import profile from "@/views/profile/profile";
import profileArticles from "@/views/profile/profileArticles";
import error from "@/views/error";


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
      path: '/articles/edit/:id(\\d+)',
      name: 'edit',
      component: edit
    },
    {
      path: '/articles/:id(\\d+)',
      name: 'articles',
      component: articles
    },
    {
      path: '/notifications',
      name: 'notifications',
      component: notifications,
      children: [
        {path: '',name: 'notification',component: notification}
      ]
    },
    {
      path: '/profiles',
      name: 'profiles',
      component: profiles,
      children: [
        {path: '',name: 'profile',component: profile},
        {path: 'articles',name: 'profileArticles',component: profileArticles}

      ]
    },
    {
      path: '*',
      name: 'error',
      component: error
    }



  ]
});
