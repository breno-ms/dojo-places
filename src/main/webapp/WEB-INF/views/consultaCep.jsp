<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Consulta de CEP</title>
</head>
<body>
<h1>Consulta de CEP</h1>
<form action="${pageContext.request.contextPath}/form/consultar-cep" method="post">
    <label for="cep">Digite o CEP:</label>
    <input type="text" id="cep" name="cep" maxlength="8" pattern="\d{8}" required>
    <button type="submit">Consultar</button>
</form>

<c:if test="${not empty error}">
    <p style="color: red;">${error}</p>
</c:if>

<c:if test="${not empty bairro}">
    <p><strong>CEP:</strong> ${cep}</p>
    <p><strong>Bairro:</strong> ${bairro}</p>
    <p><strong>Cidade:</strong> ${cidade}</p>
</c:if>
</body>
</html>
