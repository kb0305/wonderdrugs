package com.example.Wonderdrug.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

//Wrapper class for the full response
public class ApiResponse {
 private String responseStatus;
 private ResponseDetails responseDetails;
 private Employee[] data;

 // Getters and Setters
 public String getResponseStatus() {
     return responseStatus;
 }

 public void setResponseStatus(String responseStatus) {
     this.responseStatus = responseStatus;
 }

 public ResponseDetails getResponseDetails() {
     return responseDetails;
 }

 public void setResponseDetails(ResponseDetails responseDetails) {
     this.responseDetails = responseDetails;
 }

 public Employee[] getData() {
     return data;
 }

 public void setData(Employee[] data) {
     this.data = data;
 }

 // Inner class for response details
 public static class ResponseDetails {
     private int total;
     private int offset;
     private int limit;
     private String url;
     private ObjectDetails object;

     // Getters and Setters
     public int getTotal() {
         return total;
     }

     public void setTotal(int total) {
         this.total = total;
     }

     public int getOffset() {
         return offset;
     }

     public void setOffset(int offset) {
         this.offset = offset;
     }

     public int getLimit() {
         return limit;
     }

     public void setLimit(int limit) {
         this.limit = limit;
     }

     public String getUrl() {
         return url;
     }

     public void setUrl(String url) {
         this.url = url;
     }

     public ObjectDetails getObject() {
         return object;
     }

     public void setObject(ObjectDetails object) {
         this.object = object;
     }
 }

 // Inner class for the "object" details inside responseDetails
 public static class ObjectDetails {
     private String url;
     private String label;
     private String name;
     private String labelPlural;
     private String prefix;
     private int order;
     private boolean inMenu;
     private String source;
     private String[] status;
     private String configurationState;

     // Getters and Setters
     public String getUrl() {
         return url;
     }

     public void setUrl(String url) {
         this.url = url;
     }

     public String getLabel() {
         return label;
     }

     public void setLabel(String label) {
         this.label = label;
     }

     public String getName() {
         return name;
     }

     public void setName(String name) {
         this.name = name;
     }

     public String getLabelPlural() {
         return labelPlural;
     }

     public void setLabelPlural(String labelPlural) {
         this.labelPlural = labelPlural;
     }

     public String getPrefix() {
         return prefix;
     }

     public void setPrefix(String prefix) {
         this.prefix = prefix;
     }

     public int getOrder() {
         return order;
     }

     public void setOrder(int order) {
         this.order = order;
     }

     public boolean isInMenu() {
         return inMenu;
     }

     public void setInMenu(boolean inMenu) {
         this.inMenu = inMenu;
     }

     public String getSource() {
         return source;
     }

     public void setSource(String source) {
         this.source = source;
     }

     public String[] getStatus() {
         return status;
     }

     public void setStatus(String[] status) {
         this.status = status;
     }

     public String getConfigurationState() {
         return configurationState;
     }

     public void setConfigurationState(String configurationState) {
         this.configurationState = configurationState;
     }
 }

 // Inner class for Employee data
 
 public static class Employee {
	 @JsonProperty("id")
     private String id;
	 @JsonProperty("first_name__c")
     private String firstName;
	 @JsonProperty("last_name__c")
     private String lastName;
	 @JsonProperty("employee_id__c")
     private String employeeId;
	 @JsonProperty("on_board_date__c")
     private String onBoardDate;
	 @JsonProperty("name__v")
     private String name;
	 @JsonProperty("role__c")
     private String[] role;

     // Getters and Setters
     public String getId() {
         return id;
     }

     public void setId(String id) {
         this.id = id;
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

     public String getEmployeeId() {
         return employeeId;
     }

     public void setEmployeeId(String employeeId) {
         this.employeeId = employeeId;
     }

     public String getOnBoardDate() {
         return onBoardDate;
     }

     public void setOnBoardDate(String onBoardDate) {
         this.onBoardDate = onBoardDate;
     }

     public String getName() {
         return name;
     }

     public void setName(String name) {
         this.name = name;
     }

     public String[] getRole() {
         return role;
     }

     public void setRole(String[] role) {
         this.role = role;
     }
 }
}

