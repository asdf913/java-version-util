import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class JavaVersionUtilTest {

	private static Method METHOD_GET_JAVA_VERSION_AS_INTEGER_1 = null;

	@BeforeClass
	public static void beforeClass() throws NoSuchMethodException {
		//
		(METHOD_GET_JAVA_VERSION_AS_INTEGER_1 = JavaVersionUtil.class.getDeclaredMethod("getJavaVersionAsInteger1"))
				.setAccessible(true);
		//
	}

	@Test
	public void testGetJavaVersionAsInt() throws IOException {
		//
		final Integer major = getClassMajorNumber();
		//
		final int version = JavaVersionUtil.getJavaVersionAsInt();
		//
		Assert.assertEquals(toString(major) + "!=" + version, Integer.valueOf(intValue(major, 0) - 44),
				Integer.valueOf(version));
		//
	}

	private static String toString(final Object instance) {
		return instance != null ? instance.toString() : null;
	}

	private static int intValue(final Number instance, final int defaultValue) {
		return instance != null ? instance.intValue() : defaultValue;
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
	public void testGetJavaVersionAsInteger1() throws Throwable {
		//
		Assert.assertNotNull(getJavaVersionAsInteger1());
		//
	}

	private static Integer getJavaVersionAsInteger1() throws Throwable {
		try {
			final Object obj = METHOD_GET_JAVA_VERSION_AS_INTEGER_1.invoke(null);
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