package com.mt.mtmoneyapi.resource;

import com.mt.mtmoneyapi.model.Pessoa;
import com.mt.mtmoneyapi.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

    @Autowired
    private PessoaRepository pessoaRepository;

    @GetMapping
    public List<Pessoa> listar(){
        return pessoaRepository.findAll();
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Pessoa>  buscarPeloCodigo(@PathVariable Long codigo){
         Optional<Pessoa> pessoa = pessoaRepository.findById(codigo);

         return pessoa.isPresent()? ResponseEntity.ok(pessoa.get()) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pessoa cadastrar(@Valid @RequestBody Pessoa pessoa){
        return pessoaRepository.save(pessoa);
    }
}
