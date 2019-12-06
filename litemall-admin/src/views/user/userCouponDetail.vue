<template>
  <div class="app-container">
    <el-row>
      <el-col :span="8">
        <el-table v-loading="listLoading" :data="listUser" element-loading-text="正在查询中。。。" border fit highlight-current-row>
          <el-table-column align="center" width="100px" label="用户ID" prop="id" sortable/>

          <el-table-column align="center" label="用户名" prop="username"/>

          <el-table-column align="center" label="微信号" prop="mobile"/>

        </el-table>

        <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />
      </el-col>
      <el-col :span="16">
        <el-table v-loading="listLoading" :data="list" element-loading-text="正在查询中。。。" border fit highlight-current-row>

          <el-table-column align="center" label="优惠券ID" prop="id" sortable/>

          <el-table-column align="center" label="优惠券名称" prop="name"/>

          <el-table-column align="center" label="介绍" prop="desc"/>

          <el-table-column align="center" label="最少件数" prop="min">
            <template slot-scope="scope">满{{ scope.row.min }}件可用</template>
          </el-table-column>

          <el-table-column align="center" label="赠送件数" prop="discount">
            <template slot-scope="scope">赠送{{ scope.row.discount }}件</template>
          </el-table-column>

          <el-table-column align="center" label="商品使用范围" prop="goodsType">
            <template slot-scope="scope">{{ scope.row.goodsType | formatGoodsType }}</template>
          </el-table-column>

        </el-table>

        <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { fetchList } from '@/api/user'
import { listCoupon, createCoupon, updateCoupon, deleteCoupon } from '@/api/coupon'
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
    }
  }
}
</script>
