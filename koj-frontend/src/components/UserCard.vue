<template>
  <div id="userCard">
    <CommonCard :head="userName">
      <template #content>
        <el-space alignment="flex-start" style="display: flex; justify-content: space-between; margin: 15px;">
          <el-space direction="vertical">
            <el-space>
              <el-icon style="font-size: 1.5em">
                <DataLine/>
              </el-icon>
              <span>
                <span class="card-font">Rating:</span>
                <span :style="userNameFontColor[userRateLevel]" class="card-font-bold">{{ userRating }}</span>
              </span>
            </el-space>

          </el-space>
          <el-space direction="vertical">
            <el-image v-if="showAvatar" style="height: 80px" :src="getUserImg()"/>
            <div v-else style="height: 80px"></div>
            <span :style="userNameFontColor[userRateLevel]"
                  :class="userRateLevel === 8 ? 'black-first-letter' : ''"
                  class="card-font-bold">{{ userName }}</span>
          </el-space>
        </el-space>
      </template>
    </CommonCard>
  </div>
</template>

<script setup lang="ts">
import CommonCard from "@/components/CommonCard.vue";
import { userNameFontColor } from "@/common/constants";
import { ref } from "vue";
import { DataLine } from "@element-plus/icons-vue";

const showAvatar = ref(false);
const userName = ref("KyLen_Mou");
const userRateLevel = ref(5);
const userRating = ref(1917);
const getUserImg = () => {
  return new URL("../assets/img_1.png", import.meta.url).href;
}
onMounted(() => {
  setTimeout(() => {
    showAvatar.value = true;
  }, 700);
})
</script>

<style scoped>
#userCard {

}

.card-font {
  font-family: verdana, arial, sans-serif;
}

.card-font-bold {
  font-weight: 600;
  font-size: 1.1em;
}
</style>
