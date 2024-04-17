public final class JavaVersionUtil {

	private JavaVersionUtil() {
	}

	public static int getJavaVersionAsInt() {
		//
		final Integer integer = getJavaVersionAsInteger();
		//
		if (integer != null) {
			//
			return integer.intValue();
			//
		} // if
			//
		throw new IllegalStateException();
		//
	}

	public static Integer getJavaVersionAsInteger() {
		//
		if (forName("java.lang.classfile.attribute.ConstantValueAttribute") != null) {
			//
			return 22;
			//
		} else if (forName("javax.crypto.KEM") != null) {
			//
			return 21;
			//
		} else if (forName("java.lang.foreign.Arena") != null) {
			//
			return 20;
			//
		} else if (forName("java.lang.WrongThreadException") != null) {
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
		} // if
			//
		return getJavaVersionAsInteger1();
		//
	}

	private static Integer getJavaVersionAsInteger1() {
		//
		if (forName("java.io.Console") != null) {
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