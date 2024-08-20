<template>
  <div id="homeView">
    <el-row :gutter="20" style="margin-top: 50px">
      <el-col :span="18">

      </el-col>
      <!--首页右侧区域-->
      <el-col :span="6">
        <CommonCard head="Pay Attention">
          <template #content>
            <el-space style="display: flex;justify-content: center;margin:10px" direction="vertical" :size="0">
              <div class="header-font">Before contest</div>
              <div class="common-link" style="font-size: 1.2rem">Pinely Round 4 (Div. 1 + Div. 2)</div>
              <el-countdown :value="value"/>
            </el-space>
          </template>
        </CommonCard>
        <UserCard/>
      </el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts">
import UserCard from "@/components/UserCard.vue";
import CommonCard from "@/components/CommonCard.vue";
import { ref } from "vue";
const value = ref(Date.now() + 1000 * 60 * 60 * 7)
</script>

<style scoped>
#homeView {

}
</style>
