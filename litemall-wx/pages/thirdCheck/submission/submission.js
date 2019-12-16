// pages/thirdCheck/submission/submission.js
var api = require('../../../config/api.js');
const util = require('../../../utils/util.js');

var app = getApp();


Page({

  /**
   * 页面的初始数据
   */
  data: {
    clsRadioItems: [
      { name: '犬', value: '0' },
      { name: '猫', value: '1' },
      { name: '其他', value: '9' }
    ],
    genderRadioItems: [
      { name: '雄', value: '0' },
      { name: '雌', value: '1' }
    ],
    isRadioItems: [
      { name: '否', value: '0' },
      { name: '是', value: '1' }
    ],
    sampleItems: ["全血", "美国", "英国"],
    sampleItemsIndex: 0,
    sampleCheckboxItems: [
      { name: '全血', value: '0'},
      { name: '尿液', value: '1' },
      { name: '毛发/皮屑', value: '2' },
      { name: '血清', value: '3' },
      { name: '粪便', value: '4' },
      { name: '采样拭子', value: '5' },
      { name: '血浆', value: '6' },
      { name: '穿刺抽吸液', value: '7' },
      { name: '结石', value: '8' },
    ],
    hasPicture: false,
    picUrls: [],
    files: [],
    date: '2016-09-01',
  },


  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },
  formSubmit: function(e) {
    console.log(e.detail.value);
    let data = e.detail.value;

    let that = this;

    util.request(api.ThirdCheck, data, 'POST').then(function (res) {
      if (res.errno === 0) {
        wx.showToast({
          title: '提交成功',
          complete: function () {
            wx.navigateBack();
          }
        })
      }
    });
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },
  // 手机号部分

  inputPhoneNum: function (e) {

    let phoneNumber = e.detail.value

    if (phoneNumber.length === 11) {

      let checkedNum = this.checkPhoneNum(phoneNumber)

    }

  },

  checkPhoneNum: function (phoneNumber) {

    let str = /^1\d{10}$/

    if (str.test(phoneNumber)) {

      return true

    } else {

      wx.showToast({

        title: '手机号不正确',

        image: './../../../../images/fail.png'

      })

      return false

    }

  },
  clsRadioChange: function (e) {
    console.log('radio发生change事件，携带value值为：', e.detail.value);


    var clsRadioItems = this.data.clsRadioItems;
    for (var i = 0, len = clsRadioItems.length; i < len; ++i) {
      clsRadioItems[i].checked = clsRadioItems[i].value == e.detail.value;
    }

    this.setData({
      clsRadioItems: clsRadioItems
    });
  },
  genderRadioChange: function (e) {
    console.log('genderRadioItems发生change事件，携带value值为：', e.detail.value);

    var genderRadioItems = this.data.genderRadioItems;
    for (var i = 0, len = genderRadioItems.length; i < len; ++i) {
      genderRadioItems[i].checked = genderRadioItems[i].value == e.detail.value;
    }

    this.setData({
      genderRadioItems: genderRadioItems
    });
  },
  isSteredRadioChange: function (e) {
    console.log('RadioItems发生change事件，携带value值为：', e.detail.value);

    var isRadioItems = this.data.isRadioItems;
    for (var i = 0, len = isRadioItems.length; i < len; ++i) {
      isRadioItems[i].checked = isRadioItems[i].value == e.detail.value;
    }

    this.setData({
      isRadioItems: isRadioItems
    });
  },
  bindSampleItem: function (e) {
    console.log('picker country 发生选择改变，携带值为', e.detail.value);

    this.setData({
      countryIndex: e.detail.value
    })
  },
  sampleCheckboxChange: function (e) {
    console.log('checkbox发生change事件，携带value值为：', e.detail.value);

    var sampleCheckboxItems = this.data.sampleCheckboxItems, values = e.detail.value;
    for (var i = 0, lenI = sampleCheckboxItems.length; i < lenI; ++i) {
      sampleCheckboxItems[i].checked = false;

      for (var j = 0, lenJ = values.length; j < lenJ; ++j) {
        if (sampleCheckboxItems[i].value == values[j]) {
          sampleCheckboxItems[i].checked = true;
          break;
        }
      }
    }

    this.setData({
      sampleCheckboxItems: sampleCheckboxItems
    });
  },
  chooseImage: function (e) {
    if (this.data.files.length >= 5) {
      util.showErrorToast('只能上传五张图片')
      return false;
    }

    var that = this;
    wx.chooseImage({
      count: 1,
      sizeType: ['original', 'compressed'],
      sourceType: ['album', 'camera'],
      success: function (res) {
        that.setData({
          files: that.data.files.concat(res.tempFilePaths)
        });
        that.upload(res);
      }
    })
  },
  upload: function (res) {
    var that = this;
    const uploadTask = wx.uploadFile({
      url: api.StorageUpload,
      filePath: res.tempFilePaths[0],
      name: 'file',
      success: function (res) {
        var _res = JSON.parse(res.data);
        if (_res.errno === 0) {
          var url = _res.data.url
          that.data.picUrls.push(url)
          that.setData({
            hasPicture: true,
            picUrls: that.data.picUrls
          })
        }
      },
      fail: function (e) {
        wx.showModal({
          title: '错误',
          content: '上传失败',
          showCancel: false
        })
      },
    })

    uploadTask.onProgressUpdate((res) => {
      console.log('上传进度', res.progress)
      console.log('已经上传的数据长度', res.totalBytesSent)
      console.log('预期需要上传的数据总长度', res.totalBytesExpectedToSend)
    })

  },
  previewImage: function (e) {
    wx.previewImage({
      current: e.currentTarget.id, // 当前显示图片的http链接
      urls: this.data.files // 需要预览的图片http链接列表
    })
  },
  bindDateChange: function (e) {
    console.log('picker发送选择改变，携带值为', e.detail.value)
    this.setData({
      date: e.detail.value
    })
  }
})