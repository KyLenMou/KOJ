<template>
  <div id="problemView">
    <md-preview v-model="str" />
  </div>
</template>

<script setup lang="ts">
import { useRoute } from 'vue-router';
import { ref } from 'vue';

const route = useRoute();
// path: '/problem/:problemId',
const problemId = ref(route.params.problemId);

// 题目详情
const str = ref("#123\n- 123\n - 321")

</script>

<style scoped>
#problemView {

}
</style>
