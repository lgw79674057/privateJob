package com.xuaxi.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.xuaxi.domain.ChangeRecordDomain;
import com.xuaxi.service.IChangeRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xuaxi.framework.core.server.AbstractBaseService;
import com.xuaxi.framework.primarkey.PrimaryKeyGenerator;
import com.xuaxi.service.utils.SecurityContextHelper;
import com.xuaxi.dao.ICertFileDao;
import com.xuaxi.domain.CertFileDomain;
import com.xuaxi.domain.EnterpriseDomain;
import com.xuaxi.entity.CertFileEntity;
import com.xuaxi.service.ICertFileService;
import com.xuaxi.service.IEnterpriseService;

@Service("certFileServiceImpl")
public class CertFileServiceImpl extends AbstractBaseService<CertFileDomain, CertFileEntity, Long> implements ICertFileService{

	@Autowired
	private ICertFileDao certFileDao;
	
	@Autowired
	private IEnterpriseService enterpriseService;

	@Autowired
	private IChangeRecordService changeRecordService;

	@Autowired
	public CertFileServiceImpl(ICertFileDao dao) {
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

	@Transactional
	@Override
	public void modifys(List<CertFileDomain> domains) {
		Long enterpriseId=SecurityContextHelper.getCurrentUser().getEnterpriseId();
		if(domains==null||domains.size()==0){
			certFileDao.removeByEnterprise(enterpriseId);
			return;
		}
		List<CertFileDomain> list=super.findByBeanPropName("enterpriseId", enterpriseId);
		List<Long> removes=new ArrayList<>();
		List<ChangeRecordDomain> changer=new ArrayList<>();
		for(CertFileDomain cfd : list){
			boolean isHas=false;
			for(CertFileDomain dmoan : domains){
				if(cfd.getId().equals(dmoan.getId())){
					if(checkIsChange(dmoan.getCertType(),cfd.getCertType())){
						changer.add(new ChangeRecordDomain("0",enterpriseId,"certType","证照类型",getTypeName(dmoan.getCertType()),getTypeName(cfd.getCertNo())));
					}
					if(checkIsChange(dmoan.getCertNo(),cfd.getCertNo())){
						changer.add(new ChangeRecordDomain("0",enterpriseId,"certNo","证照号码",dmoan.getCertNo(),cfd.getCertNo()));
					}
					isHas=true;
					break;
				}
			}
			if(!isHas){
				removes.add(cfd.getId());
			}
		}
		if(changer.size()>0){
			changeRecordService.create(changer);
		}
		if(removes.size()>0){
			super.remove(removes);
		}
		List<CertFileDomain> insert=new ArrayList<>();
		for(CertFileDomain dmoan : domains){
			if(dmoan.getId()==null||dmoan.getId().equals(0)){
				dmoan.setEnterpriseId(enterpriseId);
				insert.add(dmoan);
			}else{
				dmoan.setEnterpriseId(null);
				super.merge(dmoan);
			}
		}
		if(insert.size()>0){
			super.create(insert);
		}
		if(insert.size()>0||removes.size()>0||domains.size()>0){
			EnterpriseDomain edm=new EnterpriseDomain();
			edm.setId(enterpriseId);
			edm.setAudit(false);
			edm.setDisable(true);
			enterpriseService.superMerge(edm);
		}
	}

	private boolean checkIsChange(String str1,String str2){
		str1=(str1==null?"":str1.trim());
		str2=(str2==null?"":str2.trim());
		return !str1.equals(str2);
	}

	public String getTypeName(String type){
		if(type.equals("1")){
			return "统一社会信用代码";
		}if(type.equals("2")){
			return "营业执照号码";
		}if(type.equals("3")){
			return "税务登记证号码";
		}if(type.equals("4")){
			return "组织机构代码";
		}
		return "";
	}

}
