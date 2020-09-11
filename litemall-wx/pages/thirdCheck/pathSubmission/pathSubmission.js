// pages/thirdCheck/pathSubmission/pathSubmission.js
var api = require('../../../config/api.js');
const util = require('../../../utils/util.js');
import WxValidate from '../../../utils/WxValidate.js';

var app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    form: {
      hospitalName: '',
      phone: '',
      sampleAddr:'',
      sampleDate: '',
      email: '',
      cls:'',
      sampleArea: '',
      texture:'',
      sampleSize: '',
      isDiolame:'',
      isRupture:'',
      isStick:'',
      clinical: '',
      history:'',
      expressCarrier: '',
      expressNo: ''
    },
    clsRadioItems: [
      { name: '犬', value: '0' },
      { name: '猫', value: '1' },
      { name: '其他', value: '9' }
    ],
    genderRadioItems: [
      { name: '雄', value: '0' },
      { name: '雌', value: '1' }
    ],
    isRadioItems1: [
      { name: '否', value: '0' },
      { name: '是', value: '1' }
    ],
    isRadioItems2: [
      { name: '否', value: '0' },
      { name: '是', value: '1' }
    ],
    isRadioItems3: [
      { name: '否', value: '0' },
      { name: '是', value: '1' }
    ],
    textureRadioItems:[
      { name: '软', value: '0' },
      { name: '中', value: '1' },
      { name: '硬', value: '2' },
      { name: '糟脆', value: '3' }
    ],
    sampleItems: ["全血", "美国", "英国"],
    sampleItemsIndex: 0,
    sampleCheckboxItems: [
      { name: '多处性病变', value: '多处性病变' },
      { name: '侵袭性生长', value: '侵袭性生长' },
      { name: '扩张性生长', value: '扩张性生长' },
      { name: '复发', value: '复发' },
      { name: '有转移性病变 ', value: '有转移性病变' }
    ],
    hasPicture: false,
    picUrls: [],
    files: [],
    date: util.formatTime(new Date())
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.initValidate();
  },
  //报错
  showModal(error) {
    wx.showModal({
      content: error.msg,
      showCancel: false,
    })
  },
  //验证函数
  initValidate() {
    const rules = {
      hospitalName: {
        required: true
      },
      phone: {
        required: true
      },
      sampleAddr:{
        required: true
      },
      sampleDate: {
        required: true
      },
      email: {
        required: true
      },
      cls:{
        required: true
      },
      sampleArea: {
        required: true
      },
      texture:{
        required: true
      },
      sampleSize: {
        required: true
      },
      isDiolame:{
        required: true
      },
      isRupture:{
        required: true
      },
      isStick:{
        required: true
      },
      clinical: {
        required: true
      },
      history:{
        required: true
      },
      expressCarrier: {
        required: true
      },
      expressNo: {
        required: true
      }
    }
    const messages = {
      hospitalName: {
        required: '请填写医院名称'
      },
      phone: {
        required: '请填写联系电话'
      },
      sampleAddr:{
        required: '请填写取样地址'
      },
      sampleDate: {
        required: '请填写采样日期'
      },
      email: {
        required: '请填写报告接收邮箱'
      },
      cls:{
        required: '请选择类别'
      },
      sampleArea: {
        required: '请填写采样部位'
      },
      texture:{
        required: '请选择质地'
      },
      sampleSize: {
        required: '请填写原发肿物大小'
      },
      isDiolame:{
        required: '请选择是否有包膜'
      },
      isRupture:{
        required: '请选择是否有破溃'
      },
      isStick:{
        required: '请选择是否与周围组织粘连'
      },
      clinical: {
        required: '请填写临床表现及临床诊断'
      },
      history:{
        required: '请填写既往病史与用药经历'
      },
      expressCarrier: {
        required: '请填写快递公司'
      },
      expressNo: {
        required: '请填写快递单号'
      }
    }
    this.WxValidate = new WxValidate(rules, messages)
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },
  formSubmit: function (e) {
    let data = e.detail.value;
    data.expressUrl = this.data.picUrls;

    let userInfo = wx.getStorageSync('userInfo');
    data.userweid = userInfo.nickName;

    console.log(data);
    let that = this;

    //校验表单
    if (!this.WxValidate.checkForm(data)) {
      const error = this.WxValidate.errorList[0]
      this.showModal(error)
      return false
    }

    if (e.detail.value.hospitalName == "" || e.detail.value.phone == "" || e.detail.value.sampleAddr == "" || e.detail.value.sampleDate == "" || e.detail.value.email == "" || e.detail.value.cls == "" || e.detail.value.sampleArea == "" || e.detail.value.texture == "" || e.detail.value.sampleSize == "" || e.detail.value.isDiolame == "" || e.detail.value.isRupture == "" || e.detail.value.isStick == "" || e.detail.value.clinical == "" || e.detail.value.history == "") {
      wx.showModal({
        title: '提示',
        content: '请输入完整信息！',
        success: function (res) {
          if (res.confirm) {
            console.log('用户点击确定')
          }
        }
      })
    } else {

      util.request(api.ThirdCheck, 
        data
      , 'POST').then(function (res) {
        if (res.errno === 0) {
          wx.showToast({
            title: '提交成功',
            duration: 2000,
            complete: function () {
              setTimeout(function () {
                //要延时执行的代码
                wx.navigateBack();
              }, 2000) //延迟时间
            }
          })
        }
      });
    }
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

    var isRadioItems1 = this.data.isRadioItems1;
    for (var i = 0, len = isRadioItems1.length; i < len; ++i) {
      isRadioItems1[i].checked = isRadioItems1[i].value == e.detail.value;
    }

    this.setData({
      isRadioItems1: isRadioItems1
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
  textureRadioChange: function (e) {
    console.log('checkbox发生change事件，携带value值为：', e.detail.value);

    var textureRadioItems = this.data.textureRadioItems, values = e.detail.value;
    for (var i = 0, lenI = textureRadioItems.length; i < lenI; ++i) {
      textureRadioItems[i].checked = false;

      for (var j = 0, lenJ = values.length; j < lenJ; ++j) {
        if (textureRadioItems[i].value == values[j]) {
          textureRadioItems[i].checked = true;
          break;
        }
      }
    }

    this.setData({
      textureRadioItems: textureRadioItems
    });
  },
  bindDateChange: function (e) {
    console.log('picker发送选择改变，携带值为', e.detail.value)
    this.setData({
      date: e.detail.value
    })
  },
  isStickRadioChange: function (e) {
    console.log('isStickRadioChange发生change事件，携带value值为：', e.detail.value);

    var isRadioItems2 = this.data.isRadioItems2;
    for (var i = 0, len = isRadioItems2.length; i < len; ++i) {
      isRadioItems2[i].checked = isRadioItems2[i].value == e.detail.value;
    }

    this.setData({
      isRadioItems2: isRadioItems2
    });
  },
  isDiolameRadioChange: function (e) {
    console.log('isDiolameRadioChange发生change事件，携带value值为：', e.detail.value);

    var isRadioItems3 = this.data.isRadioItems3;
    for (var i = 0, len = isRadioItems3.length; i < len; ++i) {
      isRadioItems3[i].checked = isRadioItems3[i].value == e.detail.value;
    }

    this.setData({
      isRadioItems3: isRadioItems3
    });
  },
  // 邮箱验证部分
  inputemail: function (e) {
    let email = e.detail.value
    let checkedNum = this.checkEmail(email)

  },
  checkEmail: function (email) {
    let str = /^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$/
    if (str.test(email)) {
      return true
    } else {
      wx.showToast({
        title: '请填写正确的邮箱号'
      })
      return false
    }
  },
  // 删除图片
  clearImg: function (e) {
    console.log(e)
    var nowList = [];//新数据
    var picUrls = this.data.picUrls;//原数据

    var newfiles = [];//新数据
    var files = this.data.files;//原数据

    for (let i = 0; i < picUrls.length; i++) {
      if (i == e.currentTarget.dataset.index) {
        continue;
      } else {
        nowList.push(picUrls[i])
        newfiles.push(files[i])
      }
    }
    this.setData({
      picUrls: nowList,
      files: newfiles
    })
  }
})