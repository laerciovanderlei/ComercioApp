<%@page import="java.util.Date"%>
<%@page import="br.upf.ads.paw.entidades.CartaoFidelidade"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="../header.jspf" %>
<div class="container">
    <form action="/cartao-fidelidade" method="post"  role="form" data-toggle="validator" >
        <c:if test ="${empty action}">                        	
            <c:set var="action" value="add"/>
        </c:if>
        <input type="hidden" id="action" name="action" value="${action}">
        <input type="hidden" id="id" name="id" value="${obj.id}">

        <h2>Cartao Fidelidade</h2>
        <div class="form-group col-xs-4">
            <label for="cliente" class="control-label col-xs-4">Cliente:</label>
            <input type="text" name="cliente" id="cliente" class="form-control" value="${obj.cliente}" required="true"/>                                   
        </div>

        <div class="form-group col-xs-4">
            <label for="senha" class="control-label col-xs-4">Senha:</label>
            <input type="number" name="senha" id="senha" class="form-control" value="" required="true">  
        </div>
        <div class="form-group col-xs-4">
            <label for="limite" class="control-label col-xs-4">Limite:</label>
            <input type="text" name="limite" id="limite" class="form-control" value="${obj.limite}" required="true"/>                                   
        </div>
        <div class="form-group col-xs-4">
            <label for="vencimento" class="control-label col-xs-4">Vencimento:</label>
            <input type="text" name="vencimento" id="nascimento" class="form-control" value="${obj.vencimento}" required="true"/>  
        </div>

        <div class="form-group col-xs-4">
            <label for="qtdPontos" class="control-label col-xs-2">Quantidade de Pontos</label>
            <input type="text" name="qtdPontos" id="qtdPontos" class="form-control" value="${obj.qtdPontos}" required="true"/>     
        </div>

        <div class="form-group col-xs-4">
            <label for="fatorConversao" class="control-label col-xs-2">Fator de Conversão</label>
            <input type="text" name="fatorConversao" id="fatorConversao" class="form-control" value="${obj.fatorConversao}" required="true"/>     
        </div>

        <div class="form-group col-xs-4">                             
            <label for="cidade" class="control-label col-xs-4">Cidade:</label>
            <select name="cidade" class="form-control">
                <c:forEach var="cidade" items="${listCidade}">
                    <option value="${cidade.id}" ${cidade.id == obj.cidade.id?"selected":""}>${cidade}</option>
                </c:forEach>
            </select>
        </div>
        <br></br>
        <button type="submit" class="btn btn-primary  btn-md">Gravar</button> 
    </form>
</div>
<%@include file="../footer.jspf" %>