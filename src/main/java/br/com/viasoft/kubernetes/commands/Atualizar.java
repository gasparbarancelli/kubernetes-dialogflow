package br.com.viasoft.kubernetes.commands;

import com.google.cloud.dialogflow.v2.QueryResult;
import org.springframework.stereotype.Service;

@Service
public class Atualizar implements ExecuteCommand {

    @Override
    public String execute(String service, QueryResult queryResult) throws Exception {
        Double versao = queryResult.getParameters().getFieldsMap().get("versao").getNumberValue();
        if (versao > 0) {
            String commandUpdate = "kubectl set image deployment/"+service+"-deployment "+service+"=gcr.io/viasoft-nimitz/"+service+":" + versao.intValue();
            Runtime.getRuntime().exec(commandUpdate);
            return "Atualizando o serviço " + service + " para a versão " + versao.intValue();
        } else {
            String commandUpdate = "kubectl set image deployment/"+service+"-deployment "+service+"=gcr.io/viasoft-nimitz/"+service+":latest";
            Runtime.getRuntime().exec(commandUpdate);
            return "Atualizando o serviço " + service + " para a última versão";
        }
    }

    @Override
    public Boolean isIntent(String intentName) {
        return intentName.equals("Atualizar") || intentName.equals("Atualizar servico");
    }
}
