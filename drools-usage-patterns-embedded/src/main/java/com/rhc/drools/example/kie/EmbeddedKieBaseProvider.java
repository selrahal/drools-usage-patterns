package com.rhc.drools.example.kie;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;

public class EmbeddedKieBaseProvider {
	private final KieContainer KIE_CONTAINER;
	
	public EmbeddedKieBaseProvider() {
		//This gives us a KieContainer that is built using the classpath. Any rules assets on the
		//classpath will be used.
		KIE_CONTAINER = KieServices.Factory.get().newKieClasspathContainer();
	}
	
	public KieBase getKieBase() {
		return KIE_CONTAINER.getKieBase();
	}
}
