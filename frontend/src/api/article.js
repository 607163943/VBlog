import request from '@/utils/request'

export const articleGetChartDataService = () => {
  return request.get('/article/dataStatistics')
}
