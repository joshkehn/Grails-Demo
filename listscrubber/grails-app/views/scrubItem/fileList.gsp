<g:if test="${urlList.size() > 0}">
	<div class="box">
	    <table>
	        <thead>
	            <th>Label</th>
	            <th>Type</th>
	            <th>Timestamp</th>
	            <th>Progress</th>
	        </thead>
	        <tbody>
	            <g:each in = "${urlList}" var="url">
	                <g:if text="${url != null}">
    	                <tr>
    	                    <td>${ url.fileName }</td>
    	                    <td>${ url.fileType }</td>
    	                    <td>${ url.timestamp }</td>
    	                    <td class="progress ${ url.status }">${ url.status }</td>
    	                </tr>
    	            </g:if>
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