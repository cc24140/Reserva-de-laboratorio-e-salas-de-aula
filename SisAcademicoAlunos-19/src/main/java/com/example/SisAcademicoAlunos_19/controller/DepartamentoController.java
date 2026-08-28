package com.example.SisAcademicoAlunos_19.controller;

import com.example.SisAcademicoAlunos_19.controller.dto.DepartamentoDTO;
import com.example.SisAcademicoAlunos_19.controller.dto.ErroResposta;
import com.example.SisAcademicoAlunos_19.exceptions.OperacaoNaoPermitidaException;
import com.example.SisAcademicoAlunos_19.exceptions.RegistroDuplicadoException;
import com.example.SisAcademicoAlunos_19.model.Departamento;
import com.example.SisAcademicoAlunos_19.service.DepartamentoService;
import com.example.SisAcademicoAlunos_19.validator.DepartamentoValidator;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("/departamentos")
// http://localhost:8080/departamentos
public class DepartamentoController {
    private final DepartamentoService departamentoService;
    private final DepartamentoValidator departamentoValidator;

    public DepartamentoController(DepartamentoService departamentoService,
                                  DepartamentoValidator departamentoValidator) {
        this.departamentoService = departamentoService;
        this.departamentoValidator = departamentoValidator;
    }

    // endPoint do POST(INSERT)
    @PostMapping
    //@RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> incluirDepartamento(@RequestBody @Valid DepartamentoDTO departamentoDTO)
    {
        try
        {
            Departamento departamentoEntidade = departamentoDTO.mapearDadosParaEntidadeDepartamento();
            departamentoService.inserirDepartamento(departamentoEntidade);

            // criar o seguinte endPoint
            // http://localhost:8080/departamentos/id
            // http://localhost:8080/departamentos/idDoNovoRegistro
            URI location = ServletUriComponentsBuilder
                  .fromCurrentRequest()
                  .path("/{id}")
                  .buildAndExpand(departamentoEntidade.getId())
                  .toUri();

            System.out.println("Dados do novo DEPARTAMENTO inseridos com sucesso! \nDados vindo do JSON = " + departamentoDTO);
            return new ResponseEntity("Dados da entidade DEPARTAMENTO inseridos com sucesso!" + departamentoDTO, HttpStatus.CREATED);
            //return ResponseEntity.created(location).build();
        } catch (RegistroDuplicadoException e)
        {
            var erroDTO = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }
    }


    @GetMapping("{id}")
    public ResponseEntity<DepartamentoDTO> pegarDadosDepartamento(@PathVariable("id") Integer id)
    {
        Optional<Departamento> departamentoOptional = departamentoService.pegarDadosDepartamentoPorId(id);
        if (departamentoOptional.isPresent())
        {
            Departamento departamento = departamentoOptional.get();
            DepartamentoDTO departamentoDTO = new DepartamentoDTO(
                    departamento.getId(),
                    departamento.getNome(),
                    departamento.getLocalizacao(),
                    departamento.getFaturamento(),
                    departamento.getResponsavel()
            );
            return ResponseEntity.ok(departamentoDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> excluirDepartamento(@PathVariable("id") Integer id)
    {
        try {
            Optional<Departamento> departamentoOptional = departamentoService.pegarDadosDepartamentoPorId(id);
            if (departamentoOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            departamentoService.excluirDepartamentoPorId(id);
            return ResponseEntity.ok().build();
        } catch (OperacaoNaoPermitidaException e)
        {
           var erroResposta = ErroResposta.respostaPadrao(e.getMessage());
           return ResponseEntity.status(erroResposta.status()).body(erroResposta);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> atualizarDepartamento(
            @PathVariable("id") Integer id,
            @RequestBody DepartamentoDTO departamentoDTO)
    {
        try {
            Optional<Departamento> departamentoOptional = departamentoService.pegarDadosDepartamentoPorId(id);
            if (departamentoOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            var departamento = departamentoOptional.get();
            departamento.setNome(departamentoDTO.nome());
            departamento.setLocalizacao(departamentoDTO.localizacao());
            departamento.setFaturamento(departamentoDTO.faturamento());
            departamento.setResponsavel(departamentoDTO.responsavel());
            departamentoService.atualizarDepartamento(departamento);
            return ResponseEntity.ok().build();
        } catch (RegistroDuplicadoException e)
        {
            var erroDTO = ErroResposta.conflito(e.getMessage());
            return  ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }
    }

    // fazer o GET com os filtros: localizacao, faturamento e responsavel
    // utilizar o Query Params
    // http://localhost:8080/departamentos?localizacao=XXXX?faturamento=999?responsavel=YYYY
    @GetMapping
    public ResponseEntity<List<DepartamentoDTO>> pesquisarPorLocalizacaoFaturamentoReponsavel(
            @RequestParam(value="localizacao",required=false) String localizacao,
            @RequestParam(value="faturamento",required=false) Double faturamento,
            @RequestParam(value="responsavel",required=false) String responsavel)
    {
        List<Departamento> resultado = departamentoService.pesquisarPorLocalizacaoFaturamentoResponsavel(
                localizacao, faturamento, responsavel);

        List<DepartamentoDTO> lista = resultado
                .stream()
                .map(departamento -> new DepartamentoDTO(
                        departamento.getId(),
                        departamento.getNome(),
                        departamento.getLocalizacao(),
                        departamento.getFaturamento(),
                        departamento.getResponsavel()
                )).collect(Collectors.toList());

        return ResponseEntity.ok(lista);
    }
}