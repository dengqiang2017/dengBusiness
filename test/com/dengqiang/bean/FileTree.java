package com.dengqiang.bean;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class FileTree {
	private String name;
	private String path;
	private String dirPath;
	private Boolean isParent=true;
	private Boolean open=true;
	private List<FileTree> children=new ArrayList<FileTree>();
	public FileTree(String name, String path, String dirPath) {
		super();
		this.name = name;
		this.path = path;
		this.dirPath = dirPath;
	}
	
	public FileTree(String name, String path) {
		super();
		this.name = name;
		this.path = path;
	}

	public FileTree() {
		super();
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getDirPath() {
		return dirPath;
	}
	public void setDirPath(String dirPath) {
		this.dirPath = dirPath;
	}
 
	public Boolean getIsParent() {
		return isParent;
	}

	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}

	public List<FileTree> getChildren() {
		return children;
	}

	public void setChildren(List<FileTree> children) {
		this.children = children;
	}

	@Override
	public String toString() {
		return "FileTree [name=" + name + ", path=" + path + ", dirPath="
				+ dirPath + "]";
	}

	public Boolean getOpen() {
		return open;
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}
	
}
