package ca.app.security.oauth;

public interface OAuthUrlSigner {
	public String sign(String urlString);
}