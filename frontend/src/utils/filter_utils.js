import Vue from 'vue'
Vue.filter('formatDate', function formatDate (value) {
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
Vue.filter('formatDateTime', function formatDateTime (value) {
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
