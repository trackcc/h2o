package h2o.common.dynalang;

import h2o.common.exception.ExceptionUtil;
import h2o.common.collections.CollectionUtil;
import h2o.common.util.io.FileUtil;
import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.commons.compiler.AbstractJavaSourceClassLoader;
import org.codehaus.commons.compiler.CompilerFactoryFactory;
import org.codehaus.commons.compiler.ICompilerFactory;

import java.io.File;
import java.util.concurrent.atomic.AtomicReference;

public class JavaSourceClassUtil {

	private final String characterEncoding;

	private final String[] srcDirs;

	private final String compilerFactoryClassName;

	public JavaSourceClassUtil(String... srcDirs) {
		this.srcDirs = srcDirs;
		this.characterEncoding = null;
		this.compilerFactoryClassName = null;
	}

	public JavaSourceClassUtil(String[] srcDirs, String characterEncoding, String compilerFactoryClassName) {
		this.srcDirs = srcDirs;
		this.characterEncoding = characterEncoding;
		this.compilerFactoryClassName = compilerFactoryClassName;
	}

	private final AtomicReference<ClassLoader> classLoaderReference = new AtomicReference<ClassLoader>();

	public ClassLoader getClassLoader() {

		ClassLoader cl = classLoaderReference.get();
		if (cl == null) {

			File[] dirs = null;
			if (!CollectionUtil.argsIsBlank(srcDirs)) {
				dirs = new File[srcDirs.length];
				for (int i = 0; i < srcDirs.length; i++) {
					dirs[i] = FileUtil.newFile(srcDirs[i]);
				}
			}

			try {

				ICompilerFactory iCompilerFactory;
				if (StringUtils.isBlank(compilerFactoryClassName)) {
					iCompilerFactory = CompilerFactoryFactory.getDefaultCompilerFactory();
				} else {
					iCompilerFactory = CompilerFactoryFactory.getCompilerFactory(compilerFactoryClassName);
				}

				AbstractJavaSourceClassLoader javaSourceClassLoader = iCompilerFactory.newJavaSourceClassLoader(this.getClass().getClassLoader());
				javaSourceClassLoader.setSourcePath(dirs);
				if (characterEncoding != null) {
					javaSourceClassLoader.setSourceFileCharacterEncoding(characterEncoding);
				}

				classLoaderReference.compareAndSet(null, javaSourceClassLoader);

				cl = classLoaderReference.get();

			} catch (Exception e) {
				throw ExceptionUtil.toRuntimeException(e);
			}

		}

		return cl;

	}

	@SuppressWarnings("rawtypes")
	public Class getClass(String className) throws ClassNotFoundException {
		return ClassUtils.getClass(this.getClassLoader(), className);
	}

}
