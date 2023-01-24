package org.apache.commons.lang3;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class JavaVersionUtilTest {

	private static Method METHOD_GET_CLASS_MAJOR_NUMBER, METHOD_TO_STRING, METHOD_GET_LATEST_JAVA_VERSION_AS_INTEGER,
			METHOD_GET_JAVA_VERSIONS_BY_VERSION, METHOD_GET_FIELD_BY_VALUE, METHOD_GET_NAME, METHOD_AND,
			METHOD_GET_JAVA_VERSION_AS_INTEGER, METHOD_GET_JAVA_VERSION_AS_INTEGER1, METHOD_SELECT = null;

	@BeforeClass
	public static void beforeClass() throws NoSuchMethodException {
		//
		final Class<?> clz = JavaVersionUtil.class;
		//
		(METHOD_GET_CLASS_MAJOR_NUMBER = clz.getDeclaredMethod("getClassMajorNumber")).setAccessible(true);
		//
		(METHOD_TO_STRING = clz.getDeclaredMethod("toString", Object.class)).setAccessible(true);
		//
		(METHOD_GET_LATEST_JAVA_VERSION_AS_INTEGER = clz.getDeclaredMethod("getLatestJavaVersionAsInteger"))
				.setAccessible(true);
		//
		(METHOD_GET_JAVA_VERSIONS_BY_VERSION = clz.getDeclaredMethod("getJavaVersionsByVersion", JavaVersion[].class,
				Integer.class)).setAccessible(true);
		//
		(METHOD_GET_FIELD_BY_VALUE = clz.getDeclaredMethod("getFieldByValue", Field[].class, Object.class))
				.setAccessible(true);
		//
		(METHOD_GET_NAME = clz.getDeclaredMethod("getName", Member.class)).setAccessible(true);
		//
		(METHOD_AND = clz.getDeclaredMethod("and", Boolean.TYPE, Boolean.TYPE)).setAccessible(true);
		//
		(METHOD_GET_JAVA_VERSION_AS_INTEGER = clz.getDeclaredMethod("getJavaVersionAsInteger")).setAccessible(true);
		//
		(METHOD_GET_JAVA_VERSION_AS_INTEGER1 = clz.getDeclaredMethod("getJavaVersionAsInteger1")).setAccessible(true);
		//
		(METHOD_SELECT = clz.getDeclaredMethod("select", Element.class, String.class)).setAccessible(true);
		//
	}

	private static Integer getClassMajorNumber() throws Throwable {
		try {
			final Object obj = METHOD_GET_CLASS_MAJOR_NUMBER.invoke(null);
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

	private static String toString(final Object instance) throws Throwable {
		try {
			final Object obj = METHOD_TO_STRING.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			} // if
			throw new Throwable(toString(obj.getClass()));
		} catch (InvocationTargetException e) {
			throw e.getTargetException();
		}
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

	@Test
	public void testGetJavaVersionsByVersion() throws Throwable {
		//
		Assert.assertNull(getJavaVersionsByVersion(null, null));
		//
		Assert.assertEquals(Collections.singletonList(null),
				getJavaVersionsByVersion(new JavaVersion[] { null }, null));
		//
		Assert.assertNull(getJavaVersionsByVersion(new JavaVersion[] { null }, Integer.valueOf(0)));
		//
		Assert.assertEquals(Collections.singletonList(null),
				getJavaVersionsByVersion(new JavaVersion[] { null, null }, null));
		//
	}

	private static List<JavaVersion> getJavaVersionsByVersion(final JavaVersion[] jvs, final Integer version)
			throws Throwable {
		try {
			final Object obj = METHOD_GET_JAVA_VERSIONS_BY_VERSION.invoke(null, jvs, version);
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

	@Test
	public void testGetFieldByValue() throws Throwable {
		//
		Assert.assertNull(getFieldByValue(null, null));
		//
		final Field fieldMaxValue = Integer.class.getDeclaredField("MAX_VALUE");
		//
		Assert.assertNull(getFieldByValue(
				new Field[] { null, Integer.class.getDeclaredField("value"), fieldMaxValue, fieldMaxValue }, null));
		//
	}

	private static Field getFieldByValue(final Field[] fs, final Object value) throws Throwable {
		try {
			final Object obj = METHOD_GET_FIELD_BY_VALUE.invoke(null, fs, value);
			if (obj == null) {
				return null;
			} else if (obj instanceof Field) {
				return (Field) obj;
			} // if
			throw new Throwable(toString(obj.getClass()));
		} catch (InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	public void testGetName() throws Throwable {
		//
		Assert.assertNull(getName(null));
		//
		final String name = "toString";
		//
		Assert.assertEquals(name, getName(Object.class.getDeclaredMethod(name)));
		//
	}

	private static String getName(final Member instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_NAME.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			} // if
			throw new Throwable(toString(obj.getClass()));
		} catch (InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	public void testAnd() throws Throwable {
		//
		Assert.assertFalse(and(true, false));
		//
		Assert.assertTrue(and(true, true));
		//
	}

	private static boolean and(final boolean a, final boolean b) throws Throwable {
		try {
			final Object obj = METHOD_AND.invoke(null, a, b);
			if (obj instanceof Boolean) {
				return (Boolean) obj;
			} // if
			throw new Throwable(toString(obj != null ? obj.getClass() : null));
		} catch (InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	public void testGetJavaVersionAsInteger() throws Throwable {
		//
		Assert.assertNotNull(getJavaVersionAsInteger());
		//
	}

	private static Integer getJavaVersionAsInteger() throws Throwable {
		try {
			final Object obj = METHOD_GET_JAVA_VERSION_AS_INTEGER.invoke(null);
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
	public void testGetJavaVersionAsInteger1() throws Throwable {
		//
		Assert.assertNotNull(getJavaVersionAsInteger1());
		//
	}

	private static Integer getJavaVersionAsInteger1() throws Throwable {
		try {
			final Object obj = METHOD_GET_JAVA_VERSION_AS_INTEGER1.invoke(null);
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
	public void testSelect() throws Throwable {
		//
		Assert.assertNull(select(null, null));
		//
	}

	private static Elements select(final Element instance, final String cssQuery) throws Throwable {
		try {
			final Object obj = METHOD_SELECT.invoke(null, instance, cssQuery);
			if (obj == null) {
				return null;
			} else if (obj instanceof Elements) {
				return (Elements) obj;
			} // if
			throw new Throwable(toString(obj.getClass()));
		} catch (InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}