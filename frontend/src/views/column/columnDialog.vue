<template>
  <el-dialog
      :title="dialogMode==='add'?'添加专栏':'编辑专栏'"
      :visible.sync="dialogVisible"
      width="420px"
      top="25vh"
    >
      <el-form ref="dialogForm" :model="dialogForm" :rules="rules" label-position="top">
        <el-form-item label="专栏" label-width="120px" prop="columnName">
          <el-input
            v-model="dialogForm.columnName"
            autocomplete="off"
          ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveOrEdit">{{ dialogMode==='add'?'添加':'保存' }}</el-button>
      </div>
    </el-dialog>
</template>

<script>
import { columnAddService, columnGetByIdService, columnUpdateService } from '@/api/column'
import { mapActions, mapMutations } from 'vuex'
export default {
  name: 'ColumnDialog',
  data () {
    return {
      dialogVisible: false,
      dialogMode: 'add',
      dialogForm: {
        id: 0,
        columnName: ''
      },
      rules: {
        columnName: [
          { required: true, message: '专栏名不能为空', trigger: 'blur' },
          { min: 3, max: 15, message: '专栏名长度在 3 到 15 个字符', trigger: 'blur' },
          { pattern: /^[\u4e00-\u9fa5_a-zA-Z0-9]+$/, message: '专栏名只能是中文、英文、数字、下划线' }
        ]
      },
      formLabelWidth: '120px'
    }
  },
  methods: {
    // 打开对话框
    open (obj) {
      if (obj.id) {
        this.dialogMode = 'edit'
        this.dialogForm = obj
        this.getById()
      } else {
        this.dialogMode = 'add'
      }
      this.dialogVisible = true
    },
    // 根据id获取专栏
    async getById () {
      try {
        const res = await columnGetByIdService(this.dialogForm.id)
        if (res.data.code === 200) {
          this.dialogForm = res.data.data
        }
      } catch (error) {
        this.$message.error('专栏查询失败')
      }
    },
    // 保存或修改专栏
    async saveOrEdit () {
      try {
        await this.$refs.dialogForm.validate()
      } catch (error) {
        return
      }

      if (this.dialogMode === 'add') {
        this.add()
      } else {
        this.save()
      }
    },
    // 添加专栏
    async add () {
      try {
        const res = await columnAddService(this.dialogForm)
        if (res.data.code === 200) {
          this.$message.success('添加成功')
          this.dialogVisible = false

          this.searchColumn()
        }
      } catch (error) {
        this.$message.error('添加失败')
      }
    },
    // 保存专栏
    async save () {
      try {
        const res = await columnUpdateService(this.dialogForm)
        if (res.data.code === 200) {
          this.$message.success('保存成功')
          this.dialogVisible = false

          this.searchColumn()
        }
      } catch (error) {
        this.$message.error('保存失败')
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
::v-deep(.el-form--label-top .el-form-item__label) {
  padding: 0;
}
</style>
