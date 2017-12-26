package h2o.common.thirdparty.spring.view.support;

import h2o.common.thirdparty.spring.view.OutputProcessor;

public abstract class AbstractOutputProcessor implements OutputProcessor {
	
	protected final String outCharacterEncoding;
	
	public AbstractOutputProcessor( String outCharacterEncoding ) {
		this.outCharacterEncoding = outCharacterEncoding;
	}

}
