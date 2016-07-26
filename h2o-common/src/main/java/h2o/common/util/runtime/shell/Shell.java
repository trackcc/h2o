package h2o.common.util.runtime.shell;

import h2o.common.util.collections.builder.ListBuilder;
import h2o.common.util.collections.tuple.Tuple2;
import h2o.common.util.collections.tuple.TupleUtil;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Shell {
	
	
	
	public static Tuple2<Boolean, String[]> call( String sh , RunCallback rc ) throws Exception {
		
		List<String> lines = ListBuilder.newList();

		boolean r = true;

		Process p;
		try {
			p = Runtime.getRuntime().exec(sh);

			BufferedInputStream in = new BufferedInputStream(p.getInputStream());
			BufferedReader inBr = new BufferedReader(new InputStreamReader(in));

			String lineStr;
			while ((lineStr = inBr.readLine()) != null) {

				System.out.println(lineStr);

				lines.add(lineStr);

				if (rc != null) {
					int c = rc.check(lineStr);
					if (c == 0) {
						r = true;
						break;
					} else if (c == 1) {
						r = false;
						break;
					} else if (c == 2) {
						r = true;
					} else if (c == 3) {
						r = false;
					}
				}

			}

			// 检查命令是否执行失败。
			if (p.waitFor() != 0) {
				if (p.exitValue() == 1)// p.exitValue()==0表示正常结束，1：非正常结束
					if (rc != null) {
						rc.onFinish(2);
					}
					return TupleUtil.t(false, null);
			}
			inBr.close();
			in.close();

		} catch (IOException e) {			
			e.printStackTrace();
			if (rc != null) {
				rc.onFinish(2);
			}
			return TupleUtil.t(false, null);
		} catch (InterruptedException e) {
			if (rc != null) {
				rc.onFinish(2);
			}
			return TupleUtil.t(false, null);
		}

		if (rc != null) {
			rc.onFinish(1);
		}
		return TupleUtil.t(r, lines.toArray(new String[lines.size()]));
	}
	
	
	
	
	public static Tuple2<Boolean,String[] > run( String sh , RunCallback rc ) throws Exception {
		return run(sh,rc,-1);
	}
	
	public static Tuple2<Boolean,String[] > run( String sh , RunCallback rc , int timeout ) throws Exception {
		
		
	            Callable<Tuple2<Boolean, String[]>> task = new ShellProcess(sh,rc);  
	            ExecutorService executorService = Executors.newSingleThreadExecutor();// 单线程executor  
	        

	            @SuppressWarnings("unchecked")
				List<Callable<Tuple2<Boolean, String[]>>> asList = Arrays.asList(task);
	            
	            
	            List<Future<Tuple2<Boolean, String[]>>> futures;
	            if( timeout < 0 ) {
	            	futures = executorService.invokeAll(asList); 
	            } else {
	            	 futures = executorService.invokeAll(asList, timeout, TimeUnit.SECONDS); 
	            }
	            executorService.shutdown();  
	            Future<Tuple2<Boolean, String[]>> future = futures.get(0);  
	            if (future.isCancelled()) {
	            	if (rc != null) {
						rc.onFinish(3);
					}
	            	return TupleUtil.t(false, new String[] {"Timeout."} );
	            }  
	            
	            return future.get();
	       
	    }  
		
	

}
