package org.armitage.inc.AAInfo.service.impl;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.armitage.inc.AAInfo.entity.User;
import org.armitage.inc.AAInfo.extra.PushMessageDto;
import org.armitage.inc.AAInfo.service.PushService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class PushServiceImpl implements PushService{
	@Autowired
	private Logger logger;
	
	private static final String POST_URL = "https://api.pushover.net/1/messages.json";
	private static final String TOKEN_ID = "ap5oyugh3yca7s1zt27ypff7dqu3f8";
	
	@Override
	@Async
	public void sendPushMessage(User user, String message){
		logger.info("begin");
        logger.info("thread: "+Thread.currentThread());
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(POST_URL);
		
		PushMessageDto data = new PushMessageDto();
		data.setToken(TOKEN_ID);
		data.setMessage(message);
		data.setUser(user.getPushToken());
		
		List<NameValuePair> postData = data.generatePostData();
		if(postData == null){
			logger.error("couldnt send push message. Dto data: "+data.toString());
			return;
		}
        post.setEntity(new UrlEncodedFormEntity(postData, Charset.defaultCharset()));
        
        HttpResponse response = null;
        try{
        	response = httpClient.execute(post);
        	StatusLine httpResponse = response.getStatusLine();
            logger.info(String.format("finished with http response: %s (%s)",httpResponse.getStatusCode(),httpResponse.getReasonPhrase()));
        }catch(ClientProtocolException e){
        	logger.error("couldnt send message. Details: "+e.getMessage());
        }catch(IOException e){
        	logger.error("caught an io exception while trying to send a http request. Details: "+e.fillInStackTrace());
        }
        logger.info("end");
	}
}
