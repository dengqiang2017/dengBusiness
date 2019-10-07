package com.dengqiang.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import com.dengqiang.util.ConfigFile;
/**
 * 订单明细表 
 * @author dengqiang
 *
 */
@Entity
@Table(name="dq_order_detail")
@DynamicUpdate(true)
public class OrderDetail implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "status",nullable=false,length=500)
	private String scheduleContent;//进度内容
	
	@Column(name = "followUpTime")
	@DateTimeFormat(pattern=ConfigFile.DATETIME_FORMAT)
	@JsonIgnore
	private Date followUpTime;//跟进时间
}
