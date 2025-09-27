# Imagem base Node.js
FROM node:18-alpine

# Cria diretório de trabalho
WORKDIR /app

# Copia arquivos para dentro da imagem
COPY . .

# Instala o "serve"
RUN npm install -g serve

# Expõe a porta 80
EXPOSE 80

# Comando que roda o servidor
CMD ["serve", "-l", "80", "."]
