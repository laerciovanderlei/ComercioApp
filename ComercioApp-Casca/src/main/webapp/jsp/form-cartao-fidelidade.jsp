<%@page import="java.util.Date"%>
<%@page import="br.upf.ads.paw.entidades.Funcionario"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="../header.jspf" %>
<div class="container">
    <form action="/funcionario" method="post"  role="form" data-toggle="validator" >
        <c:if test ="${empty action}">                        	
            <c:set var="action" value="add"/>
        </c:if>
        <input type="hidden" id="action" name="action" value="${action}">
        <input type="hidden" id="id" name="id" value="${obj.id}">
        <h2>Funcionario</h2>
        <div class="form-group col-xs-4">
            <label for="nome" class="control-label col-xs-4">Nome:</label>
            <input type="text" name="nome" id="nome" class="form-control" value="${obj.nome}" required="true"/>                                   
        </div>
        <div class="form-group col-xs-4">
            <label for="nascimento" class="control-label col-xs-4">Nascimento:</label>
            <input type="date" name="nascimento" id="nascimento" class="form-control" value="" required="true">  
        </div>
        <div class="form-group col-xs-4">
            <label for="cpf" class="control-label col-xs-4">CPF:</label>
            <input type="text" name="cpf" id="cpf" class="form-control" value="${obj.cpf}" required="true"/>                                   
        </div>
        <div class="form-group col-xs-4">
            <label for="rg" class="control-label col-xs-4">RG:</label>
            <input type="text" name="rg" id="nascimento" class="form-control" value="${obj.rg}" required="true"/>  
        </div>
        <div class="form-group col-xs-4">
            <label for="logradouro" class="control-label col-xs-6">Logradouro:</label>
            <input type="text" name="logradouro" id="logradouro" class="form-control" value="${obj.logradouro}" required="true"/>                                   
        </div>
        <div class="form-group col-xs-4">
            <label for="numero" class="control-label col-xs-2">Número:</label>
            <input type="text" name="numero" id="numero" class="form-control" value="${obj.numero}" required="true"/>     
        </div>
        <div class="form-group col-xs-4">
            <label for="complemento" class="control-label col-xs-6">Complemento:</label>
            <input type="text" name="complemento" id="complemento" class="form-control" value="${obj.complemento}" required="true"/>                                   
        </div>
        <div class="form-group col-xs-4">
            <label for="bairro" class="control-label col-xs-6">Bairro:</label>
            <input type="text" name="bairro" id="bairro" class="form-control" value="${obj.bairro}" required="true"/>                                   
        </div>
        <div class="form-group col-xs-4">
            <label for="email" class="control-label col-xs-4">E-Mail:</label>
            <input type="text" name="email" id="email" class="form-control" value="${obj.email}" required="true"/>                                   
        </div>
        <div class="form-group col-xs-4">
            <label for="telefone" class="control-label col-xs-4">Telefone:</label>
            <input type="text" name="telefone" id="telefone" class="form-control" value="${obj.telefone}" required="true"/>     
        </div>
        <div class="form-group col-xs-4">                             
            <label for="cidade" class="control-label col-xs-4">Cidade:</label>
            <select name="cidade" class="form-control">
                <c:forEach var="cidade" items="${listCidade}">
                    <option value="${cidade.id}" ${cidade.id == obj.cidade.id?"selected":""}>${cidade}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group col-xs-4">                             
            <label for="cep" class="control-label col-xs-4">Cep:</label>
            <input type="text" name="cep" id="cep" class="form-control" value="${obj.cep}" required="true"/>      
        </div>
        <div class="form-group col-xs-4">
            <label for="admissao" class="control-label col-xs-4">Admissao:</label>
            <input type="date" name="admissao" id="admissao" class="form-control"/>  
        </div>
        <div class="form-group col-xs-4">
            <label for="demissao" class="control-label col-xs-4">Demissao:</label>
            <input type="date" name="demissao" id="demissao" class="form-control"/>  
        </div>
        <div class="form-group col-xs-4">
            <label for="ctps" class="control-label col-xs-4">Ctps:</label>
            <input type="text" name="ctps" id="ctps" class="form-control" value="${obj.ctps}" required="true"/>                                   
        </div>
        <div class="form-group col-xs-4">
            <label for="salario" class="control-label col-xs-4">Salario:</label>
            <input type="number" name="salario" id="salario" class="form-control" value="${obj.salario}" required="true"/>  
        </div>
        <div class="form-group col-xs-4">
            <label for="login" class="control-label col-xs-4">Login:</label>
            <input type="text" name="login" id="login" class="form-control" value="${obj.login}" required="true"/>                                   
        </div>
        <div class="form-group col-xs-4">
            <label for="senha" class="control-label col-xs-4">Senha:</label>
            <input type="password" name="senha" id="senha" class="form-control" value="${obj.senha}" required="true"/>  
        </div>
        <br></br>
        <button type="submit" class="btn btn-primary  btn-md">Gravar</button> 
    </form>
</div>
<%@include file="../footer.jspf" %>