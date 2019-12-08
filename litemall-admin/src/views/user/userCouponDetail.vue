<template>
  <div class="app-container">
    <!-- 查询和其他操作 -->
    <div class="filter-container">
      <el-input v-model="listQuery.username" clearable class="filter-item" style="width: 200px;" placeholder="请输入用户名"/>
      <el-input v-model="listQuery.mobile" clearable class="filter-item" style="width: 200px;" placeholder="请输入手机号"/>
      <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">查找</el-button>
      <el-button :loading="downloadLoading" class="filter-item" type="primary" icon="el-icon-check" @click="handleSave">保存</el-button>
    </div>
    <el-row>
      <el-col :span="8">
        <el-table v-loading="listLoading" :data="listUser" element-loading-text="正在查询中。。。" border fit highlight-current-row @row-click="clickUser">
          <el-table-column align="center" width="100px" label="用户ID" prop="id" sortable/>

          <el-table-column align="center" label="用户名" prop="username"/>

          <el-table-column align="center" label="微信号" prop="nickname"/>

        </el-table>

        <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />
      </el-col>
      <el-col :span="16" style="padding:0 0 0 40px">
        <el-table v-loading="listLoading" ref="checkTable" :data="list" element-loading-text="正在查询中。。。" border fit highlight-current-row>

          <el-table-column type="selection" width="55"/>

          <el-table-column align="center" label="活动ID" prop="id" sortable/>

          <el-table-column align="center" label="活动名称" prop="name" sortable/>

          <el-table-column align="center" label="介绍" prop="desc" sortable/>

          <el-table-column align="center" label="最少件数" prop="min">
            <template slot-scope="scope">满{{ scope.row.min }}件可用</template>
          </el-table-column>

          <el-table-column align="center" label="赠送件数" prop="discount">
            <template slot-scope="scope">赠送{{ scope.row.discount }}件</template>
          </el-table-column>

          <el-table-column align="center" label="商品ID" prop="goodsType" sortable>
            <template slot-scope="scope">{{ scope.row.total }}</template>
          </el-table-column>

        </el-table>

        <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { fetchList } from '@/api/user'
import { listCoupon, createCoupon, updateCoupon, deleteCoupon, listCouponUser, saveCouponUser } from '@/api/coupon'
import Pagination from '@/components/Pagination' // Secondary package based on el-pagination

export default {
  name: 'User',
  components: { Pagination },
  data() {
    return {
      listUser: null,
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
      userQuery: {
        page: 1,
        limit: 20,
        couponId: undefined,
        userId: undefined,
        status: undefined,
        sort: 'add_time',
        order: 'desc'
      },
      downloadLoading: false,
      genderDic: ['未知', '男', '女'],
      levelDic: ['普通用户', 'VIP用户', '高级VIP用户'],
      statusDic: ['可用', '禁用', '注销']
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      fetchList(this.listQuery).then(response => {
        this.listUser = response.data.data.list
        this.total = response.data.data.total
        this.listLoading = false
      }).catch(() => {
        this.listUser = []
        this.total = 0
        this.listLoading = false
      }),
      listCoupon(this.listQuery).then(response => {
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
    // 处理表格数据,选择用户优惠券
    clickUser(row) {
      this.$refs.checkTable.clearSelection()
      this.userQuery.userId = row.id
      listCouponUser(this.userQuery).then(response => {
        const couponIds = []
        response.data.data.list.forEach(item => {
          couponIds.push(item.couponId)
        })
        const table = this.list // 从后台获取到的数据
        table.forEach(item => {
          if (couponIds.indexOf(item.id) > -1) {
            // toggleRowSelection可以切换表格行的选中状态,接收两个参数第一个是要切换的行,这里需要的对象格式,
            // 第二个参数是设置选中还是不选中,不传的话会取与原来相反的选择状态
            // 方法调用要等元素挂载才后才能执行,这里加一个判断,这个方法要放在数据获取后
            this.$refs.checkTable && this.$refs.checkTable.toggleRowSelection(item, true)
          }
        })
      })
    },
    handleSave() {
      const rows = this.$refs.checkTable.selection
      saveCouponUser(this.userQuery.userId, rows).then(response => {
        this.$notify.success({
          title: '成功',
          message: '保存成功'
        })
      }).catch(response => {
        this.$notify.error({
          title: '失败',
          message: response.data.errmsg
        })
      })
    }
  }
}
</script>
