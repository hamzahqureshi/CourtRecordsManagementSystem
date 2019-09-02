package com.pjs.cjs.model;

import javax.persistence.*;

@Entity
@Table(name = "file_details")
public class FileDetails {

    @Id
    private String id;

    @Column(name = "court_name")
    private String courtName;

    @Column(name = "appeal_number")
    private String appealNumber;

    @Column(name = "appellant")
    private String appellant;

    @Column(name = "respondent")
    private String respondent;

    @Column(name = "date_of_hearing")
    private String dateOfHearing;

    @Column(name = "advocates_for_appellant")
    private String advocatesForAppellant;

    @Column(name = "advocates_for_respondent")
    private String advocatesForRespondent;

    @Column(name = "judge_name")
    private String judgeName;

    @OneToOne
    @MapsId
    private SimpleFile file;

    public FileDetails() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourtName() {
        return courtName;
    }

    public void setCourtName(String courtName) {
        this.courtName = courtName;
    }

    public SimpleFile getFile() {
        return file;
    }

    public void setFile(SimpleFile file) {
        this.file = file;
    }

    public String getAppealNumber() {
        return appealNumber;
    }

    public void setAppealNumber(String appealNumber) {
        this.appealNumber = appealNumber;
    }

    public String getAppellant() {
        return appellant;
    }

    public void setAppellant(String appellant) {
        this.appellant = appellant;
    }

    public String getRespondent() {
        return respondent;
    }

    public void setRespondent(String respondent) {
        this.respondent = respondent;
    }

    public String getDateOfHearing() {
        return dateOfHearing;
    }

    public void setDateOfHearing(String dateOfHearing) {
        this.dateOfHearing = dateOfHearing;
    }

    public String getAdvocatesForAppellant() {
        return advocatesForAppellant;
    }

    public void setAdvocatesForAppellant(String advocatesForAppellant) {
        this.advocatesForAppellant = advocatesForAppellant;
    }

    public String getAdvocatesForRespondent() {
        return advocatesForRespondent;
    }

    public void setAdvocatesForRespondent(String advocatesForRespondent) {
        this.advocatesForRespondent = advocatesForRespondent;
    }

    public String getJudgeName() {
        return judgeName;
    }

    public void setJudgeName(String judgeName) {
        this.judgeName = judgeName;
    }
}

