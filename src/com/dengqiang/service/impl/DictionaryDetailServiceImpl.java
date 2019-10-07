package com.dengqiang.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dengqiang.bean.HousingInfo;
import com.dengqiang.bean.dictionary.DataDictionary;
import com.dengqiang.bean.dictionary.DictionaryDetail;
import com.dengqiang.service.IDictionaryDetailService;
@Service
@Transactional
public class DictionaryDetailServiceImpl extends BaseServiceImpl<DictionaryDetail> implements
		IDictionaryDetailService {

	@Override
	public List<DictionaryDetail> getDetailsByName(String name) throws Exception{
		String hql="from DictionaryDetail o  where o.dictionary.name=?";
		return baseDAO.findByHql(hql, name);
	}

	@Override
	public void updateDictionary(HousingInfo housingInfo) throws Exception {
		housingInfo.setDistrict(saveDictionaryDetail(housingInfo.getDistrict(),0));
		housingInfo.setPlate(saveDictionaryDetail(housingInfo.getPlate(),1));
		housingInfo.setCommunity(saveDictionaryDetail(housingInfo.getCommunity(),2));
		housingInfo.setHouseType(saveDictionaryDetail(housingInfo.getHouseType(),3));
		housingInfo.setRentType(saveDictionaryDetail(housingInfo.getRentType(),4));
		housingInfo.setDecorationType(saveDictionaryDetail(housingInfo.getDecorationType(),5));
		housingInfo.setHousingType(saveDictionaryDetail(housingInfo.getHousingType(),6));
	}
	private DictionaryDetail saveDictionaryDetail(DictionaryDetail detail,int key) throws Exception {
		if (detail==null) {
			return null;
		}
		String hql="select o from DictionaryDetail o where o.name=?";
		List<DictionaryDetail> list= baseDAO.findByHql(hql, detail.getName());
		if (list.size()==0) {
			DataDictionary dictionary= getDataDictionaryName(DataDictionary.getDictionaryName(key));
			detail.setDictionary(dictionary);
			save(detail);
		}else{
			DictionaryDetail dic=(DictionaryDetail)list.get(0);
			detail=dic;
		}
		return detail;
	}

	private DataDictionary getDataDictionaryName(String name) throws Exception {
		String hql="from DataDictionary o  where o.name=?";
		List<DataDictionary> list=baseDAO.findByHql(hql,name);
		if (list.size()>0) {
			return list.get(0);
		}else{
			return null;
		}
	}
}
