package com.xuaxi.service.utils;

import org.springframework.security.crypto.password.PasswordEncoder;
/**
 * 密码加密
 * @author xiazhijian
 *
 */
public class PasswordTools implements PasswordEncoder{

	private PasswordEncoder passwordEncoder;
	
	@Override
	public String encode(CharSequence paramCharSequence) {
		return passwordEncoder.encode(paramCharSequence);
	}

	@Override
	public boolean matches(CharSequence paramCharSequence, String paramString) {
		return passwordEncoder.matches(paramCharSequence, paramString);
	}

	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
}
