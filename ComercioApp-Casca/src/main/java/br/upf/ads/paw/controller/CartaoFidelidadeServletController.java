/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.upf.ads.paw.controller;

import br.upf.ads.paw.controladores.GenericDao;
import br.upf.ads.paw.entidades.CategoriaFuncional;
import br.upf.ads.paw.entidades.Funcionario;
import br.upf.ads.paw.entidades.Cidade;
import br.upf.ads.paw.entidades.Permissao;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
@WebServlet(name = "FuncionarioServletController", urlPatterns = {"/funcionario"})
public class CartaoFidelidadeServletController extends HttpServlet {

    GenericDao<Funcionario> daoFuncionario = new GenericDao(Funcionario.class);
    GenericDao<Cidade> daoCidade = new GenericDao(Cidade.class);
    GenericDao<CategoriaFuncional> daoCategoriaFuncional = new GenericDao(CategoriaFuncional.class);

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {

        Permissao p = Valida.acesso(req, resp, "Funcionario");
        
        if (p == null) {
            req.setAttribute("message", "Acesso negado. Tente fazer login.");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/login?url=/funcionario");
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
                List<Funcionario> result = null;
                if (p.getConsultar()) {
                    result = daoFuncionario.findEntities();
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
        Funcionario obj = null;
        try {
            obj = daoFuncionario.findEntity(id);
        } catch (Exception ex) {
            Logger.getLogger(CartaoFidelidadeServletController.class.getName()).log(Level.SEVERE, null, ex);
        }
        req.setAttribute("obj", obj);
        req.setAttribute("listCidade", daoCidade.findEntities());
        req.setAttribute("action", "edit");
        String nextJSP = "/jsp/form-funcionario.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        dispatcher.forward(req, resp);
    }

    private void search(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String search = req.getParameter("search");
        List<Funcionario> result = daoFuncionario.findEntitiesByField("nome", search);  // buscar por nome
        forwardList(req, resp, result);
    }

    private void forwardList(HttpServletRequest req, HttpServletResponse resp, List entityList)
            throws ServletException, IOException {
        String nextJSP = "/jsp/list-funcionario.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        req.setAttribute("entities", entityList);
        dispatcher.forward(req, resp);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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
        String nextJSP = "/jsp/form-funcionario.jsp";
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

            String cpf = req.getParameter("cpf");
            String rg = req.getParameter("rg");
            Character sexo = 'M';
            Date nascimento = new SimpleDateFormat("yyyy-MM-dd").parse(req.getParameter("nascimento"));

            Date admissao = new SimpleDateFormat("yyyy-MM-dd").parse(req.getParameter("admissao"));
            Date demissao = new SimpleDateFormat("yyyy-MM-dd").parse(req.getParameter("demissao"));
            String ctps = req.getParameter("ctps");
            Double salario = Double.parseDouble(req.getParameter("salario"));
            String login = req.getParameter("login");
            String senha = req.getParameter("senha");
            CategoriaFuncional categoriaFuncional = daoCategoriaFuncional.findEntities().get(0);

            Funcionario obj = new Funcionario(admissao, demissao, ctps, salario, login, senha, categoriaFuncional, cpf, rg, sexo, nascimento, null, nome, logradouro, numero, complemento, bairro, email, telefone, cep, cidade);
            daoFuncionario.create(obj);
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

            String cpf = req.getParameter("cpf");
            String rg = req.getParameter("rg");
            Character sexo = 'M';
            Date nascimento = new SimpleDateFormat("yyyy-MM-dd").parse(req.getParameter("nascimento"));

            Date admissao = new SimpleDateFormat("yyyy-MM-dd").parse(req.getParameter("admissao"));
            Date demissao = new SimpleDateFormat("yyyy-MM-dd").parse(req.getParameter("demissao"));
            String ctps = req.getParameter("ctps");
            Double salario = Double.parseDouble(req.getParameter("salario"));
            String login = req.getParameter("login");
            String senha = req.getParameter("senha");
            CategoriaFuncional categoriaFuncional = daoCategoriaFuncional.findEntities().get(0);

            Funcionario obj = new Funcionario(admissao, demissao, ctps, salario, login, senha, categoriaFuncional, cpf, rg, sexo, nascimento, id, nome, logradouro, numero, complemento, bairro, email, telefone, cep, cidade);
            boolean success = false;
            try {
                daoFuncionario.edit(obj);
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
            daoFuncionario.destroy(id);
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

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
