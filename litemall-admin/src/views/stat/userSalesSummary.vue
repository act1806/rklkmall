<template>
  <div class="app-container">

    <!-- 查询和其他操作 -->
    <div class="filter-container">
      <el-date-picker v-model="listQuery.beginTime" type="datetime" placeholder="起止日期" value-format="yyyy-MM-ddTHH:mm:ss" style="width: 200px;"/> -
      <el-date-picker v-model="listQuery.endTime" type="datetime" placeholder="起止日期" value-format="yyyy-MM-ddTHH:mm:ss" style="width: 200px;"/>
      <el-select v-model="listQuery.sailer" multiple style="width: 200px" placeholder="请选择业务员">
        <el-option v-for="(item) in orderSailer" :key="item" :label="item" :value="item"/>
      </el-select>
      <el-select v-model="listQuery.userName" multiple style="width: 200px" placeholder="请选择客户">
        <el-option v-for="(item) in orderUser" :key="item.id" :label="item.agentName" :value="item.agentName"/>
      </el-select>
      <el-button v-permission="['GET /admin/order/list']" type="primary" icon="el-icon-search" @click="handleFilter">查找</el-button>
      <el-button :loading="downloadLoading" type="primary" icon="el-icon-download" @click="handleDownload">导出</el-button>
    </div>

    <!-- 查询结果 -->
    <el-table v-loading="listLoading" :data="list" element-loading-text="正在查询中。。。" border fit highlight-current-row>

      <el-table-column align="center" label="客户名称" prop="agentName"/>

      <el-table-column align="center" label="销售经理" prop="sailer"/>

      <el-table-column align="center" label="销售数量" prop="number"/>

      <el-table-column align="center" label="销售金额" prop="price"/>

      <el-table-column align="center" label="退货数量" prop="returnNumber"/>

      <el-table-column align="center" label="退货金额" prop="returnPrice"/>

      <el-table-column align="center" label="合计金额" prop="price"/>

    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />

  </div>
</template>

<script>
import { detailOrder } from '@/api/order'
import { userSalesSummary, listOrderUser, listOrderSailer } from '@/api/stat'
import Pagination from '@/components/Pagination' // Secondary package based on el-pagination
import checkPermission from '@/utils/permission' // 权限判断函数

export default {
  name: 'Order',
  components: { Pagination },
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
      userSalesSummary(this.listQuery).then(response => {
        this.list = response.data.data.list
        this.total = response.data.data.total
        this.listLoading = false
      }).catch(() => {
        this.list = []
        this.total = 0
        this.listLoading = false
      })
      listOrderUser().then(response => {
        this.orderUser = response.data.data.list
      }).catch(() => {
        this.orderUser = []
      })
      listOrderSailer().then(response => {
        this.orderSailer = response.data.data.list
        console.log(this.orderSailer)
      }).catch(() => {
        this.orderSailer = []
      })
    },
    handleFilter() {
      this.listQuery.page = 1
      userSalesSummary(this.listQuery).then(response => {
        this.list = response.data.data.list
        this.total = response.data.data.total
        this.listLoading = false
      }).catch(() => {
        this.list = []
        this.total = 0
        this.listLoading = false
      })
    },
    handleDetail(row) {
      detailOrder(row.id).then(response => {
        this.orderDetail = response.data.data
      })
      this.orderDialogVisible = true
    },
    listDetail(row) {
      detailOrder(row.id).then(response => {
        this.orderDetail = response.data.data
      })
      this.orderDetailVisible = true
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
