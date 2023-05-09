package voxel.game.io;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.function.Supplier;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import voxel.game.objects.index.SectionIndex;

public class SectionIndexSupplier implements Supplier<SectionIndex> {
	
	private String path;
	
	public SectionIndexSupplier(String path) {
		this.path = path;
	}

	@Override
	public SectionIndex get() {
		try {
			return Utility.objectMapper.readValue(new ByteArrayInputStream(Utility.readFile(path)), SectionIndex.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
