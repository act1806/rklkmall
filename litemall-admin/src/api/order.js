import request from '@/utils/request'
import Qs from 'qs'

export function listOrder(query) {
  return request({
    url: '/order/list',
    method: 'get',
    params: query,
    paramsSerializer: function(params) {
      return Qs.stringify(params, { arrayFormat: 'repeat' })
    }
  })
}

export function detailOrder(id) {
  return request({
    url: '/order/detail',
    method: 'get',
    params: { id }
  })
}

export function shipOrder(data) {
  return request({
    url: '/order/ship',
    method: 'post',
    data
  })
}

export function confirmOrder(data) {
  return request({
    url: '/order/confirm',
    method: 'post',
    data
  })
}

export function saveOrder(data) {
  return request({
    url: '/order/save',
    method: 'post',
    data
  })
}

export function replyComment(data) {
  return request({
    url: '/order/reply',
    method: 'post',
    data
  })
}

export function listOrderUser() {
  return request({
    url: '/order/user',
    method: 'get',
    params: ''
  })
}

export function listOrderSailer() {
  return request({
    url: '/order/sailer',
    method: 'get',
    params: ''
  })
}
