package com.dengqiang.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.dengqiang.util.ConfigFile;
/**
 * 客户信息
 * @author dengqiang
 *
 */
@Entity
@Table(name="dq_user_info")
public class UserInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "number",unique=true,nullable=false,length=20)
	private String number;//客户编号
	
	@Column(name = "username",nullable=false,length=20)
	private String username;//客户名称
	
	@Column(name = "phone",unique=true,nullable=false,length=20)
	private String phone;//客户电话
	
	@Column(name = "clientType",nullable=false,length=1,columnDefinition="INT default 0")
	private int clientType=0;//客户类型,0-房东,1-租客,2-购房者
	
	@Column(name = "demand",length=100,nullable=false)
	private String demand;//需求
	//1、租房客户姓名、联系电话、需求、推荐房源、已带看房源、客户意见等。
	
	@Column(name = "customerComments",length=100,nullable=false)
	private String customerComments;//客户意见
	
	@Column(name = "remark",length=100)
	private String remark;//备注
	
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinColumn(name = "business",nullable=false)
	private Business business;//经纪人
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "entering ",nullable=false)
	private Business entering;//录入人
	
	@Column(name = "publishTime",nullable=false)
	@DateTimeFormat(pattern=ConfigFile.DATETIME_FORMAT)
	private Date publishTime=new Date();//发布时间
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(
            name = "recommended_housing",//中间表名称，如果没有这个表，程序启动后，自动在数据库创建
            //ForeignKey指定外键，none是没有；ConstraintMode.NO_CONSTRAINT是不创建
//            foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT),
            //joinColumns 是主控方给当前类指定关联，name是当前类student的属性关联的中间表属性sid，
            // referencedColumnName 是当前类的属性student实体类的id
            joinColumns = {@JoinColumn(name = "userid", referencedColumnName = "id", nullable = false)},
            //inverseJoinColumns也就是被控方的属性，
            // referencedColumnName后面写Teacher的属性id，JoinColumn写中间表的属性名称
            inverseJoinColumns = {@JoinColumn(name = "housing_id", referencedColumnName = "id", nullable = false
            )})
	private Set<HousingInfo> recommendedHousing=new HashSet<>();//推荐房源
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(
            name = "already_looked_house",//中间表名称，如果没有这个表，程序启动后，自动在数据库创建
            //ForeignKey指定外键，none是没有；ConstraintMode.NO_CONSTRAINT是不创建
//            foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT),
            //joinColumns 是主控方给当前类指定关联，name是当前类student的属性关联的中间表属性sid，
            // referencedColumnName 是当前类的属性student实体类的id
            joinColumns = {@JoinColumn(name = "userid", referencedColumnName = "id", nullable = false)},
            //inverseJoinColumns也就是被控方的属性，
            // referencedColumnName后面写Teacher的属性id，JoinColumn写中间表的属性名称
            inverseJoinColumns = {@JoinColumn(name = "house_id", referencedColumnName = "id", nullable = false
            )})
	private Set<HousingInfo> alreadyLookedHouse=new HashSet<>();//已看房源
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
 
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Business getBusiness() {
		return business;
	}

	public void setBusiness(Business business) {
		this.business = business;
	}

	public Business getEntering() {
		return entering;
	}

	public void setEntering(Business entering) {
		this.entering = entering;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getClientType() {
		return clientType;
	}

	public void setClientType(int clientType) {
		this.clientType = clientType;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public String getDemand() {
		return demand;
	}

	public void setDemand(String demand) {
		this.demand = demand;
	}

	public String getCustomerComments() {
		return customerComments;
	}

	public void setCustomerComments(String customerComments) {
		this.customerComments = customerComments;
	}

	public Set<HousingInfo> getRecommendedHousing() {
		return recommendedHousing;
	}

	public void setRecommendedHousing(Set<HousingInfo> recommendedHousing) {
		this.recommendedHousing = recommendedHousing;
	}

	public Set<HousingInfo> getAlreadyLookedHouse() {
		return alreadyLookedHouse;
	}

	public void setAlreadyLookedHouse(Set<HousingInfo> alreadyLookedHouse) {
		this.alreadyLookedHouse = alreadyLookedHouse;
	}

	@Override
	public String toString() {
		return "UserInfo [id=" + id + ", number=" + number + ", username="
				+ username + ", phone=" + phone + ", clientType=" + clientType
				+ ", remark=" + remark + ", business=" + business
				+ ", entering=" + entering + ", publishTime=" + publishTime
				+ ", recommendedHousing=" + recommendedHousing
				+ ", alreadyLookedHouse=" + alreadyLookedHouse + ", demand="
				+ demand + ", customerComments=" + customerComments + "]";
	}
}
