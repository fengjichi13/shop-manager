package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.pojo.TbContentExample.Criteria;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemExample;
import com.taotao.pojo.TbItemParamExample;
import com.taotao.service.ContentService;

/**
 * 内容管理
 * <p>Title: ContentServiceImpl</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.com</p> 
 * @author	入云龙
 * @date	2015年9月8日上午11:09:53
 * @version 1.0
 */
@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper  contentMapper;
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${REST_CONTENT_SYNC_URL}")
	private String REST_CONTENT_SYNC_URL;
	
	
	@Override
	public TaotaoResult insertContent(TbContent content) {
		//补全pojo内容
		content.setCreated(new Date());
		content.setUpdated(new Date());
		contentMapper.insert(content);
		
		//添加缓存同步逻辑
		try {
			HttpClientUtil.doGet(REST_BASE_URL + REST_CONTENT_SYNC_URL + content.getCategoryId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return TaotaoResult.ok();
	}


	@Override
	public EUDataGridResult getContentList(Integer page, Integer rows, Long categoryId) {
		//查询内容分类管理列表
		TbContentExample example = new TbContentExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andCategoryIdEqualTo(categoryId);
		//分页处理
		PageHelper.startPage(page, rows);
		List<TbContent> list = contentMapper.selectByExample(example);
		//创建一个返回值对象
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(list);
		//取记录总条数
		PageInfo<TbContent> pageInfo = new PageInfo<TbContent>(list);
		result.setTotal(pageInfo.getTotal());
		return result;
	}


	@Override
	public TaotaoResult updateContent(TbContent content) {
		//修改内容分类管理
		//补全pojo内容
		content.setCreated(new Date());
		content.setUpdated(new Date());
//		contentMapper.insert(content);
		contentMapper.updateByPrimaryKeyWithBLOBs(content);
		
		//添加缓存同步逻辑
		try {
			HttpClientUtil.doGet(REST_BASE_URL + REST_CONTENT_SYNC_URL + content.getCategoryId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return TaotaoResult.ok();
	}


	@Override
	public TaotaoResult deleteContent(String ids) {
//		System.out.println("ids:"+ids);
		//删除查询商品参数
		List<Long> id_list = new ArrayList<Long>(); 
		TbContent content = null;
		if(ids.trim()!=null&&ids.trim()!=""){
			String[] ids_string = ids.split(",");
			String ContentId = ids_string[0];
			content = contentMapper.selectByPrimaryKey(Long.parseLong(ContentId));
			for(int i=0;i<ids_string.length;i++){
				id_list.add(Long.parseLong(ids_string[i]));
			}
		}
		
		if(id_list.size()>0){
			TbContentExample example = new TbContentExample();
			Criteria criteria = example.createCriteria();
			criteria.andIdIn(id_list);
			contentMapper.deleteByExample(example);
		}
		
		//添加缓存同步逻辑
		if(content!=null){
			try {
				//在此表中删除的数据有着相同的getCategoryId
				HttpClientUtil.doGet(REST_BASE_URL + REST_CONTENT_SYNC_URL + content.getCategoryId());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return TaotaoResult.ok();
	}

}
