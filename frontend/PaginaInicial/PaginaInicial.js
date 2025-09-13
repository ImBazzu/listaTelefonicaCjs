// Pega o token salvo no navegador
function obterToken() {
    return localStorage.getItem('token');
}

// Faz a requisição ao backend
function listarEmpresas() {
    const token = obterToken();

    return fetch("http://localhost:8080/empresa/buscar?texto=", {

        method: "GET",
        headers: {
            "Content-Type": "application/json",
        },
        credentials: 'include' // necessário se você usa allowCredentials(true)


    }).then(response => {
        if (!response.ok) {
            throw new Error("Erro: " + response.status);
        }

        return response.json();
    });
}

// Exemplo de uso ao clicar no botão
document.getElementById("btn").addEventListener("click", () => {
    listarEmpresas().then(empresas => {
        console.log(empresas); // Verifica os dados no console
        const ul = document.getElementById("lista");
        ul.innerHTML = ""; // limpa antes de renderizar

        empresas.forEach(e => {
            const li = document.createElement("li");
            li.textContent = e.nome; // ajuste conforme o campo do JSON
            ul.appendChild(li);
        });
    }).catch(err => console.error(err));
});