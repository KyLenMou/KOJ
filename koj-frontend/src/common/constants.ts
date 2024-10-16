export const userNameFontColor = [
  'color: #000000',
  'color: #808080',
  'color: #008000',
  'color: #03a89e',
  'color: #0000ff',
  'color: #aa00aa',
  'color: #ff8c00',
  'color: #ff0000',
  'color: #ff0000'
]

/**
  NULL = 0;

  IN_QUEUE = 1;
  SUBMIT_FAIL = -1;
  CANCELLED = 2;
  REJECTED = -2;

  COMPILING = 100;
  COMPILE_ERROR = 101;

  ACCEPTED = 200;
  PARTIALLY_ACCEPTED = 201;

  WRONG_ANSWER = 300;
  TIME_LIMIT_EXCEEDED = 301;
  MEMORY_LIMIT_EXCEEDED = 302;
  STACK_LIMIT_EXCEEDED = 303;
  OUTPUT_LIMIT_EXCEEDED = 304;
  RUNTIME_ERROR = 305;

  SYSTEM_ERROR = 400;
*/

const VerdictStyleMap = {
  'black': 'color: black',
  'blue': 'color: blue',
  'green': 'color: #0bae0b; font-weight: bold;',
  'gray': 'color: gray',
}

interface VerdictEntity {
  style: string
  text: string
}

const VerdictMap: Record<string, VerdictEntity> = {
  "0": {
    "style": VerdictStyleMap.black,
    "text": "Unknown"
  },
  "1": {
    "style": VerdictStyleMap.gray,
    "text": "In Queue"
  },
  "-1": {
    "style": VerdictStyleMap.black,
    "text": "Submit Fail"
  },
  "2": {
    "style": VerdictStyleMap.black,
    "text": "Cancelled"
  },
  "-2": {
    "style": VerdictStyleMap.black,
    "text": "Rejected"
  },
  "100": {
    "style": VerdictStyleMap.gray,
    "text": "Compiling"
  },
  "101": {
    "style": VerdictStyleMap.black,
    "text": "Compile Error"
  },
  "200": {
    "style": VerdictStyleMap.green,
    "text": "Accepted"
  },
  "201": {
    "style": VerdictStyleMap.blue,
    "text": "Partially Accepted"
  },
  "300": {
    "style": VerdictStyleMap.blue,
    "text": "Wrong Answer"
  },
  "301": {
    "style": VerdictStyleMap.blue,
    "text": "Time Limit Exceeded"
  },
  "302": {
    "style": VerdictStyleMap.blue,
    "text": "Memory Limit Exceeded"
  },
  "303": {
    "style": VerdictStyleMap.blue,
    "text": "Stack Limit Exceeded"
  },
  "304": {
    "style": VerdictStyleMap.blue,
    "text": "Output Limit Exceeded"
  },
  "305": {
    "style": VerdictStyleMap.blue,
    "text": "Runtime Error"
  },
  "400": {
    "style": VerdictStyleMap.black,
    "text": "System Error"
  }
}
export const getVerdictColorAndText = (verdict: number) => {
  if (verdict in VerdictMap) {
    return VerdictMap[verdict.toString()]
  } else {
    return {
      style: VerdictStyleMap.black,
      text: "Unknown"
    }
  }
}
