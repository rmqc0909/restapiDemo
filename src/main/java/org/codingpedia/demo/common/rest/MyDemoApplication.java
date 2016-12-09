package org.codingpedia.demo.common.rest;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;

/**
 * Registers the components to be used by the JAX-RS application  
 * 
 * @author ama
 *
 */
public class MyDemoApplication extends ResourceConfig {

    /**
	* Register JAX-RS application components.
	*/	
	public MyDemoApplication(){
		// 服务类所在的包路径
		packages("org.codingpedia.demo");

		register(RequestContextFilter.class);
		// 注册JSON转换器
		register(JacksonJsonProvider.class);
		// 注册 MultiPart
		register(MultiPartFeature.class);
	}
}
