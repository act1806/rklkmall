<template>
  <div class="app-container">

    <!-- 查询和其他操作 -->
    <div class="filter-container">
      <el-input v-model="listQuery.username" clearable class="filter-item" style="width: 200px;" placeholder="请输入用户名"/>
      <el-input v-model="listQuery.mobile" clearable class="filter-item" style="width: 200px;" placeholder="请输入手机号"/>
      <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">查找</el-button>
      <el-button :loading="downloadLoading" class="filter-item" type="primary" icon="el-icon-download" @click="handleDownload">导出</el-button>
      <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleModify">修改</el-button>
    </div>

    <!-- 查询结果 -->
    <el-table v-loading="listLoading" :data="list" element-loading-text="正在查询中。。。" border fit highlight-current-row @row-click="openDetails">
      <el-table-column align="center" width="100px" label="用户ID" prop="id" sortable/>

      <el-table-column align="center" label="客户名称" prop="agentName"/>

      <el-table-column align="center" label="微信名" prop="nickname"/>

      <el-table-column align="center" label="手机号码" prop="mobile"/>

      <el-table-column align="center" label="性别" prop="gender">
        <template slot-scope="scope">
          <el-tag >{{ genderDic[scope.row.gender] }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column align="center" label="余额" prop="amount" />

      <el-table-column align="center" label="状态" prop="status">
        <template slot-scope="scope">
          <el-tag>{{ statusDic[scope.row.status] }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column align="center" label="消费等级" prop="level" >
        <template slot-scope="scope">
          <el-tag >{{ levelDic[scope.row.level] }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column align="center" label="付款状态" prop="paid" >
        <template slot-scope="scope">
          <el-tag >{{ paidDic[scope.row.paid] }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column align="center" label="销售员" prop="sailer"/>

    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />

    <el-dialog :visible.sync="dialogVisible" title="用户信息">
      <el-button class="filter-item" type="primary" @click="handleConfirm">确定</el-button>
      <el-button class="filter-item" type="primary" @click="dialogVisible = false">取消</el-button>
      <el-form >
        <el-form-item label="客户名称">
          <el-input v-model="userItem.agentName"/>
        </el-form-item>
        <el-form-item label="余额">
          <el-input v-model="userItem.amount" />
        </el-form-item>
        <el-form-item label="消费等级 0-不可下单 1-可下单">
          <el-input v-model="userItem.level" />
        </el-form-item>
        <el-form-item label="付款状态 0-未付款 1-已付款">
          <el-input v-model="userItem.paid" />
        </el-form-item>
        <el-form-item label="销售员">
          <el-input v-model="userItem.sailer" />
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import { fetchList, updateAmountLevel } from '@/api/user'
import Pagination from '@/components/Pagination' // Secondary package based on el-pagination
import { MessageBox } from 'element-ui'

export default {
  name: 'User',
  components: { Pagination },
  data() {
    return {
      list: null,
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 20,
        username: undefined,
        mobile: undefined,
        sort: 'add_time',
        order: 'desc'
      },
      downloadLoading: false,
      genderDic: ['未知', '男', '女'],
      levelDic: ['不可下单', '可下单'],
      statusDic: ['可用', '禁用', '注销'],
      paidDic: ['未付款', '已付款'],
      thisSelectedRow: undefined,
      dialogVisible: false,
      userItem: {
        id: undefined,
        username: '',
        amount: '',
        level: '',
        sailer: ''
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      fetchList(this.listQuery).then(response => {
        this.list = response.data.data.list
        this.total = response.data.data.total
        this.listLoading = false
      }).catch(() => {
        this.list = []
        this.total = 0
        this.listLoading = false
      })
    },
    handleFilter() {
      this.listQuery.page = 1
      this.getList()
    },
    handleDownload() {
      this.downloadLoading = true
      import('@/vendor/Export2Excel').then(excel => {
        const tHeader = ['用户名', '手机号码', '性别', '生日', '状态']
        const filterVal = ['username', 'mobile', 'gender', 'birthday', 'status']
        excel.export_json_to_excel2(tHeader, this.list, filterVal, '用户信息')
        this.downloadLoading = false
      })
    },
    // 打开详情页弹窗
    openDetails(val) {
      console.log(val)
      this.thisSelectedRow = val
    },
    handleModify() {
      console.log(this.thisSelectedRow)
      this.userItem = this.thisSelectedRow
      this.dialogVisible = true
    },
    handleConfirm() {
      updateAmountLevel(this.userItem)
        .then(response => {
          console.log(response)
          this.$notify.success({
            title: '成功',
            message: '修改成功'
          })
          this.dialogVisible = false
        })
        .catch(response => {
          MessageBox.alert('业务错误：' + response.data.errmsg, '警告', {
            confirmButtonText: '确定',
            type: 'error'
          })
        })
    }
  }
}
</script>
