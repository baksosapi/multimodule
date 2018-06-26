package org.smartregister.bidan.database.model;

import static org.smartregister.bidan.constant.BidanConstants.ScreenStage;

/**
 * Created by ndegwamartin on 23/01/2018.
 */

public class Contact {

    private Long id;
    private String baseEntityId;
    private String indexRelationship;
    private ScreenStage stage;
    private String firstName;
    private String lastName;
    private String age;
    private String gender;
    private String contactId;
    private String formSubmissionId;
    private boolean index;
    private String nationalID;
    private String createdAt;
    private Long updatedAt;

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getBaseEntityId() {
        return baseEntityId;
    }

    public void setBaseEntityId(String baseEntityId) {
        this.baseEntityId = baseEntityId;
    }

    public String getIndexRelationship() {
        return indexRelationship;
    }

    public void setIndexRelationship(String indexRelationship) {
        this.indexRelationship = indexRelationship;
    }

    public ScreenStage getStage() {
        return stage;
    }

    public void setStage(ScreenStage stage) {
        this.stage = stage;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getFormSubmissionId() {
        return formSubmissionId;
    }

    public void setFormSubmissionId(String formSubmissionId) {
        this.formSubmissionId = formSubmissionId;
    }

    public boolean isIndex() {
        return index;
    }

    public void setIndex(boolean index) {
        this.index = index;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNationalID() {
        return nationalID;
    }

    public void setNationalID(String nationalID) {
        this.nationalID = nationalID;
    }
}
