package com.taotao.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.service.SearchService;


/**
 * 商品搜索Service
 * <p>Title: SearchServiceImpl</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.com</p> 
 * @author	入云龙
 * @date	2015年9月12日上午9:11:58
 * @version 1.0
 */

@Service
public class SearchServiceImpl implements SearchService {

	@Value("${SEARCH_BASE_URL}")
	private String SEARCH_BASE_URL;
	@Value("${SEARCH_import_one_URL}")
	private String SEARCH_import_one_URL;
	
	@Override
	public TaotaoResult addOneItemTosearch(Long itemId) {
		// 调用taotao-search的服务,加入一个新的商品到搜索服务器
		//查询参数
		Map<String, String> param = new HashMap<>();
		param.put("itemId", itemId.toString());
		String fullUrl = SEARCH_BASE_URL+SEARCH_import_one_URL;
		try {
			String json = HttpClientUtil.doGet(fullUrl, param);
			TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, TaotaoResult.class);
			return taotaoResult;
		} catch (Exception e) {
			TaotaoResult taotaoResult = new TaotaoResult();
			taotaoResult.setStatus(500);
			taotaoResult.setMsg("服务器返回错误");
			return TaotaoResult.ok(taotaoResult);
		}
		
		
	}

}
