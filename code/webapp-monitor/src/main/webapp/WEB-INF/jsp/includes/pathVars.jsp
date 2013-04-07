<%@page contentType="text/html; charset=utf-8" %>
<%@page trimDirectiveWhitespaces="true" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}" scope="request" />
<c:set var="staticContentDir" value="resources" scope="request" />
<c:set var="lang" value="${pageContext.response.locale.language}" scope="request" />
<c:set var="locale" value="${pageContext.response.locale}" scope="request" />
<c:set var="jsPath" value="${contextPath}/${staticContentDir}/js" scope="request" />
<c:set var="cssPath" value="${contextPath}/${staticContentDir}/css" scope="request" />
<c:set var="extImgPath" value="${contextPath}/resources/ext/images/default" scope="request"/>
<c:set var="extJs" value="${contextPath}/resources/ext/js" scope="request"/>
<c:set var="extCss" value="${contextPath}/resources/ext/css" scope="request"/>
<c:set var="webPortalJsPath" value="${contextPath}/resources/js/webportal" scope="request" />

<c:set var="theme" value="webportal" scope="request" />
<c:set var="themePath" value="${contextPath}/${staticContentDir}/theme" scope="request" />
<c:set var="imgBasePath" value="${contextPath}/${staticContentDir}/images"  scope="request" />
<c:set var="imgPath" value="${imgBasePath}/${theme}" scope="request" />
<c:set var="imgLocalePath" value="${imgPath}/${lang}" scope="request" />
<c:set var="imgSharedPath" value="${imgBasePath}/shared" scope="request" />
<c:set var="imgLocaleSharedPath" value="${imgSharedPath}/${lang}" scope="request" />

<c:set var="jsLogging" value="true" scope="request" />
<c:if test="${cookie.JsLogging.value == 'true'}"><c:set var="jsLogging" value="true" scope="request"/></c:if>
<c:if test="${cookie.JsDebug.value == 'true'}"><c:set var="jsDebug" value="-debug" scope="request" /></c:if>
