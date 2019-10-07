package com.dengqiang.service.impl;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dengqiang.bean.Business;
import com.dengqiang.bean.dictionary.DataDictionary;
import com.dengqiang.bean.dictionary.DictionaryDetail;
import com.dengqiang.service.IDataDictionaryService;
import com.dengqiang.service.IDictionaryDetailService;
import com.dengqiang.service.IInitLoadService;
@Service
@Transactional
public class InitLoadServiceImpl extends BaseServiceImpl<Business> implements
		IInitLoadService {
	@Autowired
	IDataDictionaryService dataDictionaryService;
	@Autowired
	IDictionaryDetailService dictionaryDetailService;
	
	@Override
	public void initProcedure() {
		ClassLoader loader = InitLoadServiceImpl.class.getClassLoader();
		InputStream inStream = loader.getResourceAsStream("sql.xml");
		try {
			SAXReader reader = new SAXReader();
			Document document = reader.read(inStream);
			Element rootElm = document.getRootElement();
			List<Element> nodes = rootElm.elements("item");
			for (Iterator<Element> it = nodes.iterator(); it.hasNext();) {
				Element elm = it.next();
				String sql = elm.getText(); 
				if (StringUtils.isNotBlank(sql)) {
					baseDAO.execSQL(sql);
				}
			}
			inStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initData() {
		try {
			int count=dataDictionaryService.queryCount();
			if (count>0) {
				return;
			}
			for (int i = 0; i < 8; i++) {
				DataDictionary dictionary=new DataDictionary(i);
				dataDictionaryService.save(dictionary);
				if (i==6) {
					DictionaryDetail detail=new DictionaryDetail();
					detail.setDictionary(dictionary);
					detail.setName("多层住宅");
					dictionaryDetailService.save(detail);
					detail=new DictionaryDetail();
					detail.setDictionary(dictionary);
					detail.setName("电梯公寓");
					dictionaryDetailService.save(detail);
					detail=new DictionaryDetail();
					detail.setDictionary(dictionary);
					detail.setName("写字楼");
					dictionaryDetailService.save(detail);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
