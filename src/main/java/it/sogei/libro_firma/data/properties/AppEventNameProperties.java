package it.sogei.libro_firma.data.properties;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app.event-name")
public class AppEventNameProperties {
	
	public static String CREATED = "created";
	public static String READ = "read";
	public static String DELETED = "deleted";
	
	private GenericExample genericExample =new GenericExample();
	
	
	public GenericExample getGenericExample() {
		return genericExample;
	}

	public static class GenericExample{
		Map<String,String> articles=new HashMap<>();

		public Map<String, String> getArticles() {
			return articles;
		}

		public void setArticles(Map<String, String> articles) {
			this.articles = articles;
		}
		
	}
}
