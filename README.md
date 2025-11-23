

# 🪀 ReStart.AI – Recolocação Profissional Inteligente

📌 Sobre o Projeto

A ReStart.AI é uma aplicação pensada para ajudar pessoas a se realocarem
no mercado de trabalho em um cenário de mudanças rápidas trazidas pela
IA.
Em vez de começar uma carreira do zero, o sistema analisa as habilidades
que você já possui e indica caminhos de carreira compatíveis, com vagas
e cargos alinhados ao seu perfil.

Com poucos cliques, você cadastra seu currículo, a plataforma analisa
seu perfil e entrega recomendações inteligentes de áreas e
oportunidades.

------------------------------------------------------------------------

## 🔗 Links Importantes

- 🎥 Video Demonstrativo: **[Assista ao video](https://)**  
- 🌐 Deploy (aplicação online): **[Acesse a ReStart.AI](https://restart-rm558191.azurewebsites.net/)**  
- 📚 Documentação da API (Swagger/OpenAPI): **[Ver documentação](https://restart-rm558191.azurewebsites.net/swagger-ui/index.html)**

------------------------------------------------------------------------

🚀 Estrutura de Deploy

O projeto utiliza infraestrutura no Microsoft Azure, dividida em dois
scripts principais:

-   Provisionamento do Banco de Dados
-   Provisionamento da Infraestrutura da Aplicação

🗄️ Script de Criação do Banco de Dados

    #!/bin/bash
     
    # Variáveis de configuração

    RG="rg-restart"

    LOCATION="brazilsouth"

    SERVER_NAME="sqlserver-restart-rm558191"

    USERNAME="admsql"

    PASSWORD="Fiap@2tdsvms"

    DBNAME="restartdb"
     
    az group create --name $RG --location $LOCATION
     
    az sql server create -l $LOCATION -g $RG -n $SERVER_NAME -u $USERNAME -p $PASSWORD --enable-public-network true
     
    az sql db create -g $RG -s $SERVER_NAME -n $DBNAME --service-objective Basic --backup-storage-redundancy Local --zone-redundant false
     
    az sql server firewall-rule create -g $RG -s $SERVER_NAME -n AllowAll --start-ip-address 0.0.0.0 --end-ip-address 255.255.255.255
     
    echo "Infraestrutura do banco de dados criada com sucesso!"
    echo "O banco '$DBNAME' está pronto e vazio para o Flyway gerenciar o schema."

------------------------------------------------------------------------

🌐 Script de Criação da Infraestrutura da Aplicação

    #!/bin/bash
     
    # --- Variáveis de Configuração da Aplicação ---

    export RESOURCE_GROUP_NAME="rg-restart"

    export WEBAPP_NAME="restart-rm558191"

    export APP_SERVICE_PLAN="planRestart"

    export LOCATION="brazilsouth"

    export RUNTIME="JAVA:17-java17"
     
    # --- Variáveis do Banco de Dados ---

    export DB_SERVER_NAME="sqlserver-restart-rm558191"

    export DB_NAME="restartdb"

    export DB_USER="admsql"

    export DB_PASSWORD="Fiap@2tdsvms"
     
    export SPRING_AI_OPENAI_API_KEY="chave do chatgpt"
     
    # Construção da URL JDBC dinamicamente

    export JDBC_URL="jdbc:sqlserver://${DB_SERVER_NAME}.database.windows.net:1433;database=${DB_NAME};encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;"
     
    echo "Criação da infraestrutura no Azure..."
     
    az appservice plan create \
    --name "$APP_SERVICE_PLAN" \
    --resource-group "$RESOURCE_GROUP_NAME" \
    --location "$LOCATION" \
    --sku F1 \
    --is-linux
     
    az webapp create \
    --name "$WEBAPP_NAME" \
    --resource-group "$RESOURCE_GROUP_NAME" \
    --plan "$APP_SERVICE_PLAN" \
    --runtime "$RUNTIME"
     
    # Habilita a autenticação Básica (SCM) para permitir o deploy pelo pipeline

    echo "Habilitando credenciais de deploy SCM..."

    az resource update \
    --resource-group "$RESOURCE_GROUP_NAME" \
    --namespace Microsoft.Web \
    --resource-type basicPublishingCredentialsPolicies \
    --name scm \
    --parent sites/"$WEBAPP_NAME" \
    --set properties.allow=true
     
    az webapp config appsettings set \
    --name "$WEBAPP_NAME" \
    --resource-group "$RESOURCE_GROUP_NAME" \
    --settings \
    SPRING_DATASOURCE_USERNAME="$DB_USER" \
    SPRING_DATASOURCE_PASSWORD="$DB_PASSWORD" \
    SPRING_DATASOURCE_URL="$JDBC_URL" \
    OPENAI_API_KEY="$SPRING_AI_OPENAI_API_KEY"
     
    az webapp restart \
    --name "$WEBAPP_NAME" \
    --resource-group "$RESOURCE_GROUP_NAME"
     
    echo "Criação e configuração concluídas com sucesso!"

------------------------------------------------------------------------
