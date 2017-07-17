package com.xuaxi.domain;

import com.xuaxi.framework.core.domain.AbstractDomain;

/**
 * Created by lgw on 17/6/24.
 */
public class UserEnterpriseDomain extends AbstractDomain<Long>  {
    private String enterpriseName;
    private String enterpriseNo;
    private Long enterpriseId;
    private Long userId;
    private String userName;

    /**
     * 证照照片
     * @return
     */
    private String certPic;

    /**
     * 已关注
     */
    private Integer followed;
    /**
     * 可关注
     */
    private Integer followedNum;

    private Boolean disable;

    public Boolean getDisable() {
        return disable;
    }

    public void setDisable(Boolean disable) {
        this.disable = disable;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getEnterpriseNo() {
        return enterpriseNo;
    }

    public void setEnterpriseNo(String enterpriseNo) {
        this.enterpriseNo = enterpriseNo;
    }

    public Long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCertPic() {
        return certPic;
    }

    public void setCertPic(String certPic) {
        this.certPic = certPic;
    }

    public Integer getFollowed() {
        return followed;
    }

    public void setFollowed(Integer followed) {
        this.followed = followed;
    }

    public Integer getFollowedNum() {
        return followedNum;
    }

    public void setFollowedNum(Integer followedNum) {
        this.followedNum = followedNum;
    }
}
