package h2o.common.web.action.result;

import h2o.common.web.action.Result;
import h2o.common.web.action.ResultProcessor;
import h2o.common.web.action.result.data.FileData;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

public class FileResultProcessor implements ResultProcessor {
	

	public void response( Result r, HttpServletRequest request,	HttpServletResponse response ) throws ServletException, IOException {
		
		FileData fileData =  (FileData) r.resData;
		
        response.setContentType(fileData.contentType);
        response.setHeader("Content-Disposition", "attachment; filename=" + fileData.fileName);
        
        if( fileData.fileLength > 0 ) {
        	response.setContentLength( fileData.fileLength );
        }
        
        OutputStream outputStream = response.getOutputStream();

        h2o.jodd.io.StreamUtil.copy(fileData.fileStream , outputStream);

        h2o.jodd.io.StreamUtil.close( fileData.fileStream );
        h2o.jodd.io.StreamUtil.close( outputStream );
	}

}
