package h2o.common.remote;

public class RemoteReq implements java.io.Serializable {

	private static final long serialVersionUID = -3481217587466198166L;

	private String serviceId;

	private MethodSignature methodSignature;

	private Object[] args;

	private UserIdentity userIdentity;

	public RemoteReq() {
	}

	public RemoteReq(UserIdentity userIdentity, String serviceId,
			MethodSignature methodSignature, Object... args) {
		this.serviceId = serviceId;
		this.methodSignature = methodSignature;
		this.args = args;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public MethodSignature getMethodSignature() {
		return methodSignature;
	}

	public void setMethodSignature(MethodSignature methodSignature) {
		this.methodSignature = methodSignature;
	}

	public Object[] getArgs() {
		return args;
	}

	public void setArgs(Object[] args) {
		this.args = args;
	}

	public UserIdentity getUserIdentity() {
		return userIdentity;
	}

	public void setUserIdentity(UserIdentity userIdentity) {
		this.userIdentity = userIdentity;
	}

}
