package com.acs.config;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.acs.filter.HtmlCharacterEscapes;
import com.acs.interceptor.LoggerInterceptor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@EnableWebMvc
@Configuration
@RequiredArgsConstructor
@ComponentScan("com.acs")
public class WebConfig implements WebMvcConfigurer {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Bean
	public HttpSessionListener httpSessionListener(){
	    return new SessionListener();
	}
	
	/**
	 * 메세지 소스를 생성한다.
	 */
	@Bean
	public ReloadableResourceBundleMessageSource messageSource() {
		ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
		// 메세지 프로퍼티파일의 위치와 이름을 지정한다.
		source.setBasename("classpath:/messages/message");
		// 기본 인코딩을 지정한다.
		source.setDefaultEncoding("UTF-8");
		// 프로퍼티 파일의 변경을 감지할 시간 간격을 지정한다.
		source.setCacheSeconds(60);
		// 없는 메세지일 경우 예외를 발생시키는 대신 코드를 기본 메세지로 한다.
		source.setUseCodeAsDefaultMessage(true);
		return source;
	}

	/**
	 * 변경된 언어 정보를 기억할 로케일 리졸버를 생성한다. 여기서는 세션에 저장하는 방식을 사용한다.
	 * 
	 * @return
	 */
	@Bean
	public SessionLocaleResolver localeResolver() {
		return new SessionLocaleResolver();
	}

	/*
	 * CORS (strict-origin-when-cross-origin)
	 */
	/*
	 * @Override public void addCorsMappings(CorsRegistry registry) {
	 * registry.addMapping("/**") .allowedOrigins("*") .allowedHeaders()
	 * .allowedMethods(HttpMethod.GET.name(),HttpMethod.HEAD.name(),
	 * HttpMethod.POST.name(), HttpMethod.PUT.name(), HttpMethod.DELETE.name())
	 * .allowCredentials(true).maxAge(3600); }
	 */
	@Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
        			.allowedOrigins("https://localhost:55756", "http://localhost", "http://localhost:3000")
                	//.allowedOriginPatterns("*") // SpringBoot2.4.0 [allowedOriginPatterns]代替[allowedOrigins]
                	//.allowedMethods("*")
                	.allowedMethods(
                			HttpMethod.POST.name(),
                			HttpMethod.GET.name(),
                	    	HttpMethod.HEAD.name(),
                	    	HttpMethod.OPTIONS.name(),
                	    	HttpMethod.PUT.name(),
                	    	HttpMethod.DELETE.name())
                	.maxAge(3600)
                	.allowCredentials(true);
        
        logger.info(" registry \t:  " + registry);
    }

	/*
	@Override
	public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*") // SpringBoot2.4.0 [allowedOriginPatterns]代替[allowedOrigins]
                .allowedMethods("*")
                .maxAge(3600)
                .allowCredentials(true);
    }
	*/
	
	
	
	/**
	 * 언어 변경을 위한 인터셉터를 생성한다.
	 */
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
		interceptor.setParamName("lang");
		return interceptor;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//registry.addInterceptor(new LoggerInterceptor()).addPathPatterns("/**")
				//.excludePathPatterns("/api/**/").excludePathPatterns("/").excludePathPatterns("/index"); // 로그인 쪽은 예외처리를  한다.
		
		registry.addInterceptor(new LoggerInterceptor()).addPathPatterns("/**")
		.excludePathPatterns("/sms/**") 						    	 // 문자 발송 확인화면
		.excludePathPatterns("/").excludePathPatterns("/index"); 		//  로그인 쪽은 예외처리를 한다.
		
		
		
		/**
		 * 언어 변경을 위한 인터셉터
		 */
		registry.addInterceptor(localeChangeInterceptor());

	}

	// 리소스 경로
	private static final String CLASSPATH_RESOURCE_LOCATIONS = "classpath:/static/";

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/").setCachePeriod(20);
		registry.addResourceHandler("/css/**").addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS + "css/")
				.setCachePeriod(31536000);
		registry.addResourceHandler("/font/**").addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS + "font/")
		.setCachePeriod(31536000);
		registry.addResourceHandler("/image/**").addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS + "image/")
				.setCachePeriod(31536000);
		registry.addResourceHandler("/img/**").addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS + "img/")
				.setCachePeriod(31536000);
		registry.addResourceHandler("/js/**").addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS + "js/")
				.setCachePeriod(31536000);
		registry.addResourceHandler("/common/**").addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS + "common/")
				.setCachePeriod(31536000);
		registry.addResourceHandler("/upload/**").addResourceLocations("file:///D:/4k/upload/")
		//registry.addResourceHandler("/upload/**").addResourceLocations("/web/was/fileupload/")
				.setCachePeriod(31536000);
	}


	@Bean
	MappingJackson2JsonView jsonView() {
		return new MappingJackson2JsonView();
	}

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Bean
	public MappingJackson2HttpMessageConverter jsonEscapeConverter() {
		ObjectMapper copy = objectMapper.copy();
		copy.getFactory().setCharacterEscapes(new HtmlCharacterEscapes());
		return new MappingJackson2HttpMessageConverter(copy);
	}
		
	private final int MAX_SIZE = 20 * 1024 * 1024;

	 @Bean 
	 public MultipartResolver multipartResolver() { 
		 CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		 multipartResolver.setMaxUploadSize(MAX_SIZE); // 20MB
		 multipartResolver.setMaxUploadSizePerFile(MAX_SIZE); // 20MB
		 multipartResolver.setMaxInMemorySize(0); return multipartResolver;
	  }
	
    @Bean
    public ConfigurableServletWebServerFactory webServerFactory() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/error"));
        return factory;
    }


}
