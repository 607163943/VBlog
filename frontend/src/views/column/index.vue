<template>
  <div class="column">
    <div class="cate_mana_header">
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
    <div class="cate_mana_main">
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
        <el-table-column label="编号" prop="id" width="120" align="left">
        </el-table-column>
        <el-table-column
          label="栏目名称"
          prop="cateName"
          width="120"
          align="left"
        >
        </el-table-column>
        <el-table-column prop="date" label="启用时间" align="left">
          <template slot-scope="scope">{{
            scope.row.date | formatDate
          }}</template>
        </el-table-column>
        <el-table-column label="操作" align="left">
          <template slot-scope="scope">
            <el-button size="mini" @click="handleEdit(scope.$index, scope.row)"
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
      <el-button
        type="danger"
        :disabled="this.selItems.length == 0"
        style="margin-top: 10px; width: 100px"
        @click="deleteAll"
        v-if="this.categories.length > 0"
        >批量删除
      </el-button>
    </div>
  </div>
</template>
<script>
import {
  postRequest,
  putRequest,
  deleteRequest,
  getRequest
} from '../../api/api'
import { mapState, mapMutations } from 'vuex'

export default {
  name: 'ColumnCom',
  data () {
    return {
      searchForm: { columnName: '' },
      selItems: [],
      categories: [],
      loading: false
    }
  },
  computed: {
    ...mapState('column', ['columnTableData'])
  },
  mounted () {
    this.searchColumn()
  },
  methods: {
    // 添加专栏
    addNewCate () {
      this.loading = true
      const _this = this
      postRequest('/admin/column/', { cateName: this.columnForm.columnName }).then(
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
          for (let i = 0; i < selItems.length; i++) {
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
    searchColumn () {
      this.loading = true
      getRequest('/admin/column/all').then(
        (resp) => {
          this.setColumnTableData(resp.data)
          this.loading = false
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
    },
    ...mapMutations('column', ['setColumnTableData'])
  }
}
</script>
<style>
.cate_mana_header {
  background-color: #ececec;
  margin-top: 20px;
  padding-left: 5px;
  display: flex;
  justify-content: flex-start;
}

.cate_mana_main {
  /*justify-content: flex-start;*/
  display: flex;
  flex-direction: column;
  padding-left: 5px;
  background-color: #ececec;
  margin-top: 20px;
  padding-top: 10px;
}
</style>
