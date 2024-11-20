// src/stores/useLoginStore.js
import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useLoginStore = defineStore('login', () => {
  const visible = ref(false)

  function showLoginDialog() {
    visible.value = true
  }

  function hideLoginDialog() {
    visible.value = false
  }

  return { visible, showLoginDialog, hideLoginDialog }
})