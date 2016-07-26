package h2o.common.web.action;

import h2o.common.Tools;
import h2o.common.exception.ExceptionUtil;
import h2o.common.util.collections.builder.MapBuilder;
import h2o.common.util.collections.tuple.Tuple2;
import h2o.common.util.collections.tuple.TupleUtil;
import h2o.common.web.action.result.FileResultProcessor;
import h2o.common.web.action.result.ForwardResultProcessor;
import h2o.common.web.action.result.JsonResultProcessor;
import h2o.common.web.action.result.RedirectResultProcessor;
import h2o.common.web.action.result.StringResultProcessor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;


public class ActionServlet extends HttpServlet {


	private static final long serialVersionUID = -4403148334408543400L;
	
	private volatile String prePath = null;
	private volatile String appName = null;
	
	private volatile ActionFactory factory = null;
	
	
	private final Map<String,ResultProcessor> resultProcessors = MapBuilder.newConcurrentHashMap(); {
		resultProcessors.put( Result.ResType.STRING   , new StringResultProcessor()		);
		resultProcessors.put( Result.ResType.JSON	  , new JsonResultProcessor()		);
		resultProcessors.put( Result.ResType.FORWARD  , new ForwardResultProcessor()	);
		resultProcessors.put( Result.ResType.REDIRECT , new RedirectResultProcessor()	);
		resultProcessors.put( Result.ResType.FILE 	  , new FileResultProcessor()		);
	}
	
	protected void regResultProcessor( String resType ,ResultProcessor resultProcessor ) {
		resultProcessors.put( resType , resultProcessor );
	}

	

	@Override
	public void init(ServletConfig config) throws ServletException {

		super.init(config);
		
		prePath = config.getInitParameter("prePath");		
		appName = config.getInitParameter("appName");
		
		factory = new ActionFactory( appName , appName + ".bcs");
		
		
	}
	
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setDateHeader("Expires", -1);// IE
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		
		this.doAction(request, response);
		
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		this.doGet(request, response);
		
	}


	protected void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
			
			
			long st = System.currentTimeMillis();
			
			String uri = request.getRequestURI();
			
			String reqUri = StringUtils.substringAfter(uri, request.getContextPath() + prePath);		
			
			Tools.log.debug( "reqUri ========{}" , reqUri  );
			
			Tuple2<String, Map<String, Object>> pr = parse(reqUri, request, response);
			
			String actionId = pr.e0;
			Map<String, Object> para = pr.e1;
			
			Tools.log.debug( "Action ========{}:{}" , appName ,  actionId  );
	
			
			Action action = factory.getAction(actionId);
						
			if( action == null ) {
				throw new ServletException( "Not found Action [" + actionId +  "]."  );
			}
			
			action.setPara(para);
			
			Result r = action.procRequest( request, response, this);
			
			Tools.log.debug("Action[{}:{}]--return:{}" , appName ,  actionId , r);
			
			this.response(r, request, response);
		
			long et = System.currentTimeMillis();
			
			Tools.log.info( "Action[{}:{}] OK,  {} ms ." , appName ,  actionId  , ( et - st ) );
		
		} catch( ServletException e ) {
			Tools.log.debug("",e);
			throw e;			
		} catch( IOException e ) {
			Tools.log.debug("",e);
			throw e;
		} catch( Throwable e ) {
			Tools.log.debug("",e);
			throw ExceptionUtil.toRuntimeException(e);
		}
	}
	
	
	protected void response( Result r , HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		
		if( r != null ) {
			
			ResultProcessor processor = resultProcessors.get( r.resType );
			if( processor == null ) {
				throw new ServletException("The return type[" + r.resType + "] can not be processed.");
			}
			
			processor.response( r , request , response );		
		}		
		
	}
	
	
	protected Tuple2<String,Map<String,Object>> parse( String reqUri , HttpServletRequest request, HttpServletResponse response ) {
		
		String actionId = StringUtils.substringBefore(reqUri, ".");
		Map<String,Object> para = new HashMap<String,Object>();
		
		return TupleUtil.t( actionId , para );
		
	}
	
	


	
	
	

}
