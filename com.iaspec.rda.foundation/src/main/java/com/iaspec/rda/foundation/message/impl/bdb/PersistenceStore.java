/*
 * Copyright (c) 2012 iASPEC. All Rights Reserved.
 */


package com.iaspec.rda.foundation.message.impl.bdb;


import java.io.Serializable;
import java.util.List;
import java.util.Map;


/**
 * Class description goes here. User: Peter
 * 
 * @author <a href="mailto:yang.li@iaspec.com">Peter</a>
 * @version 1.00 13-1-11
 */
public interface PersistenceStore<T extends Serializable>
{
	/**
	 * 写入一条数据。会自动生成一个与之对应的key。
	 * 
	 * @param data
	 * @return 数据Key。如果没有Key，则返回<code>null</code>。
	 */
	String write(String sessionKey, T data);

	/**
	 * 写入多条数据。
	 * 
	 * @param dataList
	 */
	void batchWrite(List<T> dataList);

	/**
	 * 读一条数据。<br>
	 * 如果读多条数据的情况下，尽量使用{@link #batchRead(int)}方法，有更好的效率。
	 * 
	 * @return 读到的数据。如果store中没有数据，则返回<code>null</code>。
	 */
	Map.Entry<String, T> read();

	Map.Entry<String, T> read(String sessionKey);

	/**
	 * 读多条数据。
	 * 
	 * @param count
	 *            要读数据的条数
	 * @return 读到的数据。如果store中没有数据，则返回空的Map(即size ==0)。
	 */
	Map<String, T> batchRead(int count);

	/**
	 * 删除一条数据。
	 * 
	 * @param key
	 */
	void delete(String key);

	/**
	 * 删除多条数据。
	 * 
	 * @param keys
	 *            要删除数据的Key
	 */
	void delete(List<String> keys);
}
