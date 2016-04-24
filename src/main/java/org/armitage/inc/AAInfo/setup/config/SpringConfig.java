package org.armitage.inc.AAInfo.setup.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;

@Configuration
@EnableWebMvc // -> mvc:annotation-driven
@ComponentScan({"org.armitage.inc.AAInfo.controller","org.armitage.inc.AAInfo.service"}) 
public class SpringConfig extends WebMvcConfigurerAdapter {
	@Bean
	public UrlBasedViewResolver viewResolver() {
		UrlBasedViewResolver viewResolver = new UrlBasedViewResolver();
		viewResolver.setViewClass(TilesView.class);
		return viewResolver;
	}

	@Bean
	public TilesConfigurer tilesConfigurer() {
		TilesConfigurer tilesConfigurer = new TilesConfigurer();
		tilesConfigurer.setDefinitions(new String[] { "/WEB-INF/tiles/tilesCfg.xml" });
		tilesConfigurer.setCheckRefresh(true);
		
		return tilesConfigurer;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/res/**").addResourceLocations("uiresources/");
	}
	
    @Bean(name = "messageSource")
    public ReloadableResourceBundleMessageSource getMessageSource() {
        ReloadableResourceBundleMessageSource resource =
                new ReloadableResourceBundleMessageSource();
        resource.setBasename("uiresources/localization/message");
        resource.setDefaultEncoding("UTF-8");
        return resource;
    }

}
