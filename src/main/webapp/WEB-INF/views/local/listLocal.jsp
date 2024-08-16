<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Locais cadastrados</title>
</head>
<body>
<h2>Locais cadastrados</h2>
<c:if test="${not empty locais}">
    <c:forEach var="local" items="${locais}">
        <div>
            <label for="name"><strong>Nome:</strong></label>
            <span id="name">${local.name}</span>
        </div>
        <div>
            <label for="code"><strong>Código:</strong></label>
            <span id="code">${local.code}</span>
        </div>
        <div>
            <label for="neighbourhood"><strong>Bairro:</strong></label>
            <span id="neighbourhood">${local.neighbourhood}</span>
        </div>
        <div>
            <label for="city"><strong>Cidade:</strong></label>
            <span id="city">${local.city}</span>
        </div>
        <div>
            <label for="city"><strong>Data de criação:</strong></label>
            <span id="createdAt">${local.createdAt}</span>
        </div>
        <div>
            <label for="city"><strong>Data de atualização:</strong></label>
            <span id="updatedAt">${local.updatedAt}</span>
        </div>
        <hr/>
    </c:forEach>
</c:if>
<c:if test="${empty locais}">
    <p>Nenhum local cadastrado.</p>
</c:if>
</body>
</html>
