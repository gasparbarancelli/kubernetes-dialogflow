package br.com.viasoft.kubernetes.commands;

import com.google.cloud.dialogflow.v2.QueryResult;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class Memoria implements ExecuteCommand{

    @Override
    public String execute(String service, QueryResult queryResult) throws Exception {
        String commandMemory = "kubectl top pods";
        Process process = Runtime.getRuntime().exec(commandMemory);
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
        List<String> stringList = stdInput.lines().filter(l -> l.startsWith(service)).collect(Collectors.toList());
        int totalMemoria = 0;
        for (String linha : stringList) {
            String[] linhaQuebrada = linha.trim().split(" ");
            totalMemoria += Integer.parseInt(linhaQuebrada[linhaQuebrada.length - 1].replaceAll("\\D+", ""));
        }
        return "O serviço " + service + " está consumindo um total de " + totalMemoria + " mega bytes de memória";
    }

    @Override
    public Boolean isIntent(String intentName) {
        return intentName.equals("Memoria") || intentName.equals("Memoria servico");
    }
}
