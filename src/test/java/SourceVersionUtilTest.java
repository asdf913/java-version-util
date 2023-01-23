import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.lang.model.SourceVersion;

import org.junit.Assert;
import org.junit.Test;

public class SourceVersionUtilTest {

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

}