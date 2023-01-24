package org.apache.commons.lang3;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class JavaVersionUtilTest {

	private static Method METHOD_GET_LATEST_JAVA_VERSION_AS_INTEGER = null;

	@BeforeClass
	public static void beforeClass() throws NoSuchMethodException {
		//
		(METHOD_GET_LATEST_JAVA_VERSION_AS_INTEGER = JavaVersionUtil.class
				.getDeclaredMethod("getLatestJavaVersionAsInteger")).setAccessible(true);
		//
	}

	private static Integer getClassMajorNumber() throws IOException {
		//
		InputStream is = null;
		//
		DataInputStream dis = null;
		//
		try {
			//
			if ((dis = new DataInputStream(is = Object.class.getResourceAsStream("/java/lang/Object.class")))
					.readInt() != 0xCAFEBABE) {
				//
				throw new IOException("Invalid Java class");
				//
			} // if
				//
				// minor
				//
			dis.readShort();
			//
			// major
			//
			return Integer.valueOf(0xFFFF & dis.readShort());
			//
		} finally {
			//
			if (dis != null) {
				//
				dis.close();
				//
			} // if
				//
			if (is != null) {
				//
				is.close();
				//
			} // if
				//
		} // try
			//
	}

	@Test
	public void testGetJavaVersion() throws Throwable {
		//
		final Integer major = getClassMajorNumber();
		//
		if (intValue(getLatestJavaVersionAsInteger(), 0) == intValue(major, 0) - 44) {
			//
			Assert.assertSame(JavaVersion.JAVA_RECENT, JavaVersionUtil.getJavaVersion());
			//
		} else {
			//
			final JavaVersion[] jvs = JavaVersion.values();
			//
			String name = null;
			//
			Double version = null;
			//
			for (int i = 0; jvs != null && i < jvs.length; i++) {
				//
				if (!Character.isDigit((name = name(jvs[i])).charAt(StringUtils.length(name) - 1))) {
					//
					continue;
					//
				} // if
					//
				version = ObjectUtils.max(version, Double.valueOf(toString(jvs[i])));
				//
			} // for
				//
			if ((intValue(major, 0)) - 44 <= version) {
				//
				Assert.assertNotNull(toString(major) + "!=" + version, JavaVersionUtil.getJavaVersion());
				//
			} // if
				//
		} // if
			//
	}

	private static String name(final Enum<?> instance) {
		return instance != null ? instance.name() : null;
	}

	private static int intValue(final Number instance, final int defaultValue) {
		return instance != null ? instance.intValue() : defaultValue;
	}

	private static String toString(final Object instance) {
		return instance != null ? instance.toString() : null;
	}

	private static Integer getLatestJavaVersionAsInteger() throws Throwable {
		try {
			final Object obj = METHOD_GET_LATEST_JAVA_VERSION_AS_INTEGER.invoke(null);
			if (obj == null) {
				return null;
			} else if (obj instanceof Integer) {
				return (Integer) obj;
			} // if
			throw new Throwable(toString(obj.getClass()));
		} catch (InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}