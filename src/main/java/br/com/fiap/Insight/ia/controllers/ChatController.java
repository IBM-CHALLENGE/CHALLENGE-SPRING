package br.com.fiap.Insight.ia.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.Insight.ia.exception.RestNotAuthorizedException;
import br.com.fiap.Insight.ia.exception.RestNotFoundException;
import br.com.fiap.Insight.ia.exception.RestValueInvalidException;
import br.com.fiap.Insight.ia.models.Anuncio;
import br.com.fiap.Insight.ia.models.Comando;
import br.com.fiap.Insight.ia.models.IChat;
import br.com.fiap.Insight.ia.models.Insight;
import br.com.fiap.Insight.ia.models.Status;
import br.com.fiap.Insight.ia.models.Transacao;
import br.com.fiap.Insight.ia.models.Usuario;
import br.com.fiap.Insight.ia.repository.AnuncioRepository;
import br.com.fiap.Insight.ia.repository.ComandoRepository;
import br.com.fiap.Insight.ia.repository.InsightRepository;
import br.com.fiap.Insight.ia.repository.TransacaoRepository;
import br.com.fiap.Insight.ia.repository.UsuarioRepository;
import br.com.fiap.Insight.ia.services.AuthService;
import br.com.fiap.Insight.ia.services.ChatService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    AnuncioRepository anuncioRepository;

    @Autowired
    TransacaoRepository transacaoRepository;

    @Autowired
    ComandoRepository comandoRepository;

    @Autowired
    InsightRepository insightRepository;

    @Autowired
    ChatService chatService;

    AuthService authService = new AuthService();

    @PostMapping("{idAnuncio}")
    public ResponseEntity<Insight> create(@PathVariable Integer idAnuncio, @RequestBody @Valid Comando comando) {
        Usuario usuarioLogado = authService.getUsuarioLogado(usuarioRepository);
        Anuncio anuncio = getAnuncio(idAnuncio);

        if(anuncio.getUsuario().getId() != usuarioLogado.getId()){
            throw new RestNotAuthorizedException("Não é possivel cadastrar comando para outro usuario");
        }

        if(usuarioLogado.getSaldo() < 2.0){
            throw new RestValueInvalidException("Saldo insuficiente para gerar um insight");
        }

        Insight insight = chatService.gerarInsight(comando);
        Transacao transacao = Transacao
                                .builder()
                                .usuario(usuarioLogado)
                                .titulo("Insight Gerado")
                                .descricao("Anúncio: " + anuncio.getDescricao())
                                .valor(-2.0)
                                .build();
        usuarioLogado.setSaldo(usuarioLogado.getSaldo() - 2.0);

        insightRepository.save(insight);
        comandoRepository.save(comando);
        transacaoRepository.save(transacao);
        usuarioRepository.save(usuarioLogado);

        return ResponseEntity.status(201).body(insight);
    }

    @GetMapping("{idAnuncio}")
    public ResponseEntity<ArrayList<IChat>> listByAnuncio(@PathVariable Integer idAnuncio){
        Usuario usuarioLogado = authService.getUsuarioLogado(usuarioRepository);
        Anuncio anuncio = getAnuncio(idAnuncio);

        if(anuncio.getUsuario().getId() != usuarioLogado.getId()){
            throw new RestNotAuthorizedException("Não é possivel listar o chat de outro usuario");
        }

        List<Comando> comandos = comandoRepository.findByAnuncioIdOrderById(idAnuncio);
        List<Insight> insights = insightRepository.findByAnuncioIdOrderById(idAnuncio);

        ArrayList<IChat> chat = new ArrayList<IChat>();
        for (int i = 0; i < comandos.size(); i++) {
            chat.add(comandos.get(i));
            chat.add(insights.get(i));
        }

        return ResponseEntity.ok(chat);
    }

    private Anuncio getAnuncio(Integer id) {
        return anuncioRepository
                .findById(id)
                .filter(anuncio -> anuncio.getStatus().equals(Status.ATIVO))
                .orElseThrow(() -> new RestNotFoundException("Anuncio não encontrado"));
    }
}
