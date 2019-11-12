<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="../header.jspf" %>
        <div class="container">
            <h2>Formas de Pagamento</h2>
            <!--Search Form -->
            <form action="/formaPagamento" method="get" id="searchFormaPagamentoForm" role="form">
                <input type="hidden" id="searchAction" name="searchAction" value="searchByName">
                <div class="form-group col-xs-5">
                    <input type="text" name="search" id="search" class="form-control" required="true" placeholder="Digite a descri��o da forma de pagamento a procurar"/>                    
                </div>
                <button type="submit" class="btn btn-info">
                    <span class="glyphicon glyphicon-search"></span> Procurar
                </button>
                <br></br>
                <br></br>
            </form>

            <!-- List-->
            <c:if test="${not empty message}">                
                <div class="alert alert-success">
                    ${message}
                </div>
            </c:if> 
            <form action="/formaPagamento" method="post" id="formaPagamentoForm" role="form" >              
                <input type="hidden" id="id" name="id">
                <input type="hidden" id="action" name="action">
                <c:choose>
                    <c:when test="${not empty entities}">
                        <table  class="table table-striped">
                            <thead>
                                <tr>
                                    <td>#</td>
                                    <td>Descri��o</td>
                                    <td></td>
                                </tr>
                            </thead>
                            <c:forEach var="obj" items="${entities}">
                                <tr class="${id == obj.id?"info":""}">
                                    <td>
                                        <a href="/formaPagamento?id=${obj.id}&searchAction=searchById">${obj.id}</a>
                                    </td>                                    
                                    <td>${obj.descricao}</td>
                                    <td><a href="#" id="remove" 
                                           onclick="document.getElementById('action').value = 'remove';document.getElementById('id').value = '${obj.id}';                                                    
                                                    document.getElementById('formaPagamentoForm').submit();"> 
                                            <span class="glyphicon glyphicon-trash"/>
                                        </a>
                                                   
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
            <form action ="jsp/form-formaPagamento.jsp">            
                <br></br>
                <button type="submit" class="btn btn-primary  btn-md">Novo</button> 
            </form>
        </div>
<%@include file="../footer.jspf" %>