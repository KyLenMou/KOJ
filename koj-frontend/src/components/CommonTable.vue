<template>
  <div id="commonTable">
    <div class="tableContainer">
      <span style="margin-left: 5px; color: #000;line-height: 25px">{{ p.tableHead }}</span>
      <el-table
        :data="tableData"
        stripe border
        :cell-style="{
          borderColor:'#e1e1e1',
          textAlign: 'center',
        }"
        :header-cell-style="{
          borderColor:'#e1e1e1',
          textAlign: 'center',
          color: '#000',
          padding: '5px',
        }"
      >
        <slot name="tableContent"></slot>
      </el-table>
    </div>
  </div>
</template>

<script setup lang="ts">
import { defineProps, withDefaults } from "vue";

interface Props {
  tableHead?: string;
  tableData?: any[];
}

const p = withDefaults(defineProps<Props>(), {
  tableHead: () => 'Table',
  tableData: () => [],
});

</script>

<style scoped>
#commonTable {

}
.el-table {
  --el-table-border-color: #e1e1e1;
}

:deep(.el-table) {
  border-radius: 8px !important;
}

.tableContainer {
  background-color: #e1e1e1;
  padding: 2px;
  border-radius: 6px;
}
</style>
