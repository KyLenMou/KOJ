import type { VNode } from "vue";
import { storeToRefs } from "pinia";
import { useCurrentUserStore } from "@/stores/currentUser";


export const getColumnNames = (obj: object): string[] =>{
    return Object.keys(obj)
}

export const formatTimeColumn = (row: any, column: any, cellValue: any, index: number) => {
    if (column.property === 'createTime' || column.property === 'updateTime') {
        return new Date(cellValue).toLocaleString();
    } else {
        return row[column.property];
    }
};

export const isLogin = () => {
    const { currentUser } = useCurrentUserStore();
    return !!currentUser?.id;
}
