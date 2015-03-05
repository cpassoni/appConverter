package ca.app.security.oauth;

import ca.app.config.ServerConfiguration;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.exception.OAuthException;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.transport.http.HTTPConduit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.HttpURLConnection;
import java.net.URL;

@Component("oauthPhaseInterceptor")
public class OAuthPhaseInterceptor<T extends Message> extends AbstractPhaseInterceptor<T> {
	@Autowired
	private ServerConfiguration serverConfiguration;

	private OAuthConsumer consumer;

	public OAuthPhaseInterceptor() {
		super(Phase.SEND);
	}

	@Override
	public void handleMessage(T message) throws Fault {
		if (consumer == null) {
			consumer = new DefaultOAuthConsumer(serverConfiguration.getOAuthConsumerKey(), serverConfiguration.getOAuthConsumerSecret());
		}
        System.out.printf("Entering handleMessage \n");
		HttpURLConnection connect = (HttpURLConnection) message.get(HTTPConduit.KEY_HTTP_CONNECTION);
		if (connect == null) {
			return;
		}
		URL url = connect.getURL();
		if (url == null) {
			return;
		}
        System.out.printf("Request: %s \n", url);
		try {
			consumer.sign(connect);
            System.out.printf("Request: %s signed\n", url);
		} catch (OAuthException e) {
			throw new Fault(e);
		}
	}
}
