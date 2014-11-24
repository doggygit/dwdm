package dwdm.intersection;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class ChunkWriterTest {
	
	@Rule
	public TemporaryFolder tmp = new TemporaryFolder();
	
	File tmpFolder;

	ChunkWriter chunkWriter;
	
	@Before
	public void setUp() throws IOException{
		tmpFolder = tmp.newFolder();
		chunkWriter = new ChunkWriter(tmpFolder, 1);
	}
	
	@Test
	public void testFileCreated() throws IOException {
		chunkWriter.close();
		assertTrue(new File(tmpFolder, "chunk01.cpr").exists());
	}
	
	@Test
	public void testFileHasCorrectSize1() throws IOException{
		chunkWriter.getNextIndex();
		chunkWriter.close();
		assertEquals(new File(tmpFolder, "chunk01.cpr").length(), 6);
	}
	
	@Test
	public void testFileHasCorrectSize2() throws IOException{
		for(int i = 0; i < 32_001; i++){
			chunkWriter.getNextIndex();
		}
		chunkWriter.close();
		assertEquals(new File(tmpFolder, "chunk01.cpr").length(), 32_001 * 6);
	}
	
	@Test
	public void testFileHasCorrectContent() throws IOException{
		chunkWriter.getNextIndex();
		chunkWriter.buffer[0] = 14;
		chunkWriter.buffer[1] = 1;
		chunkWriter.buffer[2] = 3;
		chunkWriter.close();
		Path path = Paths.get(tmpFolder.getAbsolutePath(), "chunk01.cpr");
		byte[] data = Files.readAllBytes(path);
		assertArrayEquals(new byte[]{14, 1, 3, 0, 0, 0}, data);
	}
	
	
}
