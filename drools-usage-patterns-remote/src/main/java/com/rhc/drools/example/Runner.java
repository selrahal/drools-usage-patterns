package com.rhc.drools.example;

import org.drools.core.command.runtime.BatchExecutionCommandImpl;
import org.drools.core.command.runtime.rule.FireAllRulesCommand;
import org.drools.core.command.runtime.rule.InsertObjectCommand;
import org.kie.api.runtime.ExecutionResults;

import com.rhc.drools.example.model.Person;
import com.rhc.drools.example.util.RemoteCommandExecutor;

public class Runner {
	public static void main(String[] args) {
		//Create a Fact
		Person person = new Person();
		person.setName("Sal");
		
		//Determine age using a certain container
		Person sal = determinePersonsAge(person, "age");
		
		//Was our age set correctly?
		System.out.println(sal);
	}
	
	private static Person determinePersonsAge(Person person, String containerId) {
		//The container id is used to specify the KieBase to be used on the remote server
		RemoteCommandExecutor remoteCommandExecutor = new RemoteCommandExecutor();

        BatchExecutionCommandImpl batchExecutionCommand = new BatchExecutionCommandImpl();
        
        //Set the KieSession name
        batchExecutionCommand.setLookup("defaultKieSession");
        InsertObjectCommand insertObjectCommand = new InsertObjectCommand(person);
        
        //Ensure the person object is returned
        insertObjectCommand.setOutIdentifier("person");
        FireAllRulesCommand fireAllRulesCommand = new FireAllRulesCommand();
        batchExecutionCommand.getCommands().add(insertObjectCommand);
        batchExecutionCommand.getCommands().add(fireAllRulesCommand);

        ExecutionResults executionResult = (ExecutionResults) remoteCommandExecutor.execute(batchExecutionCommand, containerId);
		
        //Get the person object back 
        Object result = executionResult.getValue("person");
		
		
		if (result instanceof Person) {
			return (Person)result;
		} else {
			System.err.println(result.toString());
			return null;
		}
	}

}
