package h2o.common.web.action.result.data;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class FileData {
	
	public final String fileName;	
	
	public final int fileLength;
	
	public final String contentType;
	
	public final InputStream fileStream;
	
	
	public FileData( String fileName , String contentType , byte[] fileContets ) {
		this.fileName 		= fileName;
		this.contentType 	= contentType;
		this.fileLength		= fileContets.length;
		this.fileStream 	= new ByteArrayInputStream( fileContets );
	}
	
	public FileData( String fileName , String contentType , InputStream fileStream , int length ) {
		this.fileName 		= fileName;
		this.contentType 	= contentType;
		this.fileStream 	= fileStream;
		this.fileLength		= length;
	}

	
	

}
