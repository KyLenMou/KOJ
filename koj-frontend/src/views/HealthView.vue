<template>
  <div id="healthView">
    <template v-if="isHealth">
      <h1>The Backend Service Is Healthy :-)</h1>
      <p>{{ BETime }}</p>
    </template>
    <template v-else>
      <h1>The Backend Service Is Unhealthy :-(</h1>
    </template>
  </div>
</template>

<script setup lang="ts">

import { HealthControllerService } from "@/api";

const isHealth = ref(false)
const BETime = ref('')

const getStatus = async () => {
  const res = await HealthControllerService.getHealthUsingGet();
  if (res.code === 0) {
    isHealth.value = true
    BETime.value = res.data as string
  }
}

onMounted(() => {
  getStatus()
})

</script>

<style scoped>
#healthView {

}
</style>
