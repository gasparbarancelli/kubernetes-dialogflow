package br.com.viasoft.kubernetes;

import br.com.viasoft.kubernetes.commands.ExecuteCommand;
import com.google.cloud.dialogflow.v2.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class KubernetesServiceImpl implements KubernetesService {

    private final List<ExecuteCommand> executeCommandList;

    private static final AtomicInteger SESSION_ID = new AtomicInteger(0);

    @Autowired
    public KubernetesServiceImpl(List<ExecuteCommand> executeCommandList) {
        this.executeCommandList = executeCommandList;
    }

    @Override
    public void resetContext() {
        KubernetesServiceImpl.SESSION_ID.incrementAndGet();
    }

    @Override
    public String execute(String msg) {
        try (SessionsClient sessionsClient = SessionsClient.create()) {
            SessionName session = SessionName.of("viasoft-nimitz", String.valueOf(SESSION_ID.get()));

            TextInput.Builder textInput = TextInput.newBuilder().setText(msg).setLanguageCode("pt-BR");
            QueryInput queryInput = QueryInput.newBuilder().setText(textInput).build();
            DetectIntentResponse response = sessionsClient.detectIntent(session, queryInput);
            QueryResult queryResult = response.getQueryResult();

            String servico = queryResult.getParameters().getFieldsMap().get("servico").getStringValue().toLowerCase();

            String intentName = queryResult.getIntent().getDisplayName();
            System.out.println("Comandos: " + executeCommandList.size());
            System.out.println("Intent: " + intentName);

            return executeCommandList
                    .stream()
                    .filter(f -> f.isIntent(intentName))
                    .findAny()
                    .map(f -> {
                        try {
                            return f.execute(servico, queryResult);
                        } catch (Exception e) {
                            return "Comando foi encontrado mas algum erro ocorreu ao executar a ação";
                        }
                    }).orElse("Comando não foi encontrado");
        } catch (Exception e) {
            return "Comando não foi reconhecido";
        }
    }

}
