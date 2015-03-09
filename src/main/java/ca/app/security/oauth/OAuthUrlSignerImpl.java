package ca.app.security.oauth;


import ca.app.config.ServerConfiguration;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.exception.OAuthException;
import oauth.signpost.signature.QueryStringSigningStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("oauthUrlSigner")
public class OAuthUrlSignerImpl implements OAuthUrlSigner {
	private final OAuthConsumer consumer;

	@Autowired
	public OAuthUrlSignerImpl(ServerConfiguration serverConfiguration) {
		consumer = new DefaultOAuthConsumer(serverConfiguration.getOAuthConsumerKey(), serverConfiguration.getOAuthConsumerSecret());
		consumer.setSigningStrategy(new QueryStringSigningStrategy());
	}

	@Override
	public String sign(String urlString) {
        System.out.printf("Signing URL: %s.\n", urlString);
		try {
			String signedUrl = consumer.sign(urlString);
            System.out.printf("Signing URL: %s.\n", signedUrl);
            return signedUrl;
		} catch (OAuthException e) {
            System.err.println(e);
            return urlString;
		}
	}
}
