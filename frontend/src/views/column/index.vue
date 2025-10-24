<template>
  <div class="column">
    <!-- 专栏搜索 -->
    <column-search></column-search>
    <div class="column-main">
      <div class="button-group">
        <el-button type="primary" plain @click="openAddDialog"
          >新增专栏</el-button
        >
        <el-button
          type="danger"
          plain
          :disabled="selectColumns.length == 0"
          @click="deleteAll"
          v-if="columnTableData.length > 0"
          >批量删除
        </el-button>
      </div>
      <column-table @edit="openEditDialog"></column-table>
    </div>
    <column-dialog ref="dialog"></column-dialog>
  </div>
</template>
<script>
import { columnDeleteByIdsService } from '@/api/column'
import { mapState, mapMutations, mapActions } from 'vuex'
import columnSearch from './columnSearch.vue'
import columnDialog from './columnDialog.vue'
import columnTable from './columnTable.vue'

export default {
  name: 'ColumnCom',
  components: {
    columnSearch,
    columnDialog,
    columnTable
  },
  computed: {
    ...mapState('column', ['selectColumns', 'columnTableData'])
  },
  mounted () {
    this.searchColumn()
  },
  methods: {
    // 打开添加专栏对话框
    openAddDialog () {
      this.$refs.dialog.open({})
    },
    // 打开编辑专栏对话框
    openEditDialog (row) {
      this.$refs.dialog.open(row)
    },
    // 批量删除
    async deleteAll () {
      await this.$confirm(
        `确认删除这${this.selectColumns.length}条数据?`,
        '提示',
        {
          type: 'warning',
          confirmButtonText: '确定',
          cancelButtonText: '取消'
        }
      )

      this.deleteColumn(this.selectColumns)
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
    // 搜索专栏
    async searchColumn () {
      this.setTableLoading(true)
      try {
        await this.pageQueryColumn()
      } finally {
        this.setTableLoading(false)
      }
    },
    ...mapMutations('column', ['setTableLoading']),
    ...mapActions('column', ['pageQueryColumn'])
  }
}
</script>
<style lang="less" scoped>
.column-main {
  display: flex;
  flex-direction: column;
  padding-left: 5px;
}

::v-deep(.el-dialog__body) {
  padding-top: 0;
}
</style>
