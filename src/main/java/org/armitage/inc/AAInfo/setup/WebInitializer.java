package org.armitage.inc.AAInfo.setup;

import java.util.ArrayList;
import java.util.List;

import org.armitage.inc.AAInfo.setup.config.AsyncConfig;
import org.armitage.inc.AAInfo.setup.config.HibernateConfig;
import org.armitage.inc.AAInfo.setup.config.MailConfig;
import org.armitage.inc.AAInfo.setup.config.SpringConfig;
import org.armitage.inc.AAInfo.setup.config.SpringWebSecurityConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer{

	@Override
	protected Class<?>[] getRootConfigClasses() {
		List<Class<?>> configClasses = new ArrayList<Class<?>>();
		configClasses.add(SpringConfig.class);
		configClasses.add(HibernateConfig.class);
		configClasses.add(SpringWebSecurityConfig.class);
		configClasses.add(LoggerInitializer.class);
		configClasses.add(DataFiller.class);
		configClasses.add(MailConfig.class);
		configClasses.add(AsyncConfig.class);
		
		
		Class<?> configs[] = new Class[]{};
		configs = configClasses.toArray(configs);
		return configs;
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return null;
	}

	@Override
	protected String[] getServletMappings() {
		String[] mapping = new String[]{"/"};
		return mapping;
	}
}
