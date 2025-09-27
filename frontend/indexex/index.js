// ---------------------------
// Função para obter termo de busca da URL
// ---------------------------
function getSearchTermFromURL() {
  const params = new URLSearchParams(window.location.search);
  return params.get("texto") || ""; // pega o valor de ?texto=algumaCoisa
}

// ---------------------------
// Função para pegar token
// ---------------------------
async function getAccessToken() {
  const token = localStorage.getItem("accessToken");
  if (!token) {
    throw new Error("Nenhum token encontrado. Faça login primeiro.");
  }
  return token;
}

// ---------------------------
// Função para fetch com token
// ---------------------------
async function fetchWithAuth(url, options = {}) {
  options.headers = {
    ...(options.headers || {}),
    "Content-Type": "application/json"
  };
  options.credentials = "include";

  let response = await fetch(url, options);

  if (response.status === 401) {
    console.error("Token expirado ou inválido. Redirecionando para login...");
    window.location.href = "login.html";
    return;
  }

  if (!response.ok) {
    throw new Error(`Erro na requisição: ${response.status}`);
  }

  return response.json();
}

// ---------------------------
// Função para carregar estabelecimentos
// ---------------------------
async function carregarEstabelecimentos(textoBusca = "") {
  try {
    const data = await fetchWithAuth("http://backendListaTelefonica:8080/empresa/buscar?texto=" + encodeURIComponent(textoBusca), {
      method: "GET"
    });

    const container = document.querySelector(".cards");
    container.innerHTML = ""; // limpa antes de adicionar

    data.conteudo.forEach(estabelecimento => {
      const card = document.createElement("div");
      card.classList.add("card");
      card.innerHTML = `
        <h3>${estabelecimento.nome}</h3>
        ${estabelecimento.telefones.map(tel => `<p>📞 ${tel.principal ? 'Principal' : 'Outro'}: ${tel.numero}</p>`).join('')}
        <p>${estabelecimento.endereco.logradouro}, ${estabelecimento.endereco.numero}</p>
        <p>${estabelecimento.endereco.bairro} - ${estabelecimento.endereco.cidade}/${estabelecimento.endereco.uf}</p>
      `;
      container.appendChild(card);
    });

  } catch (error) {
    console.error("Erro ao carregar estabelecimentos:", error);
    const container = document.querySelector(".cards");
    container.innerHTML = "<p style='color:red; text-align:center;'>Não foi possível carregar os estabelecimentos. Faça login novamente.</p>";
  }
}

// ---------------------------
// Executa ao carregar a página
// ---------------------------
document.addEventListener("DOMContentLoaded", () => {
  const termo = getSearchTermFromURL();
  carregarEstabelecimentos(termo);
});

// ---------------------------
// Função de busca via input
// ---------------------------
function buscarEstabelecimentos() {
  const termo = document.querySelector("#txtProcurar").value;

  // Atualiza a URL com o termo pesquisado (sem recarregar a página)
  const params = new URLSearchParams(window.location.search);
  params.set("texto", termo);
  window.history.replaceState({}, "", `${window.location.pathname}?${params}`);

  carregarEstabelecimentos(termo);
}
