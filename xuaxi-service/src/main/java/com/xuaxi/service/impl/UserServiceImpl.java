package com.xuaxi.service.impl;

import com.xuaxi.dao.IFollowDao;
import com.xuaxi.domain.EnterpriseDomain;
import com.xuaxi.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xuaxi.framework.core.server.AbstractBaseService;
import com.xuaxi.framework.core.spring.SpringContextUtil;
import com.xuaxi.framework.primarkey.PrimaryKeyGenerator;
import com.xuaxi.dao.IUserDao;
import com.xuaxi.domain.CertFileDomain;
import com.xuaxi.domain.CodeDomain;
import com.xuaxi.domain.EnterpriseRegDomain;
import com.xuaxi.domain.UserDomain;
import com.xuaxi.entity.UserEntity;
import com.xuaxi.service.ICertFileService;
import com.xuaxi.service.ICodeService;
import com.xuaxi.service.IEnterpriseService;
import com.xuaxi.service.IInvoiceService;
import com.xuaxi.service.utils.PasswordTools;
import com.xuaxi.service.utils.SecurityContextHelper;

import java.util.List;

@Service("userServiceImpl")
public class UserServiceImpl extends AbstractBaseService<UserDomain, UserEntity, Long> implements IUserService {
	@Autowired
	private IUserDao iUserDao;

	@Autowired
	public UserServiceImpl(IUserDao dao) {
		super(dao);
	}
	
	@Autowired
	private IEnterpriseService enterpriseService;
	
	@Autowired
	private ICertFileService certFileService;
	
	@Autowired
	private IInvoiceService invoiceService;
	
	@Autowired
	private ICodeService codeService;


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
	@Transactional
	public void regEnterprise(EnterpriseRegDomain domain) {
		Long enterpriseId=getPrimaryKey();
		domain.getEnterpriseInfo().setId(enterpriseId);
		domain.getUserInfo().setEnterpriseId(enterpriseId);
		domain.getUserInfo().setPassword(SpringContextUtil.getBean(PasswordTools.class).encode(domain.getUserInfo().getPassword()));
		domain.getEnterpriseInfo().setAudit(false);
		domain.getEnterpriseInfo().setDisable(true);
		domain.getInvoiceInfo().setEnterpriseId(enterpriseId);
		domain.getUserInfo().setFollowed(0);
		domain.getUserInfo().setFollowedNum(50);
		enterpriseService.create(domain.getEnterpriseInfo());
		super.create(domain.getUserInfo());
		if(domain.getFilesInfo()!=null&&domain.getFilesInfo().size()>0){
			for(CertFileDomain cfd : domain.getFilesInfo()){
				cfd.setEnterpriseId(enterpriseId);
			}
			certFileService.create(domain.getFilesInfo());
		}
		invoiceService.create(domain.getInvoiceInfo());
		CodeDomain code=new CodeDomain();
		code.setId(codeService.findByBeanPropName("no", domain.getEnterpriseInfo().getEnterpriseNo()).get(0).getId());
		code.setEnterpriseId(enterpriseId);
		code.setState(true);
		codeService.merge(code);
		//判断是否有推荐人,如果有,查询出对应推荐人,将推荐数量+3
		if(domain.getUserInfo().getRecommendNo()!=null && !"".equals(domain.getUserInfo().getRecommendNo())){
			//查询出对应推荐人,如果有增加推荐数量,没有则不进行增加
			List<EnterpriseDomain> list=enterpriseService.findByBeanPropName("enterpriseNo",domain.getUserInfo().getRecommendNo());
			if(list!=null && list.size()>0){
				//正常应该取出一个,如果为2个为错误
				EnterpriseDomain enterpriseDomain=list.get(0);
				List<UserDomain> userDomains=super.findByBeanPropName("enterpriseId",enterpriseDomain.getId());
				UserDomain userDomain=userDomains.get(0);
				userDomain.setFollowedNum(userDomain.getFollowedNum()+3);
				super.merge(userDomain);
			}
		}
	}

	@Override
	public void removeFollowed(Long userId) {
		iUserDao.removeFollowEd(userId);
	}
}
