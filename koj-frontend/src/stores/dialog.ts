// src/stores/useLoginStore.js
import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useDialogStore = defineStore('dialog', () => {
  const passportDialogVisible = ref(false)

  function showPassportDialogVisible() {
    passportDialogVisible.value = true
  }

  function hidePassportDialogVisible() {
    passportDialogVisible.value = false
  }

  return { passportDialogVisible, showPassportDialogVisible, hidePassportDialogVisible }
})
