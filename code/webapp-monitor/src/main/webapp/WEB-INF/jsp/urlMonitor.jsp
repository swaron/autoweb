<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="u" uri="http://express-scripts.com/tags/utils"%>
<%@taglib prefix="jwr" uri="http://jawr.net/tags-el" %>
<jsp:include page="/WEB-INF/jsp/includes/pathVars.jsp" flush="false" />
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>Application Status</title>
<meta content="text/html; charset=UTF-8" http-equiv="Content-Type" />
<meta http-equiv="Expires" content="0" />
<meta http-equiv="Cache-Control" content="no-cache" />
<meta http-equiv="Pragma" content="no-cache" />
<!-- <meta http-equiv="refresh" content="10" /> --> 
<link rel="stylesheet" type="text/css" href="${extCss}/ext-all.css" />
<jwr:style src="/bundles/css/app.css" />
<script type="text/javascript" src="${extJs}/adapter/ext/ext-base${jsDebug}.js"></script>
<script type="text/javascript" src="${extJs}/ext-all${jsDebug}.js"></script>
<jwr:script src="/bundles/js/messages.js"/>
<jwr:script src="/bundles/js/widget.js" useRandomParam="false"/> 
<jwr:script src="/bundles/js/base.js" useRandomParam="false"/>
<script type="text/javascript">
	var Page = {};
	Ext.apply(Page,{
		task : ${taskJson},
		editOrSaveTask: function(btn){
			if(btn.getText() == 'Edit'){
				this.form.items.each(function(item){
					item.setReadOnly(false);
				});
				btn.setText("Save");
			}else{
				if(this.task.running){
					var proceed = confirm("Current task need to be stopped befored changed, click ok to stop current task and saving editions.");
					if(!proceed){
						return;
					}
				}
				Ext.Ajax.request({
					url : '${contextPath}/urlmonitor/saveTask.json',
					form : this.form.getForm().getEl(),
					success : function(response, opts) {
						var obj = Ext.decode(response.responseText);
						if (obj.errors) {
							alert("unable to save task. errors:\n"+ obj.errors);
						}else{
							location.reload(true);
						}
					}
				});
			}
		},
		init:function(){
			this.initWidget();
			this.initEvents();
		},
		initEvents:function(){
			var form = this.form;
			Ext.get('taskAction').on('click',function(evt,el,o){
				el.disabled = true;
				var startTask = el.innerHTML.indexOf("Start Task") != -1;
				Ext.Ajax.request({
					url : '${contextPath}/urlmonitor/taskAction.json',
					form : form.getForm().getEl(),
					params: { 
						action: startTask?'start':'stop'
					},
					success : function(response, opts) {
						var obj = Ext.decode(response.responseText);
						if (obj.errors) {
							alert("unable to perform this action. errors:\n"+ obj.errors);
						}else{
							location.reload(true);
						}
					}
				});
			});
		},
		initWidget: function(){
			var task = this.task;
			var config = this.task;
			this.form = new Ext.form.FormPanel({
				labelWidth: 175,
				frame:false,
				border:false,
				defaults: {
					width: 420,
					readOnly:true
				},
				defaultType: 'textfield',
				items: [{
	                fieldLabel: 'Target URL',
	                name: 'config.url',
	                itemId:'url',
	                value: task.config.url,
	                allowBlank:false
	            },{
	                fieldLabel: 'Expected Content(Contains)',
	                name: 'config.expectedContent',
	                itemId:'expectedContent',
	                value: task.config.expectedContent
	            },{
	            	xtype: 'checkbox',
	                fieldLabel: 'Stop Probe On Error',
	                name: 'config.stopOnError',
	                itemId:'stopOnError',
	                checked: task.config.stopOnError
	            },{
	                xtype: 'hidden',
	                value: task.id,
	                name: 'id'
	            },{
	                xtype: 'hidden',
	                value: task.config.name,
	                name: 'config.name'
	            },{
	                xtype: 'numberfield',
	                fieldLabel: 'Probe Interval(in seconds)',
	                readOnly:true,
	                value: task.config.probeInterval,
	                itemId:'interval',
	                name: 'config.probeInterval'
	            },{
	                fieldLabel: 'Subscriber Emails',
	                readOnly:true,
	                value: task.config.subscribers,
	                itemId:'subscribers',
	                name: 'config.subscribers'
	            },{
	                fieldLabel: 'Last Response',
	                readOnly:true,
	                xtype:'textarea',
	                value: task.lastResponse,
	                itemId:'lastResponse',
	                name: 'lastResponse'
	            }],
		        buttons: [{
	                itemId:'editBtn',
		            text: 'Edit',
		            handler:this.editOrSaveTask,
		            scope: this
		        }]
			});
			if(task.lastError){
				this.form.add({
	            	width:600,
	                xtype:'fieldset',
	                title: 'Last Error',
	                collapsible: true,
	                autoHeight:true,
	                labelWidth: 125,
	                defaults: {width: 425},
	                defaultType: 'textfield',
	                items :[{
	                        fieldLabel: 'Error Occur Time',
	                        name: 'occurTime',
	                        value: task.lastError.occurTime
	                    },{
	                        fieldLabel: 'Server response',
	                        xtype:'textarea',
	                        height:300,
	                        name: 'htmlResponse',
	                        value: task.lastError.htmlResponse
	                    }
	                ]
	            });
			}
			this.form.render('task-body');
		}
	});
	Ext.onReady(function() {
		Page.init();
	});
</script>
<style type="text/css">
.round-box{
	margin-bottom: 20px;
}
.round-box .x-panel-body{
    background-color: transparent;
}
.task-state{
	float: right;
}
.task-state button{
	margin-left: 20px;
}
body {
	margin: 0 auto;
    width: 960px;
}
h3 {
	font-size: large;
}
</style>
</head>
<body>
	<h2>URL Monitor Console</h2>
	<div class="round-box">
		<div class="box-header clearfix" id="task-title">
			<div class="task-state">
				<c:if test="${task.running}">
					<span>Current State: Running</span>
					<button id="taskAction">Stop Task</button>
				</c:if>
				<c:if test="${not task.running}">
					<span>Current State: Stopped</span>
					<button id="taskAction">Start Task</button>
				</c:if>
			</div>
			<span class="form-title uppercase"><u:out text="${task.config.name}" />&nbsp;</span>
			<hr class="dashedHr" />
		</div>
		<div id="task-body"></div>
<%-- 		
		<div class="btn-bar">
			<button id="edit-btn" class="normal-button">
				<u:out code="Edit"></u:out>
			</button>
			<button id="remove-btn" class="normal-button">
				<u:out code="Remove"></u:out>
			</button>
		</div> --%>
	</div>
</body>
</html>
