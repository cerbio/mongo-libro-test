package it.sogei.libro_firma.data.configuration.security;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.env.Environment;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Component
public class ControllerMatcher implements BasicMatcher {

    private static final String BASE_PACKAGE = "it.sogei";

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private Environment environment;

    @SuppressWarnings("unchecked")
	private Class<? extends Annotation>[] annotationTypes = new Class[] {
            GetMapping.class,
            PatchMapping.class,
            PostMapping.class,
            PutMapping.class,
            DeleteMapping.class,
            DeleteMapping.class,
            RequestMapping.class
    };

    @Override
    public Map<String, RequestMatcher> getMatchers() throws ClassNotFoundException {

        Map<String, RequestMatcher> matchers = new HashMap<>();

        ClassPathScanningCandidateComponentProvider classPathScanningCandidateComponentProvider =
                new ClassPathScanningCandidateComponentProvider(false);
        classPathScanningCandidateComponentProvider.addIncludeFilter(new AnnotationTypeFilter(RestController.class));
        Set<BeanDefinition> candidateComponents = classPathScanningCandidateComponentProvider.findCandidateComponents(BASE_PACKAGE);

        for (BeanDefinition beanDefinition : candidateComponents) {
            Class<?> beanClass = ClassUtils.forName(beanDefinition.getBeanClassName(), this.getClass().getClassLoader());
            String[] beanNamesForType = applicationContext.getBeanNamesForType(beanClass);
            RequestMapping annotationOnBean = applicationContext.findAnnotationOnBean(beanNamesForType[0], RequestMapping.class);
            if (annotationOnBean != null) {
                addRequestMatcherIfNecessary(matchers, annotationOnBean);
            }else {
                Method[] methods = beanClass.getMethods();
//                --Method[] methods = beanClass.getInterfaces()[0].getMethods();--
                for (Method method : methods) {
                    addRequestMatcherIfNecessary(matchers, method, annotationTypes);
                }
            }
        }
        return matchers;
    }

    /**
     * 
     * @param requestMatchers
     * @param method
     * @param annotationTypes
     */
    private void addRequestMatcherIfNecessary(Map<String, RequestMatcher> requestMatchers, Method method, @SuppressWarnings("unchecked") Class<? extends Annotation>... annotationTypes) {
        for (Class<? extends Annotation> annotationType : annotationTypes) {
        	Annotation annotation = AnnotationUtils.findAnnotation(method, annotationType);
        	if (annotation != null) {
//        	--if --(method.isAnnotationPresent(annotationType)) {--
//                --Annotation annotation = AnnotationUtils.findAnnotation(method, annotationType);--
                addRequestMatcherIfNecessary(requestMatchers, annotation);
            }
        }
    }

    /**
     * 
     * @param requestMatchers
     * @param annotation
     */
    private void addRequestMatcherIfNecessary(Map<String, RequestMatcher> requestMatchers, Annotation annotation) {
        String[] value = (String[]) AnnotationUtils.getValue(annotation, "value");
        for (String path : value) {

            if (path.startsWith("${") && path.endsWith("}")) {
                String tmp = environment.resolvePlaceholders(path);
                if (tmp == null) throw new IllegalArgumentException("Unable to resolve parametric path: "+ tmp);
                path = tmp;
            }

            path = getPath(path);

            int i = path.indexOf('/', 1);
            if (i != -1) {
                path = path.substring(0, i);
            }

            if (!requestMatchers.containsKey(path)) {
                requestMatchers.put(path, new AntPathRequestMatcher(path + "/**"));
            }
        }
    }
    
    /**
     * 
     * @param path
     * @return
     */
    private static String getPath(String path) {
    	if (StringUtils.isEmpty(path)) {
    		throw new IllegalArgumentException("path is empty");
    	}
    	if (path.charAt(0) == '/') {
    		return path;
    	}
    	return "/" + path;
    }
    
}
