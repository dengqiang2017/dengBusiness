package com.dengqiang.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 商家表
 * @author dengqiang
 *
 */
@Entity
@Table(name="dq_business")
public class Business  implements Serializable{
	private static final long serialVersionUID = 1L;
	private boolean pwd=false;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "businessName",nullable=false,length=50)
	private String businessName;//商家名称
	@Column(name = "loginName",unique=true,nullable=false,length=20)
	private String loginName;//登录名
	
	@Column(name = "password",nullable=false,length=32)
//	@JsonSerialize(as=Pwd.class)
	private String password;
	
	@Column(name = "phone",unique=true,nullable=false,length=11)
	private String phone;
	
	@Column(name = "email",length=50)
	private String email;
	
	@Column(name = "license",length=32)
	private String license;//注册码
	
	@Column(name = "type",nullable=false,length=1,columnDefinition="INT default 0")
	private int type;//状态 0-初始注册
	
	@Column(name = "level",nullable=false,length=1,columnDefinition="INT default 0")
	private int level;//用户级别0为普通用户,1为管理员用户,2已删除
	
	@Column(name = "district",length=50)
	private String district;//所在地区
	
	@Column(name = "address",length=50)
	private String address;//详细地址
	
	@Column(name = "store",length=20)
	private String store;//门店
	
	@Column(name = "logoUrl",length=50)
	private String logoUrl;//图标
	////////////////////////////////////
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public String getPassword() {
		if (pwd) {
			return password;
		}
		return null;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLicense() {
		return license;
	}
	public void setLicense(String license) {
		this.license = license;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getLogoUrl() {
		return logoUrl;
	}
	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getStore() {
		return store;
	}
	public void setStore(String store) {
		this.store = store;
	}
	public boolean isPwd() {
		return pwd;
	}
	public void setPwd(boolean pwd) {
		this.pwd = pwd;
	}
	
}
