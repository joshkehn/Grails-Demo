<!DOCTYPE html>
<html>
    <head>
        <title>Sample Grails</title>
        <style type="text/css" media="screen">
            html, body, div, span, applet, object, iframe,
            h1, h2, h3, h4, h5, h6, p, blockquote, pre,
            a, abbr, acronym, address, big, cite, code,
            del, dfn, em, font, img, ins, kbd, q, s, samp,
            small, strike, strong, sub, sup, tt, var,
            dl, dt, dd, ol, ul, li,
            fieldset, form, label, legend,
            table, caption, tbody, tfoot, thead, tr, th, td {
            	margin: 0;
            	padding: 0;
            	border: 0;
            	outline: 0;
            	font-weight: inherit;
            	font-style: inherit;
            	font-size: 100%;
            	font-family: inherit;
            	vertical-align: baseline;
            }
            /* remember to define focus styles! */
            :focus {
            	outline: 0;
            }
            body {
            	line-height: 1;
            	color: black;
            	background: white;
            }
            ol, ul {
            	list-style: none;
            }
            /* tables still need 'cellspacing="0"' in the markup */
            table {
            	border-collapse: separate;
            	border-spacing: 0;
            }
            caption, th, td {
            	text-align: left;
            	font-weight: normal;
            }
            blockquote:before, blockquote:after,
            q:before, q:after {
            	content: "";
            }
            blockquote, q {
            	quotes: "" "";
            }
            html {font-size:100.01%;}
            body {font-size:75%;color:#222;background:#fff;font-family:"Helvetica Neue", Arial, Helvetica, sans-serif;}
            h1, h2, h3, h4, h5, h6 {font-weight:normal;color:#111;}
            h1 {font-size:3em;line-height:1;margin-bottom:0.5em;}
            h2 {font-size:2em;margin-bottom:0.75em;}
            h3 {font-size:1.5em;line-height:1;margin-bottom:1em;}
            h4 {font-size:1.2em;line-height:1.25;margin-bottom:1.25em;}
            h5 {font-size:1em;font-weight:bold;margin-bottom:1.5em;}
            h6 {font-size:1em;font-weight:bold;}
            h1 img, h2 img, h3 img, h4 img, h5 img, h6 img {margin:0;}
            p {margin:0 0 1.5em;}
            p img.left {float:left;margin:1.5em 1.5em 1.5em 0;padding:0;}
            p img.right {float:right;margin:1.5em 0 1.5em 1.5em;}
            a:focus, a:hover {color:#000;}
            a {color:#009;text-decoration:underline;}
            blockquote {margin:1.5em;color:#666;font-style:italic;}
            strong {font-weight:bold;}
            em, dfn {font-style:italic;}
            dfn {font-weight:bold;}
            sup, sub {line-height:0;}
            abbr, acronym {border-bottom:1px dotted #666;}
            address {margin:0 0 1.5em;font-style:italic;}
            del {color:#666;}
            pre {margin:1.5em 0;white-space:pre;}
            pre, code, tt {font:1em 'andale mono', 'lucida console', monospace;line-height:1.5;}
            li ul, li ol {margin:0;}
            ul, ol {margin:0 1.5em 1.5em 0;padding-left:3.333em;}
            ul {list-style-type:disc;}
            ol {list-style-type:decimal;}
            dl {margin:0 0 1.5em 0;}
            dl dt {font-weight:bold;}
            dd {margin-left:1.5em;}
            table {margin-bottom:1.4em;width:100%;}
            th {font-weight:bold;}
            thead th {background:#c3d9ff;}
            th, td, caption {padding:4px 10px 4px 5px;}
            tr.even td {background:#e5ecf9;}
            tfoot {font-style:italic;}
            caption {background:#eee;}
            .small {font-size:.8em;margin-bottom:1.875em;line-height:1.875em;}
            .large {font-size:1.2em;line-height:2.5em;margin-bottom:1.25em;}
            .hide {display:none;}
            .quiet {color:#666;}
            .loud {color:#000;}
            .highlight {background:#ff0;}
            .added {background:#060;color:#fff;}
            .removed {background:#900;color:#fff;}
            .first {margin-left:0;padding-left:0;}
            .last {margin-right:0;padding-right:0;}
            .top {margin-top:0;padding-top:0;}
            .bottom {margin-bottom:0;padding-bottom:0;}

            /* forms.css */
            label {font-weight:bold;}
            fieldset {padding:1.4em;margin:0 0 1.5em 0;border:1px solid #ccc;}
            legend {font-weight:bold;font-size:1.2em;}
            input[type=text], input[type=password], input.text, input.title, textarea, select {background-color:#fff;border:1px solid #bbb;}
            input[type=text]:focus, input[type=password]:focus, input.text:focus, input.title:focus, textarea:focus, select:focus {border-color:#666;}
            input[type=text], input[type=password], input.text, input.title, textarea, select {margin:0.5em 0;}
            input.text, input.title {width:300px;padding:5px;}
            input.title {font-size:1.5em;}
            textarea {width:390px;height:250px;padding:5px;}
            input[type=checkbox], input[type=radio], input.checkbox, input.radio {position:relative;top:.25em;}
            form.inline {line-height:3;}
            form.inline p {margin-bottom:0;}
            .error, .notice, .success {padding:.8em;margin-bottom:1em;border:2px solid #ddd;}
            .error {background:#FBE3E4;color:#8a1f11;border-color:#FBC2C4;}
            .notice {background:#FFF6BF;color:#514721;border-color:#FFD324;}
            .success {background:#E6EFC2;color:#264409;border-color:#C6D880;}
            .error a {color:#8a1f11;}
            .notice a {color:#514721;}
            .success a {color:#264409;}
            
            body
            {
                font-family: Helvetica, Arial, sans-serif;
            }
            #wrapper
            {
                width: 800px;
                margin: 0 auto;
            }
            .progress
            {
                color: #F5D041;
                font-weight: bold;
            }
            .queued
            {
                color: #999;
            }
            .done
            {
                color: #157D23;
            }
            #nav
            {
                margin-bottom: 20px;
            }
            #nav a
            {
                padding: 10px;
                font-weight: bold;
                text-decoration: none;
            }
            #nav a.current
            {
                background: #B3EDA1;
            }
            #nav a:hover
            {
                background: #ddd;
                text-decoration: none;
            }
        </style>
        <script type="text/javascript" charset="utf-8" src="http://code.jquery.com/jquery-1.4.3.min.js"></script>
    </head>
    <body>
        <div id="wrapper">
            <h1>Suppression Management</h1>
            <div id="nav">
                <g:link action="suppression" class="current">Suppression Management</g:link>&nbsp;|&nbsp;<g:link action="index">List Scrubbing</g:link>
            </div>
		    <g:each in="${errors}" var="m">
		        <div class="error">${m}</div>
		    </g:each>
			<g:each in="${successes}" var="m">
				<div class="success">${m}</div>
			</g:each>
            <g:form name="scrubform" action="suppression" method="post" enctype="multipart/form-data">
                <fieldset>
                    <legend>File Upload</legend>
					
					<label>Suppression File:</label>&nbsp;<input type="file" name="suppressionFile" />
                    
                    <br />
                    
                    <g:submitButton name="submit" value="Process" />
                </fieldset>
            </g:form>
            <hr />
            <h4><g:link action="suppressionClear">Clear Suppression List</g:link></h4>
            <p>
                Clearing the database will remove <strong>all current suppression users.</strong>
            </p>
        </div>
    </body>
</html>