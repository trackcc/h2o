// Copyright (c) 2003-present, Jodd Team (http://jodd.org)
// All rights reserved.
//
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions are met:
//
// 1. Redistributions of source code must retain the above copyright notice,
// this list of conditions and the following disclaimer.
//
// 2. Redistributions in binary form must reproduce the above copyright
// notice, this list of conditions and the following disclaimer in the
// documentation and/or other materials provided with the distribution.
//
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
// AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
// IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
// ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
// LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
// CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
// SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
// INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
// CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
// ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
// POSSIBILITY OF SUCH DAMAGE.

package h2o.jodd.core;

import h2o.jodd.Jodd;
import h2o.jodd.io.FileUtil;
import h2o.jodd.io.FileUtilParams;
import h2o.jodd.util.StringPool;
import h2o.jodd.util.cl.ClassLoaderStrategy;
import h2o.jodd.util.cl.DefaultClassLoaderStrategy;


/**
 * Jodd CORE module.
 * Contains some global defaults.
 */
public class JoddCore {

	/**
	 * Default temp file prefix.
	 */
	public static String tempFilePrefix = "jodd-";

	/**
	 * Default file encoding (UTF8).
	 */
	public static String encoding = StringPool.UTF_8;

	/**
	 * Default IO buffer size (16 KB).
	 */
	public static int ioBufferSize = 16384;

	/**
	 * Default parameters used in {@link FileUtil} operations.
	 */
	public static FileUtilParams fileUtilParams = new FileUtilParams();

	/**
	 * Default class loader strategy.
	 */
	public static ClassLoaderStrategy classLoaderStrategy = new DefaultClassLoaderStrategy();

	// ---------------------------------------------------------------- module

	static {
		init();
	}

	public static void init() {
		Jodd.init(JoddCore.class);
	}

}