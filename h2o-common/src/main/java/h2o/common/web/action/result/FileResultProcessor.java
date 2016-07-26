package h2o.common.web.action.result;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import h2o.common.web.action.Result;
import h2o.common.web.action.ResultProcessor;
import h2o.common.web.action.result.data.FileData;

public class FileResultProcessor implements ResultProcessor {
	

	public void response( Result r, HttpServletRequest request,	HttpServletResponse response ) throws ServletException, IOException {
		
		FileData fileData =  (FileData) r.resData;
		
        response.setContentType(fileData.contentType);
        response.setHeader("Content-Disposition", "attachment; filename=" + fileData.fileName);
        
        if( fileData.fileLength > 0 ) {
        	response.setContentLength( fileData.fileLength );
        }
        
        OutputStream outputStream = response.getOutputStream();
        
       jodd.io.StreamUtil.copy(fileData.fileStream , outputStream);
       
       jodd.io.StreamUtil.close( fileData.fileStream );
       jodd.io.StreamUtil.close( outputStream );
	}

}
