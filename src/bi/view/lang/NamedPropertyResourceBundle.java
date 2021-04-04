package bi.view.lang;

import java.io.IOException;
import java.io.InputStream;
import java.util.PropertyResourceBundle;

public class NamedPropertyResourceBundle extends PropertyResourceBundle{

	String name = ""; //$NON-NLS-1$
	public NamedPropertyResourceBundle(InputStream stream, String name) throws IOException {
		super(stream);
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}