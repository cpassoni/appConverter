package ca.app.security.oauth;

import ca.app.config.ServerConfiguration;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.exception.OAuthException;
import oauth.signpost.http.HttpRequest;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.transport.http.HTTPConduit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.HttpURLConnection;
import java.net.URL;

@Component("oauthPhaseInterceptor")
public class OAuthPhaseInterceptor<T extends Message> extends AbstractPhaseInterceptor<T> implements HandlerInterceptor {
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

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        if (consumer == null) {
            consumer = new DefaultOAuthConsumer(serverConfiguration.getOAuthConsumerKey(), serverConfiguration.getOAuthConsumerSecret());
        }
        System.out.printf("Entering handleMessage \n");
//        HttpURLConnection connect = (HttpURLConnection) message.get(HTTPConduit.KEY_HTTP_CONNECTION);
//        if (connect == null) {
//            return;
//        }
//        URL url = connect.getURL();
//        if (url == null) {
//            return;
//        }

        try {
            consumer.sign(httpServletRequest);
            System.out.println(httpServletRequest.getRequestURL().toString());
        } catch (OAuthException e) {
            throw new Fault(e);
        }

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
