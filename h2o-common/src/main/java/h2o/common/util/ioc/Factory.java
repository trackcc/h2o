package h2o.common.util.ioc;

public interface Factory {

    <T> T get( String id , Object... args );

    <T> T silentlyGet( String id , Object... args );

}
