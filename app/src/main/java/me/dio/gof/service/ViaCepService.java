package me.dio.gof.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import me.dio.gof.model.Endereco;

/**
 * Cliente HTTP para consumo da API do <b>ViaCEP</b>
 * 
 * @see <a href="http://viacep.com.br">ViaCEP</a>
 * 
 * @author Alem
 *
 */

@FeignClient(name = "viacep", url = "http://viacep.com.br/ws")
public interface ViaCepService {
	
	@GetMapping("/{cep}/json/")
	Endereco consultarCEP(@PathVariable("cep") String cep);

}
