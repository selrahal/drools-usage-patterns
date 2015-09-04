package com.rhc.drools.example.kie;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;

public class KieBaseProvider {
	private static final KieServices KIE_SERVICES = KieServices.Factory.get();
	private final KieContainer KIE_CONTAINER;
	
	public KieBaseProvider() {
		String groupId = "com.rhc.drools.example";
		String artifactId = "drools-usage-patterns-kjar";
		String version = "1.0.0-SNAPSHOT";
		ReleaseId releaseId = KIE_SERVICES.newReleaseId(groupId, artifactId, version);
		KIE_CONTAINER = KieServices.Factory.get().newKieContainer(releaseId);
	}
	
	public KieBase getKieBaseForDecisionTable() {
		return KIE_CONTAINER.getKieBase();
	}
}
