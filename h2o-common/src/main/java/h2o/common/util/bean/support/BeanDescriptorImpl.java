package h2o.common.util.bean.support;

import h2o.common.Tools;
import h2o.common.exception.ExceptionUtil;
import h2o.common.util.bean.BeanDescriptor;

import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;

public class BeanDescriptorImpl implements BeanDescriptor {
	
	private boolean excludeClass = true;
	
	

	public void setExcludeClass(boolean excludeClass) {
		this.excludeClass = excludeClass;
	}

	public BeanDescriptorImpl() {}
	
	public BeanDescriptorImpl( boolean excludeClass ) {
		this.excludeClass = excludeClass;
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String[] getPrepNames(Object bean) {
		try {
			Set ps = BeanUtils.describe(bean).keySet();
			if (ps == null || ps.size() == 0) {
				return new String[0];
			}
			
			if(excludeClass) {
				ps.remove("class");
			}

			return (String[]) ps.toArray(new String[ps.size()]);

		} catch (Exception e) {
			Tools.log.debug("getPrepNames", e);
			throw ExceptionUtil.toRuntimeException(e);
		}
	}

}
