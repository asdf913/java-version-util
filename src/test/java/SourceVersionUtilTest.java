import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import javax.lang.model.SourceVersion;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class SourceVersionUtilTest {

	private static Method METHOD_GET_JAVA_VERSION_AS_INTEGER_1, METHOD_GET_JAVA_VERSION_AS_SOURCE_VERSION_LIST = null;

	@BeforeClass
	public static void beforeClass() throws NoSuchMethodException {
		//
		final Class<?> clz = SourceVersionUtil.class;
		//
		(METHOD_GET_JAVA_VERSION_AS_INTEGER_1 = clz.getDeclaredMethod("getJavaVersionAsInteger1")).setAccessible(true);
		//
		(METHOD_GET_JAVA_VERSION_AS_SOURCE_VERSION_LIST = clz.getDeclaredMethod("getJavaVersionAsSourceVersionList",
				SourceVersion[].class)).setAccessible(true);
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
	public void testGetJavaVersionAsSourceVersion() throws IOException {
		//
		final Integer major = getClassMajorNumber();
		//
		final SourceVersion sourceVersion = SourceVersionUtil.getJavaVersionAsSourceVersion();
		//
		Assert.assertNotNull(toString(major) + "!=" + sourceVersion, sourceVersion);
		//
	}

	private static String toString(final Object instance) {
		return instance != null ? instance.toString() : null;
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

	@Test
	public void testGetJavaVersionAsSourceVersionList() throws Throwable {
		//
		Assert.assertNull(getJavaVersionAsSourceVersionList(null));
		//
		Assert.assertNull(getJavaVersionAsSourceVersionList(new SourceVersion[] { null }));
		//
	}

	private static List<SourceVersion> getJavaVersionAsSourceVersionList(final SourceVersion[] svs) throws Throwable {
		try {
			final Object obj = METHOD_GET_JAVA_VERSION_AS_SOURCE_VERSION_LIST.invoke(null, (Object) svs);
			if (obj == null) {
				return null;
			} else if (obj instanceof List) {
				return (List) obj;
			} // if
			throw new Throwable(toString(obj.getClass()));
		} catch (InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}