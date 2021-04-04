package model.brimport;

import java.util.Set;

/**
 * @author Sergio Moreno
 *  represents all the parts of a business rules text
 */
public interface BRPart {

	public BRLanguaje getLanguaje();
	public Set<String> getInvalidIdentifiers();
}
