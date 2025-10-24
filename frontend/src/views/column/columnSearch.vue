<template>
  <div class="column-header">
      <el-form :inline="true" :model="searchForm">
        <el-form-item label="专栏" prop="columnName">
          <el-input
            placeholder="请输入专栏名称"
            v-model="searchForm.columnName"
            style="width: 200px">
          </el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" style="margin-left: 10px"
            @click="search">搜索</el-button>
        </el-form-item>
      </el-form>
    </div>
</template>

<script>
import { mapMutations, mapActions } from 'vuex'
export default {
  data () {
    return {
      searchForm: { columnName: '' }
    }
  },
  methods: {
    // 搜索专栏
    // TODO 需要做防抖处理
    async search () {
      this.setSearchParams(this.searchForm)
      this.pageQueryColumn()
    },
    ...mapMutations('column', ['setSearchParams']),
    ...mapActions('column', ['pageQueryColumn'])
  }
}
</script>

<style lang="less" scoped>
.column-header {
  margin-top: 20px;
  padding-left: 5px;

  ::v-deep(.el-form-item__label) {
    font-weight: 700;
  }
}
</style>
