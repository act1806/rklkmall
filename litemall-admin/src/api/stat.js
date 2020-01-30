import request from '@/utils/request'
import Qs from 'qs'

export function statUser(query) {
  return request({
    url: '/stat/user',
    method: 'get',
    params: query
  })
}

export function statOrder(query) {
  return request({
    url: '/stat/order',
    method: 'get',
    params: query
  })
}

export function statGoods(query) {
  return request({
    url: '/stat/goods',
    method: 'get',
    params: query
  })
}

export function listOrderUser() {
  return request({
    url: '/stat/allUser',
    method: 'get',
    params: ''
  })
}

export function listOrderSailer() {
  return request({
    url: '/stat/allSailer',
    method: 'get',
    params: ''
  })
}

export function userSalesSummary(query) {
  return request({
    url: '/stat/userSalesSummary',
    method: 'get',
    params: query,
    paramsSerializer: function(params) {
      return Qs.stringify(params, { arrayFormat: 'repeat' })
    }
  })
}

export function userSalesDetail(query) {
  return request({
    url: '/stat/userSalesDetail',
    method: 'get',
    params: query,
    paramsSerializer: function(params) {
      return Qs.stringify(params, { arrayFormat: 'repeat' })
    }
  })
}

export function goodsSalesSummary(query) {
  return request({
    url: '/stat/goodsSalesSummary',
    method: 'get',
    params: query,
    paramsSerializer: function(params) {
      return Qs.stringify(params, { arrayFormat: 'repeat' })
    }
  })
}

export function goodsSalesDetail(query) {
  return request({
    url: '/stat/goodsSalesDetail',
    method: 'get',
    params: query,
    paramsSerializer: function(params) {
      return Qs.stringify(params, { arrayFormat: 'repeat' })
    }
  })
}

export function sailerSalesDetail(query) {
  return request({
    url: '/stat/sailerSalesDetail',
    method: 'get',
    params: query,
    paramsSerializer: function(params) {
      return Qs.stringify(params, { arrayFormat: 'repeat' })
    }
  })
}

export function unorderedUser(query) {
  return request({
    url: '/stat/unorderedUser',
    method: 'get',
    params: query,
    paramsSerializer: function(params) {
      return Qs.stringify(params, { arrayFormat: 'repeat' })
    }
  })
}

