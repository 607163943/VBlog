import Vue from 'vue'
// 时间格式化(年月日)
Vue.filter('formatDate', (value) => {
  const date = new Date(value)
  const year = date.getFullYear()
  let month = date.getMonth() + 1
  let day = date.getDate()
  if (month < 10) {
    month = '0' + month
  }
  if (day < 10) {
    day = '0' + day
  }
  return year + '-' + month + '-' + day
})

// 时间格式化(年月日时分)
Vue.filter('formatDateTime', (value) => {
  const date = new Date(value)
  const year = date.getFullYear()
  let month = date.getMonth() + 1
  let day = date.getDate()
  const hours = date.getHours()
  const minutes = date.getMinutes()
  if (month < 10) {
    month = '0' + month
  }
  if (day < 10) {
    day = '0' + day
  }
  return year + '-' + month + '-' + day + ' ' + hours + ':' + minutes
})
