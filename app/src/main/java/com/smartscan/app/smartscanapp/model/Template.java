package com.smartscan.app.smartscanapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jack_Allcock on 22/06/2017.
 */

public class Template implements Parcelable {

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

    protected Template(Parcel in) {
        templateName = in.readString();
        templateDescription = in.readString();
        templatePower = in.readInt();
        templateStatus = in.readInt();
    }

    public Template() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(templateName);
        dest.writeString(templateDescription);
        dest.writeInt(templatePower);
        dest.writeInt(templateStatus);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Template> CREATOR = new Parcelable.Creator<Template>() {
        @Override
        public Template createFromParcel(Parcel in) {
            return new Template(in);
        }

        @Override
        public Template[] newArray(int size) {
            return new Template[size];
        }
    };
}
