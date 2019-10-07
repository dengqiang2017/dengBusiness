package com.dengqiang.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import com.dengqiang.bean.dictionary.DictionaryDetail;
import com.dengqiang.util.ConfigFile;
/**
 * 房屋信息
 * @author dengqiang
 *
 */
@Entity
@Table(name="dq_housing_info")
@DynamicUpdate(true)
public class HousingInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@OneToOne//(fetch = FetchType.LAZY)
	@JoinColumn(name = "district",nullable=false)
	private DictionaryDetail district;//区县
	
	@OneToOne//(fetch = FetchType.LAZY)
	@JoinColumn(name = "plate")
	private DictionaryDetail plate;//板块
	
	@OneToOne//(fetch = FetchType.LAZY)
	@JoinColumn(name = "community")
	private DictionaryDetail community;//小区
	
	@OneToOne//(fetch = FetchType.LAZY)
	@JoinColumn(name = "houseType",nullable=false)
	private DictionaryDetail houseType;//户型
	
	@Column(name = "floor",nullable=false,length=30)
	private String floor;//楼层
	@Column(name = "building",nullable=false,length=30)
	private String building;//单元栋
	///////////////////////////////////////////////////////////
	@Column(name = "acreage",length=4,scale=2)
	private Double acreage;//面积
	
	@Column(name = "totalPrice",length=20)
	private Integer totalPrice;//总价格
	///////----------------///////
	@Column(name = "unitPrice",length=10)
	private Integer unitPrice;//单价
	
	@Column(name = "monthlyRent",length=10)
	private Integer monthlyRent;//月租
	@OneToOne//(fetch = FetchType.LAZY)
	@JoinColumn(name = "rentType")
	private DictionaryDetail rentType;//房屋类型/房源类型
	/////////////////////////////////////////////////////////////
	@OneToOne//(fetch = FetchType.LAZY)
	@JoinColumn(name = "decorationType",nullable=false)
	@JsonIgnore
	private DictionaryDetail decorationType;//装修
	
	@Column(name = "orientation",length=20)
	@JsonIgnore
	private String orientation;//朝向
	
	@Column(name = "buildTime",length=4)
	private String buildTime;//修建时间/年代
	
	@Column(name = "remark",length=200)
	private String remark;
	
	@Column(name = "refreshTime",nullable=false)
//	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern=ConfigFile.DATETIME_FORMAT)
//	@JsonIgnore
	private Date refreshTime;//刷新时间
	
	@Column(name = "publishTime",nullable=false)
//	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern=ConfigFile.DATETIME_FORMAT)
	private Date publishTime;//发布时间
	
	@Column(name = "followUpTime")
//	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern=ConfigFile.DATETIME_FORMAT)
	@JsonIgnore
	private Date followUpTime;//跟进时间
	
	@OneToOne//(fetch = FetchType.LAZY)
	@JoinColumn(name = "housingType",nullable=false)
	@JsonIgnore
	private DictionaryDetail housingType;//房屋类型/房源类型
	
	@Column(name = "number",nullable=false,length=20)
//	@JsonIgnore
	private String number;//编号
	
	@OneToOne//(fetch = FetchType.LAZY)
	@JoinColumn(name = "homeowner",nullable=false)
	private UserInfo homeowner;//房主/房东
	
	@Column(name = "type",nullable=false,length=1,columnDefinition="INT default 0")
	private int type;//数据类型--0二手卖房,1--二手租房,2--新房
	
	@Column(name = "delFlag",nullable=false,length=1,columnDefinition="INT default 1")
	private int delFlag=1;//数据类型--0--删除,1-未删除
	
	@Column(name = "status",nullable=false,length=1,columnDefinition="INT default 0")
	private int status;//房屋类型--0-未出租,1-已出租,0-未卖出,2-已预订,3-交易中,4-已卖出
	
	@OneToOne//(fetch = FetchType.LAZY)
	@JoinColumn(name = "business",nullable=false)
	private Business business;//录入人
	
	@Column(name = "establishTime",nullable=false)
//	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern=ConfigFile.DATETIME_FORMAT)
	private Date establishTime;//创建时间
	/////////////////房屋图片///////////////////////////
	@OneToMany(fetch = FetchType.LAZY,cascade=CascadeType.ALL)
	@JoinColumn(name="housingId")
	private Set<ImageUrl> imgUrls=new HashSet<ImageUrl>();
	////////////////////////
//	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//	@JoinTable(
//            name = "recommended_housing",//中间表名称，如果没有这个表，程序启动后，自动在数据库创建
//            //ForeignKey指定外键，none是没有；ConstraintMode.NO_CONSTRAINT是不创建
////            foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT),
//            //joinColumns 是主控方给当前类指定关联，name是当前类student的属性关联的中间表属性sid，
//            // referencedColumnName 是当前类的属性student实体类的id
//            joinColumns = {@JoinColumn(name = "userid", referencedColumnName = "id", nullable = false)},
//            //inverseJoinColumns也就是被控方的属性，
//            // referencedColumnName后面写Teacher的属性id，JoinColumn写中间表的属性名称
//            inverseJoinColumns = {@JoinColumn(name = "housing_id", referencedColumnName = "id", nullable = false
//            )})
//	private List<UserInfo> recommendedHousing=new ArrayList<>();//推荐房源
//	
//	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//	@JoinTable(
//            name = "already_looked_house",//中间表名称，如果没有这个表，程序启动后，自动在数据库创建
//            //ForeignKey指定外键，none是没有；ConstraintMode.NO_CONSTRAINT是不创建
////            foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT),
//            //joinColumns 是主控方给当前类指定关联，name是当前类student的属性关联的中间表属性sid，
//            // referencedColumnName 是当前类的属性student实体类的id
//            joinColumns = {@JoinColumn(name = "alreadyLookedHouse_userid", referencedColumnName = "id", nullable = false)},
//            //inverseJoinColumns也就是被控方的属性，
//            // referencedColumnName后面写Teacher的属性id，JoinColumn写中间表的属性名称
//            inverseJoinColumns = {@JoinColumn(name = "alreadyLookedHouse_id", referencedColumnName = "id", nullable = false
//            )})
//	private List<UserInfo> alreadyLookedHouse=new ArrayList<>();//已带看房源
	//////////////////////
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Double getAcreage() {
		return acreage;
	}
	public void setAcreage(Double acreage) {
		this.acreage = acreage;
	}
	public Integer getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}
	public Integer getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Integer unitPrice) {
		this.unitPrice = unitPrice;
	}
	public Integer getMonthlyRent() {
		return monthlyRent;
	}
	public void setMonthlyRent(Integer monthlyRent) {
		this.monthlyRent = monthlyRent;
	}
	public String getOrientation() {
		return orientation;
	}
	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}
	public String getBuildTime() {
		return buildTime;
	}
	public void setBuildTime(String buildTime) {
		this.buildTime = buildTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getRefreshTime() {
		return refreshTime;
	}
	public void setRefreshTime(Date refreshTime) {
		this.refreshTime = refreshTime;
	}
	public Date getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
	public Date getFollowUpTime() {
		return followUpTime;
	}
	public void setFollowUpTime(Date followUpTime) {
		this.followUpTime = followUpTime;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Business getBusiness() {
		return business;
	}
	public void setBusiness(Business business) {
		this.business = business;
	}
	public DictionaryDetail getDistrict() {
		return district;
	}
	public void setDistrict(DictionaryDetail district) {
		this.district = district;
	}
	public DictionaryDetail getPlate() {
		return plate;
	}
	public void setPlate(DictionaryDetail plate) {
		this.plate = plate;
	}
	public DictionaryDetail getCommunity() {
		return community;
	}
	public void setCommunity(DictionaryDetail community) {
		this.community = community;
	}
	public DictionaryDetail getHouseType() {
		return houseType;
	}
	public void setHouseType(DictionaryDetail houseType) {
		this.houseType = houseType;
	}
	public String getFloor() {
		return floor;
	}
	public void setFloor(String floor) {
		this.floor = floor;
	}
	public DictionaryDetail getRentType() {
		return rentType;
	}
	public void setRentType(DictionaryDetail rentType) {
		this.rentType = rentType;
	}
	public DictionaryDetail getDecorationType() {
		return decorationType;
	}
	public void setDecorationType(DictionaryDetail decorationType) {
		this.decorationType = decorationType;
	}
	public DictionaryDetail getHousingType() {
		return housingType;
	}
	public void setHousingType(DictionaryDetail housingType) {
		this.housingType = housingType;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getEstablishTime() {
		return establishTime;
	}
	public void setEstablishTime(Date establishTime) {
		this.establishTime = establishTime;
	}
	public Set<ImageUrl> getImgUrls() {
		return imgUrls;
	}
	public void setImgUrls(Set<ImageUrl> imgUrls) {
		this.imgUrls = imgUrls;
	}
	public int getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}
	public UserInfo getHomeowner() {
		return homeowner;
	}
	public void setHomeowner(UserInfo homeowner) {
		this.homeowner = homeowner;
	}
	public String getBuilding() {
		return building;
	}
	public void setBuilding(String building) {
		this.building = building;
	}
	@Override
	public String toString() {
		return "HousingInfo [id=" + id + ", district=" + district + ", plate="
				+ plate + ", community=" + community + ", houseType="
				+ houseType + ", floor=" + floor + ", building=" + building
				+ ", acreage=" + acreage + ", totalPrice=" + totalPrice
				+ ", unitPrice=" + unitPrice + ", monthlyRent=" + monthlyRent
				+ ", rentType=" + rentType + ", decorationType="
				+ decorationType + ", orientation=" + orientation
				+ ", buildTime=" + buildTime + ", remark=" + remark
				+ ", refreshTime=" + refreshTime + ", publishTime="
				+ publishTime + ", followUpTime=" + followUpTime
				+ ", housingType=" + housingType + ", number=" + number
				+ ", homeowner=" + homeowner + ", type=" + type + ", delFlag="
				+ delFlag + ", status=" + status + ", business=" + business
				+ ", establishTime=" + establishTime + ", imgUrls=" + imgUrls
				+ "]";
	}
 
}
