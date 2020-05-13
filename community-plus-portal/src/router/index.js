import Vue from 'vue'
// 导入路由插件
import Router from 'vue-router'

import index from '@/views/index'
import login_github from "@/views/user/login_github";
import register from "../views/user/register";
import publish from "../views/article/publish";


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
      path: '/article/publish',
      name: 'publish',
      component: publish
    }

  ]
});
