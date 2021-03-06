package principal.emprestimo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import principal.item.Item;
import principal.user.Usuario;

/**
 * Representacao de um Controller de Emprestimos.
 * 
 * Projeto de Laboratorio de Progamacao 2 - 2017.1 (TT - Tracking things)
 * 
 * @author Cassio Cordeiro - 116210038 Geovane Silva - 116211149 Hemillainy
 *         Santos - 116210802
 *
 */
public class EmprestimoController {
	private EmprestimoListador listador;
	private EmprestimoValidacao validacao;
	private Map<IdEmprestimo, Emprestimo> emprestimos;

	/**
	 * Controi um EmprestimoController. Todo EmprestimoController tem um
	 * listador, um validador de entradas e um Map de Emprestimos.
	 */
	public EmprestimoController() {
		this.listador = new EmprestimoListador();
		this.validacao = new EmprestimoValidacao();
		this.emprestimos = new HashMap<>();
	}

	/**
	 * Cria um emprestimo e um idEmprestimo. Adiciona o emprestimo no map de
	 * emprestimos e passa-o para o dono e o requerente.
	 * 
	 * @param dono
	 *            dono do item emprestado.
	 * @param requerente
	 *            usuario que ira pegar o item emprestado.
	 * @param itemEmprestar
	 *            item que sera emprestado.
	 * @param data
	 *            data em que foi realizado o emprestimo.
	 * @param periodo
	 *            periodo em que o requerente deve ficar com o item.
	 */
	private void alocarEmprestimos(Usuario dono, Usuario requerente, Item itemEmprestar, LocalDate data, int periodo) {
		Emprestimo e = new Emprestimo(dono, requerente, itemEmprestar, data, periodo);
		IdEmprestimo ie = new IdEmprestimo(dono, requerente, itemEmprestar, data);
		emprestimos.put(ie, e);
		dono.addEmprestimo(e);
		requerente.addEmprestimo(e);
		itemEmprestar.setStaus();
	}

	/**
	 * Metodo que realiza um emprestimo.
	 * 
	 * @param dono
	 *            usuario dono do item.
	 * @param requerente
	 *            usuario que esta pegando o item emprestado.
	 * @param nomeItem
	 *            a ser emprestado.
	 * @param dataEmprestimo
	 *            que houve o emprestimo.
	 * @param periodo
	 *            de emprestimo.
	 */
	public void registraEmprestimo(Usuario dono, Usuario requerente, String nomeItem, String dataEmprestimo,
			int periodo) {
		validacao.validaItemEmprestimo(dono.getItem(nomeItem));
		validacao.itemNaoEncontrado(dono.hasItem(nomeItem));
		Item itemEmprestar = dono.getItem(nomeItem);
		validacao.ItemJaEmprestado(itemEmprestar.verificaEmprestado());

		LocalDate dataE = createData(dataEmprestimo);
		alocarEmprestimos(dono, requerente, itemEmprestar, dataE, periodo);
		dono.addReputacao(itemEmprestar.getPreco(), 0.1);
		itemEmprestar.addNumeroEmprestimo();
	}

	/**
	 * Cria um objeto LocalDate a partir de uma string.
	 * 
	 * @param data
	 *            a String da data.
	 * @return uma LocalDate.
	 */
	private LocalDate createData(String data) {
		validacao.atributoInvalido(data);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return LocalDate.parse(data, dtf);

	}

	/**
	 * Verifica se um emprestimo esta atrasado.
	 * 
	 * @param dataEmprestimo
	 *            em que foi emprestado o item.
	 * @param dataDevolucao
	 *            que foi devolvido o item.
	 * @return o periodo que o requerente ficou com o item.
	 */
	private long emprestimoAtrasado(LocalDate dataEmprestimo, LocalDate dataDevolucao) {
		long periodo = dataEmprestimo.until(dataDevolucao, ChronoUnit.DAYS);
		return periodo;
	}

	/**
	 * Metodo que devolve um item.
	 * 
	 * @param dono
	 *            dono do item.
	 * @param requerente
	 *            usuario que esta pegando o item emprestado.
	 * @param nomeItem
	 *            a ser devolvido.
	 * @param dataEmprestimo
	 *            que aconteceu o emprestimo.
	 * @param dataDevolucao
	 *            do item.
	 */
	public void devolveItem(Usuario dono, Usuario requerente, String nomeItem, String dataEmprestimo,
			String dataDevolucao) {
		
		validacao.validaItemEmprestimo(dono.getItem(nomeItem));
		validacao.itemNaoEncontrado(dono.hasItem(nomeItem));
		Item itemDevolver = dono.getItem(nomeItem);
		LocalDate dataE = createData(dataEmprestimo);
		LocalDate dataD = createData(dataDevolucao);
		IdEmprestimo ie = new IdEmprestimo(dono, requerente, itemDevolver, dataE);

		validacao.emprestimoNaoEncontrado(emprestimos.containsKey(ie));
		emprestimos.get(ie).devolverItem(dataD);
		itemDevolver.setStaus();

		reputacaoDevolucao(requerente, itemDevolver, dataE, dataD);
	}

	/**
	 * Calcula qual valor sera adicionado ou retirado da reputacao do usuario
	 * apos a entrega do item.
	 * 
	 * @param requerente o usuario que esta devolvendo. 
	 * @param itemDevolver o item a ser devolvido. 
	 * @param dataE a data do emprestimo. 
	 * @param dataD a data de devolucao. 
	 */
	private void reputacaoDevolucao(Usuario requerente, Item itemDevolver, LocalDate dataE, LocalDate dataD) {
		if (emprestimoAtrasado(dataE, dataD) <= requerente.getPeriodo()) {
			requerente.addReputacao(itemDevolver.getPreco(), 0.05);
		} else {
			double taxa = (emprestimoAtrasado(dataE, dataD) - requerente.getPeriodo()) / 100.00;
			requerente.addReputacao(-itemDevolver.getPreco(), taxa);
		}
	}

	/**
	 * Metodo que lista os associados a um item.
	 * 
	 * @param nome
	 *            do item.
	 * @return a lista com todos os emprestimos relacionados a um item.
	 */
	public String listarEmprestimosItem(String nome) {
		List<Emprestimo> empres = new ArrayList<>(emprestimos.values());
		return listador.listaEmprestimosItem(empres, nome);
	}

	/**
	 * Metodo que lista os itens cadastrados emprestados.
	 * 
	 * @return a listagem com os itens emprestados.
	 */
	public String listaItensEmprestados() {
		List<Emprestimo> emp = new ArrayList<>(emprestimos.values()); // stub
		return listador.listaItensEmprestados(emp);
	}

	/**
	 * Metodo que lista os emprestimos concedidos a um ususario.
	 * 
	 * @param usuario
	 *            o usuario que tera o que ele emprestou listado.
	 * @return a listagem do emprestimos concedidos a um usuario.
	 */
	public String listarEmprestimosUsuarioEmprestando(Usuario usuario) {
		return listador.listaEmprestimosUsuarioEmprestando(usuario);
	}

	/**
	 * Metodo que lista os emprestimos concedidos a um ususario.
	 * 
	 * @param usuario
	 *            o usuario que tera seus emprestimos listados.
	 * @return a listagem do emprestimos concedidos a um usuario.
	 */
	public String listarEmprestimosUsuarioPegandoEmprestado(Usuario usuario) {
		return listador.listaEmprestimosUsuarioPegandoEmprestado(usuario);
	}

	/**
	 * Metodo para acesso ao mapa de emprestimos do sistema. 
	 * @return o mapa de emprestimos cadastrados. 
	 */
	public Map<IdEmprestimo, Emprestimo> mapaEmprestimo() {
		return emprestimos;
	}

	/**
	 * Metodo que altera a base de dados de emprestimos cadastrados no sistema. 
	 * @param emprestimos novo mapa de emprestimos. 
	 */
	public void setData(Map<IdEmprestimo, Emprestimo> emprestimos) {
		this.emprestimos = emprestimos;
	}

}
