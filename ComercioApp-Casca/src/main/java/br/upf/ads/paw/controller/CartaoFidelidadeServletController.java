package br.upf.ads.paw.controller;

import br.upf.ads.paw.controladores.GenericDao;
import br.upf.ads.paw.entidades.CartaoFidelidade;
import br.upf.ads.paw.entidades.Cidade;
import br.upf.ads.paw.entidades.Permissao;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author pavan
 */
@WebServlet(name = "CartaoFidelidadeServletController", urlPatterns = {"/cartão-fidelidade"})
public class CartaoFidelidadeServletController extends HttpServlet {

    GenericDao<CartaoFidelidade> daoCartaoFidelidade = new GenericDao(CartaoFidelidade.class);
    GenericDao<Cidade> daoCidade = new GenericDao(Cidade.class);
    //GenericDao<CategoriaFuncional> daoCategoriaFuncional = new GenericDao(CategoriaFuncional.class);

    @Override
    protected void doGet(HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {

        Permissao p = Valida.acesso(req, resp, "CartaoFidelidade");

        if (p == null) {
            req.setAttribute("message", "Acesso negado. Tente fazer login.");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/login?url=/cartao-fidelidade");
            dispatcher.forward(req, resp);
        } else {
            req.setAttribute("permissao", p);
            String action = req.getParameter("searchAction");
            if (action != null) {
                switch (action) {
                    case "searchById":
                        searchById(req, resp);
                        break;
                    case "search":
                        if (p.getConsultar()) {
                            search(req, resp);
                        } else {
                            req.setAttribute("message", "Você não tem permissão para consultar.");
                        }
                        forwardList(req, resp, null);
                        break;
                }
            } else {
                List<CartaoFidelidade> result = null;
                if (p.getConsultar()) {
                    result = daoCartaoFidelidade.findEntities();
                } else {
                    req.setAttribute("message", "Você não tem permissão para consultar.");
                }
                forwardList(req, resp, result);
            }
        }
    }

    private void searchById(HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {
        long id = Integer.valueOf(req.getParameter("id"));
        CartaoFidelidade obj = null;
        try {
            obj = daoCartaoFidelidade.findEntity(id);
        } catch (Exception ex) {
            Logger.getLogger(CartaoFidelidadeServletController.class.getName()).log(Level.SEVERE, null, ex);
        }
        req.setAttribute("obj", obj);
        req.setAttribute("listCidade", daoCidade.findEntities());
        req.setAttribute("action", "edit");
        String nextJSP = "/jsp/form-cartao-fidelidade.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        dispatcher.forward(req, resp);
    }

    private void search(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String search = req.getParameter("search");
        List<CartaoFidelidade> result = daoCartaoFidelidade.findEntitiesByField("nome", search);  // buscar por nome
        forwardList(req, resp, result);
    }

    private void forwardList(HttpServletRequest req, HttpServletResponse resp, List entityList)
            throws ServletException, IOException {
        String nextJSP = "/jsp/list-cartao-fidelidade.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        req.setAttribute("entities", entityList);
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            doGet(req, resp);
        }
        switch (action) {
            case "new":
                newAction(req, resp);
                break;
            case "add":
                addAction(req, resp);
                break;
            case "edit":
                editAction(req, resp);
                break;
            case "remove":
                removeById(req, resp);
                break;
        }
    }

    private void newAction(HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {
        String nextJSP = "/jsp/form-cartao-fidelidade.jsp";
        List<Cidade> list = daoCidade.findEntities();
        req.setAttribute("listCidade", list);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        dispatcher.forward(req, resp);
    }

    private void addAction(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            String nome = req.getParameter("nome");
            String logradouro = req.getParameter("logradouro");
            String numero = req.getParameter("numero");
            String complemento = req.getParameter("complemento");
            String bairro = req.getParameter("bairro");
            String email = req.getParameter("email");
            String telefone = req.getParameter("telefone");
            String cep = req.getParameter("cep");
            Cidade cidade = daoCidade.findEntity(Long.parseLong(req.getParameter("cidade")));

            String vencimento = req.getParameter("vencimento");
            Double limite = Double.parseDouble(req.getParameter("limite"));
            Double qtdPontos = Double.parseDouble(req.getParameter("qtdPontos"));
            Double fatorConversao = Double.parseDouble(req.getParameter("fatorConversao"));
            String senha = req.getParameter("senha");

            CartaoFidelidade obj = new CartaoFidelidade();
           
            daoCartaoFidelidade.create(obj);
            long id = obj.getId();
            req.setAttribute("id", id);
            String message = "Um novo registro foi criado com sucesso.";
            req.setAttribute("message", message);
            doGet(req, resp);
        } catch (Exception ex) {
            Logger.getLogger(CartaoFidelidadeServletController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void editAction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            long id = Integer.valueOf(req.getParameter("id"));
            String nome = req.getParameter("nome");
            String logradouro = req.getParameter("logradouro");
            String numero = req.getParameter("numero");
            String complemento = req.getParameter("complemento");
            String bairro = req.getParameter("bairro");
            String email = req.getParameter("email");
            String telefone = req.getParameter("telefone");
            String cep = req.getParameter("cep");
            Cidade cidade = daoCidade.findEntity(Long.parseLong(req.getParameter("cidade")));

            String vencimento = req.getParameter("vencimento");
            Double limite = Double.parseDouble(req.getParameter("limite"));
            Double qtdPontos = Double.parseDouble(req.getParameter("qtdPontos"));
            Double fatorConversao = Double.parseDouble(req.getParameter("fatorConversao"));
            String senha = req.getParameter("senha");
//            

            CartaoFidelidade obj = new CartaoFidelidade();
            boolean success = false;
            try {
                daoCartaoFidelidade.edit(obj);
                success = true;
            } catch (Exception ex) {
                Logger.getLogger(CartaoFidelidadeServletController.class.getName()).log(Level.SEVERE, null, ex);
            }
            String message = null;
            if (success) {
                message = "O registro foi atualizado com sucesso";
            }
            req.setAttribute("id", obj.getId());
            req.setAttribute("message", message);
            doGet(req, resp);
        } catch (Exception ex) {
            Logger.getLogger(CartaoFidelidadeServletController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void removeById(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        long id = Integer.valueOf(req.getParameter("id"));
        boolean confirm = false;
        try {
            daoCartaoFidelidade.destroy(id);
            confirm = true;
        } catch (Exception ex) {
            Logger.getLogger(CartaoFidelidadeServletController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (confirm) {
            String message = "O registro foi removido com sucesso.";
            req.setAttribute("message", message);
        }
        doGet(req, resp);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
