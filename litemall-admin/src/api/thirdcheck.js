import request from '@/utils/request'

export function fetchList(query) {
  return request({
    url: '/thirdcheck/list',
    method: 'get',
    params: query
  })
}
