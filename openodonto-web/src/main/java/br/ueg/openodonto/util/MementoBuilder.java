package br.ueg.openodonto.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MementoBuilder {

	@SuppressWarnings("unchecked")
	public static <T> T deepClone(T objeto){
		T memento = null;
		try {
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			ObjectOutputStream oout = new ObjectOutputStream(bout);
			oout.writeObject(objeto);
			ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
			ObjectInputStream in = new ObjectInputStream(bin);
			return (T)in.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return memento;
	}
	
}
