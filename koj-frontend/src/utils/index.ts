import dayjs from 'dayjs'
import relativeTime from 'dayjs/plugin/relativeTime'
import { shouldTransformRef } from 'vue/compiler-sfc'

const getDiffcultyColor = (difficulty: number): string => {
  if (difficultyColorMap[difficulty]) {
    return difficultyColorMap[difficulty]
  } else if (difficulty < 800) {
    return '#a0a0a0'
  } else if (difficulty > 3000) {
    return '#000000'
  } else {
    return '#a0a0a0'
  }
}

const getDiffcultyTagColor = (difficulty: number): string => {
  // 先检查是否在map中
  if (difficultyTagColorMap[difficulty]) {
    return difficultyTagColorMap[difficulty]
  } else if (difficulty < 800) {
    return '#dfdfdf'
  } else if (difficulty > 3000) {
    return '#bfbfbf'
  } else {
    return '#dfdfdf'
  }
}
const difficultyColorMap: { [key: number]: string } = {
  800: '#a6a6a6',
  900: '#8fbc8f',
  1000: '#79d379',
  1100: '#62e962',
  1200: '#4cff4c',
  1300: '#4cffa6',
  1400: '#4cffff',
  1500: '#4ca6ff',
  1600: '#4c4cff',
  1700: '#884cff',
  1800: '#c34cff',
  1900: '#ff4cff',
  2000: '#ff88c3',
  2100: '#ff8888',
  2200: '#ff884c',
  2300: '#ff4c4c',
  2400: '#ff3333',
  2500: '#ff1919',
  2600: '#ff0000',
  2700: '#c00000',
  2800: '#800000',
  2900: '#400000',
  3000: '#000000'
}

const difficultyTagColorMap: { [key: number]: string } = {
  800: '#f2f2f2',
  900: '#eff5ef',
  1000: '#ebf9eb',
  1100: '#e8fce8',
  1200: '#e5ffe5',
  1300: '#e5fff2',
  1400: '#e5ffff',
  1500: '#e5f2ff',
  1600: '#e5e5ff',
  1700: '#eee5ff',
  1800: '#f6e5ff',
  1900: '#ffe5ff',
  2000: '#ffeef6',
  2100: '#ffeeee',
  2200: '#ffeee5',
  2300: '#ffe5e5',
  2400: '#ffe5e5',
  2500: '#ffe5e5',
  2600: '#ffe5e5',
  2700: '#f9e5e5',
  2800: '#f2e5e5',
  2900: '#ebe5e5',
  3000: '#e5e5e5'
}

const difficultyTagArray = [
  { difficulty: 800, color: '#a6a6a6' },
  { difficulty: 900, color: '#8fbc8f' },
  { difficulty: 1000, color: '#79d379' },
  { difficulty: 1100, color: '#62e962' },
  { difficulty: 1200, color: '#4cff4c' },
  { difficulty: 1300, color: '#4cffa6' },
  { difficulty: 1400, color: '#4cffff' },
  { difficulty: 1500, color: '#4ca6ff' },
  { difficulty: 1600, color: '#4c4cff' },
  { difficulty: 1700, color: '#884cff' },
  { difficulty: 1800, color: '#c34cff' },
  { difficulty: 1900, color: '#ff4cff' },
  { difficulty: 2000, color: '#ff88c3' },
  { difficulty: 2100, color: '#ff8888' },
  { difficulty: 2200, color: '#ff884c' },
  { difficulty: 2300, color: '#ff4c4c' },
  { difficulty: 2400, color: '#ff3333' },
  { difficulty: 2500, color: '#ff1919' },
  { difficulty: 2600, color: '#ff0000' },
  { difficulty: 2700, color: '#c00000' },
  { difficulty: 2800, color: '#800000' },
  { difficulty: 2900, color: '#400000' },
  { difficulty: 3000, color: '#000000' }
]

const getVerdictModel = (verdict: number) => {
  switch (verdict) {
    case 0:
      return undefined
    case 1:
      return {
        short: 'IQ',
        full: 'In Queue',
        zh: '正在排队',
        type: 'warning',
        description: '您的评测正在排队中',
        percentage: 20,
        color: '#1376FF'
      }
    case 100:
      return {
        short: 'Compiling',
        full: 'Compiling',
        zh: '正在编译',
        type: 'warning',
        description: '您的代码正在编译中',
        percentage: 40,
        color: '#FF8800'
      }
    case 101:
      return {
        short: 'CE',
        full: 'Compile Error',
        zh: '编译错误',
        type: 'warning',
        description: '您的程序未通过编译',
        percentage: 100,
        color: '#FF8800'
      }
    case 200:
      return {
        short: 'Running',
        full: 'Running',
        zh: '正在运行',
        type: 'info',
        description: '您的程序正在运行中',
        percentage: 60,
        color: '#5CB300'
      }
    case 201:
      return {
        short: 'Judging',
        full: 'Judging',
        zh: '正在评测',
        type: 'info',
        description: '您的程序正在评测中',
        percentage: 80,
        color: '#6E51E0'
      }
    case 300:
      return {
        short: 'AC',
        full: 'Accepted',
        zh: '答案正确',
        type: 'success',
        description: '您的程序通过了该测试用例',
        percentage: 100,
        color: '#5CB300'
      }
    case 301:
      return {
        short: 'PAC',
        full: 'Partially Accepted',
        zh: '部分正确',
        type: 'info',
        description: '您的程序通过了部分测试用例',
        percentage: 100,
        color: '#5CB300'
      }
    case 400:
      return {
        short: 'WA',
        full: 'Wrong Answer',
        zh: '答案错误',
        type: 'danger',
        description: '您的程序未通过该测试用例',
        percentage: 100,
        color: '#F23030'
      }
    case 401:
      return {
        short: 'RE',
        full: 'Runtime Error',
        zh: '运行时错误',
        type: 'danger',
        description: '您的程序在运行时发生错误',
        percentage: 100,
        color: '#F23030'
      }
    case 402:
      return {
        short: 'TLE',
        full: 'Time Limit Exceeded',
        zh: '时间超限',
        type: 'danger',
        description: '您的程序运行时间超过了限制',
        percentage: 100,
        color: '#F23030'
      }
    case 403:
      return {
        short: 'MLE',
        full: 'Memory Limit Exceeded',
        zh: '内存超限',
        type: 'danger',
        description: '您的程序使用的内存超过了限制',
        percentage: 100,
        color: '#F23030'
      }
    case 404:
      return {
        short: 'SLE',
        full: 'Stack Limit Exceeded',
        zh: '栈空间超限',
        type: 'danger',
        description: '您的程序使用的栈空间超过了限制',
        percentage: 100,
        color: '#F23030'
      }
    case 405:
      return {
        short: 'OLE',
        full: 'Output Limit Exceeded',
        zh: '输出超限',
        type: 'danger',
        description: '您的程序输出内容超过了限制',
        percentage: 100,
        color: '#F23030'
      }
    case 500:
      return {
        short: 'SE',
        full: 'System Error',
        zh: '系统错误',
        type: 'primary',
        description: '系统发生了错误，请联系管理员或稍后再试',
        percentage: 100,
        color: '#191919'
      }
    default:
      return undefined
  }
}

const getLanguageByShortName = (shortName: string | any) => {
  switch (shortName) {
    case 'c':
      return 'C'
    case 'cpp':
      return 'C++'
    case 'java':
      return 'Java'
    case 'python':
      return 'Python'
    default:
      return shortName
  }
}

const fromNow = (date: string) => {
  dayjs.extend(relativeTime)
  return dayjs(date).fromNow()
}

const formatTime = (date: string) => {
  return dayjs(date).format('YYYY-MM-DD HH:mm:ss')
}


const isSubmissionRunning = (verdict: number | any) => {
  return [1, 100, 200, 201].includes(verdict)
}
export {
  getDiffcultyColor,
  getDiffcultyTagColor,
  difficultyTagArray,
  getVerdictModel,
  getLanguageByShortName,
  fromNow,
  formatTime,
  isSubmissionRunning
}
