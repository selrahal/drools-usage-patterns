package com.rhc.drools.example.util;

import org.drools.core.runtime.help.impl.BatchExecutionHelperProviderImpl;
import org.kie.api.command.Command;
import org.kie.server.api.model.ServiceResponse;
import org.kie.server.api.model.ServiceResponse.ResponseType;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.impl.KieServicesClientImpl;
import org.kie.server.client.impl.KieServicesConfigurationImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.xstream.XStream;

public class RemoteCommandExecutor {
	private static final Logger LOG = LoggerFactory.getLogger(RemoteCommandExecutor.class);
    private KieServicesClient kieServicesClient;


    public RemoteCommandExecutor() {
    	//Creating a client to the KieServer requires a url, and username/password for a user
    	//with the 'kie-server' role.
    	String url = "http://localhost:9080/kie-server/services/rest/server";
        String username = "bpmsAdmin";
        String password = "abcd1234!";
        KieServicesConfiguration config = new KieServicesConfigurationImpl(url, username, password);
        kieServicesClient = new KieServicesClientImpl(config);
    }

	public Object execute(Command command, String containerId) {
		BatchExecutionHelperProviderImpl batchExecutionHelperProviderImpl = new BatchExecutionHelperProviderImpl();
		XStream xstream = batchExecutionHelperProviderImpl
				.newXStreamMarshaller();
		String payload = xstream.toXML(command);
		LOG.debug("payload=" + payload);
		ServiceResponse<String> serviceResponse = kieServicesClient
				.executeCommands(containerId, payload);
		if (serviceResponse.getType().equals(ResponseType.FAILURE)) {
			throw new RuntimeException(serviceResponse.getMsg());
		}
		String response = serviceResponse.getResult();
		LOG.debug("response=" + response);
		return xstream.fromXML(response);
	}
}