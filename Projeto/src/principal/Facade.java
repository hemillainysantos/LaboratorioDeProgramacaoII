package principal;

import easyaccept.EasyAccept;
import principal.emprestimo.EmprestimoController;
import principal.item.ItemController;
import principal.user.UserController;
import principal.user.Usuario;

/**
 * Classe facade do sistema.
 * 
 * Projeto de Laboratorio de Progamacao 2 - 2017.1 (TT - Tracking things)
 * 
 * @author Cassio Cordeiro - 116210038 Geovane Silva - 116211149 Hemillainy
 *         Santos - 116210802
 *
 */
public class Facade {
	public static void main(String[] args) {
		Facade fc= new Facade(); 
		fc.iniciarSistema();
		args = new String[] { "principal.Facade","acceptance_test/us8_test.txt"};
		EasyAccept.main(args);
	}
	

	 
	private UserController userController;
	private ItemController itemController;
	private EmprestimoController emprestimoController;

	/**
	 * Construtor de facade.
	 */
	public Facade() {
		this.userController = new UserController();
		this.itemController = new ItemController(); 
		this.emprestimoController = new EmprestimoController();
	} 

	/**
	 * Inicializa o sistema carregando dados de execucoes passadas.
	 * 
	 */
	public void iniciarSistema()  {
		Persistencia persistencia = new Persistencia();
		persistencia.carregar(userController, itemController, emprestimoController);
	}

	/**
	 * Fecha o sistema salvando dados para execucoes futuras.
	 */
	public void fecharSistema() {
		Persistencia persistencia = new Persistencia();
		persistencia.salvar(userController.mapaUsuarios(), itemController.mapaItens(), emprestimoController.mapaEmprestimo());
	}

	/**
	 * Metodo que cadastra um usuario no sistema.
	 * 
	 * @param nome
	 *            do usuario.
	 * @param telefone
	 *            do usuario.
	 * @param email
	 *            do usuario.
	 */
	public void cadastrarUsuario(String nome, String telefone, String email) {
		userController.cadastraUsuario(nome, telefone, email);
	}

	/**
	 * Metodo que remove um usuario do sistema.
	 * 
	 * @param nome
	 *            do usuario.
	 * @param telefone
	 *            do usuario.
	 */
	public void removerUsuario(String nome, String telefone) {
		userController.removeUsuario(nome, telefone);
	}

	/**
	 * Metodo que retorna um atributo de um usuario.
	 * 
	 * @param nome
	 *            do usuario.
	 * @param telefone
	 *            do usuario.
	 * @param atributo
	 *            que se deseja.
	 * @return o atributo desejado.
	 */
	public String getInfoUsuario(String nome, String telefone, String atributo) {
		return userController.getInfoUsuario(nome, telefone, atributo);
	}

	/**
	 * Metodo que atualiza um atributo de um usuario.
	 * 
	 * @param nome
	 *            do usuario
	 * @param telefone
	 *            do usuario.
	 * @param atributo
	 *            a ser alterado.
	 * @param valor
	 *            para substituir o valor antigo do atributo.
	 */
	public void atualizarUsuario(String nome, String telefone, String atributo, String valor) {
		userController.atualizaUsuario(nome, telefone, atributo, valor);
	}

	/**
	 * Pega o usuario de nome e telefone passados.
	 * 
	 * @param nome
	 *            nome do usuario.
	 * @param telefone
	 *            telefone do usuario.
	 * @return um usuario.
	 */
	private Usuario pegaUsuario(String nome, String telefone) {
		return userController.getUsuario(nome, telefone);
	}

	/**
	 * Retorna um usuario caso o mesmo possa pegar um item emprestado.
	 * 
	 * @param nome
	 *            nome do usuario.
	 * @param telefone
	 *            telefone do usuario.
	 * @param periodo
	 *            tempo o qual o usuario deseja ficar o item.
	 * @return
	 */
	private Usuario pegarEmprestado(String nome, String telefone, int periodo) {
		return userController.pegarEmprestado(nome, telefone, periodo);
	}

	/**
	 * Metodo que cadastra um jogo eletronico.
	 * 
	 * @param nome
	 *            do usuario.
	 * @param telefone
	 *            do usuario.
	 * @param nomeItem
	 *            a ser cadastrado.
	 * @param preco
	 *            do jogo eletronico.
	 * @param plataforma
	 *            do jogo eletronico.
	 */
	public void cadastrarEletronico(String nome, String telefone, String nomeItem, double preco, String plataforma) {
		itemController.cadastrarEletronico(pegaUsuario(nome, telefone), nomeItem, preco, plataforma);
	}

	/**
	 * Metodo que cadastra um jogo de tabuleiro.
	 * 
	 * @param nome
	 *            do usuario.
	 * @param telefone
	 *            do usuario.
	 * @param nomeItem
	 *            a ser cadastrado.
	 * @param preco
	 *            do item.
	 */
	public void cadastrarJogoTabuleiro(String nome, String telefone, String nomeItem, double preco) {
		itemController.cadastrarJogoTabuleiro(pegaUsuario(nome, telefone), nomeItem, preco);
	}

	/**
	 * Metodo que cadastra um filme.
	 * 
	 * @param nome
	 *            do usuario.
	 * @param telefone
	 *            do usuario.
	 * @param nomeItem
	 *            a ser cadastrado.
	 * @param preco
	 *            do item.
	 * @param duracao
	 *            do filme.
	 * @param genero
	 *            do filme.
	 * @param classificacao
	 *            indicativa do filme.
	 * @param lancamento
	 *            do filme.
	 */
	public void cadastrarBluRayFilme(String nome, String telefone, String nomeItem, double preco, int duracao,
			String genero, String classificacao, int lancamento) {
		itemController.cadastraBluRayFilme(pegaUsuario(nome, telefone), nomeItem, preco, duracao, genero, classificacao,
				lancamento);
	}

	/**
	 * Metodo que cadastra um show.
	 * 
	 * @param nome
	 *            do usuario.
	 * @param telefone
	 *            do usuario.
	 * @param nomeItem
	 *            a ser cadastrado.
	 * @param preco
	 *            do item.
	 * @param duracao
	 *            do show.
	 * @param faixas
	 *            do show.
	 * @param artista
	 *            que realizou o show.
	 * @param classificacao
	 *            indicativa do show.
	 */
	public void cadastrarBluRayShow(String nome, String telefone, String nomeItem, double preco, int duracao,
			int faixas, String artista, String classificacao) {
		itemController.cadastraBluRayShow(pegaUsuario(nome, telefone), nomeItem, preco, duracao, faixas, artista,
				classificacao);
	}

	/**
	 * Metodo que cadastra uma serie.
	 * 
	 * @param nome
	 *            do usuario.
	 * @param telefone
	 *            do usuario.
	 * @param nomeItem
	 *            a ser cadastrado.
	 * @param preco
	 *            do item.
	 * @param descricao
	 *            da serie.
	 * @param duracao
	 *            da serie.
	 * @param classificacao
	 *            indicativa da serie.
	 * @param genero
	 *            da serie
	 * @param temporada
	 *            da serie.
	 */
	public void cadastrarBluRaySerie(String nome, String telefone, String nomeItem, double preco, String descricao,
			int duracao, String classificacao, String genero, int temporada) {
		itemController.cadastraBluRaySerie(pegaUsuario(nome, telefone), nomeItem, preco, descricao, duracao,
				classificacao, genero, temporada);
	}

	/**
	 * Metodo que adiciona um BluRay a uma serie.
	 * 
	 * @param nome
	 *            do usuario.
	 * @param telefone
	 *            do usuario.
	 * @param nomeBluRayTemporada
	 *            a ser cadastrado o BluRay.
	 * @param duracao
	 *            do episodio.
	 */
	public void adicionarBluRay(String nome, String telefone, String nomeBluRayTemporada, int duracao) {
		itemController.adicionaBluRay(pegaUsuario(nome, telefone), nomeBluRayTemporada, duracao);
	}

	/**
	 * Metodo que adiciona uma peca perida a um jogo de tabuleiro.
	 * 
	 * @param nome
	 *            do usuario.
	 * @param telefone
	 *            do usuario.
	 * @param nomeItem
	 *            a ser adicionada a peca.
	 * @param nomePeca
	 *            a ser adicionada como perdida.
	 */
	public void adicionarPecaPerdida(String nome, String telefone, String nomeItem, String nomePeca) {
		itemController.adicionaPecaPerdida(pegaUsuario(nome, telefone), nomeItem, nomePeca);
	}

	/**
	 * Metodo que retorna uma informacao de um item.
	 * 
	 * @param nome
	 *            do usuario.
	 * @param telefone
	 *            do usuario.
	 * @param nomeItem
	 *            que se deseja pegar a informacao.
	 * @param atributo
	 *            que se deseja visualizar.
	 * @return a informacao correspondente ao atributo desejado.
	 */
	public String getInfoItem(String nome, String telefone, String nomeItem, String atributo) {
		return itemController.getInfoItem(pegaUsuario(nome, telefone), nomeItem, atributo);
	}

	/**
	 * Metodo que remove um item.
	 * 
	 * @param nome
	 *            do usuario.
	 * @param telefone
	 *            do usuario.
	 * @param nomeItem
	 *            a ser removido.
	 */
	public void removerItem(String nome, String telefone, String nomeItem) {
		itemController.removeItem(pegaUsuario(nome, telefone), nomeItem);
	}

	/**
	 * Metodo que atualiza uma informacao de um item.
	 * 
	 * @param nome
	 *            do usuario.
	 * @param telefone
	 *            do usuario.
	 * @param nomeItem
	 *            a ser alterado.
	 * @param atributo
	 *            que se deseja alterar.
	 * @param valor
	 *            para substituir o valor antigo do atributo.
	 */
	public void atualizarItem(String nome, String telefone, String nomeItem, String atributo, String valor) {
		itemController.atualizaItem(pegaUsuario(nome, telefone), nomeItem, atributo, valor);
	}

	/**
	 * Metodo que lista todos os itens em ordem lexicografica.
	 * 
	 * @return a listagem de todos os itens em ordem lexicografica.
	 */
	public String listarItensOrdenadosPorNome() {
		return itemController.listarItemOrdenadosPorNome();
	}

	/**
	 * Metodo que lista todos os itens em ordem crescente de valor.
	 * 
	 * @return a listagem de todos os itens em ordem crecente de valor.
	 */
	public String listarItensOrdenadosPorValor() {
		return itemController.listarItemOrdenadosPorValor();
	}

	/**
	 * Metodo que retorna a representacao de um item.
	 * 
	 * @param nome
	 *            do usuario.
	 * @param telefone
	 *            do usuario.
	 * @param nomeItem
	 *            a ser pesquisado.
	 * @return a representacao do item.
	 */
	public String pesquisarDetalhesItem(String nome, String telefone, String nomeItem) {
		return itemController.pesquisaDetalhesItens(pegaUsuario(nome, telefone), nomeItem);
	}

	/**
	 * Metodo que realiza um emprestimo.
	 * 
	 * @param nomeDono
	 *            do item.
	 * @param telefoneDono
	 *            do item.
	 * @param nomeRequerente
	 *            do emprestimo.
	 * @param telefoneRequerente
	 *            do emprestimo.
	 * @param nomeItem
	 *            a ser emprestado.
	 * @param dataEmprestimo
	 *            que houve o emprestimo.
	 * @param periodo
	 *            de emprestimo.
	 */
	public void registrarEmprestimo(String nomeDono, String telefoneDono, String nomeRequerente,
			String telefoneRequerente, String nomeItem, String dataEmprestimo, int periodo) {
		emprestimoController.registraEmprestimo(pegaUsuario(nomeDono, telefoneDono),
				pegarEmprestado(nomeRequerente, telefoneRequerente, periodo), nomeItem, dataEmprestimo, periodo);
	}

	/**
	 * Metodo que devolve um item.
	 * 
	 * @param nomeDono
	 *            do item.
	 * @param telefoneDono
	 *            do item.
	 * @param nomeRequerente
	 *            do emprestimo.
	 * @param telefoneRequerente
	 *            do emprestimo.
	 * @param nomeItem
	 *            a ser devolvido.
	 * @param dataEmprestimo
	 *            que aconteceu o emprestimo.
	 * @param dataDevolucao
	 *            do item.
	 *
	 */
	public void devolverItem(String nomeDono, String telefoneDono, String nomeRequerente, String telefoneRequerente,
			String nomeItem, String dataEmprestimo, String dataDevolucao) {
		emprestimoController.devolveItem(pegaUsuario(nomeDono, telefoneDono),
				pegaUsuario(nomeRequerente, telefoneRequerente), nomeItem, dataEmprestimo, dataDevolucao);
	}

	/**
	 * Metodo que lista os emprestimos realizados por um usuario.
	 * 
	 * @param nome
	 *            do usuario.
	 * @param telefone
	 *            do usuario.
	 * @return a listagem dos emprestimos feitos pelo usuario.
	 */
	public String listarEmprestimosUsuarioEmprestando(String nome, String telefone) {
		return emprestimoController.listarEmprestimosUsuarioEmprestando(pegaUsuario(nome, telefone));
	}

	/**
	 * Metodo que lista os emprestimos concedidos a um ususario.
	 * 
	 * @param nome
	 *            do ususario.
	 * @param telefone
	 *            do usuario.
	 * @return a listagem do emprestimos concedidos a um usuario.
	 */
	public String listarEmprestimosUsuarioPegandoEmprestado(String nome, String telefone) {
		return emprestimoController.listarEmprestimosUsuarioPegandoEmprestado(pegaUsuario(nome, telefone));
	}

	/**
	 * Metodo que lista os emprestimos que realizados com o item.
	 * 
	 * @param nomeItem
	 *            nome do item.
	 * @return a listagem com os emprestimos que o item participou.
	 */
	public String listarEmprestimosItem(String nomeItem) {
		return emprestimoController.listarEmprestimosItem(nomeItem);
	}

	/**
	 * Metodo que lista os itens cadastrados nao emprestados.
	 * 
	 * @return a listagem dos itens nao cadastrados.
	 */
	public String listarItensNaoEmprestados() {
		return itemController.listarItensNaoEmprestados();
	}

	/**
	 * Metodo que lista os itens cadastrados nao emprestados.
	 * 
	 * @return a listagem com os itens emprestados.
	 */
	public String listarItensEmprestados() {
		return emprestimoController.listaItensEmprestados();
	}

	/**
	 * Metodo que lista os 10 itens mais emprestados.
	 * 
	 * @return a listagem com os 10 itens mais emprestados.
	 */
	public String listarTop10Itens() {
		return itemController.listarTop10Itens();
	}

	/**
	 * Metodo que lista os usuario com reputacao menor que 0.
	 * 
	 * @return a listagem com os usuario com reputacao menor que 0.
	 */
	public String listarCaloteiros() {
		return userController.listarCaloteiros();
	}

	/**
	 * Metodo que lista os 10 usuarios com melhores reputaÃ§oes.
	 * 
	 * @return a listagem com os 10 usuarios com melhor reputacao.
	 */
	public String listarTop10MelhoresUsuarios() {
		return userController.listarTop10MelhoresUsuario();
	}

	/**
	 * Metodo que lista os 10 usuarios com pior reputacao.
	 * 
	 * @return a listagem com os 10 usuarios com menor reputacao.
	 */
	public String listarTop10PioresUsuarios() {
		return userController.listarTop10PioresUsuario();
	}
}