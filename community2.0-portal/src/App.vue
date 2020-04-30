<template>
  <div style="width: 100%">
    <!-- 导航栏 -->
    <Navigation/>

    <router-view/>
  </div>
</template>

<script>
  import Navigation from './components/navigation/index'
export default {
  name: 'App',
  components: {
    Navigation
  }
}
</script>

<style>

</style>
