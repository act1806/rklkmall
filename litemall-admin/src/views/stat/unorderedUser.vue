<template>
  <div class="app-container">

    <!-- 查询和其他操作 -->
    <div class="filter-container">
      <el-date-picker v-model="listQuery.beginTime" type="datetime" placeholder="起止日期" value-format="yyyy-MM-ddTHH:mm:ss" style="width: 200px;"/> -
      <el-date-picker v-model="listQuery.endTime" type="datetime" placeholder="起止日期" value-format="yyyy-MM-ddTHH:mm:ss" style="width: 200px;"/>
      <el-button v-permission="['GET /admin/order/list']" type="primary" icon="el-icon-search" @click="handleFilter">查找</el-button>
      <el-button :loading="downloadLoading" type="primary" icon="el-icon-download" @click="handleDownload">导出</el-button>
    </div>

    <!-- 查询结果 -->
    <el-table v-loading="listLoading" :data="list" element-loading-text="正在查询中。。。" border fit highlight-current-row>

      <el-table-column align="center" label="客户名称" prop="userName"/>

      <el-table-column align="center" label="上次订购时间" prop="addTime"/>

      <el-table-column align="center" label="上次订购金额" prop="actualPrice"/>

      <el-table-column align="center" min-width="100" label="订单编号" prop="orderSn"/>

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
import { detailOrder } from '@/api/order'
import { unorderedUser } from '@/api/stat'
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
      unorderedUser(this.listQuery).then(response => {
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
      unorderedUser(this.listQuery).then(response => {
        this.list = response.data.data.list
        this.total = response.data.data.total
        this.listLoading = false
      }).catch(() => {
        this.list = []
        this.total = 0
        this.listLoading = false
      })
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
