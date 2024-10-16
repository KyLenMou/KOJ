<template>
  <div id="verdictText" :style="style">
    {{ text }}
  </div>
</template>

<script setup lang="ts">
import { defineProps, toRaw, withDefaults } from "vue";
import { getVerdictColorAndText } from "@/common/constants";

interface Props {
  verdict: number
}

const props = withDefaults(defineProps<Props>(), {
  verdict: 0
});

const { style, text } = getVerdictColorAndText(props.verdict);

console.log(style, text);
</script>

<style scoped>
#verdictText {
}
</style>
