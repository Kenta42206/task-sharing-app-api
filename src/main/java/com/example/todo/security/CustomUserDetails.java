package com.example.todo.security;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.todo.entity.User;

public class CustomUserDetails implements UserDetails {
    private final User user; // User エンティティのインスタンス

    public CustomUserDetails(User user) {
        this.user = user;
    }

    /**
	 * ロールの取得（今回は使わないのでnullをリターン）
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}


    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }
    
    /**
	 * アカウントが有効期限でないか(使わないので常にtrue)
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	/**
	 * アカウントがロックされていないか(使わないので常にtrue)
	 */
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	/**
	 * 認証情報が有効期限切れでないか(使わないので常にtrue)
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	/**
	 * アカウントが有効であるかどうか(使わないので常にtrue)
	 */
	@Override
	public boolean isEnabled() {
		return true;
	}

    public Long getId() {
        return user.getId(); // User ID の取得
    }
}