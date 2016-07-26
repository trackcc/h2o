package h2o.common.web.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;

import h2o.common.Tools;
import h2o.common.util.collections.builder.MapBuilder;
import h2o.common.util.collections.tuple.Tuple2;
import h2o.common.util.collections.tuple.TupleUtil;
import h2o.common.util.date.DateUtil;
import h2o.common.util.id.UuidUtil;
import jodd.io.StreamUtil;

public class FileUploadPreprocessor implements ActionPreprocessor {
	
	private String characterEncoding;
	private String filePath = "/";
	private String tempPath;
	private String absolutePathFilePath;
	
	private int sizeThreshold 	= 1024000;
	private int fileSizeMax 	= 102400000;
	private int sizeMax 		= 204800000;
	
	
	public void setFilePath(String filePath) {
		Tools.log.debug("setFilePath:{}" , filePath);
		this.filePath = filePath;
	}


	public void setTempPath(String tempPath) {
		Tools.log.debug("setTempPath:{}" , tempPath);
		this.tempPath = tempPath;
	}


	public void setAbsolutePathFilePath(String absolutePathFilePath) {
		Tools.log.debug("setAbsolutePathFilePath:{}" , absolutePathFilePath);
		this.absolutePathFilePath = absolutePathFilePath;
	}


	public void setSizeThreshold(int sizeThreshold) {
		this.sizeThreshold = sizeThreshold;
	}


	public void setFileSizeMax(int fileSizeMax) {
		this.fileSizeMax = fileSizeMax;
	}


	public void setSizeMax(int sizeMax) {
		this.sizeMax = sizeMax;
	}


	public Result proc(AbstractAction action, HttpServletRequest request, HttpServletResponse response, HttpServlet servlet) throws ServletException, IOException {
		
		this.characterEncoding = action.getCharacterEncoding();
		
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);		
		
		if( isMultipart ) {			
			action.getPara().putAll( procMultipartRequest(request , servlet ) );			
		}	
		
		return null;
	}
	
	
	
	

	protected Map<String,Object> procMultipartRequest( HttpServletRequest request , HttpServlet servlet ) throws ServletException, IOException {
		
		Map<String,Object> p = MapBuilder.newMap();
		try {
			
			// 实例化一个硬盘文件工厂,用来配置上传组件ServletFileUpload     
            DiskFileItemFactory factory =  new DiskFileItemFactory();  
              
            if( this.tempPath != null ) {
            	
	            //设置文件存放的临时文件夹，这个文件夹要真实存在  
	            File fileDir = new File(this.tempPath);  
	            if(fileDir.isDirectory() && fileDir.exists()==false){  
	                fileDir.mkdir();  
	            }
	            
	            factory.setRepository(fileDir);  
            }
              
            //设置最大占用的内存  
            factory.setSizeThreshold( sizeThreshold );  
              
            //创建ServletFileUpload对象  
            ServletFileUpload sfu = new ServletFileUpload(factory);  
            sfu.setHeaderEncoding( characterEncoding );  
              
            //设置单个文件最大值byte   
            sfu.setFileSizeMax( fileSizeMax );  
              
            //所有上传文件的总和最大值byte     
            sfu.setSizeMax( sizeMax );  
              
            List<FileItem> items =  null;  
              
           
            items = sfu.parseRequest(request);  
           
              
            //取得items的迭代器  
            Iterator<FileItem> iter = items==null ? null : items.iterator();  
            
            
            Tuple2<String,String> path = buildFilePath( request,  servlet  );
              
            //图片上传后存放的路径目录  
            File dir = new File( path.e0 );  
            if(dir.exists()==false){
            	dir.mkdirs();  
            }
            //迭代items  
            while( iter!=null && iter.hasNext() ) {
            	
                FileItem item = (FileItem) iter.next();  
                  
                //如果传过来的是普通的表单域  
                if(item.isFormField()){  
                	 p.put( item.getFieldName() ,item.getString(characterEncoding));                        
                }  
                //文件域  
                else if(!item.isFormField()){
                	
                	String realFileName = item.getName();
                	if( StringUtils.isBlank(realFileName) ) {
                		continue;
                	}
                   
                    BufferedInputStream in = new BufferedInputStream(item.getInputStream());    
                    
                    String fileName = buildFileName(realFileName , request , servlet );
                    String realFilePath =  path.e0 + "/" + fileName;
                    BufferedOutputStream out = new BufferedOutputStream(     
                            new FileOutputStream( realFilePath ));   
                    
                    StreamUtil.copy(in, out);
                    
                    StreamUtil.close(in);
                    StreamUtil.close(out);
                    
                    String url = ( StringUtils.endsWith( this.filePath , "/") ? this.filePath : this.filePath + "/" ) + path.e1 + "/";
                    FileField ff = new FileField();
                    ff.setName(realFileName);
                    ff.setUrl( url + fileName  );
                    ff.setRealPath (realFilePath );
                    ff.setSize(item.getSize());
                    
                    
                    p.put( item.getFieldName() , ff);
                    
                }  
            }  
		
		} catch( IOException e ) {
			throw e;
		} catch( Exception e ) {
			throw new ServletException(e);
		}
		
		return p;
	}
	
	
	protected Tuple2<String,String> buildFilePath( HttpServletRequest request, HttpServlet servlet ) {
		
		String path;		
		if( StringUtils.startsWith(absolutePathFilePath , "/") ) {
			path = absolutePathFilePath;
		} else {			
			path = servlet.getServletContext().getRealPath( absolutePathFilePath == null ? "/" : "/" + absolutePathFilePath );
			
		}
		if( path.endsWith("/") ) {
			path = StringUtils.substringBeforeLast(path, "/");
		}
		
		String subPath = buildFileSubPath(  request, servlet );
		return TupleUtil.t( path + "/" + subPath , subPath );
	}
	
	protected String buildFileSubPath( HttpServletRequest request, HttpServlet servlet ) {
		return DateUtil.toString( new Date(), "yyyyMMdd");
	}
	
	protected String buildFileName( String realFileName , HttpServletRequest request, HttpServlet servlet ) {
		return UuidUtil.getUuid() + "." + StringUtils.substringAfterLast(realFileName, ".") ;
	}

}
