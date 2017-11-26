<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html; charset=euc-kr" %>

<%--@page import="com.model2.mvc.common.util.CommonUtil"--%>
<%--@page import="com.model2.mvc.view.product.ListProductAction"--%>
<%--@page import="com.model2.mvc.service.product.vo.*"--%>
<%--@page import="com.model2.mvc.common.*"--%>
<%--@page import="java.util.*"--%>
<%--@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
"http://www.w3.org/TR/html4/loose.dtd">
<%--
	List<ProductVO> list =(List<ProductVO>)request.getAttribute("list");
	String menu = request.getParameter("menu");
	Page resultPage=(Page)request.getAttribute("resultPage");
	Search search=(Search)request.getAttribute("search");
	//==> null 을 ""(nullString)으로 변경
	String searchCondition = CommonUtil.null2str(search.getSearchCondition());
	String searchKeyword = CommonUtil.null2str(search.getSearchKeyword());
	
	
	--%>
	

<html>
<head>


<title>상품 목록조회</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript">
function fncGetUserList(currentPage) {
	document.getElementById("currentPage").value = currentPage;
   	document.detailForm.submit();		
}
</script>
</head>

<body bgcolor="#ffffff" text="#000000">

	<div style="width: 98%; margin-left: 10px;">

		<form name="detailForm" action="/product/listProduct/"
			method="get">

			<table width="100%" height="37" border="0" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="15" height="37"><img src="/images/ct_ttl_img01.gif"
						width="15" height="37" /></td>
					<td background="/images/ct_ttl_img02.gif" width="100%"
						style="padding-left: 10px;">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="93%" class="ct_ttl01">
								<c:if test="${param.menu eq 'manage'}" > 상품관리조회</td></c:if>
								<c:if test="${param.menu eq 'search'}"> 상품목록조회 </td></c:if>
							</tr>
						</table>
					</td>
					<td width="12" height="37"><img src="/images/ct_ttl_img03.gif"
						width="12" height="37" /></td>
				</tr>
			</table>


			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				style="margin-top: 10px;">
				<tr>

					<td align="right"><select name="searchCondition"
						class="ct_input_g" style="width: 80px">

							<option value="0" ${! empty searchCondition.equals && searchCondition.equals==0 ? "selected" : "" }>상품번호</option>
							<option value="1" ${! empty searchCondition.equals && searchCondition.equals==1 ? "selected" : "" }>상품명</option>
							<option value="2" ${! empty searchCondition.equals && searchCondition.equals==2 ? "selected" : "" }>상품가격</option>
					</select> <input type="text" name="searchKeyword"
						value="${! empty search.searchKeyword ? search.searchKeyword : "" }" class="ct_input_g"
						style="width: 200px; height: 19px" /></td>

					<td align="right" width="70">
						<table border="0" cellspacing="0" cellpadding="0">

							<tr>
								<td width="17" height="23"><img
									src="/images/ct_btnbg01.gif" width="17" height="23"></td>

								<td background="/images/ct_btnbg02.gif" class="ct_btn01"
									style="padding-top: 3px;"><a
									href="javascript:fncGetUserList('1');">검색</a>
								</td>
								<td width="14" height="23"><img
									src="/images/ct_btnbg03.gif" width="14" height="23">
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>


			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				style="margin-top: 10px;">
				<tr>
					<td colspan="11">전체 ${resultPage.totalCount} 건수,	현재 ${resultPage.currentPage} 페이지
					</td>
				</tr>
				<tr>
					<td class="ct_list_b" width="100">No</td>
					<td class="ct_line02"></td>
					<td class="ct_list_b" width="150">상품명</td>
					<td class="ct_line02"></td>
					<td class="ct_list_b" width="150">가격</td>
					<td class="ct_line02"></td>
					<td class="ct_list_b">등록일</td>
					<td class="ct_line02"></td>
					<td class="ct_list_b">현재상태</td>
				</tr>
				<tr>
					<td colspan="11" bgcolor="808285" height="1"></td>
				</tr>
				<%--
					int no = list.size();
					for (int i = 0; i < list.size(); i++) {
						ProductVO vo = (ProductVO) list.get(i);
				
				<tr class="ct_list_pop">
					<td align="center"><%=i+1%></td>
					<td></td>
					<td align="left">
						<%
							if ("manage".equals(menu)) {
						%> <a
						href="/updateProductView.do?menu=<%=menu%>&prodNo=<%=vo.getProdNo()%>"><%=vo.getProdName()%></a>
					</td>

					<%
						} else if("search".equals(menu)) {
					%>
					<a href="/getProduct.do?menu=<%=menu%>&prodNo=<%=vo.getProdNo()%>"><%=vo.getProdName()%></a>
					</td>
					<%
						}
					--%>
					<c:set var="i" value="0"/>
					<c:forEach var="product" items="${list}">
					<c:set var="i" value="${i+1}"/>
					<tr class="ct_list_pop">
					<td align="center">${ i }</td>
					<td></td>
					<td align="left">
					<c:if test="${param.menu eq 'manage'}" >
					<a href="/product/updateProduct?menu=${param.menu}&prodNo=${product.prodNo}">${product.prodName}</a>
					</c:if>
					<c:if test="${param.menu eq 'search'}">
					<a href="/product/getProduct?menu=${param.menu}&prodNo=${product.prodNo}">${product.prodName}</a></c:if></td>
					
					<td></td>
					<td align="left">${product.price}</td>
					<td></td>

					<td align="left">${product.regDate}</td>
					<td></td>
					
					<td> 
					
					<c:if test="${product.proTranCode.trim() == null && param.menu =='search'}">판매중</c:if>
					<c:if test="${product.proTranCode.trim() == null && param.menu =='manage'}">판매중</c:if>
					<c:if test="${product.proTranCode.trim() == '1' && user.role =='user'}">재고없음</c:if>
					<c:if test="${product.proTranCode.trim() == '1' && user.role =='admin'}">구매완료
					<a href="/updateTranCode.do?">&nbsp;배송하기</a></c:if>
					<c:if test="${product.proTranCode.trim() =='2' && param.menu =='manage'}">배송중</c:if>
					<c:if test="${product.proTranCode.trim() =='2' }">배송중</c:if>
					<c:if test="${product.proTranCode.trim() =='3' }">배송완료</c:if>
					
					
					</td>
					
				</tr>
				
				</c:forEach>
			</table>

			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				style="margin-top: 10px;">
				<tr>
					<td align="center">
					 <input type="hidden" id="currentPage" name="currentPage" value=""/>
			<%-- if( resultPage.getCurrentPage() <= resultPage.getPageUnit() ){ 
					◀ 이전
			<% }else{ %>
					<a href="javascript:fncgetProductList('<%=resultPage.getCurrentPage()-1%>')">◀ 이전</a>
			<% } %>

			<%	for(int i=resultPage.getBeginUnitPage();i<= resultPage.getEndUnitPage() ;i++){	%>
					<a href="javascript:fncgetProductList('<%=i %>');"><%=i %></a>
			<% 	}  %>
	
			<% if( resultPage.getEndUnitPage() >= resultPage.getMaxPage() ){ %>
					이후 ▶
			<% }else{ %>
					<a href="javascript:fncgetProductList('<%=resultPage.getEndUnitPage()+1%>')">이후 ▶</a>
			<% } --%>
			<jsp:include page="../common/pageNavigator.jsp"/>
					</td>
				</tr>
			</table>
			<!--  페이지 Navigator 끝 -->

		</form>

	</div>
</body>
</html>
