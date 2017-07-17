package com.xuaxi.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.xuaxi.service.IFollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xuaxi.framework.core.exceptions.ApiException;
import com.xuaxi.framework.core.server.AbstractBaseService;
import com.xuaxi.framework.primarkey.PrimaryKeyGenerator;
import com.xuaxi.service.utils.SecurityContextHelper;
import com.alibaba.fastjson.JSON;
import com.xuaxi.dao.IInvoiceDao;
import com.xuaxi.domain.ChangeRecordDomain;
import com.xuaxi.domain.EnterpriseDomain;
import com.xuaxi.domain.InvoiceDomain;
import com.xuaxi.entity.InvoiceEntity;
import com.xuaxi.service.IChangeRecordService;
import com.xuaxi.service.IEnterpriseService;
import com.xuaxi.service.IInvoiceService;

@Service("invoiceServiceImpl")
public class InvoiceServiceImpl extends AbstractBaseService<InvoiceDomain, InvoiceEntity, Long> implements IInvoiceService{

	@Autowired
	public InvoiceServiceImpl(IInvoiceDao dao) {
		super(dao);
	}

	@Autowired
	private IChangeRecordService changeRecordService;
	
	@Autowired
	private IEnterpriseService enterpriseService;

	@Autowired
	private IFollowService followService;
	
	@Override
	protected Long getPrimaryKey() {
		return PrimaryKeyGenerator.SEQUENCE.next();
	}

	@Override
	protected Long getLoginUserId() {
		return SecurityContextHelper.getCurrentUserId();
	}

	@Override
	protected String getLoginUserName() {
		return SecurityContextHelper.getCurrentUserName();
	}
	
	
	private InvoiceDomain cleanProptey(InvoiceDomain domain){
		domain.setCreateBy(null);
		domain.setCreateByName(null);
		domain.setCreateTime(null);
		domain.setModifyBy(null);
		domain.setModifyByName(null);
		domain.setModifyTime(null);
		domain.setEnterpriseId(null);
		domain.setDeleteTag(false);
		return domain;
	}
	
	private boolean checkIsChange(String str1,String str2){
		str1=(str1==null?"":str1.trim());
		str2=(str2==null?"":str2.trim());
		return !str1.equals(str2);
	}

	@Transactional
	@Override
	public int merge(InvoiceDomain domain) {
		Long enterpriseId=SecurityContextHelper.getCurrentUser().getEnterpriseId();
		InvoiceDomain oldDomain = super.findByBeanPropName("enterpriseId", SecurityContextHelper.getCurrentUser().getEnterpriseId()).get(0);
		domain.setId(oldDomain.getId());
		if(JSON.toJSONString(cleanProptey(oldDomain)).equals(JSON.toJSONString(cleanProptey(domain)))){
			throw new ApiException(0, "您未修改任何内容");
		}
		domain.setEnterpriseName(null);
		List<ChangeRecordDomain> changer=new ArrayList<>();
//		if(checkIsChange(oldDomain.getEnterpriseName(), domain.getEnterpriseName())){
//			changer.add(new ChangeRecordDomain("1",domain.getId(),"enterpriseName","企业名称",domain.getEnterpriseName(),oldDomain.getEnterpriseName()));
//		}
		
		if(checkIsChange(oldDomain.getInvoiceType(), domain.getInvoiceType())){
			changer.add(new ChangeRecordDomain("1",enterpriseId,"invoiceType","纳税人类型",getTypeName(domain.getInvoiceType()),getTypeName(oldDomain.getInvoiceType())));
		}
		
		if(checkIsChange(oldDomain.getInvoiceCode(), domain.getInvoiceCode())){
			changer.add(new ChangeRecordDomain("1",enterpriseId,"invoiceCode","纳税识别号",domain.getInvoiceCode(),oldDomain.getInvoiceCode()));
		}
		
		if(checkIsChange(oldDomain.getAddress(), domain.getAddress())){
			changer.add(new ChangeRecordDomain("1",enterpriseId,"address","地址、电话",domain.getAddress(),oldDomain.getAddress()));
		}
		
//		if(checkIsChange(oldDomain.getPhone(), domain.getPhone())){
//			changer.add(new ChangeRecordDomain("1",enterpriseId,"phone","企业电话",domain.getPhone(),oldDomain.getPhone()));
//		}
		
		if(checkIsChange(oldDomain.getBankName(), domain.getBankName())){
			changer.add(new ChangeRecordDomain("1",enterpriseId,"bankName","开户行及账号",domain.getBankName(),oldDomain.getBankName()));
		}
		
//		if(checkIsChange(oldDomain.getDetilBack(), domain.getDetilBack())){
//			changer.add(new ChangeRecordDomain("1",enterpriseId,"detilBack","开户支行",domain.getDetilBack(),oldDomain.getDetilBack()));
//		}
//
//		if(checkIsChange(oldDomain.getBancAccount(), domain.getBancAccount())){
//			changer.add(new ChangeRecordDomain("1",enterpriseId,"bancAccount","开户账号",domain.getBancAccount(),oldDomain.getBancAccount()));
//		}
		if(changer.size()>0){
			changeRecordService.create(changer);
			followService.updateNewChange(enterpriseId);

		}
		EnterpriseDomain entdomain=new EnterpriseDomain();
		entdomain.setId(SecurityContextHelper.getCurrentUser().getEnterpriseId());
		entdomain.setDisable(true);
		entdomain.setAudit(false);
		enterpriseService.superMerge(entdomain);
		return super.merge(domain);
	}

	@Override
	public int superMerge(InvoiceDomain domain) {
		return super.merge(domain);
	}

	public String getTypeName(String type){
		if(type.equals("0")){
			return "非一般纳税人";
		}if(type.equals("1")){
			return "一般纳税人";
		}
		return "";
	}
}
