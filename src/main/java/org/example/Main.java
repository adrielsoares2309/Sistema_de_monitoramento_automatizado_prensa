package org.example;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class Main {

    static String broker = "tcp://broker.hivemq.com:1883";
    static String topicTemp = "senai/adriel/temperatura";
    static String topicPres = "senai/adriel/pressao";

    static double ultimaTemp = 0;
    static double ultimaPres = 0;

    static boolean tempRecebida = false;
    static boolean presRecebida = false;

    public static void main(String[] args) throws Exception {

        MqttClient client = new MqttClient(broker, "JavaCerebro");

        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);

        System.out.println("Conectando ao Broker...");
        client.connect(options);
        System.out.println("Conectado com sucesso!");

        client.subscribe(new String[]{topicTemp, topicPres});

        client.setCallback(new MqttCallback() {

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {

                String valor = new String(message.getPayload());

                if (topic.equals(topicTemp)) {
                    ultimaTemp = Double.parseDouble(valor);
                    tempRecebida = true;
                }

                if (topic.equals(topicPres)) {
                    ultimaPres = Double.parseDouble(valor);
                    presRecebida = true;
                }

                // Só processa quando tiver os dois dados
                if (tempRecebida && presRecebida) {
                    processarLogica(client);
                    tempRecebida = false;
                    presRecebida = false;
                }
            }

            @Override
            public void connectionLost(Throwable cause) {
                System.out.println("Conexão perdida!");
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
            }
        });
    }

    private static void processarLogica(MqttClient client) throws MqttException {

        String corLed = "";
        String msgLcd = "";

        if (ultimaTemp > 60 || ultimaPres > 80) {
            corLed = "VERMELHO";
            msgLcd = "DESLIGUE!";
        }
        else if ((ultimaTemp >= 45 && ultimaTemp <= 60) ||
                (ultimaPres >= 50 && ultimaPres <= 80)) {
            corLed = "AMARELO";
            msgLcd = "CUIDADO!";
        }
        else {
            corLed = "VERDE";
            msgLcd = "ESTAVEL";
        }

        client.publish("senai/adriel/comando/led",
                new MqttMessage(corLed.getBytes()));

        client.publish("senai/adriel/comando/lcd",
                new MqttMessage(msgLcd.getBytes()));

        System.out.println("Comando enviado -> " + msgLcd +
                " | Temp: " + ultimaTemp +
                " | Pressao: " + ultimaPres + "%");
    }
}