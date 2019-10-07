package com.dengqiang.page;

/**
 * 数据字典明细查询对象
 * @author dengqiang
 *
 */
public class DictionaryDetailQuery extends ObjectQuery {
	private Long dictionary;
	@Override
	public void addHql() {
		 if (dictionary!=null) {
			addHql(" o.dictionary.id=? ", dictionary);
		}
	}
	public Long getDictionary() {
		return dictionary;
	}
	public void setDictionary(Long dictionary) {
		this.dictionary = dictionary;
	}

}
