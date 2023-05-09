package voxel.game.io;

import static org.lwjgl.BufferUtils.createByteBuffer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.io.IOUtils;
import org.lwjgl.BufferUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.undercouch.bson4jackson.BsonFactory;

public final class Utility {
	
	public static ObjectMapper objectMapper = new ObjectMapper(new BsonFactory());
	
	public static byte[] compress(byte[] content) {
	    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	    try {
	        GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream);
	        gzipOutputStream.write(content);
	        gzipOutputStream.close();
	    } catch (IOException e) {
	        throw new RuntimeException(e);
	    }
	    return byteArrayOutputStream.toByteArray();
	}

	public static byte[] decompress(byte[] data) throws IOException {
	    ByteArrayOutputStream out = new ByteArrayOutputStream();
	    try {
	        IOUtils.copy(new GZIPInputStream(new ByteArrayInputStream(data)), out);
	    } catch (IOException e) {
	        throw new RuntimeException(e);
	    }
	    return out.toByteArray();
	}
	
	public static void writeCompressedFile(String path, byte[] bytes) throws IOException {
		Files.write(Paths.get(path), compress(bytes));
	}
	
	public static byte[] readCompressedFile(String path) throws IOException {
		return decompress(Files.readAllBytes(Paths.get(path)));
	}
	
	public static void writeFile(String path, byte[] bytes) throws IOException {
		Files.write(Paths.get(path), bytes);
	}
	
	public static byte[] readFile(String path) throws IOException {
		return Files.readAllBytes(Paths.get(path));
	}
		
	private static ByteBuffer resizeBuffer(ByteBuffer buffer, int newCapacity) {
		ByteBuffer newBuffer = BufferUtils.createByteBuffer(newCapacity);
		buffer.flip();
		newBuffer.put(buffer);
		return newBuffer;
	}

	@SuppressWarnings("resource")
	public static ByteBuffer resourceToByteBuffer(String resource, int bufferSize) throws IOException {
		ByteBuffer buffer;

		File file = new File(resource);
		if(file.isFile()) {						
			FileChannel fc = new FileInputStream(file).getChannel();
			buffer = BufferUtils.createByteBuffer((int)fc.size() + 1);

			while(fc.read(buffer) != -1);
			
			fc.close();
		} 
		else {
			buffer = createByteBuffer(bufferSize);

			InputStream source = Thread.currentThread().getContextClassLoader().getResourceAsStream(resource);
			if(source == null) {
				throw new FileNotFoundException(resource);
			}
			try {
				ReadableByteChannel rbc = Channels.newChannel(source);
				try {
					while(true) {
						int bytes = rbc.read(buffer);
						if(bytes == -1) {
							break;
						}
						if(buffer.remaining() == 0) {
							buffer = resizeBuffer(buffer, buffer.capacity() * 2);
						}
					}
				} 
				finally {
					rbc.close();
				}
			} 
			finally {
				source.close();
			}
		}

		buffer.flip();
		return buffer;
	}
	
}
