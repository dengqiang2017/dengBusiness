package com.dengqiang.bean;

/**
 * 空间管理
 * @author dengqiang
 *
 */
public class ZoneBean {
	private Long id;
	private String filename;
	private Boolean filetype;// true是文件,false是文件夹
	private String size;
	private String date;// 修改日期
	private String path;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Boolean getFiletype() {
		return filetype;
	}

	public void setFiletype(Boolean filetype) {
		this.filetype = filetype;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
 
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String toString() {
		return "ZoneBean [id=" + id + ", filename=" + filename + ", filetype="
				+ filetype + ", size=" + size + ", date=" + date + "]";
	}
 

}
