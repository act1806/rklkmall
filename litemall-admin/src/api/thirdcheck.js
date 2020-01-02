import request from '@/utils/request'
import Qs from 'qs'

export function fetchList(query) {
  return request({
    url: '/thirdcheck/list',
    method: 'get',
    params: query,
    paramsSerializer: function(params) {
      return Qs.stringify(params, { arrayFormat: 'repeat' })
    }
  })
}

export function operation(data) {
  return request({
    url: '/thirdcheck/operation',
    method: 'post',
    data
  })
}
