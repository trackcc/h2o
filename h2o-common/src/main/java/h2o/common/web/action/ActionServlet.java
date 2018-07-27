package h2o.common.web.action;

import h2o.common.collections.builder.MapBuilder;
import h2o.common.collections.tuple.Tuple2;
import h2o.common.collections.tuple.TupleUtil;
import h2o.common.exception.ExceptionUtil;
import h2o.common.web.action.result.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class ActionServlet extends HttpServlet {


	private static final long serialVersionUID = -4403148334408543400L;

    private static final Logger log = LoggerFactory.getLogger( ActionServlet.class.getName() );

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
			
			log.debug( "reqUri ========{}" , reqUri  );
			
			Tuple2<String, Map<String, Object>> pr = parse(reqUri, request, response);
			
			String actionId = pr.e0;
			Map<String, Object> para = pr.e1;
			
			log.debug( "Action ========{}:{}" , appName ,  actionId  );
	
			
			Action action = factory.getAction(actionId);
						
			if( action == null ) {
				throw new ServletException( "Not found Action [" + actionId +  "]."  );
			}
			
			action.setPara(para);
			
			Result r = action.procRequest( request, response, this);
			
			log.debug("Action[{}:{}]--return:{}" , appName ,  actionId , r);
			
			this.response(r, request, response);
		
			long et = System.currentTimeMillis();
			
			log.info( "Action[{}:{}] OK,  {} ms ." , appName ,  actionId  , ( et - st ) );
		
		} catch( ServletException e ) {
			log.debug("",e);
			throw e;			
		} catch( IOException e ) {
			log.debug("",e);
			throw e;
		} catch( Throwable e ) {
			log.debug("",e);
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
