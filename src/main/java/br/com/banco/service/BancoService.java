package br.com.banco.service;

import br.com.banco.domain.Transferencia;
import br.com.banco.exception.BadRequestException;
import br.com.banco.reposytory.BancoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class BancoService {
    @Autowired
    private BancoRepository bancoRepository;

    public List<Transferencia> findall() {
        return (List<Transferencia>) bancoRepository.findAll();
    }

    public List<Transferencia>  getTransferenciasByIdConta(long contaId) {
        List<Transferencia> idExist = bancoRepository.findByContaId(contaId);
        if (idExist.isEmpty()) {
            throw new BadRequestException("Conta inexistente");
        }

        return bancoRepository.findByContaId(contaId);
    }

    public List<Transferencia> buscarTransferenciasPorData(LocalDateTime dataInicio, LocalDateTime dataFim) {
        List<Transferencia> idExist =  bancoRepository.findByDataBetween(dataInicio, dataFim);
        if (idExist.isEmpty()) {
            throw new BadRequestException("Não há tranferencia para esse intervalo de data");
        }
        return bancoRepository.findByDataBetween(dataInicio, dataFim);
    }

    public Transferencia nomeOperadorTransacao(String nomeOperadorTransacao) {
        Transferencia nameExist = bancoRepository.nomeOperadorTransacao(nomeOperadorTransacao);
        if (nameExist == null){
            throw new BadRequestException("Nome inexistente");
        }
        return bancoRepository.nomeOperadorTransacao(nomeOperadorTransacao);
    }

    public List<Transferencia> obterTransferenciasPorPeriodoEOperador(LocalDateTime dataInicial, LocalDateTime dataFinal, String nomeOperador) {
        if (dataInicial != null && dataFinal != null && nomeOperador != null) {
            return bancoRepository.findByDataBetweenAndNomeOperadorTransacao(dataInicial, dataFinal, nomeOperador);
        } else if (dataInicial != null && dataFinal != null) {
            return bancoRepository.findByDataBetween(dataInicial, dataFinal);
        } else if (nomeOperador != null) {
            return bancoRepository.findByNomeOperadorTransacao(nomeOperador);
        } else {
            return bancoRepository.findAll();
        }
    }

    public List<Transferencia> findByTipo(String tipo) {
        String tipoLowerCase = tipo.toLowerCase();
        List<Transferencia> type = bancoRepository.findByTipoIgnoreCase(tipoLowerCase);

        if (type.isEmpty()) {
            throw new BadRequestException("Tipo não encontrado ou não existente");
        }
        return type;
    }
}
