package br.com.banco.controller;

import br.com.banco.domain.Transferencia;
import br.com.banco.service.BancoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/banco/transfer")
public class BancoController {
    private BancoService bancoService;

    @Autowired
    public BancoController(BancoService bancoService) {
        this.bancoService = bancoService;
    }

    @GetMapping("")
    public List < Transferencia> listTransfer(){
        return bancoService.findall();
    }

    @GetMapping("/id/{contaId}")
    public List<Transferencia> findById(@Valid @PathVariable long contaId) {
        return bancoService.getTransferenciasByIdConta(contaId);
    }

    @GetMapping("/date")
    public List<Transferencia> buscarTransferenciasPorData(
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss") LocalDateTime dataInicio,
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss") LocalDateTime dataFim) {
        return bancoService.buscarTransferenciasPorData(dataInicio, dataFim);
    }

    @GetMapping("/tipo")
    public List<Transferencia> findByTipo(@RequestParam String tipo) {
        return bancoService.findByTipo(tipo);
    }

    @GetMapping("/name/{nomeOperadorTransacao}")
    public Transferencia nomeOperadorTransacao(@Valid @PathVariable String nomeOperadorTransacao){
        return bancoService.nomeOperadorTransacao(nomeOperadorTransacao);
    }

    @GetMapping("/filters")
    public List<Transferencia> obterTransferencias(
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss") LocalDateTime dataInicial,
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss") LocalDateTime dataFinal,
            @RequestParam(required = false) String nomeOperador) {
        return bancoService.obterTransferenciasPorPeriodoEOperador(dataInicial, dataFinal, nomeOperador);
    }

}

