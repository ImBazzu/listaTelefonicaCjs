async function loginUsuario(login, senha) {
  try {
    const response = await fetch("http://backendListaTelefonica:8080/auth/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      credentials: "include",
      body: JSON.stringify({ login, senha })
    });

    if (!response.ok) {
      throw new Error("Usuário ou senha inválidos");
    }

    const data = await response.json();
    localStorage.setItem("accessToken", data);

    document.getElementById("mensagem").textContent = "✅ Login realizado com sucesso!";
    document.getElementById("mensagem").style.color = "lightgreen";

    // Redireciona para página inicial após 1 segundo
    setTimeout(() => {
      window.location.href = "/indexex/";
    }, 1000);

  } catch (error) {
    document.getElementById("mensagem").textContent = "❌ " + error.message;
    document.getElementById("mensagem").style.color = "red";
  }
}

// Captura evento do formulário
document.getElementById("login-form").addEventListener("submit", (e) => {
  e.preventDefault();
  const login = document.getElementById("campo-login").value;
  const senha = document.getElementById("campo-senha").value;
  loginUsuario(login, senha);
});
