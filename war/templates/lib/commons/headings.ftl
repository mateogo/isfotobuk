<#ftl strip_whitespace=true>
<#macro head title="Articulo" mname="vista general de un articulo" >
	<meta charset="utf-8">
	<title>${title}</title>
	<meta name=${mname} content="">
	<meta name="fotobuk uk" content="">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="">

	<!--[if lt IE 9]>
		<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
		<![endif]-->

	<link href="/css/bootstrap-2.1.1.css" rel="stylesheet">
	<link href="/images/favicon.gif" rel="icon" type="image/gif">

</#macro>
<#macro footer >
	<div id="modal-from-dom" class="modal hide fade">
		<div class="modal-header">
			<a href="#" class="close">&times;</a>
			<h3>Condiciones Legales</h3>
		</div>
		<div class="modal-body">
			<p>Etiam porta sem malesuada magna mollis euismod. Integer
				posuere erat a ante venenatis dapibus posuere velit aliquet. Aenean
				eu leo quam. Pellentesque ornare sem lacinia quam venenatis
				vestibulum. Duis mollis, est non commodo luctus, nisi erat porttitor
				ligula, eget lacinia odio sem nec elit.</p>
		</div>
	</div>
	<p>
		<a href="#" data-controls-modal="modal-from-dom" data-backdrop="true"
			data-keyboard="true"> Condiciones Legales </a>
	</p>
</#macro>

<#macro topmenu brand="fotobuk" >
  <div class="navbar navbar-inverse navbar-fixed-top">
    <div class="navbar-inner">
      <div class="container">
        <a class="brand" href="#">${brand}</a>
          <ul class="nav">
			<#list menu.left as litem>
				<#if litem.haschild>
					<li class="dropdown" >
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">${litem.descr}<b class="caret"></b></a>
						<ul class="dropdown-menu" >
							<#list litem.smenus as item>
							<#if item.isdivider><li class="divider"></li><#elseif item.isheader><li class="nav-header">${item.descr}</li><#else><li><a href="${item.url}"><#if item.hasicon><i class="${item.icon}"></i></#if>${item.descr}</a></li></#if>
							</#list>
						</ul>
					</li>
				<#else>
					<li class="${litem.active}"><a href="${litem.url}"><#if litem.hasicon><i class="${litem.icon}"></i></#if>${litem.descr}</a></li>
				</#if>
    		</#list>
          </ul>
		<div class="pull-right">
			<ul class="nav">
			<#list menu.right as ritem>
				<#if ritem.haschild >
					<li class="dropdown" >
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">${ritem.descr}<b class="caret"></b></a>
						<ul class="dropdown-menu" >
							<#list ritem.smenus as item>
							<#if item.isdivider><li class="divider"></li><#elseif item.isheader><li class="nav-header">${item.descr}</li><#else><li><a href="${item.url}"><#if item.hasicon><i class="${item.icon}"></i></#if>${item.descr}</a></li></#if>
							</#list>
						</ul>
					</li>
				<#else>
					<li class="${ritem.active}"><a href="${ritem.url}"><#if ritem.hasicon><i class="${ritem.icon}"></i></#if>${ritem.descr}</a></li>
				</#if>
			</#list>
			</ul>
		</div>
      </div>  <!--end container -->
     </div>	 <!--end navbar-inner -->
   </div> <!--end navbar -->
</#macro>

 s