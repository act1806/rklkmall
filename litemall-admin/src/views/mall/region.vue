<template>
  <div class="app-container">

    <el-table v-loading="listLoading" :data="list" element-loading-text="正在查询中。。。" row-key="id" style="width: 100%;margin-bottom: 20px;" border="">

      <el-table-column label="船名" prop="name"/>

      <el-table-column label="航次" prop="type">
        <template slot-scope="scope">
          {{ scope.row.type | typeFilter }}
        </template>
      </el-table-column>

      <el-table-column label="" prop="code"/>

    </el-table>

  </div>
</template>

<script>
import { listRegion } from '@/api/region'

export default {
  name: 'Region',
  filters: {
    typeFilter(status) {
      const typeMap = {
        '1': '90',
        '2': 'Yuan shun hai',
        '3': 'Pola Ma'
      }
      return typeMap[status]
    }
  },
  data() {
    return {
      list: [],
      listLoading: true,
      downloadLoading: false
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      listRegion().then(response => {
        this.list = response.data.data.list
        this.listLoading = false
      }).catch(() => {
        this.list = []
        this.listLoading = false
      })
    }
  }
}
</script>
