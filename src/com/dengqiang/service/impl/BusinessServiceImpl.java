package com.dengqiang.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dengqiang.bean.Business;
import com.dengqiang.page.BusinessQuery;
import com.dengqiang.page.PageList;
import com.dengqiang.service.IBusinessService;
import com.dengqiang.util.MD5Util;

/**
 * 实现商家业务逻辑层service接口的具体方法
 * @author dengqiang
 */
@Service
@Transactional
public class BusinessServiceImpl extends BaseServiceImpl<Business> implements
IBusinessService {
	public boolean checkName(String name)throws Exception{
		String hql=	"select count(o) from "+entityClass.getName()+" o where o.name=?";
		List<Long> list= baseDAO.findByHql(hql, name);
		if (list.get(0)==0) {
			return true;
		}
		return  false;
	}

	@Override
	public Business checkLogin(String loginName, String pwd)throws Exception{
//		 pwd=MD5Util.MD5(pwd);
		 String hql="from Business o where (o.loginName=? or o.phone=?)  and o.level<2";
		 List list=baseDAO.findByHql(hql, loginName.trim(),pwd.trim());
		 if (list.size()>0) {
			 return (Business)list.get(0);
		}else{
			return null;
		}
	}

	@Override
	public boolean checkRegister()throws Exception{
//		String hql="select o.license as license from Business o where o.level=1";
//		Object license=baseDAO.findOneColumnByHql(hql);
//		if (license!=null) {
//			String code=MD5Util.getGeneratedCode();
//			if (code.equals(license)) {
//				return true;
//			}
//		}
		return true;
	}

	@Override
	public void saveLicense(String license)throws Exception{
		Business business=checkInit();
		business.setLicense(license);
		baseDAO.update(business);
	}

	@Override
	public Business checkInit()throws Exception{
		String hql="select o from Business o where o.level=1";
		List list=baseDAO.findByHql(hql);
		if (list.size()>0) {
			return (Business) list.get(0);
		}
		return null;
	}

	@Override
	public void deleteBusiness(Long id)throws Exception{
		 Business business=get(id);
		 business.setLevel(2);
		 update(business);
	}

	@Override
	public PageList<Business> getBusinessList(BusinessQuery query)throws Exception{
		return findQuery(query);
	}

	private void updateBusiness(String filed,String val,Long id) throws Exception{
		StringBuilder hql=new StringBuilder("update from Business set ");
		if (StringUtils.isNotBlank(val)) {
			hql.append(filed+"=? where id=?");
			baseDAO.execHQL(hql.toString(),val,id);
		}
	}
	
	@Override
	public void updateBusiness(Business business)throws Exception{
		Long id=business.getId();
		updateBusiness("businessName", business.getBusinessName(), id);
		updateBusiness("phone", business.getPhone(), id);
		updateBusiness("district", business.getDistrict(), id);
		updateBusiness("email", business.getEmail(), id);
		updateBusiness("address", business.getAddress(), id);
		updateBusiness("license", business.getLicense(), id);
		StringBuilder hql=new StringBuilder("update from Business set ");
		hql.append("level=? "); 
		hql.append(" where id=?");
		baseDAO.execHQL(hql.toString(),business.getLevel(),id);
	}

	@Override
	public void updatePassword(Long id, String newPwd)throws Exception{
		String hql="update from Business set password=? where id=?";
		baseDAO.execHQL(hql,newPwd, id);
	}

	@Override
	public boolean checkPassword(String oldPwd,Long id)throws Exception{
		 String hql="select o.password from Business o where o.id=?";
		String pwd=(String) baseDAO.findOneColumnByHql(hql, id);
		if (StringUtils.isNotBlank(pwd)&&pwd.equals(oldPwd)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean checkData(String obj, Integer key,Long id)throws Exception{
		 StringBuilder builder=new StringBuilder("select");
		 String column="loginName";//登录名101
		 switch (key) {
		case 102://电话号码
			column="phone";
			break;
		case 103://email
			column="email";
			break;
		}
		 builder.append(" o.").append(column).append(" from ");
		 builder.append(" Business o ").append(" where o.").append(column).append("=?");
		 if (id!=null) {
			 builder.append(" and o.id<>?");
		 }
		 Object object= baseDAO.findOneColumnByHql(builder.toString(), obj,id);
		 if (object!=null) {
			return true;
		}
		return false;
	}
}
