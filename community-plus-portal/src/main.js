// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'

import App from './App'
import Vuex from 'vuex'
import VueRouter from 'vue-router'

import store from '@/store'

import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

import router from './router'

import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'



Vue.use(Vuex);
Vue.use(VueRouter);
Vue.use(ElementUI);
Vue.use(mavonEditor);

Vue.config.productionTip = false;
Vue.config.devtools = true;

/* eslint-disable no-new */
new Vue({
  el: '#app',
  // 启用 ElementUI
  router,
  store,
  render: h => h(App),
});



