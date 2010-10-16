package org.ztemplates.web.workflow;


public class ZWorkflowDefinition {
    public <WI extends ZIWorkflowInput, WO extends ZIWorkflowOutput, WA extends ZIWorkflowAction<WI, WO>> void addAction(Class<WA> act, Class<WI> in)
    {	
    }
    
    public <WI extends ZIWorkflowInput, WO extends ZIWorkflowOutput, WA extends ZIWorkflowAction<WI, WO>> void addTransition(Class<WA> act1, Class<WO> act1Out, Class<? extends ZIWorkflowAction>... targets)
    {
    }
}
