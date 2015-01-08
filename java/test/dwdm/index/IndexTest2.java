package dwdm.index;

import net.sf.jsi.SpatialIndex;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.Before;

/**
 * Overrides the set up by first writing the index into a
 * serialized byte representation and then read it again as a 
 * Java object.
 * To verify this process, the tests from {@link IndexTest} are
 * reused
 * @author Christian Windolf
 *
 */
public class IndexTest2 extends IndexTest {

	@Before
	public void setUp() {
		super.setUp();
		
		ByteArrayOutputStream binaryArray = new ByteArrayOutputStream();
		try {
			ObjectOutputStream objectStream = new ObjectOutputStream(binaryArray);
			objectStream.writeObject(index);
			objectStream.close();
			byte[] serializedIndex = binaryArray.toByteArray();
			InputStream input = new ByteArrayInputStream(serializedIndex);
			ObjectInputStream objectInput = new ObjectInputStream(input);
			index = (SpatialIndex) objectInput.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
