<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="../header.jspf" %>
<div class="container">
    <h2>Funcionarios</h2>
    <!--Search Form -->
    <form action="/funcionario" method="get" id="searchFuncionarioForm" role="form">
        <input type="hidden" id="searchAction" name="searchAction" value="searchByName">
        <div class="form-group col-xs-5">
            <input type="text" name="search" id="search" class="form-control" required="true" placeholder="Digite o nome do funcionario a procurar"/>                    
        </div>
        <button type="submit" class="btn btn-info">
            <span class="glyphicon glyphicon-search"></span> Procurar
        </button>
        <br></br>
        <br></br>
    </form>

    <!-- Include Botton -->
    <form action ="/funcionario?action=new" method="POST">            
        <c:if test="${permissao.getCriar()}">
            <button type="submit" class="btn btn-primary  btn-md">Novo Cadastro</button> 
        </c:if>
        <br></br>
    </form>

    <!-- List-->
    <c:if test="${not empty message}">                
        <div class="alert alert-success">
            ${message}
        </div>
    </c:if> 
    <form action="/funcionario" method="post" id="funcionarioForm" role="form" >              
        <input type="hidden" id="id" name="id">
        <input type="hidden" id="action" name="action">
        <c:choose>
            <c:when test="${not empty entities}">
                <table  class="table-striped table-xl col-xs-10">
                    <thead>
                        <tr>
                            <td>#</td>
                            <td>Nome</td>
                            <td></td>
                        </tr>
                    </thead>
                    <c:forEach var="obj" items="${entities}">
                        <tr class="${id == obj.id?"info":""}">
                            <td>
                                <c:if test="${permissao.getAlterar()}">
                                    <a href="/funcionario?id=${obj.id}&searchAction=searchById">${obj.id}</a>
                                </c:if>
                                <c:if test="${!permissao.getAlterar()}">
                                    ${obj.id}
                                </c:if>
                            </td>                                    
                            <td>${obj.nome}</td>
                            <td>
                                <c:if test="${permissao.getExcluir()}">
                                    <a href="#" id="remove" 
                                       onclick="document.getElementById('action').value = 'remove';document.getElementById('id').value = '${obj.id}';
                                               document.getElementById('funcionarioForm').submit();"> 
                                        <span class="glyphicon glyphicon-trash"/>
                                    </a>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>               
                </table>  
            </c:when>                    
            <c:otherwise>
                <br>           
                <div class="alert alert-info">
                    Nenhum registro encontrado.
                </div>
            </c:otherwise>
        </c:choose>                        
    </form>
</div>
<%@include file="../footer.jspf" %>
