package br.com.viasoft.kubernetes.commands;

import com.google.cloud.dialogflow.v2.QueryResult;
import org.springframework.stereotype.Service;

@Service
public class EscaleServicos implements ExecuteCommand {

    @Override
    public String execute(String service, QueryResult queryResult) throws Exception {
        Double quantidade = queryResult.getParameters().getFieldsMap().get("quantidade").getNumberValue();
        String commandScalePods = "kubectl scale --replicas="+quantidade.intValue()+" deployment/"+service+"-deployment";
        Runtime.getRuntime().exec(commandScalePods);
        return "Escalando os " + quantidade.intValue() + " serviços de " + service;
    }

    @Override
    public Boolean isIntent(String intentName) {
        return intentName.equals("Escale os servicos") || intentName.equals("Escale");
    }
}
