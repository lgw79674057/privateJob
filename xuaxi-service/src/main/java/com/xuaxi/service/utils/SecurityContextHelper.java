package com.xuaxi.service.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.xuaxi.framework.core.entity.User;

public class SecurityContextHelper {
	
	public static Long getCurrentUserId() {
		User user = getCurrentUser();
		if (user != null) {
			return user.getId();
		}
		return null;
	}

	public static String getCurrentUserName() {
		User user = getCurrentUser();
		if (user != null) {
			return user.getLoginName();
		}
		return null;
	}

	public static User getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null&&authentication.getDetails() instanceof User) {
			return (User) authentication.getDetails();
		}
		return null;
	}
	
	public static boolean isLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object detail = authentication.getDetails();
            if (detail instanceof User) {
                return true;
            }
        }
        return false;
    }
}
