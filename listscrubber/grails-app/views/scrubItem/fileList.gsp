<g:if test="${urlList.size() > 0 && urlList != null }">
	<div class="box">
	    <table>
	        <thead>
	            <th>Label</th>
	            <th>Type</th>
	            <th>Timestamp</th>
	            <th>Progress</th>
	            <th></th>
	        </thead>
	        <tbody>
	            <g:each in = "${urlList}" var="url">
	                <g:if test="${url != null}">
    	                <tr>
    	                    <td>${ url.fileName }</td>
    	                    <td>${ url.fileType }</td>
    	                    <td>${ url.timestamp }</td>
    	                    <td class="progress ${ url.status }">${ url.status }</td>
    	                    <td><g:if test="${url.status == 'done'}"><g:link action="download" id="${ url.label }">Download</g:link></g:if></td>
    	                </tr>
    	            </g:if>
    	            <g:else>
    	                <tr>
    	                    <td>${url}</td>
    	                </tr>
    	            </g:else>
	            </g:each>
		    </tbody>
		</table>
	</div>
</g:if>
<g:else>
    <div class="notice">
        No files found.
    </div>
</g:else>