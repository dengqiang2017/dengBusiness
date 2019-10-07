package com.dengqiang.page;

import org.apache.commons.lang.StringUtils;

public class HousingQuery extends ObjectQuery {
	private Long district;
	private Long plate;
	private Long rentType;
	private Long houseType;
	private Long decorationType;
	private Integer monthlyRent;
	private Integer monthlyRent2;
	private Double acreage;
	private Double acreage2;
	private String buildTime;
	private String floor;
	private String building;
	private Long totalPrice;
	private Long totalPrice2;
	private Long business;
	private int type; 
	private int delFlag=1; 
	private int status=0;
	private String all;
	
	@Override
	public void addHql() {
		if (StringUtils.isNotBlank(searchKey)) {
			String param = "%" + searchKey + "%";
			addHql("(o.community.name like ? or o.homeowner.phone like ? or o.homeowner.username like ?)", param,param,param);
		}
		if (district!=null&&district>0) {//区县
			addHql(" o.district.id =? ",district);
		}
		if (plate!=null&&plate>0) {//板块
			addHql(" o.plate.id =? ",plate);
		}
		if (rentType!=null&&rentType>0) {//出租类型
			addHql(" o.rentType.id =? ",rentType);
		}
		if (houseType!=null&&houseType>0) {//房屋类型
			addHql(" o.houseType.id =? ",houseType);
		}
		if (decorationType!=null&&decorationType>0) {//装修
			addHql(" o.decorationType.id =? ",decorationType);
		}
		if (totalPrice!=null&&totalPrice>0) {//总价
			addHql(" o.totalPrice >=? ",totalPrice);
		}
		if (totalPrice2!=null&&totalPrice2>0) {//总价
			addHql(" o.totalPrice <=? ",totalPrice2);
		}
		if (monthlyRent!=null&&monthlyRent>0) {//租金
			addHql(" o.monthlyRent >=? ",monthlyRent);
		}
		if (monthlyRent2!=null&&monthlyRent2>0) {//租金
			addHql(" o.monthlyRent <=? ",monthlyRent2);
		}
		if (acreage!=null&&acreage>0) {//面积
			addHql(" o.acreage >? ",acreage);
		}
		if (acreage2!=null&&acreage2>0) {//面积
			addHql(" o.acreage <? ",acreage2);
		}
		if (StringUtils.isNotBlank(buildTime)) {//年代
			addHql(" o.buildTime =? ",buildTime);
		}
		if (StringUtils.isNotBlank(building)) {//楼层
			addHql(" o.building like ? ","%"+building+"%");
		}
		if (StringUtils.isNotBlank(floor)) {//楼层
			addHql(" o.floor =? ",floor);
		}
		if (business!=null&&business>0&&StringUtils.isBlank(all)) {//录入人//查询所有人的
			addHql(" o.business.id=? ",business);
		}
		if (status>=0) {
			addHql(" o.status =?",status);//信息状态类型
		}
		addHql(" o.type =?",type);//信息类型
		addHql(" o.delFlag =?",delFlag);//信息是否被删除
	}
	public Long getDistrict() {
		return district;
	}
	public void setDistrict(Long district) {
		this.district = district;
	}
	public Long getPlate() {
		return plate;
	}
	public void setPlate(Long plate) {
		this.plate = plate;
	}
	public Long getRentType() {
		return rentType;
	}
	public void setRentType(Long rentType) {
		this.rentType = rentType;
	}
	public Long getDecorationType() {
		return decorationType;
	}
	public void setDecorationType(Long decorationType) {
		this.decorationType = decorationType;
	}
	public String getBuildTime() {
		return buildTime;
	}
	public void setBuildTime(String buildTime) {
		this.buildTime = buildTime;
	}
	public Long getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Long totalPrice) {
		this.totalPrice = totalPrice;
	}
	public Long getTotalPrice2() {
		return totalPrice2;
	}
	public void setTotalPrice2(Long totalPrice2) {
		this.totalPrice2 = totalPrice2;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Long getBusiness() {
		return business;
	}
	public void setBusiness(Long business) {
		this.business = business;
	}
	public String getAll() {
		return all;
	}
	public void setAll(String all) {
		this.all = all;
	}
	public int getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}
	public Long getHouseType() {
		return houseType;
	}
	public void setHouseType(Long houseType) {
		this.houseType = houseType;
	}
	public Integer getUnitPrice() {
		return monthlyRent;
	}
	public Integer getMonthlyRent() {
		return monthlyRent;
	}
	public void setMonthlyRent(Integer monthlyRent) {
		this.monthlyRent = monthlyRent;
	}
	public Integer getMonthlyRent2() {
		return monthlyRent2;
	}
	public void setMonthlyRent2(Integer monthlyRent2) {
		this.monthlyRent2 = monthlyRent2;
	}
	public Double getAcreage() {
		return acreage;
	}
	public void setAcreage(Double acreage) {
		this.acreage = acreage;
	}
	public Double getAcreage2() {
		return acreage2;
	}
	public void setAcreage2(Double acreage2) {
		this.acreage2 = acreage2;
	}
	public String getFloor() {
		return floor;
	}
	public void setFloor(String floor) {
		this.floor = floor;
	}
	public String getBuilding() {
		return building;
	}
	public void setBuilding(String building) {
		this.building = building;
	}
 
}
