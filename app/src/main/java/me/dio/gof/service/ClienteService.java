package me.dio.gof.service;

import me.dio.gof.model.Cliente;

/**
 * Interface que define o padrão <b>Strategy</b> no domínio do cliente. 
 * Com isso podemos ter multiplas implementações dessa mesma interface.
 * 
 * @author Alem
 *
 */
public interface ClienteService {
	
	Iterable<Cliente> burcarTodos();
	Cliente buscarPorId(Long id);
	void inserir(Cliente cliente);
	void atualizar(Long id, Cliente cliente);
	void deletar(Long id);
	
}
