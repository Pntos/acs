package com.acs.config;

import java.io.IOException;
import java.net.PasswordAuthentication;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig{
	@Bean
	public RestTemplate restTemplate() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
    		/*	
			TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;
    		SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
                    		.loadTrustMaterial(null, acceptingTrustStrategy)
                    		.build();
    		SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);
    		CloseableHttpClient httpClient = HttpClients.custom()
                    		.setSSLSocketFactory(csf)
                    		.build();
    		*/
			
    		HttpComponentsClientHttpRequestFactory requestFactory =
                    		new HttpComponentsClientHttpRequestFactory();
    		//requestFactory.setHttpClient(httpClient);
    		requestFactory.setHttpClient(httpClient());
    		
    		//RestTemplate restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(requestFactory));
    		RestTemplate restTemplate = new RestTemplate(requestFactory);
    		/*
    		restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory() {
		      @Override
		      protected HttpContext createHttpContext(HttpMethod httpMethod, URI uri) {
		    	HttpClientContext context = new HttpClientContext();  
		        if (context.getAttribute(HttpClientContext.COOKIE_STORE) == null) {
		          context.setAttribute(HttpClientContext.COOKIE_STORE, new BasicCookieStore());
		          Builder builder = RequestConfig.custom().setRedirectsEnabled(false);
		          context.setRequestConfig(builder.build());
		        }
		        return context;
		      }
		    }
    		);
    		*/
    		restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
    	        public boolean hasError(ClientHttpResponse response) throws IOException {
    	            HttpStatus statusCode = response.getStatusCode();
    	            return statusCode.series() == HttpStatus.Series.SERVER_ERROR;
    	        }
    	    });
    		restTemplate.headForHeaders("https://10.85.203.94:55756/");
    		return restTemplate;
 	}
		
	@Bean
    public CloseableHttpClient httpClient() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
        String user = "administrator";
        String password = "!086Piy?";
        String domain = "https://10.85.203.94:55756/Acs/Api/SystemFacade/GetSystem";
        
        TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;
		SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
                		.loadTrustMaterial(null, acceptingTrustStrategy)
                		.build();
		SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);
		
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new NTCredentials(user, password, null, null));

        CloseableHttpClient httpclient = HttpClientBuilder
                .create()
                .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                .setDefaultCredentialsProvider(credentialsProvider)
                .setSSLSocketFactory(csf)
                .setMaxConnTotal(100)    //최대 오픈되는 커넥션 수
                .setMaxConnPerRoute(5)   //IP, 포트 1쌍에 대해 수행할 커넥션 수
                .evictIdleConnections(2000L, TimeUnit.MILLISECONDS) //서버에서 keepalive시간동안 미 사용한 커넥션을 죽이는 등의 케이스 방어로 idle커넥션을 주기적으로 지움
                .build();
        return httpclient;
    }
	
    
}

