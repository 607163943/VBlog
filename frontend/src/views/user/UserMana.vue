<template>
  <div v-loading="loading">
    <div class="search-form">
      <el-form :inline="true" :model="searchForm">
        <el-form-item>
          <el-input
            placeholder="默认展示部分用户，可以通过用户名搜索用户..."
            prefix-icon="el-icon-search"
            v-model="searchForm.nickname"
            style="width: 400px"
            size="small"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            icon="el-icon-search"
            size="small"
            class="search-button"
            @click="searchClick"
            >搜索
          </el-button>
        </el-form-item>
      </el-form>
    </div>
    <div class="user-card-list">
      <el-card
        class="user-card"
        v-for="(user, index) in users"
        :key="user.id"
        v-loading="cardLoading[index]"
      >
        <div slot="header" class="user-card-header">
          <span>{{ user.nickname }}</span>
          <el-button
            class="card-header-button"
            type="text"
            icon="el-icon-delete"
            @click="deleteUser(user.id)"
            >删除
          </el-button>
        </div>
        <div>
          <el-avatar
            class="user-card-img"
            shape="square"
            size="large"
            src="https://cube.elemecdn.com/9/c2/f0ee8a3c7c9638a54940382568c9dpng.png"
          ></el-avatar>
          <div class="user-card-content-text-item">
            <span>用户名:</span><span>{{ user.username }}</span>
          </div>
          <div class="user-card-content-text-item">
            <span>电子邮箱:</span><span>{{ user.email }}</span>
          </div>
          <div class="user-card-content-text-item">
            <span>注册时间:</span
            ><span>{{ user.regTime | formatDateTime }}</span>
          </div>
          <div class="user-card-content-text-item user-card-content-status">
            <span>用户状态:</span>
            <el-switch
              v-model="user.enabled"
              active-text="启用"
              active-color="#13ce66"
              @change="enabledChange(user.enabled, user.id, index)"
              inactive-text="禁用"
              style="font-size: 12px"
            >
            </el-switch>
          </div>
          <div class="user-card-content-text-item">
            <span>用户角色:</span>
            <el-tag
              v-for="role in user.roles"
              :key="role.id"
              size="mini"
              style="margin-right: 8px"
              type="success"
            >
              {{ role.name }}
            </el-tag>
            <el-popover
              placement="right"
              title="角色列表"
              width="200"
              :key="index + '' + user.id"
              @hide="saveRoles(user.id, index)"
              trigger="click"
              v-loading="EPLoading[index]"
            >
              <el-select
                v-model="roles"
                :key="user.id"
                multiple
                placeholder="请选择"
                size="mini"
              >
                <el-option
                  v-for="item in allRoles"
                  :key="user.id + '-' + item.id"
                  :label="item.name"
                  :value="item.id"
                >
                </el-option>
              </el-select>
              <el-button
                type="text"
                icon="el-icon-more"
                style="padding-top: 0px"
                slot="reference"
                @click="showRole(user.roles, user.id, index)"
              ></el-button>
            </el-popover>
          </div>
        </div>
      </el-card>
    </div>
  </div>
</template>
<script>
import { getRequest, putRequest } from '../../api/api'
import { userSearchService, userDeleteByIdService, userUpdateStatusService } from '@/api/user'

export default {
  data () {
    return {
      loading: false,
      searchForm: {
        nickname: ''
      },
      EPLoading: [],
      cardLoading: [],
      users: [],
      allRoles: [],
      roles: [],
      cpRoles: []
    }
  },
  mounted () {
    this.loading = true
    this.loadUserList()
    this.cardLoading = Array.apply(null, Array(20)).map(() => false)
    this.EPLoading = Array.apply(null, Array(20)).map(() => false)
  },
  methods: {
    // 加载用户列表
    async loadUserList () {
      const res = await userSearchService(this.searchForm)
      this.loading = false
      if (res.status === 200) {
        this.users = res.data
      } else {
        this.$message({ type: 'error', message: '数据加载失败!' })
      }
    },
    // 删除用户
    async deleteUser (id) {
      await this.$confirm('删除该用户, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })

      this.loading = true
      const res = await userDeleteByIdService(id)
      if (res.data.code === 200) {
        this.$message({ type: 'success', message: '删除成功!' })
        this.loadUserList()
        return
      }
      this.loading = false
      this.$message({ type: 'error', message: '删除失败!' })
    },
    // 更新用户状态
    async enabledChange (enabled, id, index) {
      this.cardLoading.splice(index, 1, true)
      const res = await userUpdateStatusService({ enabled, id })
      if (res.data.code !== 200) {
        this.$message({ type: 'error', message: '更新失败!' })
        this.loadOneUserById(id, index)
        return
      }
      this.cardLoading.splice(index, 1, false)
      this.$message({ type: 'success', message: '更新成功!' })
    },
    saveRoles (id, index) {
      const selRoles = this.roles
      if (this.cpRoles.length === selRoles.length) {
        for (let i = 0; i < this.cpRoles.length; i++) {
          for (let j = 0; j < selRoles.length; j++) {
            if (this.cpRoles[i].id === selRoles[j]) {
              selRoles.splice(j, 1)
              break
            }
          }
        }
        if (selRoles.length === 0) {
          return
        }
      }
      this.cardLoading.splice(index, 1, true)
      putRequest('/admin/user/role', { rids: this.roles, id: id }).then(
        (resp) => {
          if (resp.status === 200 && resp.data.status === 'success') {
            this.$message({ type: resp.data.status, message: resp.data.msg })
            this.loadOneUserById(id, index)
          } else {
            this.cardLoading.splice(index, 1, false)
            this.$message({ type: 'error', message: '更新失败!' })
          }
        },
        (resp) => {
          this.cardLoading.splice(index, 1, false)
          if (resp.response.status === 403) {
            const data = resp.response.data
            this.$message({ type: 'error', message: data })
          }
        }
      )
    },
    showRole (aRoles, id, index) {
      this.cpRoles = aRoles
      this.roles = []
      this.loadRoles(index)
      for (let i = 0; i < aRoles.length; i++) {
        this.roles.push(aRoles[i].id)
      }
    },
    loadRoles (index) {
      this.EPLoading.splice(index, 1, true)
      getRequest('/admin/roles').then(
        (resp) => {
          this.EPLoading.splice(index, 1, false)
          if (resp.status === 200) {
            this.allRoles = resp.data
          } else {
            this.$message({ type: 'error', message: '数据加载失败!' })
          }
        },
        (resp) => {
          this.EPLoading.splice(index, 1, false)
          if (resp.response.status === 403) {
            const data = resp.response.data
            this.$message({ type: 'error', message: data })
          }
        }
      )
    },
    loadOneUserById (id, index) {
      getRequest('/admin/user/' + id).then(
        (resp) => {
          this.cardLoading.splice(index, 1, false)
          if (resp.status === 200) {
            this.users.splice(index, 1, resp.data)
          } else {
            this.$message({ type: 'error', message: '数据加载失败!' })
          }
        },
        (resp) => {
          this.cardLoading.splice(index, 1, false)
          if (resp.response.status === 403) {
            const data = resp.response.data
            this.$message({ type: 'error', message: data })
          }
        }
      )
    },
    searchClick () {
      this.loading = true
      this.loadUserList()
    }
  }
}
</script>
<style lang="less" scoped>
.search-form {
  margin-top: 10px;
  display: flex;
  justify-content: center;

  .search-button {
    margin-left: 3px;
  }
}

.user-card-list {
  display: flex;
  justify-content: space-around;
  flex-wrap: wrap;

  .user-card {
    width: 330px;
    margin-top: 10px;

    .user-card-header {
      text-align: left;

      .card-header-button {
        float: right;
        padding: 3px 0;
        color: #ff0509;
      }
    }

    .user-card-img {
      width: 70px;
      height: 70px;
    }
    .user-card-content-text-item {
      text-align: left;
      color: #20a0ff;
      font-size: 12px;
      margin-top: 13px;
    }

    .user-card-content-status {
      display: flex;
      align-items: center;
    }
  }
}
</style>
