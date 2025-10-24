<template>
  <div class="column-table">
    <el-table
      :data="columnTableData"
      tooltip-effect="dark"
      style="width: 100%"
      @selection-change="updateSelectionList"
      v-loading="tableLoading"
    >
      <el-table-column type="selection" width="55" align="left">
      </el-table-column>
      <el-table-column label="编号" prop="id" align="left"> </el-table-column>
      <el-table-column label="栏目名称" prop="columnName" align="left">
      </el-table-column>
      <el-table-column prop="date" label="启用时间" align="left">
        <template slot-scope="scope">{{
          scope.row.date | formatDate
        }}</template>
      </el-table-column>
      <el-table-column label="操作" align="left">
        <template slot-scope="scope">
          <el-button size="mini" @click="openEditDialog(scope.row)"
            >编辑
          </el-button>
          <el-button size="mini" type="danger" @click="handleDelete(scope.row)"
            >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <div class="column-page">
      <el-pagination
        background
        @size-change="changePageSize"
        @current-change="changePage"
        :current-page="searchPage.page"
        :page-sizes="[10, 20, 30, 40]"
        :page-size="searchPage.pageSize"
        :hide-on-single-page="total <= searchPage.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      >
      </el-pagination>
    </div>
  </div>
</template>

<script>
import { columnDeleteByIdsService } from '@/api/column'
import { mapState, mapMutations, mapActions } from 'vuex'
export default {
  computed: {
    ...mapState('column', ['columnTableData', 'tableLoading', 'searchPage', 'total', 'selectColumns'])
  },
  mounted () {
    this.searchColumn()
  },
  methods: {
    // 打开编辑专栏对话框
    openEditDialog (row) {
      this.$emit('edit', row)
    },
    // 更新表格复选框选中元素集合
    updateSelectionList (newList) {
      this.setSelectColumns(newList)
    },
    // 打开删除提醒
    async handleDelete (row) {
      await this.$confirm(`确认删除${row.columnName} ?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
      this.deleteColumn([row])
    },
    // 根据id集合删除专栏
    async deleteColumn (columns) {
      const ids = columns.map((column) => column.id).join(',')
      this.setTableLoading(true)
      try {
        const res = await columnDeleteByIdsService(ids)
        if (res.data.code === 200) {
          this.$message.success('删除成功')
          // 重新搜索也会更新选中专栏集合
          this.searchColumn()
        }
      } catch (error) {
        this.$message.error('删除失败')
      } finally {
        this.setTableLoading(false)
      }
    },
    // 调整页码
    changePageSize (newSize) {
      this.setSearchPageSize(newSize)
      this.searchColumn()
    },
    // 换页查询
    changePage (newPage) {
      this.setSearchPage(newPage)
      this.searchColumn()
    },
    // 搜索专栏
    async searchColumn () {
      this.setTableLoading(true)
      try {
        await this.pageQueryColumn()
      } finally {
        this.setTableLoading(false)
      }
    },
    ...mapMutations('column', ['setTableLoading', 'setSearchPageSize', 'setSearchPage', 'setSelectColumns']),
    ...mapActions('column', ['pageQueryColumn'])
  }
}
</script>

<style lang="less" scoped>
.column-table {
  padding-top: 10px;
  text-align: right;

  .column-page {
    margin-top: 20px;
  }

  ::v-deep(.el-table thead) {
    color: #606266;
  }

  ::v-deep(.el-table th.el-table__cell) {
    background-color: #f8f8f9;
  }
}
</style>
