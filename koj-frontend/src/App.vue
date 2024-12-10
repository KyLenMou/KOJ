<template>
  <router-view />
  <passport-form v-model:passportDialogVisible="passportDialogVisible" />
</template>

<script setup lang="ts">
import useDialogStore from './stores/dialog'
import { storeToRefs } from 'pinia'
const { passportDialogVisible } = storeToRefs(useDialogStore())
</script>

<style scoped></style>
