package com.px.employmentsite.model;

public class PcLabelCompany {
    private String labelID;

    private String companyID;

    public String getLabelID() {
        return labelID;
    }

    public void setLabelID(String labelID) {
        this.labelID = labelID == null ? null : labelID.trim();
    }

    public String getCompanyID() {
        return companyID;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID == null ? null : companyID.trim();
    }
}