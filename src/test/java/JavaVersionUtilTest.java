import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Assert;
import org.junit.Test;

public class JavaVersionUtilTest {

	@Test
	public void testGetJavaVersionAsInt() throws IOException {
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
			final int major = 0xFFFF & dis.readShort();
			//
			final int version = JavaVersionUtil.getJavaVersionAsInt();
			//
			Assert.assertEquals(Integer.toString(major) + "!=" + version, Integer.valueOf(major - 44),
					Integer.valueOf(version));
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