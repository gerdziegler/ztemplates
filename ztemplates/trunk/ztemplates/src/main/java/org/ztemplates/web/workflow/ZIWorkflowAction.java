package org.ztemplates.web.workflow;

public interface ZIWorkflowAction<WI extends ZIWorkflowInput, WO extends ZIWorkflowOutput> {
    public void setWorkflowInput(WI input);
    public void setWorkflowOutput(WO output);
}
