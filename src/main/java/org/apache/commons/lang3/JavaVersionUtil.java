package org.apache.commons.lang3;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public final class JavaVersionUtil {

	private JavaVersionUtil() {
	}

	public static JavaVersion getJavaVersion() {
		//
		final Integer integer = getJavaVersionAsInteger();
		//
		final JavaVersion[] jvs = JavaVersion.values();
		//
		if (jvs != null && integer != null) {
			//
			String name = null;
			//
			List<JavaVersion> list = null;
			//
			for (final JavaVersion jv : jvs) {
				//
				if (jv == null || (name = jv.name()) == null) {
					//
					continue;
					//
				} // if
					//
				if (name.endsWith(integer.toString())) {
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
				JavaVersion jv = null;
				//
				for (int i = size - 1; i >= 0; i--) {
					//
					if ((jv = list.get(i)) == null) {
						//
						continue;
						//
					} // if
						//
						// Remove JAVA_0_9
						//
					if (integer != null && !jv.toString().equals(integer.toString())) {
						//
						list.remove(i);
						//
					} // if
						//
				} // for
					//
				final Field[] fs = JavaVersion.class.getDeclaredFields();
				//
				return Collections.max(list, new Comparator<JavaVersion>() {

					@Override
					public int compare(final JavaVersion jv1, final JavaVersion jv2) {
						//
						return ObjectUtils.compare(
								Double.valueOf(StringUtils.replace(
										StringUtils.substringAfter(getName(getFieldByValue(fs, jv1)), "_"), "_", ".")),
								Double.valueOf(StringUtils.replace(
										StringUtils.substringAfter(getName(getFieldByValue(fs, jv2)), "_"), "_", ".")));
						//
					}

				});
				//
			} // if
				//
		} // if
			//
		return null;
		//
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
				if (f.get(null) == value && !fields.contains(value)) {
					//
					fields.add(f);
					//
				} // if
					//
			} catch (final IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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

	private static Integer getJavaVersionAsInteger() {
		//
		if (forName("java.lang.WrongThreadException") != null) {
			//
			return 19;
			//
		} else if (forName("java.net.spi.InetAddressResolver") != null) {
			//
			return 18;
			//
		} else if (forName("java.util.random.RandomGeneratorFactory") != null) {
			//
			return 17;
			//
		} else if (forName("java.net.UnixDomainSocketAddress") != null) {
			//
			return 16;
			//
		} else if (forName("java.security.interfaces.EdECKey") != null) {
			//
			return 15;
			//
		} else if (forName("java.io.Serial") != null) {
			//
			return 14;
			//
		} else if (forName("com.sun.source.util.ParameterNameProvider") != null) {
			//
			return 13;
			//
		} else if (forName("com.sun.source.doctree.SystemPropertyTree") != null) {
			//
			return 12;
			//
		} else if (forName("java.net.http.HttpClient") != null) {
			//
			return 11;
			//
		} else if (forName("com.sun.source.doctree.SummaryTree") != null) {
			//
			return 10;
			//
		} else if (forName("java.lang.Module") != null) {
			//
			return 9;
			//
		} else if (forName("java.util.stream.Stream") != null) {
			//
			return 8;
			//
		} else if (forName("java.nio.file.attribute.FileAttribute") != null) {
			//
			return 7;
			//
		} else if (forName("java.io.Console") != null) {
			//
			return 6;
			//
		} else if (forName("java.lang.ProcessBuilder") != null) {
			//
			return 5;
			//
		} // if
			//
		return getJavaVersionAsInteger1();
		//
	}

	private static Integer getJavaVersionAsInteger1() {
		//
		if (forName("java.nio.ByteBuffer") != null) {
			//
			return 4;
			//
		} else if (forName("java.lang.reflect.InvocationHandler") != null) {
			//
			return 3;
			//
		} else if (forName("java.util.Collection") != null) {
			//
			return 2;
			//
		} else if (forName("java.lang.Object") != null) {
			//
			return 1;
			//
		} // if
			//
		return null;
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