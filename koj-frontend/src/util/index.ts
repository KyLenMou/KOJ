import type { VNode } from "vue";

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
