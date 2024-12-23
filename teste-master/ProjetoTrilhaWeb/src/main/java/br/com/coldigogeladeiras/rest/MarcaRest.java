package br.com.coldigogeladeiras.rest;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import br.com.coldigogeladeiras.bd.Conexao;
import br.com.coldigogeladeiras.modelo.Marca;
import br.com.coldigogeladeiras.jdbc.JDBCMarcaDAO;

@Path("marca")
public class MarcaRest extends UtilRest {
	@GET
	@Path("/buscar")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscar() {
		try {
			List<Marca> listaMarcas = new ArrayList<Marca>();

			Conexao conect = new Conexao();
			Connection conexao = conect.abrirConexao();
			JDBCMarcaDAO jdbcMarca = new JDBCMarcaDAO(conexao);
			listaMarcas = jdbcMarca.buscar();
			conect.fecharConexao();
			return this.buildResponse(listaMarcas);
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
	
	@POST
	@Path("/inserir")
	@Consumes("application/*")
	public Response inserir(String marcaParam) {
		try {
			Conexao conect = new Conexao();
			Connection conexao = conect.abrirConexao();
			
			Marca marca = new Gson().fromJson(marcaParam, Marca.class);
			JDBCMarcaDAO jdbcMarca = new JDBCMarcaDAO(conexao);
			boolean retorno = jdbcMarca.inserir(marca);
			String msg = "";
			
			if(retorno) {
				msg = "Produto cadastrado com sucesso!";
			}else {
				msg = "Erro ao cadastrar produto";
			}
			conect.fecharConexao();
			
			return this.buildResponse(msg);
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
	
	@GET
	@Path("/buscarPorNome")
	@Consumes("application/*")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarPorNome(@QueryParam("valorBusca") String nome) {
		try {
			List<JsonObject> listaMarcas = new ArrayList<JsonObject>();

			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCMarcaDAO jdbcMarca = new JDBCMarcaDAO(conexao);
			listaMarcas = jdbcMarca.buscarPorNome(nome);
			conec.fecharConexao();
			
			String json = new Gson().toJson(listaMarcas);
			return this.buildResponse(json);

		} catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
	
	@DELETE
	@Path("/excluir/{id}")
	@Consumes("application/*")
	public Response excluir(@PathParam("id") int id) {
		try {
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCMarcaDAO jdbcMarca = new JDBCMarcaDAO(conexao);
			
			boolean retorno = jdbcMarca.deletar(id);
			String msg = "";
			
			if (retorno) {
				msg = "Produto excluído com sucesso!";
			}else {
				msg = "Erro ao excluir produto.";
			}
			conec.fecharConexao();
			
			return this.buildResponse(msg);
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
	
	@PUT
	@Path("/alterar")
	@Consumes("application/*")
	public Response alterar(String marcaParam) {
		try {
			Marca marca = new Gson().fromJson(marcaParam, Marca.class);
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			
			JDBCMarcaDAO jdbcMarca = new JDBCMarcaDAO(conexao);
			boolean retorno = jdbcMarca.alterar(marca);
			
			String msg = "";
			if (retorno) {
				msg = "Marca alterada com sucesso!";
			} else {
				msg = "Erro ao alterar marca.";
			}
			
			conec.fecharConexao();
			
			return this.buildResponse(msg);
		} catch(Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
	
	@GET
	@Path("/buscarPorId")
	@Consumes("application/*")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarPorId(@QueryParam("id") int id) {
		try {
			Marca marca = new Marca();
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCMarcaDAO jdbcMarca = new JDBCMarcaDAO(conexao);
			
			marca = jdbcMarca.buscarPorId(id);
			
			conec.fecharConexao();
			
			return this.buildResponse(marca);
		} catch(Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
	
}