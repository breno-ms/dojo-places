<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>Formulário de Cadastro</title>
</head>
<body>
<h2>Formulário de Cadastro</h2>
<form:form modelAttribute="localCreateDTO" action="/form/create" method="post">
    <div>
        <label for="name">Nome:</label>
        <form:input path="name" maxlength="100" required="true"/>
        <form:errors path="name"/>
    </div>
    <div>
        <label for="code">Código:</label>
        <form:input id="code" path="code" maxlength="100" required="true" pattern="^[a-zA-Z0-9]*$"/>
        <form:errors path="code"/>
    </div>
    <div>
        <label for="neighbourhood">Bairro:</label>
        <form:input id="neighbourhood" path="neighbourhood" maxlength="100" required="true"/>
        <form:errors path="neighbourhood"/>
    </div>
    <div>
        <label for="city">Cidade:</label>
        <form:input id="city" path="city" maxlength="100" required="true"/>
        <form:errors path="city"/>
    </div>
    <div>
        <label for="cep">CEP:</label>
        <form:input id="cep" path="cep" maxlength="8" required="true"/>
        <form:errors path="cep"/>
    </div>
    <div>
        <button type="submit">Criar</button>
    </div>
</form:form>
</body>
<script>
    document.addEventListener("DOMContentLoaded", function() {
        const cepInput = document.getElementById("cep");

        cepInput.addEventListener("input", (e) => {
            e.preventDefault();
            const cep = cepInput.value;
            fetch('https://viacep.com.br/ws/'+ cep +'/json')
                .then(response => response.json())
                .then(data => {
                    document.getElementById('city').value = data.localidade
                    document.getElementById('neighbourhood').value = data.logradouro
                })
                .catch(err => console.log(err));
        })
    })
</script>
</html>
