<template>
  <div class="app-container">

    <!-- 查询和其他操作 -->
    <div class="filter-container">
      <el-input v-model="listQuery.userId" clearable class="filter-item" style="width: 200px;" placeholder="请输入用户ID"/>
      <el-input v-model="listQuery.orderSn" clearable class="filter-item" style="width: 200px;" placeholder="请输入订单编号"/>
      <el-button v-permission="['GET /admin/order/list']" class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">查找</el-button>
      <el-button :loading="downloadLoading" class="filter-item" type="primary" icon="el-icon-download" @click="handleDownload">导出</el-button>
    </div>

    <!-- 查询结果 -->
    <el-table v-loading="listLoading" :data="list" element-loading-text="正在查询中。。。" border fit highlight-current-row>

      <el-table-column align="center" min-width="100" label="订单编号" prop="orderSn"/>

      <el-table-column align="center" label="客户" prop="agentName"/>

      <el-table-column align="center" label="订单状态" prop="orderStatus">
        <template slot-scope="scope">
          <el-tag>{{ scope.row.orderStatus | orderStatusFilter }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column align="center" label="订单金额" prop="orderPrice"/>

      <el-table-column align="center" label="下单时间" width="155" prop="addTime"/>

      <el-table-column align="center" label="物流单号" prop="shipSn"/>

      <el-table-column align="center" label="物流渠道" prop="shipChannel"/>

      <el-table-column align="center" label="详情" width="160" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="printDetail(scope.row)">打印</el-button>
          <el-button v-permission="['GET /admin/order/detail']" type="primary" size="mini" @click="listDetail(scope.row)">查看</el-button>
        </template>
      </el-table-column>

      <el-table-column align="center" label="操作" width="200" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button v-if="scope.row.orderStatus==301" type="primary" size="mini" @click="handleShip(scope.row)">发货</el-button>
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

    <!-- 发货对话框 -->
    <el-dialog :visible.sync="shipDialogVisible" title="发货">
      <el-form ref="shipForm" :model="shipForm" status-icon label-position="left" label-width="100px" style="width: 400px; margin-left:50px;">
        <el-form-item label="快递公司" prop="shipChannel">
          <el-input v-model="shipForm.shipChannel"/>
        </el-form-item>
        <el-form-item label="快递编号" prop="shipSn">
          <el-input v-model="shipForm.shipSn"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="shipDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="confirmShip">确定</el-button>
      </div>
    </el-dialog>

    <!-- 订单打印对话框 -->
    <el-dialog :visible.sync="orderPrintDialogVisible" title="订单打印" width="80%">
      <section>
        <div ref="printBox" class="print-box">
          <div class="print-box-title">北京荣科利康科技有限公司</div>
          <div class="print-box-sub-title">销售出货单</div>
          <div class="print-box-form">
            <el-row>
              <el-col :span="8"><div class="text-md">单号: {{ orderDetail.order.orderSn }}</div></el-col>
              <el-col :span="8"><div class="text-md">原始单号: {{ orderDetail.order.orderOriSn }}</div></el-col>
              <el-col :span="8"><div class="text-md">客户: {{ orderDetail.user.agentName }}</div></el-col>
            </el-row>
            <el-row class="margin-divider">
              <el-col :span="8"><div class="text-md">制单日期: {{ orderDetail.order.addTime | formatDate }}</div></el-col>
              <el-col :span="16"><div class="text-md">出库仓: {{ orderDetail.order.outStock }}</div></el-col>
            </el-row>
            <el-row class="margin-divider">
              <el-col :span="24"><div class="text-md">备注: {{ orderDetail.order.couponName }};{{ orderDetail.order.message }}</div></el-col>
            </el-row>
          </div>
          <div class="print-box-table">
            <el-table
              :data="orderDetail.orderGoods"
              :header-cell-style="{
                'border-right': '1px black solid'
              }"
              border
              fit>
              <el-table-column align="center" label="序号" type="index" />
              <el-table-column align="center" label="货品简称" prop="goodsName" width="120">
                <template slot-scope="scope">
                  {{ scope.row.goodsName | extractEn }}
                </template>
              </el-table-column>
              <el-table-column align="center" label="货品名称" prop="goodsName" width="200">
                <template slot-scope="scope">
                  {{ scope.row.goodsName | extractCh }}
                </template>
              </el-table-column>
              <el-table-column align="center" label="货品规格" prop="specifications" width="120"/>
              <el-table-column align="center" label="数量" prop="number" width="120"/>
              <el-table-column align="center" label="单价" prop="price" width="120"/>
              <el-table-column align="center" label="折扣" prop="discount" width="120"/>
              <el-table-column align="center" label="金额" width="120">
                <template slot-scope="scope">
                  {{ scope.row.number * scope.row.price * scope.row.discount }}
                </template>
              </el-table-column>
              <el-table-column align="center" label="备注">
                <template slot-scope="scope">
                  {{ scope.row.couponName.substr(scope.row.couponName.indexOf("十送"), 3) }},送{{ scope.row.presentNumber }}盒,共{{ scope.row.presentNumber+scope.row.number }}盒
                </template>
              </el-table-column>
            </el-table>
          </div>
          <div class="print-box-amount">
            <el-row>
              <el-col :span="12"><div class="text-md">大写金额: 人民币{{ orderDetail.order.actualPrice | numToCh }}</div></el-col>
              <el-col :span="12"><div class="text-md">合      计:  {{ orderDetail.order.actualPrice | number }}</div></el-col>
            </el-row>
          </div>
          <div class="print-box-foot">
            <el-row>
              <el-col :span="6"><div class="text-md">制单人: {{ name }}</div></el-col>
              <el-col :span="6"><div class="text-md">经办人: {{ orderDetail.order.sailer }}</div></el-col>
              <el-col :span="6"><div class="text-md">打印日期: {{ currentDate | formatDateTime }}</div></el-col>
              <el-col :span="6"><div class="text-md">第 1 页 共 1 页</div></el-col>
            </el-row>
          </div>
        </div>
      </section>
      <span slot="footer" class="dialog-footer">
        <el-button @click="orderPrintDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="printOrder">打印</el-button>
      </span>
    </el-dialog>

  </div>
</template>

<script>
import { detailOrder, listOrder, confirmOrder, shipOrder, saveOrder } from '@/api/order'
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
    },
    formatDateTime(value) {
      const date = new Date(value)
      const y = date.getFullYear()
      let MM = date.getMonth() + 1
      MM = MM < 10 ? '0' + MM : MM
      let d = date.getDate()
      d = d < 10 ? '0' + d : d
      let h = date.getHours()
      h = h < 10 ? '0' + h : h
      let m = date.getMinutes()
      m = m < 10 ? '0' + m : m
      let s = date.getSeconds()
      s = s < 10 ? '0' + s : s
      return y + '/' + MM + '/' + d + ' ' + h + ':' + m + ':' + s
    },
    formatDate(value) {
      const date = new Date(value)
      const y = date.getFullYear()
      let MM = date.getMonth() + 1
      MM = MM < 10 ? '0' + MM : MM
      let d = date.getDate()
      d = d < 10 ? '0' + d : d
      return y + '/' + MM + '/' + d
    },
    extractEn(value) {
      return value.replace(/[^a-zA-Z]/g, '')
    },
    extractCh(value) {
      return value.replace(/[^\u4E00-\u9FA5]/g, '')
    },
    numToCh(str) {
      let num = parseFloat(str)
      let strOutput = ''
      let strUnit = '仟佰拾亿仟佰拾万仟佰拾元角分'
      num += '00'
      var intPos = num.indexOf('.')
      if (intPos >= 0) {
        num = num.substring(0, intPos) + num.substr(intPos + 1, 2)
      }
      strUnit = strUnit.substr(strUnit.length - num.length)
      for (let i = 0; i < num.length; i++) {
        strOutput += '零壹贰叁肆伍陆柒捌玖'.substr(num.substr(i, 1), 1) + strUnit.substr(i, 1)
      }
      return strOutput.replace(/零角零分$/, '整').replace(/零[仟佰拾]/g, '零').replace(/零{2,}/g, '零').replace(/零([亿|万])/g, '$1').replace(/零+元/, '元').replace(/亿零{0,3}万/, '亿').replace(/^元/, '零元')
    },
    number(value) {
      var toFixedNum = Number(value).toFixed(3)
      var realVal = toFixedNum.substring(0, toFixedNum.toString().length - 1)
      return realVal
    }
  },
  data() {
    return {
      list: [],
      total: 0,
      listLoading: true,
      orderPrintDialogVisible: false,
      listQuery: {
        page: 1,
        limit: 20,
        id: undefined,
        name: undefined,
        orderStatusArray: [301],
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
    printDetail(row) {
      detailOrder(row.id).then(response => {
        this.orderDetail = response.data.data
      })
      this.orderPrintDialogVisible = true
      this.currentDate = new Date()
    },
    handleShip(row) {
      this.shipForm.orderId = row.id
      this.shipForm.shipChannel = row.shipChannel
      this.shipForm.shipSn = row.shipSn

      this.shipDialogVisible = true
      this.$nextTick(() => {
        this.$refs['shipForm'].clearValidate()
      })
    },
    confirmShip() {
      this.$refs['shipForm'].validate((valid) => {
        if (valid) {
          shipOrder(this.shipForm).then(response => {
            this.shipDialogVisible = false
            this.$notify.success({
              title: '成功',
              message: '确认发货成功'
            })
            this.getList()
          }).catch(response => {
            this.$notify.error({
              title: '失败',
              message: response.data.errmsg
            })
          })
        }
      })
    },
    confirmOrder(data) {
      confirmOrder(data).then(response => {
        this.refundDialogVisible = false
        this.$notify.success({
          title: '成功',
          message: '订单提交成功'
        })
        this.getList()
      }).catch(response => {
        this.$notify.error({
          title: '失败',
          message: response.data.errmsg
        })
      })
    },
    saveOrder() {
      saveOrder(this.orderDetail).then(response => {
        this.orderDialogVisible = false
        this.$notify.success({
          title: '成功',
          message: '订单保存成功'
        })
        this.getList()
      }).catch(response => {
        this.$notify.error({
          title: '失败',
          message: response.data.errmsg
        })
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
