<template>
  <div class="app-container">
    <!-- 查询和其他操作 -->
    <div class="filter-container">
      <el-select v-model="listQuery.statusArray" multiple style="width: 200px" class="filter-item" placeholder="请选择检测状态">
        <el-option v-for="(key, value) in statusMap" :key="key" :label="key" :value="value"/>
      </el-select>
      <el-button v-permission="['GET /admin/thirdcheck/query']" class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">查找</el-button>
      <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleOpen">查看</el-button>
    </div>

    <!-- 查询结果 -->
    <el-table
      v-loading="listLoading"
      :data="list"
      :stripe="true"
      element-loading-text="正在查询中。。。"
      border
      fit
      highlight-current-row
      @row-click="rowSelcted"
      @row-dblclick="handledbClick"
    >
      <el-table-column type="index" width="50" />

      <el-table-column align="center" width="140px" label="状态" prop="status">
        <template slot-scope="scope">
          <el-tag>{{ statusDic[scope.row.status] }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column align="center" width="180px" label="检测单类型" prop="examine">
        <template slot-scope="scope">
          <el-tag>{{ examineDic[scope.row.examine] }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column align="center" width="180px" label="医院名称" prop="hospitalName" />

      <el-table-column align="center" width="180px" label="医师姓名" prop="doctorName" />

      <el-table-column align="center" width="180px" label="联系电话" prop="phone" />

      <el-table-column align="center" width="180px" label="取样地址" prop="sampleAddr" />

      <el-table-column align="center" width="140px" label="采样日期" prop="sampleDate" />

      <el-table-column align="center" width="140px" label="报告接收邮箱" prop="email" />

      <el-table-column align="center" label="患宠名字" prop="petName" />

      <el-table-column align="center" label="类别" prop="cls">
        <template slot-scope="scope">
          <el-tag>{{ clsDic[scope.row.cls] }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column align="center" label="性别" prop="gender">
        <template slot-scope="scope">
          <el-tag>{{ genderDic[scope.row.gender] }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column align="center" label="是否绝育" prop="isStered">
        <template slot-scope="scope">
          <el-tag>{{ yesornoDic[scope.row.isStered] }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column align="center" label="采样部位" prop="sampleArea" />

      <el-table-column align="center" label="质地" prop="texture">
        <template slot-scope="scope">
          <el-tag>{{ textureDic[scope.row.texture] }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column align="center" width="140px" label="原发肿物大小" prop="sampleSize" />

      <el-table-column align="center" width="140px" label="是否有包膜" prop="isDiolame">
        <template slot-scope="scope">
          <el-tag>{{ yesornoDic[scope.row.isDiolame] }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column align="center" width="140px" label="是否有破溃" prop="isRupture">
        <template slot-scope="scope">
          <el-tag>{{ yesornoDic[scope.row.isRupture] }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column align="center" width="140px" label="与周围组织粘连" prop="isStick">
        <template slot-scope="scope">
          <el-tag>{{ yesornoDic[scope.row.isStick] }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column align="center" label="检测项目" prop="exampleProject" />

      <el-table-column align="center" label="样本类型" prop="sampleProject" />

      <el-table-column align="center" prop="sampleFeature" />

      <el-table-column align="center" label="采样情况" prop="sampleCondition" />

      <el-table-column align="center" label="临床表现" prop="clinical" />

      <el-table-column align="center" label="既往病史与用药经历" prop="history" />

      <el-table-column align="center" label="快递公司" prop="expressCarrier" />

      <el-table-column align="center" label="快递单号" prop="expressNo" />

      <el-table-column align="center" label="操作" width="200" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button v-if="scope.row.status==0" type="primary" size="small" @click="handleOperation(scope.row)">开始检测</el-button>
          <el-button v-if="scope.row.status==1" type="primary" size="small" @click="handleOperation(scope.row)">检测完毕</el-button>
          <el-button v-if="scope.row.status==2" type="primary" size="small" @click="handleOperation(scope.row)">发送回执邮件</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="listQuery.page"
      :limit.sync="listQuery.limit"
      @pagination="getList"
    />

    <!-- Form -->

    <el-dialog :visible.sync="dialogVisible" title="详细信息">
      <el-form :model="checkForm">
        <h2 v-if="checkForm.examine === '0'" class="text-center">佰基检测送检单</h2>
        <h2 v-if="checkForm.examine === '1'" class="text-center">佰基病理检测送检单</h2>
        <el-row>
          <h4 class="text-center"> 基本信息（Basic Information）</h4>
          <el-col :span="8">
            <el-form-item label="医院名称">{{ checkForm.hospitalName }}</el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="医师姓名：">{{ checkForm.doctorName }}</el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="联系电话">{{ checkForm.phone }}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="取样地址：">{{ checkForm.sampleAddr }}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="采样日期：  ">{{ checkForm.sampleDate }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="报告接收邮箱：  ">{{ checkForm.email }}</el-form-item>
          </el-col>
        </el-row>

        <h4 class="text-center"> 患宠信息(pet information)：</h4>
        <el-row>
          <el-col :span="12">
            <el-form-item label="患宠名字：  ">{{ checkForm.petName }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="类别：  ">{{ clsDic[checkForm.cls] }}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="性别：  ">{{ genderDic[checkForm.gender] }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item v-if="checkForm.examine === '0'" label="是否绝育： ">{{ yesornoDic[checkForm.isStered] }}</el-form-item>
          </el-col>
        </el-row>

        <h4 class="text-center"> 样本信息(sample information)</h4>
        <el-row>
          <el-col :span="12">
            <el-form-item label="采样部位：  ">{{ checkForm.sampleArea }}</el-form-item>
          </el-col>
          <el-col v-if="checkForm.examine === '0'" :span="12">
            <el-form-item label="检测项目：  ">{{ checkForm.exampleProject }}</el-form-item>
          </el-col>
          <el-col v-if="checkForm.examine === '1'" :span="12">
            <el-form-item label="质地  ">{{ textureDic[checkForm.texture] }}</el-form-item>
          </el-col>
        </el-row>
        <el-row v-if="checkForm.examine === '1'">
          <el-col :span="12">
            <el-form-item label="原发肿物大小  ">{{ checkForm.sampleSize }}</el-form-item>
          </el-col>
          <el-col v-if="checkForm.examine === '1'" :span="12">
            <el-form-item label="是否有包膜：  ">{{ yesornoDic[checkForm.isDiolame] }}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col v-if="checkForm.examine === '1'" :span="12">
            <el-form-item label="是否有破溃：  ">{{ yesornoDic[checkForm.isRupture] }}</el-form-item>
          </el-col>
          <el-col v-if="checkForm.examine === '1'" :span="12">
            <el-form-item label="与周围组织粘连：  ">{{ yesornoDic[checkForm.isStick] }}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item v-if="checkForm.examine === '0'" label="样本类型： ">{{ checkForm.sampleProject }}</el-form-item>
            <el-form-item v-if="checkForm.examine === '1'" >{{ checkForm.sampleFeature }}</el-form-item>
          </el-col>
        </el-row>

        <h4 v-if="checkForm.examine === '1'" class="text-center"> 采样情况</h4>
        <el-row v-if="checkForm.examine === '1'">
          <el-col :span="24">
            <el-form-item >{{ checkForm.sampleCondition }}</el-form-item>
          </el-col>
        </el-row>

        <h4 class="text-center"> 临床表现(Clinical manifestation)</h4>
        <el-row>
          <el-col :span="24">
            <el-form-item >{{ checkForm.clinical }}</el-form-item>
          </el-col>
        </el-row>

        <h4 class="text-center"> 既往病史与用药经历(Medical history and medication experience)</h4>
        <el-row>
          <el-col :span="24">
            <el-form-item >{{ checkForm.history }}</el-form-item>
          </el-col>
        </el-row>
        <h4 class="text-center"> 图片列表(点击查看大图)</h4>
        <div class="demo-image__preview">
          <el-image
            :src="url"
            :preview-src-list="urlArr"
            style="width: 100px; height: 100px"
          />
        </div>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import { fetchList, operation } from '@/api/thirdcheck'
import Pagination from '@/components/Pagination' // Secondary package based on el-pagination

const statusMap = {
  '0': '已提交',
  '1': '已收到样本',
  '2': '样本检测中',
  '3': '样本检测完毕',
  '4': '检测结果已发送至预留邮箱'
}

export default {
  name: 'Thirdcheck',
  components: { Pagination },
  data() {
    return {
      listQuery: {
        page: 1,
        limit: 20,
        statusArray: []
      },
      statusMap,
      list: null,
      total: 0,
      listLoading: true,
      statusDic: ['已提交', '已收到样本', '样本检测中', '样本检测完毕', '检测结果已发送至预留邮箱'],
      examineDic: ['佰基检测送检单', '佰基病理学检测送检单'],
      genderDic: ['雄', '雌'],
      clsDic: ['犬', '猫', '', '', '', '', '', '', '', '其他'],
      yesornoDic: ['否', '是'],
      textureDic: ['软', '中', '硬', '糟脆'],
      dialogVisible: false,
      thisSelectedRow: undefined,
      checkForm: {
        status: '',
        examine: '',
        hospitalName: '',
        doctorName: '',
        phone: '',
        sampleAddr: '',
        sampleDate: '',
        email: '',
        petName: '',
        cls: '',
        gender: '',
        isStered: '',
        sampleArea: '',
        texture: '',
        sampleSize: '',
        isDiolame: '',
        isRupture: '',
        isStick: '',
        exampleProject: '',
        sampleProject: '',
        sampleCondition: '',
        clinical: '',
        history: '',
        sampleFeature: '',
        expressUrl: ''
      }
    }
  },
  computed: {
    urlArr() {
      return this.checkForm.expressUrl.split(',')
    },
    url() {
      return this.checkForm.expressUrl.split(',')[0]
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      fetchList(this.listQuery)
        .then(response => {
          this.list = response.data.data.list.list
          this.total = response.data.data.total
          this.listLoading = false
        })
        .catch(() => {
          this.list = []
          this.total = 0
          this.listLoading = false
        })
    },
    rowSelcted(val) {
      this.thisSelectedRow = val
    },
    handleOpen() {
      this.checkForm = this.thisSelectedRow
      console.log(this.thisSelectedRow)
      this.dialogVisible = true
    },
    handledbClick() {
      this.handleOpen()
    },
    handleFilter() {
      this.listQuery.page = 1
      this.getList()
    },
    handleOperation(data) {
      operation(data)
        .then(response => {
          this.$notify.success({
            title: '成功',
            message: '操作成功'
          })
          this.getList()
        })
        .catch(response => {
          this.$notify.error({
            title: '失败',
            message: response.data.errmsg
          })
        })
    }
  }
}
</script>

<style>
.text-center {
  text-align: center;
}
</style>
