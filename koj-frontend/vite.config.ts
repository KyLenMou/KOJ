import { fileURLToPath, URL } from 'node:url'
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueJsx from '@vitejs/plugin-vue-jsx'


// 引入自动导入 api
import AutoImport from 'unplugin-auto-import/vite'
// 引入配置需要自动导入的组件
import Components from 'unplugin-vue-components/vite'
import { ElementPlusResolver } from 'unplugin-vue-components/resolvers'

// https://vitejs.dev/config/
export default defineConfig({
  css: {
    preprocessorOptions: {
      scss: {
        // 自动导入定制化样式文件进行样式覆盖
        additionalData: `
            @use "@/styles/element/index.scss" as *;
          `,
      }
    }
  },
  plugins: [
    vue(),
    vueJsx(),
    // 配置需要自动导入的组件
    AutoImport({
      // 配置需要自动导入的模块
      imports: ['vue', 'vue-router'],
      resolvers: [ElementPlusResolver()],
      dts: 'src/types/auto-import.d.ts',
    }),
    Components({
      // 导入存放的位置
      dts: 'src/types/components.d.ts',
      resolvers: [ElementPlusResolver({ importStyle: 'sass' })],

    }),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  server:{
    // host: '127.0.0.1',
    host: '0.0.0.0',
    proxy: {
      '/api': {
        target: 'http://127.0.0.1:8090',
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, '')
      }
    }
  },
})
