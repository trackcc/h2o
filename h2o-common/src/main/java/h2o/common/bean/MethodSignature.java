package h2o.common.bean;

@SuppressWarnings("rawtypes")
public class MethodSignature implements java.io.Serializable {
	

	private static final long serialVersionUID = -2532511529363084784L;

	private String methodName;	
	
	private Class[] parameterTypes;
	
	public MethodSignature() {}
	
	public MethodSignature( String methodName , Class[] parameterTypes ) {
		this.methodName = methodName;
		this.parameterTypes = parameterTypes;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Class[] getParameterTypes() {
		return parameterTypes;
	}

	public void setParameterTypes(Class[] parameterTypes) {
		this.parameterTypes = parameterTypes;
	}
	
	

}
