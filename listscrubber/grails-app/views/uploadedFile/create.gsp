

<%@ page import="listscrubber.UploadedFile" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'uploadedFile.label', default: 'UploadedFile')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${uploadedFileInstance}">
            <div class="errors">
                <g:renderErrors bean="${uploadedFileInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
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
                                    <label for="newName"><g:message code="uploadedFile.newName.label" default="New Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: uploadedFileInstance, field: 'newName', 'errors')}">
                                    <g:textField name="newName" value="${uploadedFileInstance?.newName}" />
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
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
