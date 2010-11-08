
<%@ page import="listscrubber.UploadedFile" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'uploadedFile.label', default: 'UploadedFile')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'uploadedFile.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="fileName" title="${message(code: 'uploadedFile.fileName.label', default: 'File Name')}" />
                        
                            <g:sortableColumn property="fileType" title="${message(code: 'uploadedFile.fileType.label', default: 'File Type')}" />
                        
                            <g:sortableColumn property="newName" title="${message(code: 'uploadedFile.newName.label', default: 'New Name')}" />
                        
                            <g:sortableColumn property="status" title="${message(code: 'uploadedFile.status.label', default: 'Status')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${uploadedFileInstanceList}" status="i" var="uploadedFileInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${uploadedFileInstance.id}">${fieldValue(bean: uploadedFileInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: uploadedFileInstance, field: "fileName")}</td>
                        
                            <td>${fieldValue(bean: uploadedFileInstance, field: "fileType")}</td>
                        
                            <td>${fieldValue(bean: uploadedFileInstance, field: "newName")}</td>
                        
                            <td>${fieldValue(bean: uploadedFileInstance, field: "status")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${uploadedFileInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
