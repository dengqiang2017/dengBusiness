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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import com.dengqiang.util.ConfigFile;

@Entity
@Table(name="dq_order")
@DynamicUpdate(true)
public class OrderBean implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "orderNumber",nullable=false,length=30)
//	@JsonIgnore
	private String orderNumber;//订单编号
	
	@OneToOne
	@JoinColumn(name = "housingInfo")
//	@JsonIgnore
	private HousingInfo housingInfo;//房源
	
	@Column(name = "status",nullable=false,length=1,columnDefinition="INT default 1")
	private int status=1;//订单状态,1-已出租,2-已预订,3-交易中,4-已卖出,0-已取消
	
	@Column(name = "publishTime",nullable=false)
	@DateTimeFormat(pattern=ConfigFile.DATETIME_FORMAT)
	private Date publishTime=new Date();//订单发布时间
	
	@OneToOne
	@JoinColumn(name = "client")
	private UserInfo client;//客户名称
	
	@Column(name = "actualTotalPrice",length=20)
	private Integer actualTotalPrice;//实际成交总价格
	///////----------------///////
	@Column(name = "actualMonthlyRent",length=10)
	private Integer actualMonthlyRent;//实际成交月租
	
	@Column(name = "endTime")
	@DateTimeFormat(pattern=ConfigFile.DATETIME_FORMAT)
	private Date endTime;//订单结束时间
	
	@Column(name = "remark",length=200)
	private String remark;//备注
	
	@Column(name = "delFlag",nullable=false,length=1,columnDefinition="INT default 1")
	private int delFlag=1;
	
	@OneToOne//(fetch = FetchType.LAZY)
	@JoinColumn(name = "business",nullable=false)
	@JsonIgnore
	private Business business;//录入人
	////////////订单明细表////////
	@OneToMany(fetch = FetchType.LAZY,cascade=CascadeType.ALL)
	@JoinColumn(name="orderId")
	@JsonIgnore
	private List<OrderDetail> orderDetails=new ArrayList<OrderDetail>();
	/////////////////合同图片///////////////////////////
	@OneToMany(fetch = FetchType.LAZY,cascade=CascadeType.ALL)
	@JoinColumn(name="orderId")
//	@JsonIgnore
	private Set<ImageUrl> imgUrls=new HashSet<ImageUrl>();
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
 
	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public HousingInfo getHousingInfo() {
		return housingInfo;
	}

	public void setHousingInfo(HousingInfo housingInfo) {
		this.housingInfo = housingInfo;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public UserInfo getClient() {
		return client;
	}

	public void setClient(UserInfo client) {
		this.client = client;
	}

	public Integer getActualTotalPrice() {
		return actualTotalPrice;
	}

	public void setActualTotalPrice(Integer actualTotalPrice) {
		this.actualTotalPrice = actualTotalPrice;
	}

	public Integer getActualMonthlyRent() {
		return actualMonthlyRent;
	}

	public void setActualMonthlyRent(Integer actualMonthlyRent) {
		this.actualMonthlyRent = actualMonthlyRent;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Set<ImageUrl> getImgUrls() {
		return imgUrls;
	}

	public void setImgUrls(Set<ImageUrl> imgUrls) {
		this.imgUrls = imgUrls;
	}

	public Business getBusiness() {
		return business;
	}

	public void setBusiness(Business business) {
		this.business = business;
	}

	public int getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}

	public List<OrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}
	
}
