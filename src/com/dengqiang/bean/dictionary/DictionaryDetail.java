package com.dengqiang.bean.dictionary;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
/**
 * 数据字典明细
 * @author dengqiang
 *
 */
@Entity
@Table(name="dq_dictionary_detail")
public class DictionaryDetail  implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "name",unique=true,nullable=false,length=20)
	private String name;
	
	@Column(name = "remark",length=20)
	private String remark;
	
	@Column(name = "sorting",length=5)
	private String sorting;
	@OneToOne
	@JoinColumn(name = "dictionary",nullable=false)
	private DataDictionary dictionary;//所属字典
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public DataDictionary getDictionary() {
		return dictionary;
	}
	public void setDictionary(DataDictionary dictionary) {
		this.dictionary = dictionary;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getSorting() {
		return sorting;
	}
	public void setSorting(String sorting) {
		this.sorting = sorting;
	}
	
}
