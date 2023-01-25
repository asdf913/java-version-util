package org.apache.commons.lang3;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public final class JavaVersionUtil {

	private JavaVersionUtil() {
	}

	public static JavaVersion getJavaVersion() {
		//
		final List<JavaVersion> list = getJavaVersionsByVersion(JavaVersion.values(), getJavaVersionAsInteger());
		//
		final int size = size(list);
		//
		if (size == 1) {
			//
			return list.get(0);
			//
		} else if (size > 1) {
			//
			// For JAVA_1_9 and JAVA_9 case
			//
			final Field[] fs = JavaVersion.class.getDeclaredFields();
			//
			return Collections.max(list, new Comparator<JavaVersion>() {

				@Override
				public int compare(final JavaVersion jv1, final JavaVersion jv2) {
					//
					try {
						//
						return ObjectUtils.compare(
								Double.valueOf(StringUtils.replace(
										StringUtils.substringAfter(getName(getFieldByValue(fs, jv1)), "_"), "_", ".")),
								Double.valueOf(StringUtils.replace(
										StringUtils.substringAfter(getName(getFieldByValue(fs, jv2)), "_"), "_", ".")));
						//
					} catch (final NumberFormatException e) {
						//
						return 0;
						//
					} // try
						//
				}

			});
			//
		} else if (size == 0) {
			//
			try {
				final Integer major = getClassMajorNumber();
				//
				if (major != null && Integer.valueOf(major - 44).equals(getLatestJavaVersionAsInteger())) {
					//
					return JavaVersion.JAVA_RECENT;
					//
				} // if
					//
			} catch (final IOException e) {
				//
				printStackTrace(e);
				//
			} // try
				//
		} // if
			//
		throw new IllegalStateException();
		//
	}

	private static void printStackTrace(final Throwable throwable) {
		//
		try {
			//
			final Method method = Throwable.class.getDeclaredMethod("printStackTrace");
			//
			if (method != null) {
				//
				method.setAccessible(true);
				//
			} // if
				//
			if (method != null && (Modifier.isStatic(method.getModifiers()) || throwable != null)) {
				//
				method.invoke(throwable);
				//
			} // if
				//
		} catch (final IllegalAccessException e) {
			//
			printStackTrace(e);
			//
		} catch (final NoSuchMethodException e) {
			//
			printStackTrace(e);
			//
		} catch (final InvocationTargetException e) {
			//
			final Throwable targetException = e.getTargetException();
			//
			printStackTrace(ObjectUtils.firstNonNull(ExceptionUtils.getRootCause(targetException), targetException,
					ExceptionUtils.getRootCause(e), e));
			//
		} // try
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

	private static Integer getLatestJavaVersionAsInteger() {
		//
		Integer latestVersion = null;
		//
		try {
			//
			final List<Element> elements = select(
					Jsoup.parse(new URL("https://www.oracle.com/java/technologies/downloads/archive/"), 0),
					".icn-chevron-right a");
			//
			String text = null;
			//
			for (int i = 0; elements != null && i < elements.size(); i++) {
				//
				if (!StringUtils.startsWith(text = text(elements.get(i)), "Java SE")) {
					//
					continue;
					//
				} // if
					//
				if (StringUtils.contains(text = StringUtils.trim(StringUtils.substringAfter(text, "Java SE")), ' ')) {
					//
					text = StringUtils.substringBefore(text, " ");
					//
				} else if (StringUtils.contains(text, '.')) {
					//
					text = StringUtils.substringAfter(text, ".");
					//
				} // if
					//
				try {
					//
					latestVersion = ObjectUtils.max(latestVersion, Integer.valueOf(text));
					//
				} catch (final NumberFormatException e) {
					//
				} // try
					//
			} // for
				//
		} catch (final IOException e) {
			//
			printStackTrace(e);
			//
		} // try
			//
		return latestVersion;
		//
	}

	private static Elements select(final Element instance, final String cssQuery) {
		return instance != null ? instance.select(cssQuery) : null;
	}

	private static String text(final Element instance) {
		return instance != null ? instance.text() : null;
	}

	private static List<JavaVersion> getJavaVersionsByVersion(final JavaVersion[] jvs, final Integer version) {
		//
		List<JavaVersion> list = null;
		//
		if (jvs != null) {
			//
			String toString, versionToString = null;
			//
			for (final JavaVersion jv : jvs) {
				//
				if (StringUtils.equals(toString = toString(jv), versionToString = toString(version))
						|| StringUtils.equals(StringUtils.substringAfter(toString, "."), versionToString)) {
					//
					if (list == null) {
						//
						list = new ArrayList<JavaVersion>();
						//
					} // if
						//
					if (!list.contains(jv)) {
						//
						list.add(jv);
						//
					} // if
						//
				} // if
					//
			} // for
				//
		} // if
			//
		return list;
		//
	}

	private static String toString(final Object instance) {
		return instance != null ? instance.toString() : null;
	}

	private static int size(final Collection<?> instance) {
		return instance != null ? instance.size() : 0;
	}

	private static String getName(final Member instance) {
		return instance != null ? instance.getName() : null;
	}

	private static Field getFieldByValue(final Field[] fs, final Object value) {
		//
		Field f = null;
		//
		List<Field> fields = null;
		//
		for (int i = 0; fs != null && i < fs.length; i++) {
			//
			if ((f = fs[i]) == null || !Modifier.isStatic(f.getModifiers())) {
				//
				continue;
				//
			} // if
				//
			if (fields == null) {
				//
				fields = new ArrayList<Field>();
				//
			} // if
				//
			if (!f.isAccessible()) {
				//
				f.setAccessible(true);
				//
			} // if
				//
			try {
				//
				if (and(f.get(null) == value, !fields.contains(value))) {
					//
					fields.add(f);
					//
				} // if
					//
			} catch (final IllegalAccessException e) {
				//
				printStackTrace(e);
				//
			} // try
				//
		} // for
			//
		final int size = size(fields);
		//
		if (size == 1) {
			//
			return fields.get(0);
			//
		} else if (size > 1) {
			//
			throw new IllegalStateException();
			//
		} // if
			//
		return null;
		//
	}

	private static boolean and(final boolean a, final boolean b) {
		return a && b;
	}

	private static Integer getJavaVersionAsInteger() {
		//
		final Map<Integer, String> map = new LinkedHashMap<Integer, String>(
				Collections.singletonMap(Integer.valueOf(1), "java.lang.Object"));
		//
		map.put(Integer.valueOf(2), "java.util.Collection");
		//
		map.put(Integer.valueOf(3), "java.lang.reflect.InvocationHandler");
		//
		map.put(Integer.valueOf(4), "java.nio.ByteBuffer");
		//
		map.put(Integer.valueOf(5), "java.lang.ProcessBuilder");
		//
		map.put(Integer.valueOf(6), "java.io.Console");
		//
		map.put(Integer.valueOf(7), "java.nio.file.attribute.FileAttribute");
		//
		map.put(Integer.valueOf(8), "java.util.stream.Stream");
		//
		map.put(Integer.valueOf(9), "java.lang.Module");
		//
		map.put(Integer.valueOf(10), "com.sun.source.doctree.SummaryTree");
		//
		map.put(Integer.valueOf(11), "java.net.http.HttpClient");
		//
		map.put(Integer.valueOf(12), "com.sun.source.doctree.SystemPropertyTree");
		//
		map.put(Integer.valueOf(13), "com.sun.source.util.ParameterNameProvider");
		//
		map.put(Integer.valueOf(14), "java.io.Serial");
		//
		map.put(Integer.valueOf(15), "java.security.interfaces.EdECKey");
		//
		map.put(Integer.valueOf(16), "java.net.UnixDomainSocketAddress");
		//
		map.put(Integer.valueOf(17), "java.util.random.RandomGeneratorFactory");
		//
		map.put(Integer.valueOf(18), "java.net.spi.InetAddressResolver");
		//
		map.put(Integer.valueOf(19), "java.lang.WrongThreadException");
		//
		Integer result = null;
		//
		for (final Entry<Integer, String> entry : map.entrySet()) {
			//
			if (entry == null || forName(entry.getValue()) == null) {
				//
				continue;
				//
			} // if
				//
			result = ObjectUtils.max(result, entry.getKey());
			//
		} // for
			//
		return result;
		//
	}

	private static Class<?> forName(final String className) {
		//
		try {
			//
			return Class.forName(className);
			//
		} catch (final ClassNotFoundException e) {
			//
			return null;
			//
		} // try
			//
	}

}