package br.com.viasoft.kubernetes;

import com.google.cloud.dialogflow.v2.*;
import com.google.protobuf.ByteString;
import org.apache.tomcat.jni.Proc;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
// [END dialogflow_import_libraries]


/**
 * DialogFlow API Detect Intent sample with audio files.
 */
/*
public class DetectIntentAudio {

  // [START dialogflow_detect_intent_audio]
  */
/**
   * Returns the result of detect intent with an audio file as input.
   *
   * Using the same `session_id` between requests allows continuation of the conversation.
   * @param projectId Project/Agent Id.
   * @param audioFilePath Path to the audio file.
   * @param sessionId Identifier of the DetectIntent session.
   * @param languageCode Language code of the query.
   *//*

  public static String detectIntentAudio(String projectId, String audioFilePath, String sessionId,
      String languageCode)
      throws Exception {
    // Instantiates a client
    try (SessionsClient sessionsClient = SessionsClient.create()) {
      // Set the session name using the sessionId (UUID) and projectID (my-project-id)
      SessionName session = SessionName.of(projectId, sessionId);
      */
/*System.out.println("Session Path: " + session.toString());

      // Note: hard coding audioEncoding and sampleRateHertz for simplicity.
      // Audio encoding of the audio content sent in the query request.
      AudioEncoding audioEncoding = AudioEncoding.AUDIO_ENCODING_LINEAR_16;
      int sampleRateHertz = 48000;

      // Instructs the speech recognizer how to process the audio content.
      InputAudioConfig inputAudioConfig = InputAudioConfig.newBuilder()
          .setAudioEncoding(audioEncoding) // audioEncoding = AudioEncoding.AUDIO_ENCODING_LINEAR_16
          .setLanguageCode(languageCode) // languageCode = "en-US"
          .setSampleRateHertz(sampleRateHertz) // sampleRateHertz = 16000
          .build();

      // Build the query with the InputAudioConfig
      QueryInput queryInput = QueryInput.newBuilder().setAudioConfig(inputAudioConfig).build();

      // Read the bytes from the audio file
      byte[] inputAudio = Files.readAllBytes(Paths.get(audioFilePath));

      // Build the DetectIntentRequest
      DetectIntentRequest request = DetectIntentRequest.newBuilder()
          .setSession(session.toString())
          .setQueryInput(queryInput)
          .setInputAudio(ByteString.copyFrom(inputAudio))
          .build();

      // Performs the detect intent request
      DetectIntentResponse response = sessionsClient.detectIntent(request);*//*


      TextInput.Builder textInput = TextInput.newBuilder().setText("escale a danfe em 2").setLanguageCode(languageCode);

      // Build the query with the TextInput
      QueryInput queryInput = QueryInput.newBuilder().setText(textInput).build();

      // Performs the detect intent request
      DetectIntentResponse response = sessionsClient.detectIntent(session, queryInput);

      // Display the query result
      QueryResult queryResult = response.getQueryResult();

      String servico = queryResult.getParameters().getFieldsMap().get("servico").getStringValue().toLowerCase();

      String intentName = queryResult.getIntent().getDisplayName();
      if (intentName.equals("Quantos pods estão rodando")) {
        String commandGetPods = "kubectl get deployment "+servico+"-deployment -o jsonpath='{.spec.replicas}'";
        Runtime rt = Runtime.getRuntime();
        Process process = rt.exec(commandGetPods);
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
        int quantidade = Integer.parseInt(stdInput.readLine().replaceAll("\\D+", ""));
        if (quantidade == 0) {
          return "Nenhum serviço está rodando";
        } else if (quantidade == 1) {
          return "Um uníco serviço está rodando";
        } else  {
          return quantidade + " serviços estão rodando";
        }
      } else if (intentName.equals("Escale os servicos") || intentName.equals("Escale")) {
        Double quantidade = queryResult.getParameters().getFieldsMap().get("quantidade").getNumberValue();
        String commandScalePods = "kubectl scale --replicas="+quantidade.intValue()+" deployment/"+servico+"-deployment";
        Runtime.getRuntime().exec(commandScalePods);
        return "Escalando os " + quantidade.intValue() + " serviços de " + servico;
      }

      return "Comando não foi reconhecido";
    } catch (Exception e) {
      return "Comando não foi reconhecido";
    }
  }
  // [END dialogflow_detect_intent_audio]

  // [START run_application]
  */
/*public static void main(String[] args) throws Exception {
    String projectId = "viasoft-nimitz";
    String sessionId = UUID.randomUUID().toString();
    String languageCode = "pt-BR";

    detectIntentAudio(projectId, "/home/gaspar/kubernetes/src/main/resources/danfe.wav", sessionId, languageCode);
  }*//*

  // [END run_application]
}*/
