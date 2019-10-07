package com.dengqiang.bean.dictionary;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 数据字典
 * @author dengqiang
 *
 */
@Entity
@Table(name="dq_dictionary")
public class DataDictionary implements Serializable{
	public DataDictionary() {}
	public DataDictionary(int key) {
		this.name = getDictionaryName(key);
	}
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "name",unique=true,nullable=false,length=20)
	private String name;
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
	/**
	 * 获取数据字典名称
	 * @param key 
	 * 0-区县district,1-板块plate,2-小区community,3-户型houseType,
	 * 4-房屋类型rentType,5-装修decorationType,6-类型housingType,7-客户类型
	 * @return
	 */
	public static String getDictionaryName(int key){
		String name=null;
		switch (key) {
		case 0:
			name="区县";
			break;
		case 1:
			name="板块";
			break;
		case 2:
			name="小区";
			break;
		case 3:
			name="户型";
			break;
		case 4:
			name="房屋类型";
			break;
		case 5:
			name="装修";
			break;
		case 6:
			name="类型";
			break;
		case 7:
			name="客户类型";
			break;
		}
		return name;
	}
}
