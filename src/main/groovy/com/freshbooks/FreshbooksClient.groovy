package com.freshbooks

import groovy.xml.StreamingMarkupBuilder
import com.freshbooks.model.*
import com.freshbooks.exceptions.*

/**
 *
 * @author Jesse Varnado
 */
class FreshbooksClient {

    private String _key
    private String _url

    FreshbooksClient(String key, String url){
        _key = key
        _url = url
    }

    def performRequest(Request request, def params = null) {//throws RequestFailedException{
        def url = new URL(_url)
        def conn = url.openConnection()
        conn.setRequestMethod("POST")
        conn.setRequestProperty('Authorization', 'Basic ' + (_key+ ":X").bytes.encodeBase64())
        conn.doOutput = true
        def dataWriter = new StringWriter()
        def outputBuilder = new StreamingMarkupBuilder()
        outputBuilder.encoding = "UTF-8"
        String data = outputBuilder.bind {
            mkp.xmlDeclaration()
            out << request.xmlClosure(params)
        }
        def writer = new OutputStreamWriter(conn.outputStream)
        writer.write(data.toString())
        writer.flush()
        writer.close()
        def xml
        Response responseObj = new Response()
        if(conn.responseCode == 200){
            xml = conn.content.text
            def response = new XmlSlurper().parseText(xml)
            if(response.@status == "ok"){
                responseObj.status = response.@status
                responseObj.content = xml
            } else {
                if(response.error.toString().indexOf("not found") > -1 || response.error.toString().indexOf("does not exist") > -1 ){
                    throw new RecordNotFoundException(response.error.toString())
                } else {
                    throw new APIException("Error - Status: " + response.@status + " Error: " + response.error)
                }
            }
        } else {
            //throw new RequestFailedException(status:conn.responseCode, errorMessage:conn.responseMessage)
        }
        return responseObj
    }

    def PagedResponseContent listCallbacks(params) {
        params.putAll([method:RequestMethod.CALLBACK_LIST])
        def requestObj = new Request(params)
        def responseObj = performRequest(requestObj, params)
        return Callback.parse(responseObj)
    }

    def Response createCallback(Callback callback) {
        def params = [method:RequestMethod.CALLBACK_CREATE]
        def requestObj = new Request(params, callback.xmlClosure())
        return performRequest(requestObj, params)
    }

    def Response deleteCallback(String callbackId) {
        def params = [method:RequestMethod.CALLBACK_DELETE, callback_id:callbackId]
        def requestObj = new Request(params)
        return performRequest(requestObj, params)
    }

    def Response resendCallbackToken(String callbackId) {
        def params = [method:RequestMethod.CALLBACK_RESEND_TOKEN, callback_id:callbackId]
        def requestObj = new Request(params)
        return performRequest(requestObj, params)
    }

    def Response verifyCallback(Callback callback) {
        def params = [method:RequestMethod.CALLBACK_VERIFY]
        def requestObj = new Request(params, callback.xmlClosure())
        return performRequest(requestObj, params)
    }

    def getCategory(params) {
        params.putAll([method:RequestMethod.CATEGORY_GET])
        def requestObj = new Request(params)
        def responseObj = performRequest(requestObj, params)
        if(responseObj.status == "ok"){
            return Category.parse(responseObj)
        } else {
            throw new Exception("Request Failed: " + responseObj.toString()) //TODO replace with APIException
        }
    }

    def listCategories() throws Exception {
        def params = [method:RequestMethod.CATEGORY_LIST]
        def requestObj = new Request(params)
        def responseObj = performRequest(requestObj, params)
        if(responseObj.status == "ok"){
            return Category.parse(responseObj)
        } else {
            throw new Exception("Request Failed: " + responseObj.toString()) //TODO replace with APIException
        }
    }

    def createCategory(Category category) {
        def params = [method:RequestMethod.CATEGORY_CREATE]
        def requestObj = new Request(params, category.xmlClosure())
        def responseObj = performRequest(requestObj, params)
        if(responseObj.status == "ok"){
            def slurper = new XmlSlurper().parseText(responseObj.content)
            return slurper.category_id
        } else {
            throw new Exception("Request Failed: " + responseObj.toString()) //TODO replace with APIException
        }
    }

    def deleteCategory(String categoryId) {
        def params = [method:RequestMethod.CATEGORY_DELETE, category_id:categoryId]
        def requestObj = new Request(params)
        def responseObj = performRequest(requestObj, params)
        if(responseObj.status == "ok"){
            return responseObj
        } else {
            throw new Exception("Request Failed: " + responseObj.toString()) //TODO replace with APIException
        }
    }

    def updateCategory(Category category){
        def params = [method:RequestMethod.CATEGORY_UPDATE]
        def requestObj = new Request(params, category.xmlClosure())
        def responseObj = performRequest(requestObj, params)
        if(responseObj.status == "ok"){
            return responseObj
        } else {
            throw new Exception("Request Failed: " + responseObj.toString()) //TODO replace with APIException
        }
    }

    def getClient(params) {
        params.putAll([method:RequestMethod.CLIENT_GET])
        def requestObj = new Request(params)
        def responseObj = performRequest(requestObj, params)
        if(responseObj.status == "ok"){
            return Client.parse(responseObj)
        } else {
            throw new Exception("Request Failed: " + responseObj.toString()) //TODO replace with APIException
        }
    }

    def listClients(params) throws APIException {
        params.putAll([method:RequestMethod.CLIENT_LIST])
        def requestObj = new Request(params)
        def responseObj = performRequest(requestObj, params)
        if(responseObj.status == "ok"){
            return Client.parse(responseObj)
        } else {
            throw new APIException("Request Failed: " + responseObj.toString()) //TODO replace with APIException
        }
    }

    def createClient(Client client) {
        def params = [method:RequestMethod.CLIENT_CREATE]
        def requestObj = new Request(params, client.xmlClosure())
        def responseObj = performRequest(requestObj, params)
        if(responseObj.status == "ok"){
            return responseObj
        } else {
            throw new Exception("Request Failed: " + responseObj.toString()) //TODO replace with APIException
        }
    }

    def deleteClient(String clientId) {
        def params = [method:RequestMethod.CLIENT_DELETE, client_id:clientId]
        def requestObj = new Request(params)
        def responseObj = performRequest(requestObj, params)
        if(responseObj.status == "ok"){
            return responseObj
        } else {
            throw new Exception("Request Failed: " + responseObj.toString()) //TODO replace with APIException
        }
    }

    def updateClient(Client client){
        def params = [method:RequestMethod.CLIENT_UPDATE]
        def requestObj = new Request(params, client.xmlClosure())
        def responseObj = performRequest(requestObj, params)
        if(responseObj.status == "ok"){
            return responseObj
        } else {
            throw new Exception("Request Failed: " + responseObj.toString()) //TODO replace with APIException
        }
    }

    def getEstimate(params) {
        params.putAll([method:RequestMethod.ESTIMATE_GET])
        def requestObj = new Request(params)
        def responseObj = performRequest(requestObj, params)
        if(responseObj.status == "ok"){
            return Estimate.parse(responseObj)
        } else {
            throw new Exception("Request Failed: " + responseObj.toString()) //TODO replace with APIException
        }
    }

    def listEstimates(params) throws Exception {
        params.putAll([method:RequestMethod.ESTIMATE_LIST])
        def requestObj = new Request(params)
        def responseObj = performRequest(requestObj, params)
        if(responseObj.status == "ok"){
            return Estimate.parse(responseObj)
        } else {
            throw new Exception("Request Failed: " + responseObj.toString()) //TODO replace with APIException
        }
    }

    def createEstimate(Estimate estimate) {
        def params = [method:RequestMethod.ESTIMATE_CREATE]
        def requestObj = new Request(params, estimate.xmlClosure())
        def responseObj = performRequest(requestObj, params)
        if(responseObj.status == "ok"){
            return responseObj
        } else {
            throw new Exception("Request Failed: " + responseObj.toString()) //TODO replace with APIException
        }
    }

    def deleteEstimate(String estimateId) {
        def params = [method:RequestMethod.ESTIMATE_DELETE, estimate_id:estimateId]
        def requestObj = new Request(params)
        def responseObj = performRequest(requestObj, params)
        if(responseObj.status == "ok"){
            return responseObj
        } else {
            throw new Exception("Request Failed: " + responseObj.toString()) //TODO replace with APIException
        }
    }

    def updateEstimate(Estimate estimate){
        def params = [method:RequestMethod.ESTIMATE_UPDATE]
        def requestObj = new Request(params, estimate.xmlClosure())
        def responseObj = performRequest(requestObj, params)
        if(responseObj.status == "ok"){
            return responseObj
        } else {
            throw new Exception("Request Failed: " + responseObj.toString()) //TODO replace with APIException
        }
    }

    def sendEstimateByEmail(def estimateId, def _subject, def _message) {
        def params = [method:RequestMethod.ESTIMATE_DELETE,
            estimate_id:estimateId,
            subject:_subject,
            message:_message]
        def requestObj = new Request(params)
        def responseObj = performRequest(requestObj, params)
        if(responseObj.status == "ok"){
            return responseObj
        } else {
            throw new Exception("Request Failed: " + responseObj.toString()) //TODO replace with APIException
        }
    }

    def getExpense(params) {
        params.putAll([method:RequestMethod.EXPENSE_GET])
        def requestObj = new Request(params)
        def responseObj = performRequest(requestObj, params)
        if(responseObj.status == "ok"){
            return Expense.parse(responseObj)
        } else {
            throw new Exception("Request Failed: " + responseObj.toString()) //TODO replace with APIException
        }
    }

    def listExpenses(params) throws Exception {
        params.putAll([method:RequestMethod.EXPENSE_LIST])
        def requestObj = new Request(params)
        def responseObj = performRequest(requestObj, params)
        if(responseObj.status == "ok"){
            return Expense.parse(responseObj)
        } else {
            throw new Exception("Request Failed: " + responseObj.toString()) //TODO replace with APIException
        }
    }

    def createExpense(Expense expense) {
        def params = [method:RequestMethod.EXPENSE_CREATE]
        def requestObj = new Request(params, expense.xmlClosure())
        def responseObj = performRequest(requestObj, params)
        if(responseObj.status == "ok"){
            return responseObj
        } else {
            throw new Exception("Request Failed: " + responseObj.toString()) //TODO replace with APIException
        }
    }

    def deleteExpense(String expenseId) {
        def params = [method:RequestMethod.EXPENSE_DELETE, expense_id:expenseId]
        def requestObj = new Request(params)
        def responseObj = performRequest(requestObj, params)
        if(responseObj.status == "ok"){
            return responseObj
        } else {
            throw new Exception("Request Failed: " + responseObj.toString()) //TODO replace with APIException
        }
    }

    def updateExpense(Expense expense){
        def params = [method:RequestMethod.EXPENSE_UPDATE]
        def requestObj = new Request(params, expense.xmlClosure())
        def responseObj = performRequest(requestObj, params)
        if(responseObj.status == "ok"){
            return responseObj
        } else {
            throw new Exception("Request Failed: " + responseObj.toString()) //TODO replace with APIException
        }
    }

    def PagedResponseContent getInvoice(params){
        params.putAll([method:RequestMethod.INVOICE_GET])
        def requestObj = new Request(params)
        def responseObj = performRequest(requestObj, params)
        return Invoice.parse(responseObj)
    }

    def PagedResponseContent listInvoices(params) {
        params.putAll([method:RequestMethod.INVOICE_LIST])
        def requestObj = new Request(params)
        def responseObj = performRequest(requestObj, params)
        return Invoice.parse(responseObj)
    }

    def Response createInvoice(Invoice invoice) {
        def params = [method:RequestMethod.INVOICE_CREATE]
        def requestObj = new Request(params, invoice.xmlClosure())
        return performRequest(requestObj, params)
    }

    def Response deleteInvoice(String invoiceId) {
        def params = [method:RequestMethod.INVOICE_DELETE, invoice_id:invoiceId]
        def requestObj = new Request(params)
        return performRequest(requestObj, params)
    }

    def Response updateInvoice(Invoice invoice) {
        def params = [method:RequestMethod.INVOICE_UPDATE]
        def requestObj = new Request(params, invoice.xmlClosure())
        return performRequest(requestObj, params)
    }

    def Response sendInvoiceByEmail(def invoiceId, def _subject, def _message) {
        def params = [method:RequestMethod.INVOICE_DELETE,
            invoice_id:invoiceId,
            subject:_subject,
            message:_message]
        def requestObj = new Request(params)
        return performRequest(requestObj, params)
    }

    def PagedResponseContent getItem(params){
        params.putAll([method:RequestMethod.ITEM_GET])
        def requestObj = new Request(params)
        def responseObj = performRequest(requestObj, params)
        return Item.parse(responseObj)
    }

    def PagedResponseContent listItems(params) {
        params.putAll([method:RequestMethod.ITEM_LIST])
        def requestObj = new Request(params)
        def responseObj = performRequest(requestObj, params)
        return Item.parse(responseObj)
    }

    def Response createItem(Item item) {
        def params = [method:RequestMethod.ITEM_CREATE]
        def requestObj = new Request(params, item.xmlClosure())
        return performRequest(requestObj, params)
    }

    def Response deleteItem(String itemId) {
        def params = [method:RequestMethod.ITEM_DELETE, item_id:itemId]
        def requestObj = new Request(params)
        return performRequest(requestObj, params)
    }

    def Response updateItem(Item item) {
        def params = [method:RequestMethod.ITEM_UPDATE]
        def requestObj = new Request(params, item.xmlClosure())
        return performRequest(requestObj, params)
    }

    def PagedResponseContent getPayment(params){
        params.putAll([method:RequestMethod.PAYMENT_GET])
        def requestObj = new Request(params)
        def responseObj = performRequest(requestObj, params)
        return Payment.parse(responseObj)
    }

    def PagedResponseContent listPayments(params) {
        params.putAll([method:RequestMethod.PAYMENT_LIST])
        def requestObj = new Request(params)
        def responseObj = performRequest(requestObj, params)
        return Payment.parse(responseObj)
    }

    def Response createPayment(Payment payment) {
        def params = [method:RequestMethod.PAYMENT_CREATE]
        def requestObj = new Request(params, payment.xmlClosure())
        return performRequest(requestObj, params)
    }

    def Response deletePayment(String paymentId) {
        def params = [method:RequestMethod.PAYMENT_DELETE, payment_id:paymentId]
        def requestObj = new Request(params)
        return performRequest(requestObj, params)
    }

    def Response updatePayment(Payment payment) {
        def params = [method:RequestMethod.PAYMENT_UPDATE]
        def requestObj = new Request(params, payment.xmlClosure())
        return performRequest(requestObj, params)
    }

    def PagedResponseContent getProject(params){
        params.putAll([method:RequestMethod.PROJECT_GET])
        def requestObj = new Request(params)
        def responseObj = performRequest(requestObj, params)
        return Project.parse(responseObj)
    }

    def PagedResponseContent listProjects(params) {
        params.putAll([method:RequestMethod.PROJECT_LIST])
        def requestObj = new Request(params)
        def responseObj = performRequest(requestObj, params)
        return Project.parse(responseObj)
    }

    def Response createProject(Project project) {
        def params = [method:RequestMethod.PROJECT_CREATE]
        def requestObj = new Request(params, project.xmlClosure())
        return performRequest(requestObj, params)
    }

    def Response deleteProject(String projectId) {
        def params = [method:RequestMethod.PROJECT_DELETE, project_id:projectId]
        def requestObj = new Request(params)
        return performRequest(requestObj, params)
    }

    def Response updateProject(Project project) {
        def params = [method:RequestMethod.PROJECT_UPDATE]
        def requestObj = new Request(params, project.xmlClosure())
        return performRequest(requestObj, params)
    }

    def PagedResponseContent getRecurring(params){
        params.putAll([method:RequestMethod.RECURRING_GET])
        def requestObj = new Request(params)
        def responseObj = performRequest(requestObj, params)
        return Recurring.parse(responseObj)
    }

    def PagedResponseContent listRecurrings(params) {
        params.putAll([method:RequestMethod.RECURRING_LIST])
        def requestObj = new Request(params)
        def responseObj = performRequest(requestObj, params)
        PagedResponseContent pagedResponseObj = Recurring.parse(responseObj)
        if(pagedResponseObj.page == 999){
            throw new RecordNotFoundException("No Recurring Invoices Found")
        }
        return pagedResponseObj
    }

    def Response createRecurring(Recurring recurring) {
        def params = [method:RequestMethod.RECURRING_CREATE]
        def requestObj = new Request(params, recurring.xmlClosure())
        return performRequest(requestObj, params)
    }

    def Response deleteRecurring(String recurringId) {
        def params = [method:RequestMethod.RECURRING_DELETE, recurring_id:recurringId]
        def requestObj = new Request(params)
        return performRequest(requestObj, params)
    }

    def Response updateRecurring(Recurring recurring) {
        def params = [method:RequestMethod.RECURRING_UPDATE]
        def requestObj = new Request(params, recurring.xmlClosure())
        return performRequest(requestObj, params)
    }

    def getStaff(params) {
        params.putAll([method:RequestMethod.STAFF_GET])
        def requestObj = new Request(params)
        def responseObj = performRequest(requestObj, params)
        if(responseObj.status == "ok"){
            return Staff.parse(responseObj)
        } else {
            throw new Exception("Request Failed: " + responseObj.toString()) //TODO replace with APIException
        }
    }

    def listStaff() throws Exception {
        def params = [method:RequestMethod.STAFF_LIST]
        def requestObj = new Request(params)
        def responseObj = performRequest(requestObj, params)
        if(responseObj.status == "ok"){
            return Staff.parse(responseObj)
        } else {
            throw new Exception("Request Failed: " + responseObj.toString()) //TODO replace with APIException
        }
    }

    def PagedResponseContent getTask(params){
        params.putAll([method:RequestMethod.TASK_GET])
        def requestObj = new Request(params)
        def responseObj = performRequest(requestObj, params)
        return Task.parse(responseObj)
    }

    def PagedResponseContent listTasks(params) {
        params.putAll([method:RequestMethod.TASK_LIST])
        def requestObj = new Request(params)
        def responseObj = performRequest(requestObj, params)
        return Task.parse(responseObj)
    }

    def Response createTask(Task task) {
        def params = [method:RequestMethod.TASK_CREATE]
        def requestObj = new Request(params, task.xmlClosure())
        return performRequest(requestObj, params)
    }

    def Response deleteTask(String taskId) {
        def params = [method:RequestMethod.TASK_DELETE, task_id:taskId]
        def requestObj = new Request(params)
        return performRequest(requestObj, params)
    }

    def Response updateTask(Task task) {
        def params = [method:RequestMethod.TASK_UPDATE]
        def requestObj = new Request(params, task.xmlClosure())
        return performRequest(requestObj, params)
    }

    def getTimeEntry(params) {
        params.putAll([method:RequestMethod.TIME_ENTRY_GET])
        def requestObj = new Request(params)
        def responseObj = performRequest(requestObj, params)
        if(responseObj.status == "ok"){
            return TimeEntry.parse(responseObj)
        } else {
            throw new Exception("Request Failed: " + responseObj.toString()) //TODO replace with APIException
        }
    }

    def listTimeEntrys(params) throws Exception {
        params.putAll([method:RequestMethod.TIME_ENTRY_LIST])
        def requestObj = new Request(params)
        def responseObj = performRequest(requestObj, params)
        if(responseObj.status == "ok"){
            return TimeEntry.parse(responseObj)
        } else {
            throw new Exception("Request Failed: " + responseObj.toString()) //TODO replace with APIException
        }
    }

    def createTimeEntry(TimeEntry timeEntry) {
        def params = [method:RequestMethod.TIME_ENTRY_CREATE]
        def requestObj = new Request(params, timeEntry.xmlClosure())
        def responseObj = performRequest(requestObj, params)
        if(responseObj.status == "ok"){
            return responseObj
        } else {
            throw new Exception("Request Failed: " + responseObj.toString()) //TODO replace with APIException
        }
    }

    def deleteTimeEntry(String timeEntryId) {
        def params = [method:RequestMethod.TIME_ENTRY_DELETE, time_entry_id:timeEntryId]
        def requestObj = new Request(params)
        def responseObj = performRequest(requestObj, params)
        if(responseObj.status == "ok"){
            return responseObj
        } else {
            throw new Exception("Request Failed: " + responseObj.toString()) //TODO replace with APIException
        }
    }

    def updateTimeEntry(TimeEntry timeEntry){
        def params = [method:RequestMethod.TIME_ENTRY_UPDATE]
        def requestObj = new Request(params, timeEntry.xmlClosure())
        def responseObj = performRequest(requestObj, params)
        if(responseObj.status == "ok"){
            return responseObj
        } else {
            throw new Exception("Request Failed: " + responseObj.toString()) //TODO replace with APIException
        }
    }

}
