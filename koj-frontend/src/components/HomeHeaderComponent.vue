<template>
  <div id="homeHeaderComponent">
    <el-image
      class="header-image"
      :src="getImg()"/>
    <div class="header-right">
      <el-space>
        <el-icon><Bell /></el-icon>
        <el-divider direction="vertical"></el-divider>
        <el-icon><Location /></el-icon>
      </el-space>
      <div>
        <el-link style="color: #526ecc;font-size: 0.8em">KyLen</el-link>
        <el-divider direction="vertical"></el-divider>
        <el-link style="color: #526ecc;font-size: 0.8em">Logout</el-link>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">

function getImg() {
  return new URL(`../assets/codeforces.png`, import.meta.url).href;
}
</script>

<style scoped>
#homeHeaderComponent {
  display: flex;
  justify-content: space-between;
}

.header-image {
  height: 70px;
  float: left;
}
.header-right {
  float: left;
  text-align: right;
  font-size: 20px;
}
</style>
