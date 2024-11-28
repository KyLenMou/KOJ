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


export { getDiffcultyColor, getDiffcultyTagColor, difficultyTagArray }
