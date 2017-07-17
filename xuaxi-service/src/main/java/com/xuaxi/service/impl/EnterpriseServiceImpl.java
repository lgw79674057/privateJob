package com.xuaxi.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xuaxi.domain.UserEnterpriseDomain;
import com.xuaxi.entity.UserEnterpriseEntity;
import com.xuaxi.entity.UserEntity;
import com.xuaxi.framework.core.entity.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xuaxi.framework.core.exceptions.ApiException;
import com.xuaxi.framework.core.query.Page;
import com.xuaxi.framework.core.server.AbstractBaseService;
import com.xuaxi.framework.primarkey.PrimaryKeyGenerator;
import com.xuaxi.service.utils.SecurityContextHelper;
import com.alibaba.fastjson.JSON;
import com.xuaxi.dao.IEnterpriseDao;
import com.xuaxi.domain.ChangeRecordDomain;
import com.xuaxi.domain.EnterpriseDomain;
import com.xuaxi.domain.InvoiceDomain;
import com.xuaxi.entity.EnterpriseEntity;
import com.xuaxi.service.IChangeRecordService;
import com.xuaxi.service.IEnterpriseService;
import com.xuaxi.service.IFollowService;
import com.xuaxi.service.IInvoiceService;
import com.xuaxi.service.IViewPassService;

@Service("enterpriseServiceImpl")
public class EnterpriseServiceImpl extends AbstractBaseService<EnterpriseDomain, EnterpriseEntity, Long> implements IEnterpriseService{

	@Autowired
	private IEnterpriseDao enterpriseDao;
	
	@Autowired
	private IChangeRecordService changeRecordService;
	
	@Autowired
	private IInvoiceService invoiceService;
	
	@Autowired
	private IViewPassService viewPassService;
	
	@Autowired
	public EnterpriseServiceImpl(IEnterpriseDao dao) {
		super(dao);
	}

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

	@Override
	public Page<EnterpriseDomain> search(String key, int pageSize, int pageNo) {
		Page<EnterpriseDomain> page = new Page<EnterpriseDomain>();
		Map<String,Object> map=new HashMap<>();
		int startNum=(pageNo - 1) *pageSize;
		map.put("key", "%"+key+"%");
		map.put("startNum", startNum);
		map.put("pageSize", pageSize);
		map.put("userId", SecurityContextHelper.getCurrentUserId());
		page.setTotalSize(enterpriseDao.search_count(map));
		if (page.getTotalSize() > startNum) {
			page.setDatas(copyFromEntity(enterpriseDao.search(map)));
		}
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		return page;
	}

	private EnterpriseDomain cleanProptey(EnterpriseDomain domain){
		domain.setCreateBy(null);
		domain.setCreateByName(null);
		domain.setCreateTime(null);
		domain.setModifyBy(null);
		domain.setModifyByName(null);
		domain.setModifyTime(null);
		domain.setAudit(false);
		domain.setDisable(true);
		domain.setDeleteTag(false);
		domain.setEnterpriseNo(null);
		domain.setSync(null);
		return domain;
	}
	@Autowired
	private IFollowService followService;
	
	@Transactional
	@Override
	public int merge(EnterpriseDomain domain) {
		domain.setId(SecurityContextHelper.getCurrentUser().getEnterpriseId());
		EnterpriseDomain oldDomain=super.findByPk(domain.getId());
		boolean sync="1".equals(domain.getSync());
		if(JSON.toJSONString(cleanProptey(oldDomain)).equals(JSON.toJSONString(cleanProptey(domain)))){
			throw new ApiException(0, "您未修改任何内容");
		}
		//domain.setName(null);
		domain.setEnterpriseNo(null);
		List<ChangeRecordDomain> changer=new ArrayList<>();
		boolean audit=false;
		if(checkIsChange(oldDomain.getAddress1(), domain.getAddress1())){
			changer.add(new ChangeRecordDomain("0",domain.getId(),"address1","地址1",domain.getAddress1(),oldDomain.getAddress1()));
		}
		boolean entName=false;
		if(checkIsChange(oldDomain.getName(), domain.getName())){
			changer.add(new ChangeRecordDomain("0",domain.getId(),"name","单位名称",domain.getName(),oldDomain.getName()));
			entName=true;
			audit=true;
		}
		
		if(checkIsChange(oldDomain.getAddress2(), domain.getAddress2())){
			changer.add(new ChangeRecordDomain("0",domain.getId(),"address2","地址2",domain.getAddress2(),oldDomain.getAddress2()));
		}
		
		if(checkIsChange(oldDomain.getAddress3(), domain.getAddress3())){
			changer.add(new ChangeRecordDomain("0",domain.getId(),"address3","地址3",domain.getAddress3(),oldDomain.getAddress3()));
		}
		
		if(checkIsChange(oldDomain.getPhone(), domain.getPhone())){
			changer.add(new ChangeRecordDomain("0",domain.getId(),"phone","企业电话",domain.getPhone(),oldDomain.getPhone()));
		}
		
		if(checkIsChange(oldDomain.getContacts(), domain.getContacts())){
			changer.add(new ChangeRecordDomain("0",domain.getId(),"contacts","联系人",domain.getContacts(),oldDomain.getContacts()));
		}
		
		if(checkIsChange(oldDomain.getcPhone(), domain.getcPhone())){
			changer.add(new ChangeRecordDomain("0",domain.getId(),"cPhone","联系电话",domain.getcPhone(),oldDomain.getcPhone()));
		}
		
		if(checkIsChange(oldDomain.getFax(), domain.getFax())){
			changer.add(new ChangeRecordDomain("0",domain.getId(),"fax","传真",domain.getFax(),oldDomain.getFax()));
		}
		if(checkIsChange(oldDomain.getLegalPerson(), domain.getLegalPerson())){
			changer.add(new ChangeRecordDomain("0",domain.getId(),"legalPerson","法人",domain.getLegalPerson(),oldDomain.getLegalPerson()));
		}
		if(sync||entName){
			InvoiceDomain invDomain=invoiceService.findByBeanPropName("enterpriseId", SecurityContextHelper.getCurrentUser().getEnterpriseId()).get(0);
			InvoiceDomain modifyDomain=new InvoiceDomain();
			modifyDomain.setId(invDomain.getId());
			if(entName){
				//if(checkIsChange(invDomain.getEnterpriseName(), domain.getName())){
					modifyDomain.setEnterpriseName(domain.getName());
					changer.add(new ChangeRecordDomain("1",domain.getId(),"enterpriseName","企业名称",domain.getName(),invDomain.getEnterpriseName()));
				//}
			}
			if(sync){
				if(checkIsChange(invDomain.getAddress(), domain.getAddress1())){
					modifyDomain.setAddress(domain.getAddress1());
					changer.add(new ChangeRecordDomain("1",domain.getId(),"address","企业地址",domain.getAddress1(),invDomain.getAddress()));
				}
				
				if(checkIsChange(invDomain.getPhone(), domain.getPhone())){
					modifyDomain.setPhone(domain.getPhone());
					changer.add(new ChangeRecordDomain("1",domain.getId(),"phone","企业电话",domain.getPhone(),invDomain.getPhone()));
				}
			}
			invoiceService.superMerge(modifyDomain);
			followService.updateNewChange(domain.getId());
		}
		
		if(domain.getDataLock()!=oldDomain.getDataLock()||checkIsChange(domain.getViewCode(), oldDomain.getViewCode())){
			viewPassService.removeByEnterprise(domain.getId());
		}
		
		if(changer.size()>0){
			changeRecordService.create(changer);
		}
		if(audit){
			domain.setAudit(false);
			domain.setDisable(true);
		}
		return super.merge(domain);
	}
	
	public int superMerge(EnterpriseDomain domain){
		return super.merge(domain);
	}

	@Override
	public Page<UserEnterpriseDomain> findEnterprises(String name, int pageSize, int pageNo) {
		Page<UserEnterpriseDomain> page = new Page<UserEnterpriseDomain>();
		Map<String,Object> map=new HashMap<>();
		int startNum=(pageNo - 1) *pageSize;
		map.put("key", "%"+name+"%");
		map.put("startNum", startNum);
		map.put("pageSize", pageSize);
		List<UserEnterpriseEntity> ueeList=enterpriseDao.findEnterprises(map);
		List<UserEnterpriseDomain> uedList= new ArrayList<UserEnterpriseDomain>();
		page.setTotalSize(enterpriseDao.findEnterprisesCount(map));
		if (page.getTotalSize() > startNum) {
			for (UserEnterpriseEntity uee:ueeList) {
				UserEnterpriseDomain ued= new UserEnterpriseDomain();
				BeanUtils.copyProperties(uee,ued);
				uedList.add(ued);
			}
			page.setDatas(uedList);
		}
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		return page;
	}

	private boolean checkIsChange(String str1,String str2){
		str1=(str1==null?"":str1.trim());
		str2=(str2==null?"":str2.trim());
		return !str1.equals(str2);
	}

}
