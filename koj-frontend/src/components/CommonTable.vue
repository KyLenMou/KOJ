<template>
  <div id="commonTable">
    <div class="tableContainer">
      <span style="margin-left: 5px; color: #000">{{ p.tableHead }}</span>
      <el-table
        :data="tableData"
        style="width: 100%"
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
        <el-table-column prop="date" label="Date" width="180" />
        <el-table-column prop="name" label="Name" width="180" />
        <el-table-column prop="address" label="Address" />
      </el-table>
    </div>
  </div>
</template>

<script setup lang="ts">
import { defineProps, withDefaults } from "vue";

const tableData = [
  {
    date: '2016-05-03',
    name: 'Tom',
    address: 'No. 189, Grove St, Los Angeles',
  },
  {
    date: '2016-05-02',
    name: 'Tom',
    address: 'No. 189, Grove St, Los Angeles',
  },
  {
    date: '2016-05-04',
    name: 'Tom',
    address: 'No. 189, Grove St, Los Angeles',
  },
  {
    date: '2016-05-01',
    name: 'Tom',
    address: 'No. 189, Grove St, Los Angeles',
  },
]

interface Props {
  tableHead: string;
}

const p = withDefaults(defineProps<Props>(), {
  tableHead: 'Table Head',
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
