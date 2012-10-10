/*
 * Copyright (c) 2012 by the original author(s).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.data.examples.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Common resource Utilities
 * 
 * @author David Turanski
 * 
 */
public class ResourceUtils {
	/**
	 * Convert contents of an input stream to a String
	 * 
	 * @param inputStream
	 * @return the stream contents
	 * @throws IOException
	 */
	public static String streamToString(InputStream inputStream)
			throws IOException {
		Writer writer = new StringWriter();
		byte[] b = new byte[4096];
		for (int n; (n = inputStream.read(b)) != -1;) {
			writer.append(new String(b, 0, n));
		}
		return writer.toString();
	}

	/**
	 * Get class resource as String (convenience wrapper around streamToString)
	 * 
	 * @param clazz the class
	 * @param resourcePath classpath resource path
	 * @return the contents as a String
	 * @throws IOException
	 */
	public static String classPathResourceAsString(Class<?> clazz,
			String resourcePath) throws IOException {
		return streamToString(clazz.getResourceAsStream(resourcePath));
	}
}
