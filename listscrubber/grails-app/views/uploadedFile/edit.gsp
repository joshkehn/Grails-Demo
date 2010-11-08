

<%@ page import="listscrubber.UploadedFile" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'uploadedFile.label', default: 'UploadedFile')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${uploadedFileInstance}">
            <div class="errors">
                <g:renderErrors bean="${uploadedFileInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${uploadedFileInstance?.id}" />
                <g:hiddenField name="version" value="${uploadedFileInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="fileId"><g:message code="uploadedFile.fileId.label" default="File Id" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: uploadedFileInstance, field: 'fileId', 'errors')}">
                                    <g:textField name="fileId" value="${fieldValue(bean: uploadedFileInstance, field: 'fileId')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="fileName"><g:message code="uploadedFile.fileName.label" default="File Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: uploadedFileInstance, field: 'fileName', 'errors')}">
                                    <g:textField name="fileName" value="${uploadedFileInstance?.fileName}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="fileType"><g:message code="uploadedFile.fileType.label" default="File Type" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: uploadedFileInstance, field: 'fileType', 'errors')}">
                                    <g:textField name="fileType" value="${uploadedFileInstance?.fileType}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="status"><g:message code="uploadedFile.status.label" default="Status" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: uploadedFileInstance, field: 'status', 'errors')}">
                                    <g:textField name="status" value="${uploadedFileInstance?.status}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="timestamp"><g:message code="uploadedFile.timestamp.label" default="Timestamp" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: uploadedFileInstance, field: 'timestamp', 'errors')}">
                                    <g:datePicker name="timestamp" precision="day" value="${uploadedFileInstance?.timestamp}"  />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
