const LanguageList = [
  { name: 'C', value: 'c' },
  { name: 'C++', value: 'cpp' },
  { name: 'Java', value: 'java' },
  { name: 'Python', value: 'python' }
]
const VerdictList = [
    { name: 'Submitted', short: 'Submitted', verdict: 0 },
    { name: 'In Queue', short: 'In Queue', verdict: 1 },
    { name: 'Cancelled', short: 'Cancelled', verdict: 2 },
    { name: 'Submit Failed', short: 'Submit Failed', verdict: -1 },
    { name: 'Rejected', short: 'Rejected', verdict: -2 },
    { name: 'Compiling', short: 'Compiling', verdict: 100 },
    { name: 'Complie Error', short: 'CE', verdict: 101 },
    { name: 'Judging', short: 'Judging', verdict: 201 },
    { name: 'Accepted', short: 'AC', verdict: 300 },
    { name: 'Partially Accepted', short: 'PAC', verdict: 301 },
    { name: 'Wrong Answer', short: 'WA', verdict: 400 },
    { name: 'Runtime Error', short: 'RE', verdict: 401 },
    { name: 'Time Limit Exceeded', short: 'TLE', verdict: 402 },
    { name: 'Memory Limit Exceeded', short: 'MLE', verdict: 403 },
    { name: 'Stack Limit Exceeded', short: 'SLE', verdict: 404 },
    { name: 'System Error', short: 'SE', verdict: 500 }
]
const VerdictProgressColors = [
  { color: '#1376FF', percentage: 21 },
  { color: '#FF8800', percentage: 41 },
  { color: '#5CB300', percentage: 61 },
  { color: '#6E51E0', percentage: 81 },
  { color: '#191919', percentage: 101 }
]
export { LanguageList,VerdictList,VerdictProgressColors }
