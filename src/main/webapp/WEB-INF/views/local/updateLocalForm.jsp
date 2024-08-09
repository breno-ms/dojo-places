<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>Formulário de Atualização</title>
</head>
<body>
<h2>Formulário de Atualização</h2>
<form:form modelAttribute="localUpdateDTO" action="/form/update/${localId}" method="post">
    <form:hidden path="id" value="${localId}" />
    <div>
        <label for="name">Nome:</label>
        <form:input path="name" maxlength="100" required="true"/>
    </div>
    <div>
        <label for="code">Código:</label>
        <form:input path="code" maxlength="100" required="true" pattern="^[a-zA-Z0-9]*$"/>
    </div>
    <div>
        <label for="neighbourhood">Bairro:</label>
        <form:input path="neighbourhood" maxlength="100" required="true"/>
    </div>
    <div>
        <label for="city">Cidade:</label>
        <form:input path="city" maxlength="100" required="true"/>
    </div>
    <div>
        <button type="submit">Atualizar</button>
    </div>
</form:form>
</body>
</html>
