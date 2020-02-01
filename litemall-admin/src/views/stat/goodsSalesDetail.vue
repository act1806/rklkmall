<template>
  <div class="app-container">

    <!-- 查询和其他操作 -->
    <div class="filter-container">
      <el-date-picker v-model="listQuery.beginTime" type="datetime" placeholder="起止日期" value-format="yyyy-MM-ddTHH:mm:ss" style="width: 200px;"/> -
      <el-date-picker v-model="listQuery.endTime" type="datetime" placeholder="起止日期" value-format="yyyy-MM-ddTHH:mm:ss" style="width: 200px;"/>
      <el-select v-model="listQuery.goodsName" multiple style="width: 800px" placeholder="请选择货品">
        <el-option v-for="(item) in orderGoods" :key="item.id" :label="item.name" :value="item.name"/>
      </el-select>
      <el-button v-permission="['GET /admin/order/list']" type="primary" icon="el-icon-search" @click="handleFilter">查找</el-button>
      <el-button :loading="downloadLoading" type="primary" icon="el-icon-download" @click="handleDownload">导出</el-button>
    </div>

    <!-- 查询结果 -->
    <el-table v-loading="listLoading" :data="list" element-loading-text="正在查询中。。。" border fit highlight-current-row>

      <el-table-column align="center" label="货品名称" prop="goodsName"/>

      <el-table-column align="center" label="货品规格">
        <template slot-scope="scope">
          20个/盒
        </template>
      </el-table-column>

      <el-table-column align="center" label="销售数量" prop="number"/>

      <el-table-column align="center" label="单价" prop="price"/>

      <el-table-column align="center" label="销售金额">
        <template slot-scope="scope">
          {{ scope.row.number*scope.row.price }}
        </template>
      </el-table-column>

      <el-table-column align="center" label="客户名称" prop="agentName"/>

      <el-table-column align="center" label="出货方式">
        <template slot-scope="scope">
          销售出货
        </template>
      </el-table-column>

      <el-table-column align="center" label="订单时间" prop="addTime">
        <template slot-scope="scope">
          {{ scope.row.addTime | timeFilter }}
        </template>
      </el-table-column>

      <el-table-column align="center" label="销售经理" prop="sailer"/>

      <el-table-column align="center" label="订单号" prop="orderId"/>

    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />

  </div>
</template>

<script>
import { goodsSalesDetail } from '@/api/stat'
import { listGoods } from '@/api/goods'
import Pagination from '@/components/Pagination' // Secondary package based on el-pagination
import checkPermission from '@/utils/permission' // 权限判断函数

export default {
  name: 'Order',
  components: { Pagination },
  filters: {
    timeFilter(date) {
      return date.replace('T', ' ').replace('.000+0000', '')
    }
  },
  data() {
    return {
      list: [],
      orderUser: [],
      orderSailer: [],
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 20,
        id: undefined,
        name: undefined,
        sort: 'add_time',
        order: 'desc'
      },
      orderDialogVisible: false,
      orderDetailVisible: false,
      orderDetail: {
        order: {},
        user: {},
        orderGoods: []
      },
      shipForm: {
        orderId: undefined,
        shipChannel: undefined,
        shipSn: undefined
      },
      shipDialogVisible: false,
      downloadLoading: false
    }
  },
  created() {
    this.getList()
  },
  methods: {
    checkPermission,
    getList() {
      this.listLoading = true
      goodsSalesDetail(this.listQuery).then(response => {
        this.list = response.data.data.list
        this.total = response.data.data.total
        this.listLoading = false
      }).catch(() => {
        this.list = []
        this.total = 0
        this.listLoading = false
      })
      listGoods(this.listQuery).then(response => {
        this.orderGoods = response.data.data.list
        this.total = response.data.data.total
        this.listLoading = false
      }).catch(() => {
        this.orderGoods = []
        this.total = 0
        this.listLoading = false
      })
    },
    handleFilter() {
      this.listQuery.page = 1
      goodsSalesDetail(this.listQuery).then(response => {
        this.list = response.data.data.list
        this.total = response.data.data.total
        this.listLoading = false
      }).catch(() => {
        this.list = []
        this.total = 0
        this.listLoading = false
      })
    },
    handleDownload() {
      this.downloadLoading = true
      import('@/vendor/Export2Excel').then(excel => {
        const tHeader = ['订单ID', '订单编号', '用户ID', '订单状态', '是否删除', '收货人', '收货联系电话', '收货地址']
        const filterVal = ['id', 'orderSn', 'userId', 'orderStatus', 'isDelete', 'consignee', 'mobile', 'address']
        excel.export_json_to_excel2(tHeader, this.list, filterVal, '订单信息')
        this.downloadLoading = false
      })
    },
    printOrder() {
      this.$print(this.$refs.print)
      this.orderDialogVisible = false
    }
  }
}
</script>
