package bi.controller.combination.generator;

import model.Dependency;

public interface CombinationGenerator {

	public void setDependency(Dependency dependency);

	public void generate() throws Exception;
}
