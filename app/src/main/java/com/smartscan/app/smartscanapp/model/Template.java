package com.smartscan.app.smartscanapp.model;

/**
 * Created by Jack_Allcock on 22/06/2017.
 */

public class Template {

    private String templateName;
    private String templateDescription;
    private int templatePower;
    private int templateStatus;

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateDescription() {
        return templateDescription;
    }

    public void setTemplateDescription(String templateDescription) {
        this.templateDescription = templateDescription;
    }

    public int getTemplatePower() {
        return templatePower;
    }

    public void setTemplatePower(int templatePower) {
        this.templatePower = templatePower;
    }

    public int getTemplateStatus() {
        return templateStatus;
    }

    public void setTemplateStatus(int templateStatus) {
        this.templateStatus = templateStatus;
    }
}
