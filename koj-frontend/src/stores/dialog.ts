import { defineStore } from 'pinia'
import { ref } from 'vue'

const useDialogStore = defineStore('dialog', () => {
  const passportDialogVisible = ref(false)

  const showPassportDialogVisible = () => {
    passportDialogVisible.value = true
  }

  const hidePassportDialogVisible = () => {
    passportDialogVisible.value = false
  }

  return { passportDialogVisible, showPassportDialogVisible, hidePassportDialogVisible }
})

export default useDialogStore