import java.util.ArrayList;
import java.util.List;

import javax.lang.model.SourceVersion;

public final class SourceVersionUtil {

	private SourceVersionUtil() {
	}

	public static SourceVersion getJavaVersionAsSourceVersion() {
		//
		final List<SourceVersion> list = getJavaVersionAsSourceVersionList(SourceVersion.values());
		//
		final int size = list != null ? list.size() : 0;
		//
		if (size == 1) {
			//
			return list.get(0);
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

	private static List<SourceVersion> getJavaVersionAsSourceVersionList(final SourceVersion[] svs) {
		//
		List<SourceVersion> list = null;
		//
		final Integer integer = getJavaVersionAsInteger();
		//
		if (svs != null && integer != null) {
			//
			String name = null;
			//
			for (final SourceVersion sv : svs) {
				//
				if (sv == null || (name = sv.name()) == null) {
					//
					continue;
					//
				} // if
					//
				if (name.endsWith(integer.toString())) {
					//
					if (list == null) {
						//
						list = new ArrayList<SourceVersion>();
						//
					} // if
						//
					if (!list.contains(sv)) {
						//
						list.add(sv);
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
		} else if (forName("java.nio.ByteBuffer") != null) {
			//
			return 4;
			//
		} else if (forName("java.lang.reflect.InvocationHandler") != null) {
			//
			return 3;
			//
		} // if
			//
		return getJavaVersionAsInteger1();
		//
	}

	private static Integer getJavaVersionAsInteger1() {
		//
		if (forName("java.util.Collection") != null) {
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