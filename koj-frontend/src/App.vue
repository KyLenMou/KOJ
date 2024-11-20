<template>
  <div id="app">
    <router-view />
    <passport-form v-model:passportDialogVisible="passportDialogVisible" />
  </div>
</template>

<script setup lang="ts">
import { useDialogStore } from './stores/dialog'
import { storeToRefs } from 'pinia'

const dialogStore= useDialogStore()
const { passportDialogVisible } = storeToRefs(dialogStore)
</script>
<style scoped>
#app {
}
</style>
