import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Assert;
import org.junit.Test;

public class JavaVersionUtilTest {

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

}