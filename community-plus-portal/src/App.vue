<template>
  <div style="width: 100%">
    <!-- 导航栏 -->
    <Navigation ref="navigation"/>
        <router-view/>
  </div>
</template>

<script>
  import Navigation from './components/navigation/index'
  import "@/assets/fonts/iconfont/iconfont.css";
export default {
  name: 'App',
  components: {
    Navigation
  },
  mounted() {
    window.addEventListener('unload', this.saveState);
  },
  methods: {
    saveState() {
      sessionStorage.setItem('userState', JSON.stringify(this.$store.state.user));
    },

  }
}
</script>

<style>
  html, body {
    width: 100%;
    height: 100%;
    margin: 0;
    padding: 0;
    background-color: #f0f2f5;
  }
</style>
