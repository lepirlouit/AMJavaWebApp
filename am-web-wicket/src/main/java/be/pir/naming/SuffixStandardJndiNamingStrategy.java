package be.pir.naming;

import org.wicketstuff.javaee.naming.StandardJndiNamingStrategy;

public class SuffixStandardJndiNamingStrategy extends StandardJndiNamingStrategy {
	private String suffix;
	public SuffixStandardJndiNamingStrategy(String suffix) {
		this.suffix = suffix;
	}
	@Override
	public String calculateName(String ejbName, Class<?> ejbType) {

		return super.calculateName(ejbName, ejbType);
	}
}
