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
	                <tr>
	                    <td>${ url.fileName }</td>
	                    <td>${ url.fileType }</td>
	                    <td>${ url.timestamp }</td>
	                    <td class="progress ${ url.status }">${ url.status }</td>
	                </tr>
	            </g:each>
                <!-- Commented out for visual testing purposes -->
                <!-- <tr>
                    <td>File Test</td>
                    <td>MD5</td>
                    <td>(none)</td>
                    <td class="progress" id="change"></td>
                    <script type="text/javascript" charset="utf-8">
                        var e = document.getElementById("change");
                        var p = 0;
                        var intval;
                        intval = setInterval(function () {
                            p++;
                            if(p == 101)
                            {
                                clearInterval(intval);
                                e.innerHTML = "done";
                            }
                            else
                            {
                                e.innerHTML = p + "%";
                            }
                        }, 1000);
                    </script>
                </tr>
                <tr>
                    <td>File Test</td>
                    <td>MD5</td>
                    <td>(none)</td>
                    <td class="progress done">done</td>
                </tr>
                <tr>
                    <td>File Test</td>
                    <td>MD5</td>
                    <td>(none)</td>
                    <td class="progress queued">queued</td>
                </tr> -->
		    </tbody>
		</table>
	</div>
</g:if>
<g:else>
    <div class="notice">
        No files found.
    </div>
</g:else>