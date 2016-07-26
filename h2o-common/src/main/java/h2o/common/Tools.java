package h2o.common;

import h2o.common.util.bean.BeanUtil;
import h2o.common.util.bean.support.CasePreOperateImpl;
import h2o.common.util.bean.support.JoddBeanUtilVOImpl;
import h2o.common.util.bean.support.MapVOImpl;
import h2o.common.util.debug.LogUtil;



public class Tools {


	public static final BeanUtil b = new BeanUtil(new JoddBeanUtilVOImpl(true,false) , null);
	
	public static final BeanUtil bu = new BeanUtil(new JoddBeanUtilVOImpl(true,false) ,
			new MapVOImpl( new CasePreOperateImpl("Upper")));

	public static final LogUtil log = new LogUtil();	
	

	
}
