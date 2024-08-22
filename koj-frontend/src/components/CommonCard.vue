<template>
  <div id="commonCard">
    <el-card shadow="never" style="margin-bottom: 20px">
      <template v-if="p.head.length" #header>
        <div class="card-header header-font">
           → {{ p.head }}
        </div>
      </template>
      <slot name="content"/>
      <template v-if="$slots.foot" #footer>
        <slot name="foot"/>
      </template>
    </el-card>
  </div>
</template>

<script setup lang="ts">
interface CommonCardProps {
  head: string;
}

const p = withDefaults(defineProps<CommonCardProps>(),{
  head: () => "",
})
</script>

<style scoped>
#commonCard {

}

:deep(.el-card__footer) {
  text-align: right;
  padding: 5px;
  background-color: #f5f5f5;
}

:deep(.el-card__body) {
  padding: 0;
}

:deep(.el-card) {
  border-radius: 6px 6px 0 0;
  border: 1px solid #b9b9b9;
  --el-card-border-color: #b9b9b9;
}

:deep(.el-card__header) {
  padding: 0;
}

.card-header {
  margin: 0.1em 10px;
}

:deep(.el-statistic__content) {
  color: #777 !important;
  font-size: 1.1rem;
  font-family: verdana, arial, sans-serif;
}
</style>
