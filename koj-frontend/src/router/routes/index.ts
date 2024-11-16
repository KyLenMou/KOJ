import type { RouteRecordRaw } from 'vue-router';

const modules = import.meta.glob('./modules/*.ts', { eager: true });
const appRoutes: RouteRecordRaw[] = [];

interface Module {
  default?: RouteRecordRaw | RouteRecordRaw[];
}

Object.keys(modules).forEach((key) => {
  const module = modules[key] as Module;
  const defaultModule = module.default;
  if (!defaultModule) return;
  const moduleList = Array.isArray(defaultModule)
    ? [...defaultModule]
    : [defaultModule];
  appRoutes.push(...moduleList);
});

console.error(appRoutes)

export default appRoutes;
