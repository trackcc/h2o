package h2o.common.thirdparty.redis;

public class JedisProviderException extends RuntimeException {
    private static final long serialVersionUID = 6068024518493817166L;

    public JedisProviderException() {
        super();
    }

    public JedisProviderException(String message) {
        super(message);
    }

    public JedisProviderException(String message, Throwable cause) {
        super(message, cause);
    }

    public JedisProviderException(Throwable cause) {
        super(cause);
    }

}
