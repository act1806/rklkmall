<template>
  <div class="app-container">

    <!-- 查询和其他操作 -->
    <div class="filter-container">
      <el-date-picker v-model="listQuery.beginTime" type="datetime" placeholder="起止日期" value-format="yyyy-MM-dd HH:mm:ss" style="width: 200px;"/> -
      <el-date-picker v-model="listQuery.endTime" type="datetime" placeholder="起止日期" value-format="yyyy-MM-dd HH:mm:ss" style="width: 200px;"/>
      <el-select v-model="listQuery.orderStatusArray" multiple style="width: 300px" placeholder="订单状态">
        <el-option v-for="(key, value) in statusMap" :key="key" :label="key" :value="value"/>
      </el-select>
      <el-select v-model="listQuery.orderSailer" multiple style="width: 200px" placeholder="请选择业务员">
        <el-option v-for="(item) in orderSailer" :key="item" :label="item" :value="item"/>
      </el-select>
      <el-select v-model="listQuery.orderUser" multiple style="width: 200px" placeholder="请选择客户">
        <el-option v-for="(item) in orderUser" :key="item.id" :label="item.nickname" :value="item.nickname"/>
      </el-select>
      <el-button v-permission="['GET /admin/order/list']" type="primary" icon="el-icon-search" @click="handleFilter">查找</el-button>
      <el-button :loading="downloadLoading" type="primary" icon="el-icon-download" @click="handleDownload">导出</el-button>
    </div>

    <!-- 查询结果 -->
    <el-table v-loading="listLoading" :data="list" element-loading-text="正在查询中。。。" border fit highlight-current-row>

      <el-table-column align="center" min-width="100" label="订单编号" prop="orderSn"/>

      <el-table-column align="center" label="用户" prop="userName"/>

      <el-table-column align="center" label="订单状态" prop="orderStatus">
        <template slot-scope="scope">
          <el-tag>{{ scope.row.orderStatus | orderStatusFilter }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column align="center" label="订单金额" prop="orderPrice"/>

      <el-table-column align="center" label="下单时间" width="155" prop="addTime"/>

      <el-table-column align="center" label="销售员" prop="sailer"/>

      <el-table-column align="center" label="详情" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button v-permission="['GET /admin/order/detail']" type="primary" size="mini" @click="listDetail(scope.row)">查看</el-button>
        </template>
      </el-table-column>

    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />

    <!-- 订单详情对话框 -->
    <el-dialog :visible.sync="orderDetailVisible" title="订单详情" width="800">
      <section ref="print">
        <el-form :data="orderDetail" label-position="left">
          <el-form-item label="订单编号">
            <span>{{ orderDetail.order.orderSn }}</span>
          </el-form-item>
          <el-form-item label="订单状态">
            <el-tag>{{ orderDetail.order.orderStatus | orderStatusFilter }}</el-tag>
          </el-form-item>
          <el-form-item label="订单用户">
            <span>{{ orderDetail.user.nickname }}</span>
          </el-form-item>
          <el-form-item label="用户留言">
            <span>{{ orderDetail.order.message }}</span>
          </el-form-item>
          <el-form-item label="收货信息">
            <span>（收货人）{{ orderDetail.order.consignee }}</span>
            <span>（手机号）{{ orderDetail.order.mobile }}</span>
            <span>（地址）{{ orderDetail.order.address }}</span>
          </el-form-item>
          <el-form-item label="商品信息">
            <el-table :data="orderDetail.orderGoods" border fit highlight-current-row>
              <el-table-column align="center" label="商品名称" prop="goodsName" />
              <el-table-column align="center" label="商品编号" prop="goodsSn" />
              <el-table-column align="center" label="货品规格" prop="specifications" />
              <el-table-column align="center" label="货品价格" prop="price" />
              <el-table-column align="center" label="货品数量" prop="number" />
              <el-table-column align="center" label="货品图片" prop="picUrl">
                <template slot-scope="scope">
                  <img :src="scope.row.picUrl" width="40">
                </template>
              </el-table-column>
            </el-table>
          </el-form-item>
          <el-form-item label="费用信息">
            <span>
              (实际费用){{ orderDetail.order.orderPrice }}元
            </span>
          </el-form-item>
          <el-form-item label="快递信息">
            <span>（快递公司）{{ orderDetail.order.shipChannel }}</span>
            <span>（快递单号）{{ orderDetail.order.shipSn }}</span>
            <span>（发货时间）{{ orderDetail.order.shipTime }}</span>
          </el-form-item>
          <el-form-item label="收货信息">
            <span>（确认收货时间）{{ orderDetail.order.confirmTime }}</span>
          </el-form-item>
        </el-form>
      </section>
      <span slot="footer" class="dialog-footer">
        <el-button @click="orderDetailVisible = false">取 消</el-button>
        <el-button type="primary" @click="printOrder">打 印</el-button>
      </span>
    </el-dialog>

  </div>
</template>

<script>
import { detailOrder, listOrder, listOrderUser, listOrderSailer } from '@/api/order'
import Pagination from '@/components/Pagination' // Secondary package based on el-pagination
import checkPermission from '@/utils/permission' // 权限判断函数

const statusMap = {
  101: '待销售经理确认',
  201: '待市场部确认',
  301: '待发货',
  302: '已发货',
  401: '已收货',
  402: '已收货(系统)'
}

export default {
  name: 'Order',
  components: { Pagination },
  filters: {
    orderStatusFilter(status) {
      return statusMap[status]
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
        orderStatusArray: ['401', '402'],
        sort: 'add_time',
        order: 'desc'
      },
      statusMap,
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
      listOrder(this.listQuery).then(response => {
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
      this.getList()
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
