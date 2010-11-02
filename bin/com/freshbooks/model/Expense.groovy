package com.freshbooks.model

import groovy.xml.StreamingMarkupBuilder

class Expense {
    static  final String SIMPLE_DATE_FORMAT = "yyyy-MM-dd"
    String expenseId
    String staffId
    String categoryId
    String projectId
    String clientId
    double amount
    Date   date
    String notes
    String vendor
    String status
    String tax1Name
    double tax1Percent
    double tax1Amount
    String tax2Name
    double tax2Percent
    double tax2Amount

    static PagedResponseContent parse(Response responseObj) {
        def response = new XmlSlurper().parseText(responseObj.content)
        def pagedResponseContent = new PagedResponseContent(contents:[])
        List<Expense> expensesList = []
        if(response.expenses.size() > 0){
            pagedResponseContent.page = response.expenses.@page.toString().toInteger()
            pagedResponseContent.perPage = response.expenses.@per_page.toString().toInteger()
            pagedResponseContent.pages = response.expenses.@pages.toString().toInteger()
            pagedResponseContent.total = response.expenses.@total.toString().toInteger()
            response.expenses.expense.each{
                def expenseObj = new Expense()
                if(it.expense_id) expenseObj.expenseId=it.expense_id
                if(it.staff_id) expenseObj.staffId=it.staff_id
                if(it.category_id) expenseObj.categoryId=it.category_id
                if(it.project_id) expenseObj.projectId=it.project_id
                if(it.client_id) expenseObj.clientId=it.client_id
                if(it.amount) expenseObj.amount=it.amount.toString().toDouble()
                if(it.date) expenseObj.date= new Date().parse(SIMPLE_DATE_FORMAT, it.date.toString())
                if(it.notes) expenseObj.notes=it.notes
                if(it.vendor) expenseObj.vendor=it.vendor
                if(it.status) expenseObj.status=it.status
                if(it.tax1_name) expenseObj.tax1Name=it.tax1_name
                if(it.tax1_percent && it.tax1_percent != "") expenseObj.tax1Percent=it.tax1_percent.toString().toDouble()
                if(it.tax1_amount && it.tax1_amount != "") expenseObj.tax1Percent=it.tax1_amount.toString().toDouble()
                if(it.tax2_name) expenseObj.tax2Name=it.tax2_name
                if(it.tax2_percent && it.tax2_percent != "") expenseObj.tax2Percent=it.tax2_percent.toString().toDouble()
                if(it.tax2_amount && it.tax2_amount != "") expenseObj.tax2Percent=it.tax2_amount.toString().toDouble()
                pagedResponseContent << expenseObj
            }
        } else {
            pagedResponseContent.page = 1
            pagedResponseContent.perPage = 1
            pagedResponseContent.pages = 1
            pagedResponseContent.total = 1
            response.expense.each{
                def expenseObj = new Expense()
                if(it.expense_id) expenseObj.expenseId=it.expense_id
                if(it.staff_id) expenseObj.staffId=it.staff_id
                if(it.category_id) expenseObj.categoryId=it.category_id
                if(it.project_id) expenseObj.projectId=it.project_id
                if(it.client_id) expenseObj.clientId=it.client_id
                if(it.amount) expenseObj.amount=it.amount.toString().toDouble()
                if(it.date) expenseObj.date= new Date().parse(SIMPLE_DATE_FORMAT, it.date.toString())
                if(it.notes) expenseObj.notes=it.notes
                if(it.vendor) expenseObj.vendor=it.vendor
                if(it.status) expenseObj.status=it.status
                if(it.tax1_name) expenseObj.tax1Name=it.tax1_name
                if(it.tax1_percent && it.tax1_percent != "") expenseObj.tax1Percent=it.tax1_percent.toString().toDouble()
                if(it.tax1_amount && it.tax1_amount != "") expenseObj.tax1Percent=it.tax1_amount.toString().toDouble()
                if(it.tax2_name) expenseObj.tax2Name=it.tax2_name
                if(it.tax2_percent && it.tax2_percent != "") expenseObj.tax2Percent=it.tax2_percent.toString().toDouble()
                if(it.tax2_amount && it.tax2_amount != "") expenseObj.tax2Percent=it.tax2_amount.toString().toDouble()
                pagedResponseContent << expenseObj
            }
        }
        return pagedResponseContent
    }
    
    String toString(){
        return  "{ expenseId: " + expenseId +
                ", staffId: " + staffId +
                ", categoryId: " + categoryId +
                ", projectId: " + projectId +
                ", clientId: " + clientId +
                ", amount: " + amount +
                ", date: " + date +
                ", notes: " + notes +
                ", vendor: " + vendor +
                ", status: " + status +
                ", tax1Name: " + tax1Name +
                ", tax1Percent: " + tax1Percent +
                ", tax2Name: " + tax2Name +
                ", tax2Percent: " + tax2Percent + " }"
    }


    def xmlClosure() {
        return {
            expense{
                if(expenseId) expense_id(expenseId)
                if(categoryId) category_id(categoryId)
                if(projectId) project_id(projectId)
                if(clientId) client_id(clientId)
                if(staffId) staff_id(staffId)
                if(amount) amount(amount)
                if(date) date(date.format(SIMPLE_DATE_FORMAT))
                if(notes) notes(notes)
                if(status) status(status)
                if(vendor) vendor(vendor)
                if(tax1Name) tax1_name(tax1Name)
                if(tax1Percent) tax1_percent(tax1Percent)
                if(tax2Name) tax2_name(tax2Name)
                if(tax2Percent) tax2_percent(tax2Percent)
            }
        }
    }

    def toXML(){
        def outputBuilder = new StreamingMarkupBuilder()
        outputBuilder.encoding = "UTF-8"
        String data = outputBuilder.bind {
            expense{
                if(expenseId) expense_id(expenseId)
                if(staffId) staff_id(staffId)
                if(categoryId) category_id(categoryId)
                if(projectId) project_id(projectId)
                if(clientId) client_id(clientId)
                if(amount) amount(amount)
                if(date) date(date)
                if(notes) notes(notes)
                if(vendor) vendor(vendor)
                if(status) status(status)
                if(tax1Name) tax1_name(tax1Name)
                if(tax1Percent) tax1_percent(tax1Percent)
                if(tax2Name) tax2_name(tax2Name)
                if(tax2Percent) tax2_percent(tax2Percent)
            }
        }
        return data
    }

}

//    <expense>
//
//       <expense_id>430</expense_id>
//       <staff_id>1</staff_id>
//       <category_id>5</category_id>
//       <project_id>10</project_id>
//       <client_id>10</client_id>
//       <amount>29.95</amount>
//       <date>2008-11-01</date>
//       <notes>Hardware.</notes>
//       <vendor>FreshBooks</vendor>
//       <status>1</status> <!-- Can be 0 (not assigned), 1 (unbilled) or 2 (invoiced)-->
//       <tax1_name></tax1_name>
//       <tax1_percent></tax1_percent>
//       <tax1_amount></tax1_amount>
//       <tax2_name></tax2_name>
//       <tax2_percent></tax2_percent>
//       <tax2_amount></tax2_amount>
//
//     </expense>
