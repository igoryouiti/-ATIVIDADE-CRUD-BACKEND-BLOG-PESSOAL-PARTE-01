package com.blogpessoal.blogpessoal.controller;

import com.blogpessoal.blogpessoal.model.Usuario;
import com.blogpessoal.blogpessoal.model.UsuarioLogin;
import com.blogpessoal.blogpessoal.repository.UsuarioRepository;
import com.blogpessoal.blogpessoal.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsuarioController {


    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;


    @GetMapping("/all")
    public ResponseEntity<List<Usuario>> getAll(){
        return ResponseEntity.ok(usuarioRepository.findAll());

    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getById(@PathVariable Long id){
        return usuarioRepository.findById(id)
                .map(resposta -> ResponseEntity.ok(resposta)) // funcao lambida parecido com arrowfunction do js
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/usuario/{usuario}")
    public ResponseEntity<List<Usuario>> getByTitulo(@PathVariable String usuario){
        return ResponseEntity.ok(usuarioRepository.findAllByUsuarioContainingIgnoreCase(usuario));
    }


    @PostMapping("/logar")
    public ResponseEntity<UsuarioLogin> autenticationUsuario(
            @RequestBody Optional<UsuarioLogin> usuario) {
        return usuarioService.autenticarUsuario(usuario)
                .map(resp -> ResponseEntity.ok(resp))
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }


    @PostMapping("/cadastrar")
    public ResponseEntity<Usuario> post(@Valid @RequestBody Usuario usuario){
        return usuarioService.cadastrarUsuario(usuario)
                .map(resp -> ResponseEntity.status(HttpStatus.CREATED).body(resp))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping
    public ResponseEntity<Usuario> put(@Valid @RequestBody Usuario usuario){
        return usuarioService.atualizarUsuario(usuario)
                .map(resposta -> ResponseEntity.status(HttpStatus.OK)
                        .body(usuarioRepository.save(usuario)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        Optional<Usuario> usuario = usuarioRepository.findById(id);

        if(usuario.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        usuarioRepository.deleteById(id);
    }

}
