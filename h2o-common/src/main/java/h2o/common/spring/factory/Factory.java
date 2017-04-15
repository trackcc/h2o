package h2o.common.spring.factory;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.locks.Lock;

public class Factory implements BeanFactoryAware {
	
	private static final Lock lock = new java.util.concurrent.locks.ReentrantLock();

	private static volatile BeanFactory beanFactory;

	public  static void setStaticConfigPath(String path) {
		lock.lock();
		try {
			beanFactory = new ClassPathXmlApplicationContext(path);
		} finally {
			lock.unlock();
		}	
	}
	
	public void setConfigPath(String path) {
		setStaticConfigPath(path);
	}

	@SuppressWarnings("unchecked")
	public static <T> T getObject(String beanId) {
		
		return (T) getBeanFactory().getBean(beanId);
	}

	public void setBeanFactory(BeanFactory bf) throws BeansException {
		lock.lock();
		try {
			beanFactory = bf;
		} finally {
			lock.unlock();
		}
	}
	
	public static BeanFactory getBeanFactory()  {
		BeanFactory bf = beanFactory;
		if (bf == null) {
			lock.lock();
			try {
				bf = beanFactory;
				if (bf == null) {
					setStaticConfigPath("/applicationContext.xml");
					bf = beanFactory;
				}
			} finally {
				lock.unlock();
			}
		}
		return bf;
	}

}
