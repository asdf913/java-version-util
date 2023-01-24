package org.apache.commons.lang3;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.select.Selector;

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
				e.printStackTrace();
				//
			} // try
				//
		} // if
			//
		throw new IllegalStateException();
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
			Element element = null;
			//
			String text = null;
			//
			for (int i = 0; elements != null && i < elements.size(); i++) {
				//
				if ((element = elements.get(i)) == null || !StringUtils.startsWith(text = element.text(), "Java SE")) {
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
			e.printStackTrace();
			//
		} // try
			//
		return latestVersion;
		//
	}

	private static Elements select(final Element instance, final String cssQuery) {
		return instance != null ? instance.select(cssQuery) : null;
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
				e.printStackTrace();
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