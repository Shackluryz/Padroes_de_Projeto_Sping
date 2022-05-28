package me.dio.gof.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.dio.gof.model.Cliente;
import me.dio.gof.model.ClienteRepository;
import me.dio.gof.model.Endereco;
import me.dio.gof.model.EnderecoRepository;
import me.dio.gof.service.ClienteService;
import me.dio.gof.service.ViaCepService;

@Service
public class ClienteServiceImpl implements ClienteService {
	
	// Singleton: Injetar os componentes Spring com @Autowired.
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private ViaCepService viaCepService;
	
	// Strategy: Implementar os métodos definidos na Interface.
	// Facade: Abstrair integrações com subsistemas, promovendo uma interface simples.

	@Override
	public Iterable<Cliente> buscarTodos() {
		return clienteRepository.findAll();
	}

	@Override
	public Cliente buscarPorId(Long id) {
		Optional<Cliente> cliente = clienteRepository.findById(id);
		return cliente.get();
	}

	@Override
	public void inserir(Cliente cliente) {
		salvarClienteComCep(cliente);
	}

	@Override
	public void atualizar(Long id, Cliente cliente) {
		// Buscar Cliente por ID, caso exista:
		Optional<Cliente> clienteDb = clienteRepository.findById(id);
		if(clienteDb.isPresent()) {
			salvarClienteComCep(cliente);
		}
	}

	@Override
	public void deletar(Long id) {
		// Deletar Cliente por ID.
		clienteRepository.deleteById(id);		
	}

	private void salvarClienteComCep(Cliente cliente) {
		// Verificar se o Endereço do Cliente já existe (pelo CEP).
		String cep = cliente.getEndereco().getCep();
		Endereco endereco = enderecoRepository.findById(cep).orElseGet(() -> {
			// Caso não exista, integrar com o ViaCEP e persistir o retorno.
			Endereco novoEndereco = viaCepService.consultarCEP(cep);
			enderecoRepository.save(novoEndereco);
			return novoEndereco;
		});
		cliente.setEndereco(endereco);
		// Inserir Cliente, vinculando o Endereço (novo ou existente).
		clienteRepository.save(cliente);
	}

}
