<template>
  <div id="app">
    <router-view />
    <passport-form v-model:visible="visible"/>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useLoginStore } from './stores/dialog';
import { storeToRefs } from 'pinia';

const loginStore = useLoginStore()
const { visible } = storeToRefs(loginStore)
</script>
<style scoped>
#app {
}
</style>
