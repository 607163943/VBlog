<template>
  <div class="column">
    <!-- 专栏搜索 -->
    <column-search></column-search>
    <div class="column-main">
      <div class="button-group">
        <el-button type="primary" plain @click="addColumn">新增专栏</el-button>
        <el-button
          type="danger"
          plain
          :disabled="selItems.length == 0"
          @click="deleteAll"
          v-if="columnTableData.length > 0"
          >批量删除
        </el-button>
      </div>
      <div class="column-table">
        <el-table
          ref="multipleTable"
          :data="columnTableData"
          tooltip-effect="dark"
          style="width: 100%"
          @selection-change="handleSelectionChange"
          v-loading="loading"
        >
          <el-table-column type="selection" width="55" align="left">
          </el-table-column>
          <el-table-column label="编号" prop="id" align="left">
          </el-table-column>
          <el-table-column label="栏目名称" prop="cateName" align="left">
          </el-table-column>
          <el-table-column prop="date" label="启用时间" align="left">
            <template slot-scope="scope">{{
              scope.row.date | formatDate
            }}</template>
          </el-table-column>
          <el-table-column label="操作" align="left">
            <template slot-scope="scope">
              <el-button
                size="mini"
                @click="handleEdit(scope.$index, scope.row)"
                >编辑
              </el-button>
              <el-button
                size="mini"
                type="danger"
                @click="handleDelete(scope.$index, scope.row)"
                >删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
    <!-- TODO 需要调整对话框样式 -->
    <el-dialog title="专栏" :visible.sync="dialogFormVisible" width="420px">
      <el-form :model="dialogForm" label-position="top">
        <el-form-item label="专栏" :label-width="formLabelWidth">
          <el-input v-model="dialogForm.columnName" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogFormVisible = false"
          >确 定</el-button
        >
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { postRequest, putRequest, deleteRequest } from '@/api/api'
import { mapState, mapMutations, mapActions } from 'vuex'
import columnSearch from './columnSearch.vue'

export default {
  name: 'ColumnCom',
  components: {
    columnSearch
  },
  data () {
    return {
      selItems: [],
      loading: false,
      dialogFormVisible: false,
      dialogForm: {
        columnName: ''
      },
      formLabelWidth: '120px'
    }
  },
  computed: {
    ...mapState('column', ['columnTableData'])
  },
  mounted () {
    this.searchColumn()
  },
  methods: {
    addColumn () {
      this.dialogFormVisible = true
    },
    // 添加专栏
    addNewCate () {
      this.loading = true
      const _this = this
      postRequest('/admin/column/', {
        cateName: this.columnForm.columnName
      }).then(
        (resp) => {
          if (resp.status === 200) {
            const json = resp.data
            _this.$message({ type: json.status, message: json.msg })
            _this.cateName = ''
            _this.searchColumn()
          }
          _this.loading = false
        },
        (resp) => {
          if (resp.response.status === 403) {
            _this.$message({
              type: 'error',
              message: resp.response.data
            })
          }
          _this.loading = false
        }
      )
    },
    // 批量删除
    deleteAll () {
      this.$confirm('确认删除这 ' + this.selItems.length + ' 条数据?', '提示', {
        type: 'warning',
        confirmButtonText: '确定',
        cancelButtonText: '取消'
      })
        .then(() => {
          const selItems = this.selItems
          let ids = ''
          for (let i = 0; i < this.selItems.length; i++) {
            ids += selItems[i].id + ','
          }
          this.deleteCate(ids.substring(0, ids.length - 1))
        })
        .catch(() => {
          // 取消
          this.loading = false
        })
    },
    // 更新表格复选框选中元素集合
    handleSelectionChange (val) {
      this.selItems = val
    },
    // 编辑专栏
    handleEdit (index, row) {
      this.$prompt('请输入新名称', '编辑', {
        confirmButtonText: '更新',
        inputValue: row.cateName,
        cancelButtonText: '取消'
      }).then(({ value }) => {
        // value就是输入值
        if (value == null || value.length === 0) {
          this.$message({
            type: 'info',
            message: '数据不能为空!'
          })
        } else {
          this.loading = true
          putRequest('/admin/column/', { id: row.id, cateName: value }).then(
            (resp) => {
              const json = resp.data
              this.$message({
                type: json.status,
                message: json.msg
              })
              this.searchColumn()
            },
            (resp) => {
              if (resp.response.status === 403) {
                this.$message({
                  type: 'error',
                  message: resp.response.data
                })
              }
              this.loading = false
            }
          )
        }
      })
    },
    handleDelete (index, row) {
      const _this = this
      this.$confirm('确认删除 ' + row.cateName + ' ?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          _this.deleteCate(row.id)
        })
        .catch(() => {
          // 取消
          _this.loading = false
        })
    },
    deleteCate (ids) {
      const _this = this
      this.loading = true
      // 删除
      deleteRequest('/admin/category/' + ids).then(
        (resp) => {
          const json = resp.data
          _this.$message({
            type: json.status,
            message: json.msg
          })
          _this.searchColumn()
        },
        (resp) => {
          _this.loading = false
          if (resp.response.status === 403) {
            _this.$message({
              type: 'error',
              message: resp.response.data
            })
          } else if (resp.response.status === 500) {
            _this.$message({
              type: 'error',
              message: '该栏目下尚有文章，删除失败!'
            })
          }
        }
      )
    },
    // 搜索专栏
    async searchColumn () {
      this.loading = true
      try {
        await this.pageQueryColumn()
      } finally {
        this.loading = false
      }
    },
    ...mapMutations('column', ['setColumnTableData']),
    ...mapActions('column', ['pageQueryColumn'])
  }
}
</script>
<style lang="less" scoped>
.column-main {
  display: flex;
  flex-direction: column;
  padding-left: 5px;

  .column-table {
    padding-top: 10px;

    ::v-deep(.el-table thead) {
      color: #606266;
    }

    ::v-deep(.el-table th.el-table__cell) {
      background-color: #f8f8f9;
    }
  }
}
</style>
