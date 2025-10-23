<template>
  <div class="chart-wrap">
    <VChart
      ref="dschart"
      :option="option"
      theme="dark"
      autoresize
      style="height:500px; width:100%; margin-top:20px;"
    />
  </div>
</template>

<style scoped>
.chart-wrap {
  display: flex;
  height: 500px;
  width: 100%;
  align-items: center;
  justify-content: center;
}
</style>
<script>
import { articleGetChartDataService } from '@/api/article'

export default {
  name: 'DataStatisticsChart',
  data () {
    return {
      option: {
        title: { text: '' },
        toolbox: {
          show: true,
          feature: {
            dataZoom: { yAxisIndex: 'none' },
            dataView: { readOnly: false },
            magicType: { type: ['line', 'bar'] },
            restore: {},
            saveAsImage: {}
          }
        },
        tooltip: {},
        legend: { data: ['pv'] },
        grid: { containLabel: true }, // 建议补上 grid，避免边界溢出
        xAxis: { type: 'category', data: [] },
        yAxis: { type: 'value' },
        series: [
          { name: 'pv', type: 'line', data: [] }
        ],
        animationDuration: 3000
      }
    }
  },
  created () {
    console.log('测试')
    articleGetChartDataService('/article/dataStatistics')
      .then((res) => {
        if (res.status === 200) {
          // 更新数据
          this.option.xAxis.data = res.data.data.categories
          this.option.series[0].data = res.data.data.dataStatistics

          // 触发 VChart 侦听：替换引用可确保 setOption 生效
          this.option = { ...this.option }
        } else {
          this.$message({ type: 'error', message: '数据加载失败!' })
        }
      })
      .catch(() => {
        this.$message({ type: 'error', message: '数据加载失败!' })
      })
  }
}
</script>
