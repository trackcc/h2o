package h2o.common.util.io;

import h2o.common.Tools;

import java.io.File;
import java.net.URL;

import org.apache.commons.configuration.ConfigurationUtils;

public class FileUtil {

	private FileUtil() {}

	private static void rm(File f) {
		if (f.isDirectory()) {
			File[] subfs = f.listFiles();
			for (int i = 0; i < subfs.length; i++) {
				rm(subfs[i]);
			}
		}

		f.delete();
	}

	public static boolean rmdir(File dir) {

		if (dir.exists() && dir.isDirectory()) {
			rm(dir);
			return true;
		}

		return false;
	}

	public static void mkdirs(String dir) {

		File fdir = new File(dir);
		if (!fdir.exists()) {
			fdir.mkdirs();
		}

	}

	public static String getExtname(String fileNmae) {

		int i = fileNmae.lastIndexOf('.');
		if (i != -1) {
			return fileNmae.substring(i + 1).toLowerCase();
		}

		return null;
	}
	
	
	
	public static File newFile( String path ) {
		
		Tools.log.debug("newFile path -- {}" , path );
		URL fileUrl = ConfigurationUtils.locate(path);
		
		Tools.log.debug("newFile fileUrl -- {}" , fileUrl );
		
		return ConfigurationUtils.fileFromURL(fileUrl);
	}
	
	


}
