package br.com.fiap.Insight.ia.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.fiap.Insight.ia.models.Anuncio;
import br.com.fiap.Insight.ia.models.Comando;
import br.com.fiap.Insight.ia.models.Insight;
import br.com.fiap.Insight.ia.models.Transacao;
import br.com.fiap.Insight.ia.models.Usuario;
import br.com.fiap.Insight.ia.repository.AnuncioRepository;
import br.com.fiap.Insight.ia.repository.ComandoRepository;
import br.com.fiap.Insight.ia.repository.InsightRepository;
import br.com.fiap.Insight.ia.repository.TransacaoRepository;
import br.com.fiap.Insight.ia.repository.UsuarioRepository;

@Configuration
@Profile("dev")
public class DatabaseSeeder implements CommandLineRunner{

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
    PasswordEncoder encoder;

    @Override
    public void run(String... args) throws Exception {
        
        Usuario usuario = new Usuario();
        usuario.setNome("Gustavo Balero");
        usuario.setEmail("rm93090@fiap.com.br");
        usuario.setSenha(encoder.encode("abc123"));
        usuario.setSaldo(18.0);
        usuarioRepository.save(usuario);

        Anuncio anuncio = new Anuncio();
        anuncio.setDescricao("Mecanica de Motos");
        anuncio.setUsuario(usuario);
        anuncioRepository.save(anuncio);

        Transacao transacao = new Transacao();
        transacao.setTitulo("Insight Gerado");
        transacao.setDescricao("Anúncio: " + anuncio.getDescricao());
        transacao.setValor(-2.0);
        transacao.setUsuario(usuario);
        transacaoRepository.save(transacao);

        Comando comando = new Comando();
        comando.setConteudo("Me gere um anúncio sobre uma mecanica de motos que está localizada em são paulo e oferece serviços acessiveis");
        comando.setAnuncio(anuncio);
        comandoRepository.save(comando);

        Insight insight = new Insight();
        insight.setConteudo("\nTítulo: Mecânica de Motos com Preços Acessíveis em São Paulo \n\nDescrição: A Mecânica de Motos em São Paulo oferece serviços e reparos com ótimos preços acessíveis. Confira nossos serviços e faça uma visita para constatar a qualidade dos nossos trabalhos. \n\nPalavras-Chave: Mecânica de Motos, Preços Acessíveis, São Paulo, Reparos, Serviços.");
        insight.setImagem("https://oaidalleapiprodscus.blob.core.windows.net/private/org-gUQRxh2DSPofklJyCUqh9Hia/user-lscft3WxLVXD40HhivFeI46G/img-8Fjo6gqmqn3jFKET50tErx8S.png?st=2023-09-10T18%3A48%3A31Z&se=2023-09-10T20%3A48%3A31Z&sp=r&sv=2021-08-06&sr=b&rscd=inline&rsct=image/png&skoid=6aaadede-4fb3-4698-a8f6-684d7786b067&sktid=a48cca56-e6da-484e-a814-9c849652bcb3&skt=2023-09-10T18%3A17%3A12Z&ske=2023-09-11T18%3A17%3A12Z&sks=b&skv=2021-08-06&sig=qwyqg%2Bl3hWR2eHXEhtgeFqOPv8ABiaWuMS1OwU6jR4I%3D");
        insight.setAnuncio(anuncio);
        insightRepository.save(insight);
    }
}
