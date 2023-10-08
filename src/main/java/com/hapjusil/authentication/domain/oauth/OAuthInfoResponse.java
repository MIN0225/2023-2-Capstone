package com.hapjusil.authentication.domain.oauth;

import com.hapjusil.domain.OAuthProvider;

public interface OAuthInfoResponse {
    String getEmail();
    String getNickname();
    OAuthProvider getOAuthProvider();
}
