package org.linlinjava.litemall.db.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class LitemallThirdCheck {

    public static final Boolean IS_DELETED = LitemallOrder.Deleted.IS_DELETED.value();

    public static final Boolean NOT_DELETED = LitemallOrder.Deleted.NOT_DELETED.value();

    private Integer id;

    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 状态
     */
    private String status;

    /**
     * 用户微信id
     */
    private  String userweid;

    /**
     * 负责人
     */
    private String inChargePerson;

    /**
     * 临床表现及临床诊断
     */
    private String clinical;

    /**
     * 患宠 类别
     * { name: '犬', value: '0' },
     *       { name: '猫', value: '1' },
     *       { name: '其他', value: '9' }
     */
    private String cls;

    /**
     * 医师姓名
     */
    private String doctorName;

    /**
     * 报告接收邮箱
     */
    private String email;

    /**
     * //检测单项目，0-检测单 1-病理检测单
     */
    private String examine;

    /**
     * //检测项目
     */
    private String exampleProject;

    /**
     * 性别
     *  { name: '雄', value: '0' },
     *       { name: '雌', value: '1' }
     */
    private String gender;

    /**
     *  既往病史与用药经历
     */
    private String history;

    /**
     * //医院名
     */
    private String hospitalName;

    /**
     * 是否绝育
     * 0-否 1-是
     */
    private String isStered;

    /**
     * 是否有包膜
     * 0-否 1-是
     */
    private String isDiolame;

    /**
     * 是否有破溃
     * 0-否 1-是
     */
    private String isRupture;

    /**
     * 与周围组织粘连
     * 0-否 1-是
     */
    private String isStick;

    /**
     * //宠物名
     */
    private String petName;

    /**
     * //电话
     */
    private String phone;

    /**
     * //取样地址
     */
    private String sampleAddr;

    /**
     * //采样部位
     */
    private String sampleArea;

    /**
     * 采样情况
     */
    private String sampleCondition;

    /**
     * //采样日期
     */
    private LocalDateTime sampleDate;

    /**
     * 样本特征 虚拟
     * { name: '多处性病变', value: '0' },
     *       { name: '侵袭性生长', value: '1' },
     *       { name: '扩张性生长', value: '2' },
     *       { name: '复发', value: '3' },
     *       { name: '有转移性病变 ', value: '4' }
     */
    private String sampleFeature;

    /**
     * 样本项目
     *  { name: '全血', value: '0'},
     *       { name: '尿液', value: '1' },
     *       { name: '毛发/皮屑', value: '2' },
     *       { name: '血清', value: '3' },
     *       { name: '粪便', value: '4' },
     *       { name: '采样拭子', value: '5' },
     *       { name: '血浆', value: '6' },
     *       { name: '穿刺抽吸液', value: '7' },
     *       { name: '结石', value: '8' },
     */
    private String sampleProject;

    /**
     * //原发肿物大小
     */
    private String sampleSize;

    /**
     * 质地
     *     // { name: '软', value: '0' },
     *    // { name: '中', value: '1' },
     *    // { name: '硬', value: '2' },
     *    // { name: '糟脆', value: '3' }
     */
    private String texture;

    private String expressCarrier;

    private String expressNo;

    private String expressUrl;

    private String backUpString1;

    private String backUpString2;

    private String backUpString3;

    private LocalDateTime backUpTime1;

    private Integer backUpInt1;

    public static Boolean getIsDeleted() {
        return IS_DELETED;
    }

    public static Boolean getNotDeleted() {
        return NOT_DELETED;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserweid() {
        return userweid;
    }

    public void setUserweid(String userweid) {
        this.userweid = userweid;
    }

    public String getInChargePerson() {
        return inChargePerson;
    }

    public void setInChargePerson(String inChargePerson) {
        this.inChargePerson = inChargePerson;
    }

    public String getClinical() {
        return clinical;
    }

    public void setClinical(String clinical) {
        this.clinical = clinical;
    }

    public String getCls() {
        return cls;
    }

    public void setCls(String cls) {
        this.cls = cls;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getExamine() {
        return examine;
    }

    public void setExamine(String examine) {
        this.examine = examine;
    }

    public String getExampleProject() {
        return exampleProject;
    }

    public void setExampleProject(String exampleProject) {
        this.exampleProject = exampleProject;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getIsStered() {
        return isStered;
    }

    public void setIsStered(String isStered) {
        this.isStered = isStered;
    }

    public String getIsDiolame() {
        return isDiolame;
    }

    public void setIsDiolame(String isDiolame) {
        this.isDiolame = isDiolame;
    }

    public String getIsRupture() {
        return isRupture;
    }

    public void setIsRupture(String isRupture) {
        this.isRupture = isRupture;
    }

    public String getIsStick() {
        return isStick;
    }

    public void setIsStick(String isStick) {
        this.isStick = isStick;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSampleAddr() {
        return sampleAddr;
    }

    public void setSampleAddr(String sampleAddr) {
        this.sampleAddr = sampleAddr;
    }

    public String getSampleArea() {
        return sampleArea;
    }

    public void setSampleArea(String sampleArea) {
        this.sampleArea = sampleArea;
    }

    public String getSampleCondition() {
        return sampleCondition;
    }

    public void setSampleCondition(String sampleCondition) {
        this.sampleCondition = sampleCondition;
    }

    public LocalDateTime getSampleDate() {
        return sampleDate;
    }

    public void setSampleDate(LocalDateTime sampleDate) {
        this.sampleDate = sampleDate;
    }

    public String getSampleFeature() {
        return sampleFeature;
    }

    public void setSampleFeature(String sampleFeature) {
        this.sampleFeature = sampleFeature;
    }

    public String getSampleProject() {
        return sampleProject;
    }

    public void setSampleProject(String sampleProject) {
        this.sampleProject = sampleProject;
    }

    public String getSampleSize() {
        return sampleSize;
    }

    public void setSampleSize(String sampleSize) {
        this.sampleSize = sampleSize;
    }

    public String getTexture() {
        return texture;
    }

    public void setTexture(String texture) {
        this.texture = texture;
    }

    public String getBackUpString1() {
        return backUpString1;
    }

    public void setBackUpString1(String backUpString1) {
        this.backUpString1 = backUpString1;
    }

    public String getBackUpString2() {
        return backUpString2;
    }

    public void setBackUpString2(String backUpString2) {
        this.backUpString2 = backUpString2;
    }

    public String getBackUpString3() {
        return backUpString3;
    }

    public void setBackUpString3(String backUpString3) {
        this.backUpString3 = backUpString3;
    }

    public LocalDateTime getBackUpTime1() {
        return backUpTime1;
    }

    public void setBackUpTime1(LocalDateTime backUpTime1) {
        this.backUpTime1 = backUpTime1;
    }

    public Integer getBackUpInt1() {
        return backUpInt1;
    }

    public void setBackUpInt1(Integer backUpInt1) {
        this.backUpInt1 = backUpInt1;
    }

    public String getExpressCarrier() {
        return expressCarrier;
    }

    public void setExpressCarrier(String expressCarrier) {
        this.expressCarrier = expressCarrier;
    }

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    public String getExpressUrl() {
        return expressUrl;
    }

    public void setExpressUrl(String expressUrl) {
        this.expressUrl = expressUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LitemallThirdCheck that = (LitemallThirdCheck) o;
        return id.equals(that.id) &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(updateTime, that.updateTime) &&
                Objects.equals(status, that.status) &&
                Objects.equals(userweid, that.userweid) &&
                Objects.equals(inChargePerson, that.inChargePerson) &&
                Objects.equals(clinical, that.clinical) &&
                Objects.equals(cls, that.cls) &&
                Objects.equals(doctorName, that.doctorName) &&
                Objects.equals(email, that.email) &&
                Objects.equals(examine, that.examine) &&
                Objects.equals(exampleProject, that.exampleProject) &&
                Objects.equals(gender, that.gender) &&
                Objects.equals(history, that.history) &&
                Objects.equals(hospitalName, that.hospitalName) &&
                Objects.equals(isStered, that.isStered) &&
                Objects.equals(isDiolame, that.isDiolame) &&
                Objects.equals(isRupture, that.isRupture) &&
                Objects.equals(isStick, that.isStick) &&
                Objects.equals(petName, that.petName) &&
                Objects.equals(phone, that.phone) &&
                Objects.equals(sampleAddr, that.sampleAddr) &&
                Objects.equals(sampleArea, that.sampleArea) &&
                Objects.equals(sampleCondition, that.sampleCondition) &&
                Objects.equals(sampleDate, that.sampleDate) &&
                Objects.equals(sampleFeature, that.sampleFeature) &&
                Objects.equals(sampleProject, that.sampleProject) &&
                Objects.equals(sampleSize, that.sampleSize) &&
                Objects.equals(texture, that.texture) &&
                Objects.equals(backUpString1, that.backUpString1) &&
                Objects.equals(backUpString2, that.backUpString2) &&
                Objects.equals(backUpString3, that.backUpString3) &&
                Objects.equals(backUpTime1, that.backUpTime1) &&
                Objects.equals(backUpInt1, that.backUpInt1);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createTime, updateTime, status, userweid, inChargePerson, clinical, cls, doctorName, email, examine, exampleProject, gender, history, hospitalName, isStered, isDiolame, isRupture, isStick, petName, phone, sampleAddr, sampleArea, sampleCondition, sampleDate, sampleFeature, sampleProject, sampleSize, texture, backUpString1, backUpString2, backUpString3, backUpTime1, backUpInt1);
    }

    @Override
    public String toString() {
        return "LitemallThirdCheck{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", status='" + status + '\'' +
                ", userweid='" + userweid + '\'' +
                ", inChargePerson='" + inChargePerson + '\'' +
                ", clinical='" + clinical + '\'' +
                ", cls='" + cls + '\'' +
                ", doctorName='" + doctorName + '\'' +
                ", email='" + email + '\'' +
                ", examine='" + examine + '\'' +
                ", exampleProject='" + exampleProject + '\'' +
                ", gender='" + gender + '\'' +
                ", history='" + history + '\'' +
                ", hospitalName='" + hospitalName + '\'' +
                ", isStered='" + isStered + '\'' +
                ", isDiolame='" + isDiolame + '\'' +
                ", isRupture='" + isRupture + '\'' +
                ", isStick='" + isStick + '\'' +
                ", petName='" + petName + '\'' +
                ", phone='" + phone + '\'' +
                ", sampleAddr='" + sampleAddr + '\'' +
                ", sampleArea='" + sampleArea + '\'' +
                ", sampleCondition='" + sampleCondition + '\'' +
                ", sampleDate=" + sampleDate +
                ", sampleFeature=" + sampleFeature +
                ", sampleProject=" + sampleProject +
                ", sampleSize='" + sampleSize + '\'' +
                ", texture='" + texture +
                '}';
    }
}
