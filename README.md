# 🏭 Sistema Automatizado para Monitoramento de Caldeira

Este projeto é um sistema de controle inteligente desenvolvido em **Java** que utiliza o protocolo **MQTT** para monitorar e gerenciar a segurança de uma caldeira industrial em tempo real.

O software atua como uma "central de inteligência", processando dados de sensores e enviando comandos automáticos para atuadores (LEDs e Displays).

## 🚀 Funcionalidades

- **Monitoramento Duplo:** Assina e processa simultaneamente dados de temperatura e pressão.
- **Lógica de Decisão:**
  - 🔴 **CRÍTICO:** (Temp > 60°C ou Pres > 80%) → Comando: **DESLIGUE!** (LED Vermelho).
  - 🟡 **ALERTA:** (Temp 45-60°C ou Pres 50-80%) → Comando: **CUIDADO!** (LED Amarelo).
  - 🟢 **ESTÁVEL:** (Valores abaixo disso) → Comando: **ESTÁVEL** (LED Verde).
- **Sincronização de Dados:** A lógica só é processada após garantir que ambos os sensores enviaram leituras atualizadas.
- **Reconexão Automática:** Configurado para manter a conexão ativa com o broker MQTT mesmo em caso de instabilidade.

## 🛠️ Tecnologias e Bibliotecas

* **Linguagem:** Java 22
* **Protocolo:** MQTT v3.1.1
* **Biblioteca:** [Eclipse Paho MQTT Client](https://www.eclipse.org/paho/index.php?page=clients/java/index.php)
* **Gerenciador de Dependências:** Maven
* **Broker:** HiveMQ (Público)

## 📡 Tópicos de Comunicação (MQTT)

| Tipo | Tópico | Função |
| :--- | :--- | :--- |
| **Sub** | `senai/adriel/temperatura` | Recebe o valor da temperatura (°C) |
| **Sub** | `senai/adriel/pressao` | Recebe o valor da pressão (%) |
| **Pub** | `senai/adriel/comando/led` | Envia a cor do LED (VERDE, AMARELO, VERMELHO) |
| **Pub** | `senai/adriel/comando/lcd` | Envia a mensagem de texto para o display |

## 🔧 Como Executar

1.  Certifique-se de ter o **JDK 22** e o **Maven** instalados.
2.  Clone este repositório:
    ```bash
    git clone [https://github.com/seu-usuario/projeto-caldeira.git](https://github.com/seu-usuario/projeto-caldeira.git)
    ```
3.  Abra o projeto na sua IDE (IntelliJ, Eclipse ou VS Code).
4.  Aguarde o Maven baixar a dependência `org.eclipse.paho.client.mqttv3`.
5.  Execute a classe `Main.java`.

---
Desenvolvido como projeto prático para o **SENAI**.
