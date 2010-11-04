

<%@ page import="listscrubber.SupressedEmail" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'supressedEmail.label', default: 'SupressedEmail')}" />
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
            <g:hasErrors bean="${supressedEmailInstance}">
            <div class="errors">
                <g:renderErrors bean="${supressedEmailInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="email"><g:message code="supressedEmail.email.label" default="Email" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: supressedEmailInstance, field: 'email', 'errors')}">
                                    <g:textField name="email" value="${supressedEmailInstance?.email}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="md5"><g:message code="supressedEmail.md5.label" default="Md5" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: supressedEmailInstance, field: 'md5', 'errors')}">
                                    <g:textField name="md5" value="${supressedEmailInstance?.md5}" />
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
