package com.truviq.contract;


import java.util.Random;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class ToGenerateBusinessKeyRandom  implements JavaDelegate{

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		Random nRandom=new Random();
		execution.setVariable("ProcessBusinessKey", nRandom.nextInt(3000));
		
	}

}
