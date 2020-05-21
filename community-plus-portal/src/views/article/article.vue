<template>

</template>

<script>
    export default {
      name: "article",
      mounted() {
        let id = this.$route.params.id;
      },
      data() {
        return {
          article: []
        }
      }
    }
</script>

<style scoped>

</style>
