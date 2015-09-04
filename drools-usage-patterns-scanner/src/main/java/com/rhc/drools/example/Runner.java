package com.rhc.drools.example;

import org.kie.api.KieBase;
import org.kie.api.runtime.KieSession;

import com.rhc.drools.example.kie.KieBaseProvider;
import com.rhc.drools.example.model.Person;

public class Runner {
	public static void main(String[] args) {
		KieBaseProvider kbp = new KieBaseProvider();
		KieBase kieBase = kbp.getKieBaseForDecisionTable();
		Person person = new Person();
		person.setName("Sal");
		determinePersonsAge(person,kieBase);
		System.out.println(person);
	}
	
	private static void determinePersonsAge(Person person, KieBase kieBase) {
		KieSession kieSession = kieBase.newKieSession();
		kieSession.insert(person);
		kieSession.fireAllRules();
		kieSession.dispose();
	}

}
