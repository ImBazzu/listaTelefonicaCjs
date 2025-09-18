# Imagem base Node.js
FROM node:18-alpine

# Cria diretório de trabalho
WORKDIR /app

# Copia arquivos para dentro da imagem
COPY . .

# Instala o "serve"
RUN npm install -g serve

# Expõe a porta 5500
EXPOSE 5500

# Comando que roda o servidor
CMD ["serve", "-l", "5500", "."]
