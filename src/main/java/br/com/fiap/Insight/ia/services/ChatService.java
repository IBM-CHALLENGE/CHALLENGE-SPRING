package br.com.fiap.Insight.ia.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;
import com.theokanning.openai.image.CreateImageRequest;

import br.com.fiap.Insight.ia.models.Comando;
import br.com.fiap.Insight.ia.models.Insight;

@Service
public class ChatService {

    @Value("${openai.api.key}")
    private String API_KEY;
    
    public Insight gerarInsight(Comando comando){
        Insight insight = new Insight();
        insight.setAnuncio(comando.getAnuncio());

        OpenAiService openAiService = new OpenAiService(API_KEY);
        String conteudo = gerarConteudo(openAiService, comando);
        String imagem = gerarImagem(openAiService, conteudo);

        insight.setConteudo(conteudo);
        insight.setImagem(imagem);

        return insight;
    }

    private String gerarConteudo(OpenAiService service, Comando comando){
        String msgChatGpt = "Baseado na seguinte descrição: '" + comando.getConteudo() + "'. \n\n"
                            +"""
                                Crie as seguintes informações de um anúncio no formato: \n
                                - Título \n
                                - Descrição \n
                                - Palavras-Chave (separadas por vírgula) \n
                            """;

        CompletionRequest request = CompletionRequest.builder()
                                    .model("text-davinci-003")
                                    .prompt(msgChatGpt)
                                    .maxTokens(1200)
                                    .build();

        var choices = service.createCompletion(request).getChoices();
        var texto = choices.get(0).getText();
        return texto;
    }

    private String gerarImagem(OpenAiService service, String conteudo){
        String palavrasChave = conteudo.split("Palavras-Chave:")[1].trim();

        CreateImageRequest request = CreateImageRequest.builder()
                                        .prompt(palavrasChave)
                                        .n(1)
                                        .size("512x512")
                                        .responseFormat("url")
                                        .build();

        var url = service.createImage(request).getData().get(0).getUrl();
        return url;
    }
}
