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

package jodd.util;

/**
 * Rule matcher.
 */
public interface InExRuleMatcher<T, R> {

	/**
	 * {@link Wildcard#match(String, String) Wilcard} rule matcher.
	 */
	public static final InExRuleMatcher<String, String> WILDCARD_RULE_MATCHER = new InExRuleMatcher<String, String>() {
		public boolean accept(String value, String rule, boolean include) {
			return Wildcard.match(value, rule);
		}
	};
	/**
	 * {@link Wildcard#matchPath(String, String)  Wilcard path} rule matcher.
	 */
	public static final InExRuleMatcher<String, String> WILDCARD_PATH_RULE_MATCHER = new InExRuleMatcher<String, String>() {
		public boolean accept(String value, String rule, boolean include) {
			return Wildcard.matchPath(value, rule);
		}
	};

	/**
	 * Match the value against the rule.
	 */
	boolean accept(T value, R rule, boolean include);

}