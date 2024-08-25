<template>
  <div id="codeView">
    <el-row :gutter="30">
      <el-col :span="4">
      </el-col>
      <el-col :span="20">
        <CommonCard head="code">
          <template #content>
            <CodeEditor/>
          </template>
        </CommonCard>
      </el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts">

</script>

<style scoped>
#codeView {

}
</style>
